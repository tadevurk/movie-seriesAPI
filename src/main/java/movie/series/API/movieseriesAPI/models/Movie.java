package movie.series.API.movieseriesAPI.models;


import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Movie extends Media {
    private boolean adult;
    private String backdrop_path;
    private String original_title;
    private double popularity;
    private String poster_path;
    private String release_date;
    private String title;
}
