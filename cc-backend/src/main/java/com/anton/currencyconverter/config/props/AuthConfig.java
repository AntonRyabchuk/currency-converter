package com.anton.currencyconverter.config.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "auth")
public class AuthConfig {

    private String clientId;
    private String clientSecret;
    private String url;
}
