package com.salesianostriana.dam.trianafy.dto;

import com.salesianostriana.dam.trianafy.model.Playlist;
import com.salesianostriana.dam.trianafy.model.Song;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class OneSongRequest {

        private String title;
        private Long artistId;
        private String album;
        private String year;


    }

