package movie.series.API.movieseriesAPI.services;

import movie.series.API.movieseriesAPI.models.DTOs.TVSeriesResponseDTO;
import movie.series.API.movieseriesAPI.models.TVSeries;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class TVSeriesService extends BaseService {

    public TVSeriesService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<List<TVSeries>> getTVSeries(int page, String tvSeriesName) {
        String apiUrl = TMDB_API_BASE_URL + "/discover/tv?include_adult=false&include_null_first_air_dates=false&language=en-US&page="+page+"&sort_by=popularity.desc&api_key=" + TMDB_API_KEY;

        if (tvSeriesName != null && !tvSeriesName.isEmpty()) {
            apiUrl = TMDB_API_BASE_URL + "/search/tv?include_adult=false&include_null_first_air_dates=false&language=en-US&page="+page+"&sort_by=popularity.desc&api_key=" + TMDB_API_KEY + "&query=" + tvSeriesName;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<TVSeriesResponseDTO> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, TVSeriesResponseDTO.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            TVSeriesResponseDTO tvSeriesResponseDTO = response.getBody();
            TVSeries[] tvSeries = tvSeriesResponseDTO.getResults();

            if (tvSeries != null && tvSeries.length > 0) {
                List<TVSeries> tvSeriesList = Arrays.asList(tvSeries);

                return ResponseEntity.ok(tvSeriesList);
            }
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<TVSeries> getTVSeriesDetailsById(int tvSeriesId) {
        String apiUrl = TMDB_API_BASE_URL + "/tv/" + tvSeriesId + "?language=en-US&api_key=" + TMDB_API_KEY;

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<TVSeries> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, TVSeries.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            TVSeries tvSeriesDetails = response.getBody();

            if (tvSeriesDetails != null) {
                //set the tv series trailer here
                tvSeriesDetails.setTrailerUrl(fetchTrailerUrl(tvSeriesDetails.getName()));
                return ResponseEntity.ok(tvSeriesDetails);
            }
        }
        return ResponseEntity.notFound().build();
    }
}
