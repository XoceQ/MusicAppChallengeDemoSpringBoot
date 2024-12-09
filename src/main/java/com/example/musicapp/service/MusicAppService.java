package com.example.musicapp.service;

import com.example.musicapp.dto.SingerDTO;
import com.example.musicapp.dto.SongDTO;
import com.example.musicapp.entity.Singer;
import com.example.musicapp.entity.Song;
import com.example.musicapp.repository.SingerRepository;
import com.example.musicapp.repository.SongRepository;
import com.example.musicapp.config.OpenAIService;  // Importar el servicio OpenAI
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicAppService {

    private final SingerRepository singerRepository;
    private final SongRepository songRepository;
    private final OpenAIService openAIService;  // Inyectar el servicio OpenAI

    @Autowired
    public MusicAppService(SingerRepository singerRepository, SongRepository songRepository, OpenAIService openAIService) {
        this.singerRepository = singerRepository;
        this.songRepository = songRepository;
        this.openAIService = openAIService;


    }

    // Agregar un cantante
    public Singer addSinger(SingerDTO singerDTO) {
        Singer singer = new Singer();
        singer.setName(singerDTO.getName());
        singer.setGenre(singerDTO.getGenre());
        singer.setNationality(singerDTO.getNationality());
        return singerRepository.save(singer);
    }

    // Agregar una canción
    public Song addSong(SongDTO songDTO) {
        Singer singer = singerRepository.findById(songDTO.getSingerId())
                .orElseThrow(() -> new RuntimeException("Cantante no encontrado"));
        Song song = new Song();
        song.setTitle(songDTO.getTitle());
        song.setAlbum(songDTO.getAlbum());
        song.setSinger(singer);
        return songRepository.save(song);
    }

    // Buscar canciones por cantante
    public List<Song> findSongsBySinger(String singerName) {
        Singer singer = singerRepository.findByName(singerName)
                .orElseThrow(() -> new RuntimeException("Cantante no encontrado"));
        return songRepository.findBySingerId(singer.getId());
    }

    // Obtener todos los cantantes
    public List<Singer> getAllSingers() {
        return singerRepository.findAll();
    }

    // Obtener todas las canciones
    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

    // Obtener información sobre un cantante usando OpenAI
    public String getSingerInformation(String singerName) {
        return openAIService.getSingerInfo(singerName);
    }
}
