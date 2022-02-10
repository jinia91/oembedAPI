package com.test.backend.embedapi.oembed.service;

import com.test.backend.embedapi.oembed.Url;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class OembedUrlCreateServiceUnitTest {

    @InjectMocks
    private OembedUrlCreateService oembedUrlCreateService;

    @Mock
    private ProviderListHandler providerListHandler;

    private static final String YOUTUBE_URL_SAMPLE = "https://www.youtube.com/watch?v=dBD54EZIrZo";
    //    private static final String INSTAGRAM_URL_SAMPLE = "";
    private static final String TWITTER_URL_SAMPLE = "https://twitter.com/hellopolicy/status/867177144815804416";
    private static final String VIMEO_URL_SAMPLE = "https://vimeo.com/20097015";


    @Test
    public void 유튜브OembedProviderUrl생성테스트() throws Exception {
    // given
        Url url = new Url(YOUTUBE_URL_SAMPLE);
        Mockito
                .when(providerListHandler.getProviderEndpointUrlImmutableList())
                .thenReturn(List.of("https://www.youtube.com/oembed"));
        // when
        Url oembedUrl = oembedUrlCreateService.createOembedUrl(url);
        // then
        Assertions.assertThat(oembedUrl.getUrl())
                .isEqualTo("https://www.youtube.com/oembed?format=json&url=" + YOUTUBE_URL_SAMPLE);
    }
    @Test
    public void 트위터OembedProviderUrl생성테스트() throws Exception {
        // given
        Url url = new Url(TWITTER_URL_SAMPLE);
        Mockito
                .when(providerListHandler.getProviderEndpointUrlImmutableList())
                .thenReturn(List.of("https://publish.twitter.com/oembed"));
        // when
        Url oembedUrl = oembedUrlCreateService.createOembedUrl(url);
        // then
        Assertions.assertThat(oembedUrl.getUrl())
                .isEqualTo("https://publish.twitter.com/oembed?format=json&url=" + url.getUrl());
    }
    @Test
    public void 비메오OembedProviderUrl생성테스트() throws Exception {
        // given
        Url url = new Url(VIMEO_URL_SAMPLE);
        Mockito
                .when(providerListHandler.getProviderEndpointUrlImmutableList())
                .thenReturn(List.of("https://vimeo.com/api/oembed.json"));
        // when
        Url oembedUrl = oembedUrlCreateService.createOembedUrl(url);
        // then
        Assertions.assertThat(oembedUrl.getUrl())
                .isEqualTo("https://vimeo.com/api/oembed.json?url=" + url.getUrl());
    }
}