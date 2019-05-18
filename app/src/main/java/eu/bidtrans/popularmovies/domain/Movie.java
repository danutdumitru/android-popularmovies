package eu.bidtrans.popularmovies.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Movie implements Serializable {
    private long id;
    private boolean video;
    private double voteAverage;
    private String title;
    private String posterPath;
    private String originalTitle;
    private String overview;
    private Date releaseDate;
}
