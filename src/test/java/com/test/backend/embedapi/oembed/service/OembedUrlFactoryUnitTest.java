package com.test.backend.embedapi.oembed.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class OembedUrlFactoryUnitTest {

    private final OembedUrlFactory oembedUrlFactory = new OembedUrlFactory();

    private static final String YOUTUBE_URL_SAMPLE = "https://www.youtube.com/watch?v=dBD54EZIrZo";
    //    private static final String INSTAGRAM_URL_SAMPLE = "";
    private static final String TWITTER_URL_SAMPLE = "https://twitter.com/hellopolicy/status/867177144815804416";
    private static final String VIMEO_URL_SAMPLE = "https://vimeo.com/20097015";

    @BeforeEach
    void providerListBuilding(){
        try {
            Method method = oembedUrlFactory.getClass().getDeclaredMethod("buildProviderList");
            method.setAccessible(true);
            method.invoke(oembedUrlFactory);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void 유튜브OembedProviderUrl생성테스트() throws Exception {
    // given

    // when
        String oembedUrl = oembedUrlFactory.createOembedUrl(YOUTUBE_URL_SAMPLE);
        // then
        Assertions.assertThat(oembedUrl)
                .isEqualTo("https://www.youtube.com/oembed?format=json&url=" + YOUTUBE_URL_SAMPLE);
    }
    @Test
    public void 트위터OembedProviderUrl생성테스트() throws Exception {
        // given

        // when
        String oembedUrl = oembedUrlFactory.createOembedUrl(TWITTER_URL_SAMPLE);
        // then
        Assertions.assertThat(oembedUrl)
                .isEqualTo("https://publish.twitter.com/oembed?format=json&url=" + TWITTER_URL_SAMPLE);
    }
    @Test
    public void 비메오OembedProviderUrl생성테스트() throws Exception {
        // given

        // when
        String oembedUrl = oembedUrlFactory.createOembedUrl(VIMEO_URL_SAMPLE);
        // then
        Assertions.assertThat(oembedUrl)
                .isEqualTo("https://vimeo.com/api/oembed.json?url=" + VIMEO_URL_SAMPLE);
    }
}