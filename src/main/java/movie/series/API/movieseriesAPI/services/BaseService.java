package movie.series.API.movieseriesAPI.services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class BaseService {
    protected  RestTemplate restTemplate;
    protected static final String TMDB_API_BASE_URL = "https://api.themoviedb.org/3";
    @Value("${TMDB_API_KEY}")
    protected String TMDB_API_KEY;

    protected static final String YOUTUBE_API_BASE_URL = "https://www.googleapis.com/youtube/v3/search?part=snippet&q=";
    @Value("${YOUTUBE_API_KEY}")
    protected String YOUTUBE_API_KEY;
    protected static final String TRAILER_URL = "https://www.youtube.com/watch?v=";

    protected String fetchTrailerUrl(String title) {
        try {
            // Encode the movie title because it gives errors when it contains spaces
            String encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8);

            String searchUrl = YOUTUBE_API_BASE_URL + encodedTitle + "%20official%20trailer&maxResults=1&key=" + YOUTUBE_API_KEY;
            ResponseEntity<String> response = restTemplate.getForEntity(searchUrl, String.class);
            String jsonResponse = response.getBody();

            // Parse the JSON response to get the video ID
            String videoId = parseVideoIdFromJson(jsonResponse);

            return TRAILER_URL + videoId;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private String parseVideoIdFromJson(String jsonResponse) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);

        // Get the video ID from the JSON response
        if (jsonObject.has("items")) {
            JsonObject items = jsonObject.getAsJsonArray("items").get(0).getAsJsonObject();
            JsonObject id = items.getAsJsonObject("id");
            String videoId = id.get("videoId").getAsString();

            return videoId;
        }
        return null;
    }
}
