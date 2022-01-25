package com.test.backend.embedapi.oembed.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static java.util.Objects.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class OembedService {

    private final OembedUrlFactory oembedUrlFactory;

    /*
    * oEmbed를 가져오는 메인 서비스 메서드
    * */
    @Cacheable(value = "oEmbedCaching" , key = "{#url}")
    public JsonElement getOembed(String url) {

        log.info("now {} is caching for 10m", url);
        url = oembedUrlFactory.createOembedUrl(url);
        ResponseEntity<String> response = getOembedByProvider(url);
        return JsonParser.parseString(requireNonNull(response.getBody()));
   }

   /*
   *  외부 서버(provider)에 oEmbed를 요청하는 메인 비지니스 로직
   * */
    private ResponseEntity<String> getOembedByProvider(String url) {
        log.info("request to provider for oEmbed : {} ", url);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response;
        try {
            response = restTemplate.getForEntity(url, String.class);
        } catch (RuntimeException e){
            throw new IllegalArgumentException(String.format("Provider consider that URL{%s} is not valid",url));
        }
        log.info("provider response success");
        return response;
    }

    /*
    *   url 기본 유효성 검사
    * */
    public void checkParameterUrl(String url) {
        if(url.equals(" ") || url.equals("")){
            throw new IllegalArgumentException("no valid URL parameter");
        }
    }
}
