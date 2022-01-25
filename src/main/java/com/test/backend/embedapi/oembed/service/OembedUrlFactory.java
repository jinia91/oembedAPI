package com.test.backend.embedapi.oembed.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.*;

/*
*  프로바이더별 oEmbed 요청 url을 동적으로 만들어주는 객체
* */
@Component
@Slf4j
public class OembedUrlFactory {

    static final List<String> providerEndpointUrlList = new ArrayList<>();

    /*
    * 프로바이더별 oembed 스키마를 oembed 공식 서버에 요청, 앱 구동시기에 받아와서 저장하는 메서드
    * */
    @PostConstruct
    private void buildProviderList(){
        log.info("ProviderListBuild");
        JsonArray providerList =
                JsonParser.parseString(requireNonNull(getOembedProviderList().getBody()))
                .getAsJsonArray();

        for(JsonElement provider :providerList){
            String endPoint =
                    provider.getAsJsonObject().get("endpoints")
                            .getAsJsonArray().get(0).getAsJsonObject().get("url")
                            .toString();
            providerEndpointUrlList
                    .add(endPoint.substring(1,endPoint.length()-1));
        }
        log.info("List building success");
    }

    /*
    *   외부서버에 oembed 프로바이더 리스트 요청
    * */
    private ResponseEntity<String> getOembedProviderList() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity("https://oembed.com/providers.json", String.class);
    }

    /*
     * 요청 url을 프로바이더별 oembed url로 변환하는 로직
     * */
    public String createOembedUrl(String url) {
        String host = getHostFrom(url);
        for(String endpointUrl :providerEndpointUrlList){
            if(endpointUrl.contains(host)){
                if(endpointUrl.contains("oembed.")){
                    if(endpointUrl.contains("{format}")) endpointUrl = endpointUrl.replace("{format}","json");
                    return endpointUrl + "?url=" + url;
                }
                return endpointUrl + "?format=json&url=" + url;
            }
        }
        throw new IllegalArgumentException("cant support provider");
    }

    /*
    * url 프로바이더 파싱
    * */
    private static String getHostFrom(String url) {
        String host = null;
        try {
            host = url.split("//")[1].split("/")[0];
        } catch (RuntimeException e){
            throw new IllegalArgumentException("no valid provider");
        }
        return host;
    }
}
