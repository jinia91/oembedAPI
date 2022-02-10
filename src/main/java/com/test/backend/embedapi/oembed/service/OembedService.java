package com.test.backend.embedapi.oembed.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.test.backend.embedapi.oembed.Url;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class OembedService {

    private final RestTemplate restTemplate;

    /**
    * oEmbed를 가져오는 메인 서비스 메서드
    * @Return provider에 따라 비정형데이터를 받아오기때문에, 객체 포메팅이 힘들어 JsonElement로 반환
    * */
    @Cacheable(value = "oEmbedCaching" , key = "{#oEmbedUrl}")
    public JsonElement getOembed(Url oEmbedUrl) {
        log.info("now {} is caching for 10m", oEmbedUrl.getUrl());
        String oembed = getOembedByProvider(oEmbedUrl.getUrl());
        return JsonParser.parseString(oembed);
   }

   /*
   *  외부 서버(provider)에 oEmbed를 요청하는 메인 비지니스 로직
   * */
    private String getOembedByProvider(String url) {
        log.info("request to provider for oEmbed : {} ", url);
        ResponseEntity<String> response;
        try {
            response = restTemplate.getForEntity(url, String.class);
        } catch (RuntimeException e){
            throw new IllegalArgumentException(String.format("Provider consider that URL{%s} is not valid",url));
        }
        log.info("provider response success");
        return response.getBody();
    }
}
