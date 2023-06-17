package movie.series.API.movieseriesAPI.models;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public class Media {
    @Id
    private Long id;
    private String original_language;
    private String overview;
    private String original_name;
    private String trailerUrl;
}
