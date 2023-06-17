package movie.series.API.movieseriesAPI.services;

import movie.series.API.movieseriesAPI.models.DTOs.MovieResponseDTO;
import movie.series.API.movieseriesAPI.models.Movie;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class MovieService extends BaseService {

    public MovieService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public ResponseEntity<List<Movie>> getMovies(int page, String movieName) {
        String apiUrl = TMDB_API_BASE_URL + "/discover/movie?include_adult=false&include_video=false&language=en-US&page="+page+"&sort_by=popularity.desc&api_key=" + TMDB_API_KEY;

        if (movieName != null && !movieName.isEmpty()) {
            apiUrl = TMDB_API_BASE_URL + "/search/movie?include_adult=false&include_video=false&language=en-US&page=" + page + "&sort_by=popularity.desc&api_key=" + TMDB_API_KEY + "&query=" + movieName;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<MovieResponseDTO> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, MovieResponseDTO.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            MovieResponseDTO movieResponseDTO = response.getBody();
            Movie[] movies = movieResponseDTO.getResults();

            if (movies != null && movies.length > 0) {
                List<Movie> movieList = Arrays.asList(movies);

                return ResponseEntity.ok(movieList);
            }
        }
        return ResponseEntity.notFound().build();
    }


    public ResponseEntity<Movie> getMovieDetailsById(int movieId) {
        String apiUrl = TMDB_API_BASE_URL + "/movie/" + movieId + "?language=en-US&api_key=" + TMDB_API_KEY;

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Movie> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, Movie.class);


        if (response.getStatusCode() == HttpStatus.OK) {
            Movie movieDetails = response.getBody();
            assert movieDetails != null;
            // Set the trailer URL here
            movieDetails.setTrailerUrl(fetchTrailerUrl(movieDetails.getTitle()));
            return ResponseEntity.ok(movieDetails);
        } else {
            return ResponseEntity.status(response.getStatusCode()).build();
        }
    }
}
