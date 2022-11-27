package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.service.SongService;
import dto.SongInfoResponseDTO;
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

    @GetMapping("/song")
    public ResponseEntity<List<SongInfoResponseDTO>>obtenerTodas(){
        List<Song> result = songService.findAll();
        if (result.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(result.stream().map(SongInfoResponseDTO::of).collect(Collectors.toList()));
        }
    }

    @GetMapping("/song/{id}")
    public ResponseEntity<SongInfoResponseDTO> obtenerUna (@PathVariable Long id){
        Song result = songService.findById(id).orElse(null);
        if (result == null){
            return ResponseEntity.notFound().build();
        }else
            return ResponseEntity.ok(songService.findById(id).map(SongInfoResponseDTO::of).get());
        //ResponseEntity.of(songService.findById(id).map(SongInfoResponseDTO::of));  se puede poner en una linea
    }

    @PostMapping("/song")
    public ResponseEntity<Song> nuevaCancion (@RequestBody Song nuevo){
        if(nuevo.getTitle() == ""){
            return ResponseEntity.badRequest().build();
        }
        Song saved = songService.add(nuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/song/{id}")
    public ResponseEntity<SongInfoResponseDTO> editarCancion (@RequestBody SongInfoResponseDTO editar, @PathVariable Long id){
        return songService.findById(id).map(s -> {
            s.setTitle((editar.getTitle()));
            return ResponseEntity.ok(SongInfoResponseDTO.of(songService.add(s)));
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
