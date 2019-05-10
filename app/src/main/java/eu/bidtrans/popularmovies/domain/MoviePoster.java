package eu.bidtrans.popularmovies.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MoviePoster {
    private long id;
    private String posterPath;
}
