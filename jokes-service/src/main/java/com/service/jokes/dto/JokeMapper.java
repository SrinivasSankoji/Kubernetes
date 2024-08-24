package com.service.jokes.dto;

import com.service.jokes.entity.JokeEntity;

public class JokeMapper {
    public static JokeResponse toJokeResponse(JokeEntity entity) {
        if (entity == null) {
            return null;
        }
        return JokeResponse.builder()
                .type(entity.getType())
                .setup(entity.getSetup())
                .punchline(entity.getPunchline())
                .build();
    }
}
