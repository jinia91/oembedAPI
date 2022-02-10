package com.test.backend.embedapi.oembed.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * @ThreadSafe 방어적복사
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ProviderListHandler {

    private final RestTemplate restTemplate;
    static List<String> providerEndpointUrlList;

    /*
     * 프로바이더별 oembed 스키마를 oembed 공식 서버에 요청, 앱 구동시기에 받아와서 저장하는 메서드
     * */
    @PostConstruct
    private void buildProviderList(){
        log.info("ProviderListBuild");

        ArrayList<String> tmpList = new ArrayList<>();
        JsonArray providerList =
                JsonParser.parseString(requireNonNull(getOembedProviderList().getBody()))
                        .getAsJsonArray();

        for(JsonElement provider :providerList){
            String endPoint =
                    provider.getAsJsonObject().get("endpoints")
                            .getAsJsonArray().get(0).getAsJsonObject().get("url")
                            .toString();
            tmpList
                    .add(endPoint.substring(1,endPoint.length()-1));
        }
        providerEndpointUrlList = List.copyOf(tmpList);
        log.info("List building success");
    }

    /**
     * @TreadSafe
     * @Return 방어적복사를 한 불변객체 List로 반환
     */
    public List<String> getProviderEndpointUrlImmutableList() {
        return providerEndpointUrlList;
    }

    /*
     *   외부서버에 oembed 프로바이더 리스트 요청
     * */
    private ResponseEntity<String> getOembedProviderList() {
        return restTemplate.getForEntity("https://oembed.com/providers.json", String.class);
    }

}
