package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;

    @GetMapping("/song")
    public ResponseEntity<?> obtenerTodas(){
        List<Song> result = songService.findAll();
        if (result.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(result);
        }
    }

    @GetMapping("/song/{id}")
    public ResponseEntity<?> obtenerUna (@PathVariable Long id){
        Song result = songService.findById(id).orElse(null);
        if (result == null){
            return ResponseEntity.notFound().build();
        }else
            return ResponseEntity.ok(result);
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
    public ResponseEntity<?> editarCancion (@RequestBody Song editar, @PathVariable Long id){
        return songService.findById(id).map(a -> {
            a.setTitle((editar.getTitle()));
            return ResponseEntity.ok(songService.add(a));
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
