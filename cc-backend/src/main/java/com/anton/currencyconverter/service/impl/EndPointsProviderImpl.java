package com.anton.currencyconverter.service.impl;

import com.anton.currencyconverter.config.props.AuthConfig;
import com.anton.currencyconverter.service.api.EndPointsProvider;
import org.springframework.stereotype.Service;

@Service
public class EndPointsProviderImpl implements EndPointsProvider {

    private final AuthConfig authConfig;

    public EndPointsProviderImpl(AuthConfig authConfig) {
        this.authConfig = authConfig;
    }

    @Override
    public String getTokenEndPoint() {
        return this.authConfig.getUrl() + "oauth/token";
    }
}
