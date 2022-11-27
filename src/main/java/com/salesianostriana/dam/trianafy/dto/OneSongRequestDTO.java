package com.salesianostriana.dam.trianafy.dto;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class OneSongRequestDTO {

        private String title;
        private Long artistId;
        private String album;
        private String year;


    }

