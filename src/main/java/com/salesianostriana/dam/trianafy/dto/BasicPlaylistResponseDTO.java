package com.salesianostriana.dam.trianafy.dto;

import com.salesianostriana.dam.trianafy.model.Playlist;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class BasicPlaylistResponseDTO {

    private Long id;
    private String name;
    private String description;

    public static BasicPlaylistResponseDTO of(Playlist playlist){
        return BasicPlaylistResponseDTO.builder()
                .id(playlist.getId())
                .name(playlist.getName())
                .description(playlist.getDescription())
                .build();
    }
}
