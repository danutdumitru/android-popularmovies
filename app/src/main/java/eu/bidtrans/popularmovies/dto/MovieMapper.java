package eu.bidtrans.popularmovies.dto;

import eu.bidtrans.popularmovies.domain.Movie;

public class MovieMapper {

    public static final Movie toMovieDetail(MovieDTO dto) {
        if (dto==null) return null;
        return Movie.builder().id(dto.getId())
                .originalTitle(dto.getOriginalTitle())
                .overview(dto.getOverview())
                .posterPath(dto.getPosterPath())
                .voteAverage(dto.getVoteAverage())
                .releaseDate(dto.getReleaseDate())
                .build();
    }
}
