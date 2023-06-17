package movie.series.API.movieseriesAPI.models.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;

public record MovieResponse(Long id, @JsonInclude(JsonInclude.Include.NON_NULL) String original_language,
                            String title,
                            @JsonInclude(JsonInclude.Include.NON_NULL)String original_title,
                            @JsonInclude(JsonInclude.Include.NON_NULL) String overview,
                            @JsonInclude(JsonInclude.Include.NON_NULL) String popularity,
                            @JsonInclude(JsonInclude.Include.NON_NULL)String release_date,
                            @JsonInclude(JsonInclude.Include.NON_NULL) String trailerUrl) {
}
