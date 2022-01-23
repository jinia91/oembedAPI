package com.test.backend.embedapi.oembed.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class OembedService {

    private final OembedUrlFactory oembedUrlFactory;

    public JsonElement getOembed(String url) {

        url = oembedUrlFactory.createOembedUrl(url);
        System.out.println("url = " + url);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        return JsonParser.parseString(response.getBody());
   }
}
