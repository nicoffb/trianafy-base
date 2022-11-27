package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.service.ArtistService;
import com.salesianostriana.dam.trianafy.service.SongService;
import com.salesianostriana.dam.trianafy.dto.OneSongRequest;
import com.salesianostriana.dam.trianafy.dto.OneSongResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;
    private final ArtistService artistService;

    @GetMapping("/song")
    public ResponseEntity<List<OneSongResponse>>obtenerTodas(){
        List<Song> result = songService.findAll();
        if (result.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(result.stream().map(OneSongResponse::of).collect(Collectors.toList()));
        }
    }

    @GetMapping("/song/{id}")
    public ResponseEntity<OneSongResponse> obtenerUna (@PathVariable Long id){
        Song result = songService.findById(id).orElse(null);
        if (result == null){
            return ResponseEntity.notFound().build();
        }else
            return ResponseEntity.ok(songService.findById(id).map(OneSongResponse::of).get());
        //ResponseEntity.of(songService.findById(id).map(SongInfoResponseDTO::of));  se puede poner en una linea
    }

    @PostMapping("/song/")
    public ResponseEntity<OneSongResponse> nuevaCancion (@RequestBody OneSongRequest nuevo){
        if(nuevo.getTitle() != "" && artistService.findById(nuevo.getArtistId()).isPresent()){
            Song saved = songService.add(songService.toSong(nuevo));
            return ResponseEntity.status(HttpStatus.CREATED).body(OneSongResponse.of(saved));
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/song/{id}")
    public ResponseEntity<OneSongResponse> editarCancion (@RequestBody OneSongResponse editar, @PathVariable Long id){
        return songService.findById(id).map(s -> {
            s.setTitle((editar.getTitle()));
            return ResponseEntity.ok(OneSongResponse.of(songService.add(s)));
            //EL METODO OF transforma una SONG en una SONG INFO RESPONSE
        }).orElseGet(() -> {
            return ResponseEntity.notFound().build();
        });
    }

    @DeleteMapping("/song/{id}")
    public ResponseEntity<?> borrarCancion(@PathVariable Long id){
        songService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
