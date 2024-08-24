package com.service.jokes.service;

import com.service.jokes.constants.ErrorConstants;
import com.service.jokes.dto.JokeRequest;
import com.service.jokes.exception.APIException;
import com.service.jokes.exception.ValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class JokeService {

    private final RestTemplate restTemplate;
    private final String jokeApiUrl;

    public JokeService(RestTemplate restTemplate,
                       @Value ("${joke.api.url}") String jokeApiUrl) {
        this.restTemplate = restTemplate;
        this.jokeApiUrl = jokeApiUrl;
    }

    public List<JokeRequest> getRandomJokes(Integer count) {
        if (count == null || count <= 0 || count > 100) {
            throw new ValidationException(ErrorConstants.INVALID_JOKE_REQUEST_COUNT);
        }
        List<JokeRequest> jokesList = new ArrayList<>();
        int iterations = (count / 10) + (count % 10 == 0 ? 0 : 1);
        for (int i = 0; i < iterations; i++) {
            JokeRequest[] jokes = restTemplate.getForObject(jokeApiUrl, JokeRequest[].class);
            if (jokes == null) {
                throw new APIException(ErrorConstants.NO_JOKE_FOUND);
            }
            jokesList.addAll(Arrays.asList(jokes));
            if (jokesList.size() >= count) {
                break;
            }
        }
        return jokesList.subList(0, count);
    }
}
