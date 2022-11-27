package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.model.Playlist;
import com.salesianostriana.dam.trianafy.service.PlaylistService;
import dto.AllPlaylistsResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;

    @GetMapping("/list")
    public ResponseEntity<List<AllPlaylistsResponseDTO>> obtenerTodas(){
        List<Playlist> result = playlistService.findAll();
        if (result.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(result.stream().map(AllPlaylistsResponseDTO::of).collect(Collectors.toList()));
        }
    }






}
