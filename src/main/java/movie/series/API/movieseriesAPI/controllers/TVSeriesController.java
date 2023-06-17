package movie.series.API.movieseriesAPI.controllers;

import movie.series.API.movieseriesAPI.models.DTOs.ExceptionResponse;
import movie.series.API.movieseriesAPI.models.DTOs.TVSeriesResponse;
import movie.series.API.movieseriesAPI.models.TVSeries;
import movie.series.API.movieseriesAPI.services.TVSeriesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/tv-series")
@CrossOrigin(origins = "*")
public class TVSeriesController {
    private final TVSeriesService tvSeriesService;

    public TVSeriesController(TVSeriesService tvSeriesService) {
        this.tvSeriesService = tvSeriesService;
    }

    @GetMapping()
    public ResponseEntity<?> getTVSeries(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String tvSeriesName
    ) {
        if (page < 1) {
            ExceptionResponse exception = new ExceptionResponse(
                    "Invalid page number, it must be greater than 0"
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonList(exception));
        }
        ResponseEntity<List<TVSeries>> tvSeriesResponse = tvSeriesService.getTVSeries(page, tvSeriesName);

        if (!Objects.requireNonNull(tvSeriesResponse.getBody()).isEmpty()) {
            List<TVSeriesResponse> tvSeriesResponses = tvSeriesResponse.getBody()
                    .stream()
                    .map(this::mapToTvSeriesResponse)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(tvSeriesResponses);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{tvSeriesId}")
    public ResponseEntity<TVSeriesResponse> getTVSeriesById(@PathVariable int tvSeriesId) {
        TVSeries tvSeries = tvSeriesService.getTVSeriesDetailsById(tvSeriesId).getBody();

        if (tvSeries != null) {
            TVSeriesResponse tvSeriesResponse = new TVSeriesResponse(
                    tvSeries.getId(),
                    tvSeries.getName(),
                    tvSeries.getOverview(),
                    tvSeries.getOrigin_country(),
                    tvSeries.getOriginal_language(),
                    tvSeries.getOriginal_name(),
                    tvSeries.getTrailerUrl()
            );
            return ResponseEntity.ok(tvSeriesResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    private TVSeriesResponse mapToTvSeriesResponse(TVSeries tvSeries) {
        return new TVSeriesResponse(
                tvSeries.getId(),
                tvSeries.getName(),
                tvSeries.getOverview(),
                null,
                null,
                null,
                null
        );
    }
}
