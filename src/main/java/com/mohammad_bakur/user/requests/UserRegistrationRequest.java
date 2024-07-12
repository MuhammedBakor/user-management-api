package com.mohammad_bakur.user.requests;

public record UserRegistrationRequest(
        String name,
        String email,
        Integer age
){
}
