package com.salesianostriana.dam.trianafy.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.model.Song;
import lombok.Builder;
import lombok.Value;


@Builder
@Value
public class SongJackson {

    private Long id;

    @JsonView(Views.Public.class)
    private String title;

    private String album;

    @JsonView(Views.Public.class)
    private String year;

    private Artist artist;

    public void JsonViewToSerialize()
            throws JsonProcessingException {

        Song song = new Song(1, "Por la boca muere el pez", "Fito Album", "2005", Artist artist);

        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);

        String result = mapper
                .writerWithView(Views.Public.class)
                .writeValueAsString(song);

        assertThat(result, containsString("John"));
        assertThat(result, not(containsString("1")));
    }

}
