package link.nick.com.moviedb.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.List;

import link.nick.com.moviedb.domain.Comments;
import link.nick.com.moviedb.domain.Photos;
import link.nick.com.moviedb.rest.ApiClientFake;
import link.nick.com.moviedb.rest.ApiInterfaceFake;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.subjects.BehaviorSubject;

/**
 * Created by User on 04.05.2017.
 */

public class RetainDataFragment extends Fragment {
    private List<Comments> commentsList;
    private List<Photos> photosList;
    private boolean isLoaded;
    BehaviorSubject<List<Comments>> commentObservable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retain this Fragment so that it will not be destroyed when an orientation
        // change happens and we can keep our AsyncTask running
        setRetainInstance(true);
    }

    public void startComments() {
        commentObservable = BehaviorSubject.create();
        isLoaded = false;
        ApiInterfaceFake apiInterface = ApiClientFake.getClient().create(ApiInterfaceFake.class);
        apiInterface.getCommentsList().enqueue(new Callback<List<Comments>>() {
            @Override
            public void onResponse(Call<List<Comments>> call, Response<List<Comments>> response) {
                commentsList = response.body();
                isLoaded = true;
                commentObservable.onNext(commentsList);
            }

            @Override
            public void onFailure(Call<List<Comments>> call, Throwable throwable) {
                commentsList = null;
                isLoaded = false;
                commentObservable.onError(throwable);
            }
        });
    }

    public List<Comments> getCommentsList(){
        return commentsList;
    }

    public BehaviorSubject<List<Comments>> getCommentObservable(){
        if(commentObservable == null){
            startComments();
        }
        return commentObservable;
    }
}
