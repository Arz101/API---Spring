package com.spring.api.API.chat.dto;

public record Message(
    String from,
    String to,
    String content
) {
    
}
