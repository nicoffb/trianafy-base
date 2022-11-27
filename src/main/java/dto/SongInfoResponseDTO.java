package dto;

import com.salesianostriana.dam.trianafy.model.Song;
import lombok.Builder;
import lombok.Value;


@Builder
@Value
public class SongInfoResponseDTO {

    private Long id;
    private String title;
    private String artist;
    private String album;
    private String year;

    public static SongInfoResponseDTO of (Song song){
        String artistName = "";
        if(song.getArtist() != null){
            artistName = song.getArtist().getName();
        }
        return SongInfoResponseDTO.builder()
                .id(song.getId())
                .title(song.getTitle())
                .artist(artistName)
                .album(song.getAlbum())
                .year(song.getYear())
                .build();


    }

}
