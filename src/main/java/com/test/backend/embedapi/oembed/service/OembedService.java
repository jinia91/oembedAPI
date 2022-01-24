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

    @Cacheable(value = "oEmbedCaching" , key = "{#url}")
    public JsonElement getOembed(String url) {

        log.info("now {} is caching for 10m", url);
        url = oembedUrlFactory.createOembedUrl(url);
        ResponseEntity<String> response = getOembedByProvider(url);
        return JsonParser.parseString(requireNonNull(response.getBody()));
   }

    private ResponseEntity<String> getOembedByProvider(String url) {
        log.info("request to provider for oEmbed : {} ", url);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response;
        try {
            response = restTemplate.getForEntity(url, String.class);
        } catch (RuntimeException e){
            throw new IllegalArgumentException("Provider consider that URL{} is not valid");
        }
        log.info("provider response success");
        return response;
    }

    public void checkParameterUrl(String url) {
        if(url.equals(" ") || url.equals("")){
            throw new IllegalArgumentException("no valid URL parameter");
        }
    }
}
