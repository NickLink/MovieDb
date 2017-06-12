package link.nick.com.moviedb.presenter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

import link.nick.com.moviedb.R;
import link.nick.com.moviedb.adapter.MoviesAdapter;
import link.nick.com.moviedb.fragments.RetainDataFragment;
import link.nick.com.moviedb.model.DataModel;
import link.nick.com.moviedb.model.Movie;
import link.nick.com.moviedb.model.MovieResponse;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by User on 26.04.2017.
 */

public class TopRatedPresenterImpl implements TopRatedPresenter {

    private String TAG = TopRatedPresenterImpl.class.getSimpleName();
    private DataModel model;
    private List<Movie> moviesList;
    private MoviesAdapter adapter;
    private TopRatedView topRatedView;
    private RetainDataFragment data;
    private Context mContext;

    public TopRatedPresenterImpl(TopRatedView topRatedView) {
        this.topRatedView = topRatedView;
    }

    @Override
    public void loadData(Context context) {
        mContext = context;
        topRatedView.showLoadingIndicator();
        AppCompatActivity activity = (AppCompatActivity) mContext;
        data = (RetainDataFragment)activity.getSupportFragmentManager().findFragmentByTag("retain");
        data.getObservableMovieResponse()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MovieResponse>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, " -> onCompleted ");
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        topRatedView.hideLoadingIndicator();
                        topRatedView.showToast("Loading error -> " + throwable.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(MovieResponse response) {
                        Log.e(TAG, " -> onNext " + response.getResults().toString());
                        moviesList = response.getResults();
                        topRatedView.setAdapter(new MoviesAdapter(moviesList, R.layout.list_item_movie, mContext));
                        topRatedView.hideLoadingIndicator();
                    }
                });

//        model = new DataModel(context);
//        //model.loadData();
//        model.getObservableMovieResponse()
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<MovieResponse>() {
//                    @Override
//                    public void onCompleted() {
//                        Log.e(TAG, " -> onCompleted ");
//                    }
//
//                    @Override
//                    public void onError(Throwable throwable) {
//                        topRatedView.hideLoadingIndicator();
//                        topRatedView.showToast("Loading error -> " + throwable.getLocalizedMessage());
//                    }
//
//                    @Override
//                    public void onNext(MovieResponse response) {
//                        moviesList = response.getResults();
//                        topRatedView.setAdapter(new MoviesAdapter(moviesList, R.layout.list_item_movie, context));
//                        topRatedView.hideLoadingIndicator();
//                    }
//                });

    }

    @Override
    public void deleteData() {
        data.deleteFromDatabase();
    }

    @Override
    public void recyclerClick(int position, int id) {
        topRatedView.showSnack("Hello from " + position + " & " + id);
    }

    @Override
    public void onDestroy() {
        data.onDestroyF();
    }

}
