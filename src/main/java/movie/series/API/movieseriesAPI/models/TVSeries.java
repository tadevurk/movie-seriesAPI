package movie.series.API.movieseriesAPI.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class TVSeries extends Media{
    private String name;

    @ElementCollection
    @JsonProperty("origin_country")
    private List<String> origin_country;
}
