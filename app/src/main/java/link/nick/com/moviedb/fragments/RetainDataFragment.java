package link.nick.com.moviedb.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.util.List;

import link.nick.com.moviedb.Const;
import link.nick.com.moviedb.database.MoviesDatabase;
import link.nick.com.moviedb.database.MoviesDatabaseImpl;
import link.nick.com.moviedb.domain.Comments;
import link.nick.com.moviedb.domain.Photos;
import link.nick.com.moviedb.model.MovieResponse;
import link.nick.com.moviedb.rest.ApiClient;
import link.nick.com.moviedb.rest.ApiClientFake;
import link.nick.com.moviedb.rest.ApiInterface;
import link.nick.com.moviedb.rest.ApiInterfaceFake;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by User on 04.05.2017.
 */

public class RetainDataFragment extends Fragment {
    private String TAG = RetainDataFragment.class.getSimpleName();

    private List<Comments> commentsList;
    private List<Photos> photosList;
    private boolean isLoaded;
    BehaviorSubject<List<Comments>> commentObservable;


    private MovieResponse movies;
    private MoviesDatabase database;
    private BehaviorSubject<MovieResponse> observableMovieResponse;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = new MoviesDatabaseImpl(getActivity());
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



    //===================================================================
    public void loadData(){
        observableMovieResponse = BehaviorSubject.create();
        if(getListFromDatabase() != null) {
//            listener.getData(getListFromDatabase());
            Log.e(TAG, "-> getListFromDatabase() != null");
            observableMovieResponse.onNext(getListFromDatabase());
        } else {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<MovieResponse> call = apiInterface.getTopRatedMovies(Const.v3auth_key);
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    Log.e(TAG, "-> onResponse");
                    movies = response.body();
                    saveInDatabase(movies);
                    observableMovieResponse.onNext(movies);
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    Log.e(TAG, t.toString());
                    observableMovieResponse.onError(t);
                }
            });

        }
    }

    private MovieResponse getListFromDatabase(){
        return database.getMovies();
    }

    private void saveInDatabase(MovieResponse response){
        database.saveMovies(response);
    }

    public void deleteFromDatabase(){
        database.deleteMovies();
    }

    public Observable<MovieResponse> getObservableMovieResponse(){
        if(observableMovieResponse == null){
            loadData();
        }
        return observableMovieResponse;
    }

    public void onDestroyF(){
//        super.onDestroy();
        observableMovieResponse.subscribe().unsubscribe();

    }
}
