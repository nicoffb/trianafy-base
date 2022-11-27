package com.salesianostriana.dam.trianafy.dto;

import com.salesianostriana.dam.trianafy.model.Playlist;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Value
public class OnePlaylistResponseDTO {

    private Long id;
    private String name;
    private String description;
    private List<OneSongResponseDTO> songs;

    public static OnePlaylistResponseDTO of(Playlist playlist){
        return OnePlaylistResponseDTO.builder()
                .id(playlist.getId())
                .name(playlist.getName())
                .description(playlist.getDescription())
                .songs(playlist.getSongs().stream().map(OneSongResponseDTO::of).collect(Collectors.toList()))
                .build();
    }


}
