package movie.series.API.movieseriesAPI.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import movie.series.API.movieseriesAPI.models.DTOs.ExceptionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ApiFilters extends OncePerRequestFilter {

    @Value("${valid.api.key}")
    private String VALID_API_KEY;

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String apiKeyHeader = getHeader(request);
        if (apiKeyHeader == null || !apiKeyHeader.equals(VALID_API_KEY)) {
            ResponseEntity<ExceptionResponse> exceptionResponseResponseEntity = new ResponseEntity<>(new ExceptionResponse("Please provide your API key."), HttpStatus.FORBIDDEN);
            response.getWriter().write(objectMapper.writeValueAsString(exceptionResponseResponseEntity));
            response.setStatus(HttpStatus.FORBIDDEN.value());

            return;
        }

        filterChain.doFilter(request, response);
    }

    private String getHeader(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            //Returns the string that comes after "Bearer ": which is the token
            return authHeader.substring(7);
        }
        return null;
    }
}
