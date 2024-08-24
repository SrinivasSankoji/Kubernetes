package com.service.jokes.service;

import com.service.jokes.dto.JokeRequest;
import com.service.jokes.exception.APIException;
import com.service.jokes.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith (MockitoExtension.class)
public class JokeServiceTest {
    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private JokeService jokeService;
    private final String jokeApiUrl = "https://official-joke-api.appspot.com/random_ten";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jokeService = new JokeService(restTemplate, jokeApiUrl);
    }

    @Test
    public void testGetRandomJokes_ValidCount() {
        int count = 10;
        JokeRequest[] jokes = new JokeRequest[count];
        when(restTemplate.getForObject(jokeApiUrl, JokeRequest[].class)).thenReturn(jokes);
        List<JokeRequest> result = jokeService.getRandomJokes(count);
        assertEquals(count, result.size());
    }

    @Test
    public void testGetRandomJokes_MaxValidCount() {
        int count = 100;
        JokeRequest[] jokes = new JokeRequest[10];
        when(restTemplate.getForObject(jokeApiUrl, JokeRequest[].class)).thenReturn(jokes);
        List<JokeRequest> result = jokeService.getRandomJokes(count);
        assertEquals(count, result.size());
    }

    @Test
    public void testGetRandomJokes_LessThanTen() {
        int count = 5;
        JokeRequest[] jokes = new JokeRequest[count];
        when(restTemplate.getForObject(jokeApiUrl, JokeRequest[].class)).thenReturn(jokes);
        List<JokeRequest> result = jokeService.getRandomJokes(count);
        assertEquals(count, result.size());
    }

    @Test
    public void testGetRandomJokes_NullCount() {
        assertThrows(ValidationException.class, () -> jokeService.getRandomJokes(null));
    }

    @Test
    public void testGetRandomJokes_ZeroCount() {
        assertThrows(ValidationException.class, () -> jokeService.getRandomJokes(0));
    }

    @Test
    public void testGetRandomJokes_NegativeCount() {
        assertThrows(ValidationException.class, () -> jokeService.getRandomJokes(-1));
    }

    @Test
    public void testGetRandomJokes_CountGreaterThan100() {
        assertThrows(ValidationException.class, () -> jokeService.getRandomJokes(101));
    }

    @Test
    public void testGetRandomJokes_ApiReturnsNull() {
        when(restTemplate.getForObject(jokeApiUrl, JokeRequest[].class)).thenReturn(null);

        assertThrows(APIException.class, () -> jokeService.getRandomJokes(10));
    }
}
