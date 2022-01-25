package com.test.backend.embedapi.oembed.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class OembedControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    private static final String YOUTUBE_URL_SAMPLE = "https://www.youtube.com/watch?v=dBD54EZIrZo";
    private static final String INSTAGRAM_URL_SAMPLE = "https://www.instagram.com/p/BUawPlPF_Rx/";
    private static final String TWITTER_URL_SAMPLE = "https://twitter.com/hellopolicy/status/867177144815804416";
    private static final String VIMEO_URL_SAMPLE = "https://vimeo.com/20097015";

    @Test
    public void oembedUrl공백요청실패테스트() throws Exception {
        // given
        // when
        ResultActions perform = mockMvc.perform(
                get("/v1/oembed?url="));
        // then
        perform.andExpect(status().is4xxClientError())
                .andExpect(jsonPath("message")
                        .value("no valid URL parameter"));
    }

    @Test
    public void 유튜브oembedUrl요청성공테스트() throws Exception {
        // given
        // when
        ResultActions perform = mockMvc.perform(
                get(String.format("/v1/oembed?url=%s", YOUTUBE_URL_SAMPLE)));
        // then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("title")
                        .value("언제 어디서나! 핑크퐁 BEST 모음 80분 | 차에서 듣는 동요 | 아기상어, 상어가족 외 70곡 | + 모음집 | 핑크퐁! 인기동요"));
    }

    @Test
    public void 유튜브oembedUrl실패테스트() throws Exception {
        // given
        // when
        ResultActions perform = mockMvc.perform(
                get(String.format("/v1/oembed?url=%s", YOUTUBE_URL_SAMPLE+"dd")));
        // then
        perform.andExpect(status().is4xxClientError())
                .andExpect(jsonPath("message")
                        .value(String.format("Provider consider that URL{%s} is not valid",
                                "https://www.youtube.com/oembed?format=json&url="+YOUTUBE_URL_SAMPLE+"dd")));
    }

    @Test
    public void 트위터oembedUrl요청성공테스트() throws Exception {
        // given
        // when
        ResultActions perform = mockMvc.perform(
                get(String.format("/v1/oembed?url=%s", TWITTER_URL_SAMPLE)));
        // then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("author_name")
                        .value("대한민국 정부"));
    }

    @Test
    public void 트위터oembedUrl실패테스트() throws Exception {
        // given
        // when
        ResultActions perform = mockMvc.perform(
                get(String.format("/v1/oembed?url=%s", TWITTER_URL_SAMPLE+"dd")));
        // then
        perform.andExpect(status().is4xxClientError())
                .andExpect(jsonPath("message")
                        .value(String.format("Provider consider that URL{%s} is not valid",
                                "https://publish.twitter.com/oembed?format=json&url="+TWITTER_URL_SAMPLE+"dd")));
    }

    @Test
    public void VIMEOoembedUrl요청성공테스트() throws Exception {
        // given
        // when
        ResultActions perform = mockMvc.perform(
                get(String.format("/v1/oembed?url=%s", VIMEO_URL_SAMPLE)));
        // then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("title")
                        .value("A Time Artifact - work in progress"));
    }

    @Test
    public void VIMEOoembedUrl실패테스트() throws Exception {
        // given
        // when
        ResultActions perform = mockMvc.perform(
                get(String.format("/v1/oembed?url=%s", VIMEO_URL_SAMPLE+"dd")));
        // then
        perform.andExpect(status().is4xxClientError())
                .andExpect(jsonPath("message")
                        .value(String.format("Provider consider that URL{%s} is not valid",
                                "https://vimeo.com/api/oembed.json?url="+VIMEO_URL_SAMPLE+"dd")));
    }

    // access토큰때문에 provider 지원 x 판단
    @Test
    public void 인스타그램oembedUrl실패테스트() throws Exception {
        // given
        // when
        ResultActions perform = mockMvc.perform(
                get(String.format("/v1/oembed?url=%s", INSTAGRAM_URL_SAMPLE)));
        // then
        perform.andExpect(status().is4xxClientError())
                .andExpect(jsonPath("message")
                        .value("cant support provider"));
    }
}