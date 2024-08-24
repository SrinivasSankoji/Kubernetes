package com.service.jokes.controller;

import com.service.jokes.dto.ApiResponse;
import com.service.jokes.dto.JokeRequest;
import com.service.jokes.service.JokeService;
import com.service.jokes.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping ("/api")
@AllArgsConstructor
public class JokeController {

    private final JokeService jokeService;

    @GetMapping ("/jokes")
    public ResponseEntity<ApiResponse<List<JokeRequest>>> getRandomJokes(HttpServletRequest request, @RequestParam (name = "count", required = false) Integer count) {
        List<JokeRequest> jokesResponse = jokeService.getRandomJokes(count);
        return new ResponseEntity<>(ResponseUtil.success(jokesResponse, "Joke fetched successfully", request.getRequestURI()),
                HttpStatus.OK);
    }
}
