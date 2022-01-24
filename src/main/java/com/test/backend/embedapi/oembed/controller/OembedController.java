package com.test.backend.embedapi.oembed.controller;

import com.google.gson.JsonElement;
import com.test.backend.embedapi.oembed.service.OembedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class OembedController {

    private final OembedService oembedService;

    @GetMapping("/v1/oembed")
    public ResponseEntity<JsonElement> getOembed(@RequestParam @Valid String url){
        System.out.println("url = " + url);
        return ResponseEntity.ok(oembedService.getOembed(url));
    }
}
