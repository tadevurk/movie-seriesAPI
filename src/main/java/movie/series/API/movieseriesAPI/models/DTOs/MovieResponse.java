package movie.series.API.movieseriesAPI.models.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;

public record MovieResponse(Long id, @JsonInclude(JsonInclude.Include.NON_NULL) String original_language,
                            String title,
                            @JsonInclude(JsonInclude.Include.NON_NULL)String original_title,
                            String overview,
                            @JsonInclude(JsonInclude.Include.NON_NULL) String popularity,
                            String release_date,
                            @JsonInclude(JsonInclude.Include.NON_NULL) String trailerUrl) {
}
