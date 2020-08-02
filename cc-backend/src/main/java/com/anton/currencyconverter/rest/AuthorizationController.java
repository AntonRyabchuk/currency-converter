package com.anton.currencyconverter.rest;

import com.anton.currencyconverter.config.props.AuthConfig;
import com.anton.currencyconverter.dto.TokenContainer;
import com.anton.currencyconverter.dto.request.LoginRequest;
import com.anton.currencyconverter.dto.request.RefreshTokenRequest;
import com.anton.currencyconverter.service.api.EndPointsProvider;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@RestController
@RequestMapping("auth")
public class AuthorizationController {

    private final EndPointsProvider endPointsProvider;
    private final AuthConfig authConfig;

    public AuthorizationController(EndPointsProvider endPointsProvider, AuthConfig authConfig) {
        this.endPointsProvider = endPointsProvider;
        this.authConfig = authConfig;
    }

    @RequestMapping(value = "login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public TokenContainer loginByPassword(@RequestBody LoginRequest request) {
        final RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBasicAuth(authConfig.getClientId(), authConfig.getClientSecret());
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("username", request.getUsername());
        params.add("password", request.getPassword());
        HttpEntity<MultiValueMap<String, String>> req = new HttpEntity<>(params, headers);
        ResponseEntity<TokenContainer> response = restTemplate.exchange(this.endPointsProvider.getTokenEndPoint(),
                HttpMethod.POST, req, TokenContainer.class);
        return response.getBody();
    }

    @RequestMapping(value = "refresh", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public TokenContainer refreshToken(@RequestBody RefreshTokenRequest request) {
        final RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBasicAuth(authConfig.getClientId(), authConfig.getClientSecret());
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "refresh_token");
        params.add("refresh_token", request.getRefreshToken());
        HttpEntity<MultiValueMap<String, String>> req = new HttpEntity<>(params, headers);
        ResponseEntity<TokenContainer> response = restTemplate.exchange(this.endPointsProvider.getTokenEndPoint(),
                HttpMethod.POST, req, TokenContainer.class);
        return response.getBody();
    }
}
