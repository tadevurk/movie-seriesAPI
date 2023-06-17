package movie.series.API.movieseriesAPI.models.DTOs;

import lombok.Data;
import movie.series.API.movieseriesAPI.models.Movie;

@Data
public class MovieResponseDTO {
    private Movie[] results;
}
