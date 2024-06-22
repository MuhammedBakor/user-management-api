package com.mohammad_bakur.client.requests;

public record ClientRegistrationRequest(
        String name,
        String email,
        Integer age
){
}
