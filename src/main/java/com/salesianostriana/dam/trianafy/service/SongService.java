package com.salesianostriana.dam.trianafy.service;


import com.salesianostriana.dam.trianafy.dto.OneSongRequestDTO;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.repos.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SongService {

    private final SongRepository repository;

    private final ArtistService artistService;

    public Song add(Song song) {
        return repository.save(song);
    }

    public Optional<Song> findById(Long id) {
        return repository.findById(id);
    }

    public List<Song> findAll() {
        return repository.findAll();
    }

    public Song edit(Song song) {
        return repository.save(song);
    }

    public void delete(Song song) {
        repository.delete(song);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    /**
     * Este método sirve para coger un OneSongRequest y transformarlo a Song
     * @param oneSongRequestDTO
     * @return un Song con los atributos del OneSongRequest
     */
    public Song toSong(OneSongRequestDTO oneSongRequestDTO){
        return Song.builder()
                .title(oneSongRequestDTO.getTitle())
                .artist(artistService.findById(oneSongRequestDTO.getArtistId()).get())      //OJO
                .album(oneSongRequestDTO.getAlbum())
                .year(oneSongRequestDTO.getYear())
                .build();
    }
}
