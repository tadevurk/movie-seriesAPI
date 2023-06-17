package movie.series.API.movieseriesAPI.models.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public record TVSeriesResponse(Long id, String name,
                               @JsonInclude(JsonInclude.Include.NON_NULL)String overview,
                               @JsonInclude(JsonInclude.Include.NON_NULL) List<String> originCountryArray,
                               @JsonInclude(JsonInclude.Include.NON_NULL) String originalLanguage,
                               @JsonInclude(JsonInclude.Include.NON_NULL) String originalName,
                               @JsonInclude(JsonInclude.Include.NON_NULL) String trailerUrl) {
}