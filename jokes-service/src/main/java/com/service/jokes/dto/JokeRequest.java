package com.service.jokes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class JokeRequest {
    private int id;
    private String type;
    private String setup;
    private String punchline;
}
