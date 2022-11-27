package com.salesianostriana.dam.trianafy.dto;

import com.salesianostriana.dam.trianafy.model.Playlist;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class AllPlaylistsResponseDTO {

    private Long id;
    private String name;
    private long numberOfSongs;

    public static AllPlaylistsResponseDTO of(Playlist playlist){
        return AllPlaylistsResponseDTO.builder()
                .id(playlist.getId())
                .name(playlist.getName())
                .numberOfSongs(playlist.getSongs().size())
                .build();
    }
}

