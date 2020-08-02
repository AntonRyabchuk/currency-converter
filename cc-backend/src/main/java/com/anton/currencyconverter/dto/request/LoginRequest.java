package com.anton.currencyconverter.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequest {

    private final String username;

    private final String password;
}
