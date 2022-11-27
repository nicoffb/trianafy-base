package dto;

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

