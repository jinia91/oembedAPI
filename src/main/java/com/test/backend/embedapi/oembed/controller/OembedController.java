package com.test.backend.embedapi.oembed.controller;

import com.google.gson.JsonElement;
import com.test.backend.embedapi.oembed.Url;
import com.test.backend.embedapi.oembed.service.OembedService;
import com.test.backend.embedapi.oembed.service.OembedUrlCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://3.38.36.238:3000/")
public class OembedController {

    private final OembedService oembedService;
    private final OembedUrlCreateService oembedUrlCreateService;

    @GetMapping("/v1/oembed")
    public ResponseEntity<JsonElement> getOembed(@ModelAttribute Url url){
        checkParameterUrl(url);
        Url oembedUrl = oembedUrlCreateService.createOembedUrl(url);
        JsonElement oembed = oembedService.getOembed(oembedUrl);
        return ResponseEntity.ok(oembed);
    }

    /*
     *   url 기본 유효성 검사
     * */
    public void checkParameterUrl(Url url) {
        if(url.getUrl().equals(" ") || url.getUrl().equals("")){
            throw new IllegalArgumentException("no valid URL parameter");
        }
    }
}
