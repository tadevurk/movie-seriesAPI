package movie.series.API.movieseriesAPI.controllers;

import movie.series.API.movieseriesAPI.models.DTOs.ExceptionResponse;
import movie.series.API.movieseriesAPI.models.DTOs.MovieResponse;
import movie.series.API.movieseriesAPI.models.Movie;
import movie.series.API.movieseriesAPI.services.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/movies")
@CrossOrigin(origins = "*")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }


    @GetMapping()
    public ResponseEntity<?> getMovies(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String movieName
    ) {

        if (page < 1) {
            ExceptionResponse exception = new ExceptionResponse(
                    "Invalid page number, it must be greater than 0"
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonList(exception));
        }

        ResponseEntity<List<Movie>> moviesResponse = movieService.getMovies(page, movieName);

        if (!Objects.requireNonNull(moviesResponse.getBody()).isEmpty()) {
            List<MovieResponse> movieResponses = moviesResponse.getBody()
                    .stream()
                    .map(this::mapToMovieResponse)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(movieResponses);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<MovieResponse> getMovieDetailsById(@PathVariable int movieId) throws UnsupportedEncodingException {
        Movie movie = movieService.getMovieDetailsById(movieId).getBody();

        if (movie != null) {
            // create a response which contains the movie details and the trailer URL
            MovieResponse movieResponse = new MovieResponse(
                    movie.getId(),
                    movie.getOriginal_language(),
                    movie.getTitle(),
                    movie.getOriginal_title(),
                    movie.getOverview(),
                    String.valueOf(movie.getPopularity()),
                    movie.getRelease_date(),
                    movie.getTrailerUrl()
            );
            return ResponseEntity.ok(movieResponse);
        }

        return ResponseEntity.notFound().build();
    }

    private MovieResponse mapToMovieResponse(Movie movie) {
        return new MovieResponse(
                movie.getId(),
                null,
                movie.getTitle(),
                null,
                movie.getOverview(),
                null,
                movie.getRelease_date(),
                null
        );
    }
}
