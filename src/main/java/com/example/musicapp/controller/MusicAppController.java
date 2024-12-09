package com.example.musicapp.controller;

import com.example.musicapp.dto.SingerDTO;
import com.example.musicapp.dto.SongDTO;
import com.example.musicapp.entity.Singer;
import com.example.musicapp.entity.Song;
import com.example.musicapp.service.MusicAppService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MusicAppController {

    private final MusicAppService musicAppService;

    public MusicAppController(MusicAppService musicAppService) {
        this.musicAppService = musicAppService;
    }

    // Registrar un cantante
    @PostMapping("/singers")
    public ResponseEntity<Singer> addSinger(@RequestBody SingerDTO singerDTO) {
        Singer newSinger = musicAppService.addSinger(singerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newSinger);
    }

    // Registrar una canción
    @PostMapping("/songs")
    public ResponseEntity<Song> addSong(@RequestBody SongDTO songDTO) {
        Song newSong = musicAppService.addSong(songDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newSong);
    }

    // Buscar canciones por cantante
    @GetMapping("/songs/{singerName}")
    public ResponseEntity<List<Song>> getSongsBySinger(@PathVariable String singerName) {
        List<Song> songs = musicAppService.findSongsBySinger(singerName);
        if (songs.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(songs);
    }

    // Obtener todos los cantantes
    @GetMapping("/singers")
    public ResponseEntity<List<Singer>> getAllSingers() {
        List<Singer> singers = musicAppService.getAllSingers();
        return ResponseEntity.ok(singers);
    }

    // Obtener todas las canciones
    @GetMapping("/songs")
    public ResponseEntity<List<Song>> getAllSongs() {
        List<Song> songs = musicAppService.getAllSongs();
        return ResponseEntity.ok(songs);
    }

    // Obtener información sobre un cantante usando OpenAI
    @GetMapping("/singer-info/{singerName}")
    public ResponseEntity<String> getSingerInformation(@PathVariable String singerName) {
        try {
            String singerInfo = musicAppService.getSingerInformation(singerName);
            return ResponseEntity.ok(singerInfo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener la información del cantante: " + e.getMessage());
        }
    }
}
