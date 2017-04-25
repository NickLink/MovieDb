package link.nick.com.moviedb.model;

import android.content.Context;
import android.util.Log;

import link.nick.com.moviedb.Const;
import link.nick.com.moviedb.database.MoviesDatabase;
import link.nick.com.moviedb.database.MoviesDatabaseImpl;
import link.nick.com.moviedb.rest.ApiClient;
import link.nick.com.moviedb.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by Nick on 19.04.2017.
 */

public class DataModel {

    private String TAG = DataModel.class.getSimpleName();
    private MovieResponse movies;
    private MoviesDatabase database;
    private BehaviorSubject<MovieResponse> observableMovieResponse;

    public DataModel(Context context) {
        database = new MoviesDatabaseImpl(context);
    }

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

    public Observable<MovieResponse> getObservableMovieResponse(){
        if(observableMovieResponse == null){
            loadData();
        }
        return observableMovieResponse;
    }

}
