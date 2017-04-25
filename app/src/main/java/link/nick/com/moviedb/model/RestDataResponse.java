package link.nick.com.moviedb.model;

/**
 * Created by User on 25.04.2017.
 */

public interface RestDataResponse {
    void getData(MovieResponse response);
    void getDataFailed(String error);
}
