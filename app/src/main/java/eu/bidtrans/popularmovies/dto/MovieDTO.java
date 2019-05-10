package eu.bidtrans.popularmovies.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MovieDTO {
    private long voteCount;
    private long id;
    private boolean video;
    private double voteAverage;
    private String title;
    private double popularity;
    private String posterPath;
    private String originalLanguage;
    private String originalTitle;
    private long[] genreIds;
    private String backdropPath;
    private boolean adult;
    private String overview;
    private Date releaseDate;


}
