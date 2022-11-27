package com.salesianostriana.dam.trianafy.dto;

import com.salesianostriana.dam.trianafy.model.Song;
import lombok.Builder;
import lombok.Value;


@Builder
@Value
public class OneSongResponseDTO {

    private Long id;
    private String title;
    private String artist;
    private String album;
    private String year;

    public static OneSongResponseDTO of (Song song){
        String artistName = "";
        if(song.getArtist() != null){
            artistName = song.getArtist().getName();
        }
        return OneSongResponseDTO.builder()
                .id(song.getId())
                .title(song.getTitle())
                .artist(artistName)
                .album(song.getAlbum())
                .year(song.getYear())
                .build();


    }

}
