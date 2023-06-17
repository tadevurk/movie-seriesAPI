package movie.series.API.movieseriesAPI.models.DTOs;

import lombok.Data;
import movie.series.API.movieseriesAPI.models.TVSeries;

@Data
public class TVSeriesResponseDTO {
    private TVSeries[] results;
}
