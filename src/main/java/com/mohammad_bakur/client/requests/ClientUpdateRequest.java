package com.mohammad_bakur.client.requests;

public record ClientUpdateRequest(
        String name,
        String email,
        Integer age )
{ }
