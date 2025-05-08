package com.fairytale.FairyTale.global.api.client;

import com.fairytale.FairyTale.global.api.dto.response.OIDCKeysResponse;
import com.fairytale.FairyTale.global.api.dto.response.OauthTokenResponse;
import feign.Headers;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "KakaoAuthClient", url = "https://kauth.kakao.com")
public interface KakaoOauthClient {

    @Cacheable(cacheNames = "KakaoOICD", cacheManager = "oidcKeyCacheManager")
    @GetMapping("/.well-known/jwks.json")
    OIDCKeysResponse getKakaoOIDCOpenKeys();

    @Headers("Content-type: application/x-www-form-urlencoded;charset=utf-8")
    @PostMapping(
            "/oauth/token?grant_type=authorization_code&client_id={CLIENT_ID}&redirect_uri={REDIRECT_URI}&code={CODE}")
    OauthTokenResponse kakaoAuth(
            @PathVariable("CLIENT_ID") String clientId,
            @PathVariable("REDIRECT_URI") String redirectUri,
            @PathVariable("CODE") String code);
}
