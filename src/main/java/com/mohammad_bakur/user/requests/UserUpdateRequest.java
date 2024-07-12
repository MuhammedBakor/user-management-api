package com.mohammad_bakur.user.requests;

public record UserUpdateRequest(
        String name,
        String email,
        Integer age )
{ }
