package com.example.musicapp.repository;


import com.example.musicapp.entity.Singer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SingerRepository extends JpaRepository<Singer, Long> {
    Optional<Singer> findByName(String name);
}