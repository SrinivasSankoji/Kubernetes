package com.service.jokes.repository;

import com.service.jokes.entity.JokeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JokeRepository extends JpaRepository<JokeEntity, Long> {
}
