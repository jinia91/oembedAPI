package com.test.backend.embedapi.oembed.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class OembedUrlFactory {

    static final List<String> providerEndpointUrlList = new ArrayList<>();

    @PostConstruct
    private void buildProviderMap(){
        JsonArray providerList =
                JsonParser.parseString(getOembedProviderList().getBody())
                .getAsJsonArray();

        for(JsonElement provider :providerList){
            String endPoint =
                    provider.getAsJsonObject().get("endpoints")
                            .getAsJsonArray().get(0).getAsJsonObject().get("url")
                            .toString();
            providerEndpointUrlList
                    .add(endPoint.substring(1,endPoint.length()-1));
        }
    }

    private ResponseEntity<String> getOembedProviderList() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity("https://oembed.com/providers.json", String.class);
    }

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

    private static String getHostFrom(String url) {
        return url.split("//")[1].split("/")[0];
    }
}
