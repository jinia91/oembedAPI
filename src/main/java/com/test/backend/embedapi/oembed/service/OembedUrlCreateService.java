package com.test.backend.embedapi.oembed.service;

import com.test.backend.embedapi.oembed.Url;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/*
*  프로바이더별 oEmbed 요청 url을 동적으로 만들어주는 객체
* */
@Component
@RequiredArgsConstructor
@Slf4j
public class OembedUrlCreateService {

    private final ProviderListHandler providerListHandler;

    /*
     * 요청 url을 프로바이더별 oembed url로 변환하는 로직
     * */
    public Url createOembedUrl(Url url) {
        List<String> providerEndpointUrlImmutableList = providerListHandler.getProviderEndpointUrlImmutableList();
        String host = getHostFrom(url.getUrl());
        for(String endpointUrl :providerEndpointUrlImmutableList){
            if(endpointUrl.contains(host)){
                if(endpointUrl.contains("oembed.")){
                    if(endpointUrl.contains("{format}")) endpointUrl = endpointUrl.replace("{format}","json");
                    return new Url(endpointUrl + "?url=" + url.getUrl());
                }
                return new Url(endpointUrl + "?format=json&url=" + url.getUrl());
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
