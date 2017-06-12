package link.nick.com.moviedb.rest;

import java.util.List;

import link.nick.com.moviedb.domain.Comments;
import link.nick.com.moviedb.domain.Photos;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by User on 04.05.2017.
 */

public interface ApiInterfaceFake {
    @GET("photos")
    Call<List<Photos>> getPhotosList();

    @GET("comments")
    Call<List<Comments>> getCommentsList();
}
