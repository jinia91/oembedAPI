package com.test.backend.embedapi.oembed.service;

import com.google.gson.JsonElement;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OembedServiceUnitTest {

    private static final String YOUTUBE_URL_SAMPLE = "https://www.youtube.com/watch?v=dBD54EZIrZo";
//    private static final String INSTAGRAM_URL_SAMPLE = "";
    private static final String TWITTER_URL_SAMPLE = "https://twitter.com/hellopolicy/status/867177144815804416";
    private static final String VIMEO_URL_SAMPLE = "https://vimeo.com/20097015";

    @InjectMocks
    private OembedService oembedService;

    @Mock
    private OembedUrlFactory oembedUrlFactory;

    @Test
    public void 기본유효성검사테스트() throws Exception {
        // given
        String url = " ";
        // when
        // then

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                oembedService.checkParameterUrl(url));
        assertEquals("no valid URL parameter",exception.getMessage());
    }

    @Test
    public void 유튜브oEmbedUrl생성테스트() throws Exception {
        // given
        Mockito
                .when(oembedUrlFactory.createOembedUrl(YOUTUBE_URL_SAMPLE))
                .thenReturn("https://www.youtube.com/oembed?url=" + YOUTUBE_URL_SAMPLE);
        // when
        JsonElement oembed = oembedService.getOembed(YOUTUBE_URL_SAMPLE);
        // then
        Assertions.assertThat(oembed.getAsJsonObject().get("title").toString())
                .isNotNull()
                .isEqualTo("\"언제 어디서나! 핑크퐁 BEST 모음 80분 | 차에서 듣는 동요 | 아기상어, 상어가족 외 70곡 | + 모음집 | 핑크퐁! 인기동요\"");
        Assertions.assertThat(oembed.getAsJsonObject().get("author_name").toString())
                .isNotNull()
                .isEqualTo("\"핑크퐁 (인기 동요・동화)\"");
    }

    @Test
    public void 트위터oEmbedUrl생성테스트() throws Exception {
        // given
        Mockito
                .when(oembedUrlFactory.createOembedUrl(TWITTER_URL_SAMPLE))
                .thenReturn("https://publish.twitter.com/oembed?format=json&url=" + TWITTER_URL_SAMPLE);

        // when
        JsonElement oembed = oembedService.getOembed(TWITTER_URL_SAMPLE);
        // then
        Assertions.assertThat(oembed.getAsJsonObject().get("url").toString())
                .isNotNull()
                .isEqualTo("\"https://twitter.com/hellopolicy/status/867177144815804416\"");
        Assertions.assertThat(oembed.getAsJsonObject().get("author_name").toString())
                .isNotNull()
                .isEqualTo("\"대한민국 정부\"");
    }

    @Test
    public void VIMEOoEmbedUrl생성테스트() throws Exception {
        // given
        Mockito
                .when(oembedUrlFactory.createOembedUrl(VIMEO_URL_SAMPLE))
                .thenReturn("https://vimeo.com/api/oembed.json?url=" + VIMEO_URL_SAMPLE);

        // when
        JsonElement oembed = oembedService.getOembed(VIMEO_URL_SAMPLE);
        // then
        Assertions.assertThat(oembed.getAsJsonObject().get("title").toString())
                .isNotNull()
                .isEqualTo("\"A Time Artifact - work in progress\"");
        Assertions.assertThat(oembed.getAsJsonObject().get("author_name").toString())
                .isNotNull()
                .isEqualTo("\"Depict_tk\"");
    }
}