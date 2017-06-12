package link.nick.com.moviedb.database;

import link.nick.com.moviedb.model.MovieResponse;

/**
 * Created by User on 25.04.2017.
 */

public interface MoviesDatabase {
    void saveMovies(MovieResponse response);
    MovieResponse getMovies();
    void deleteMovies();
}
