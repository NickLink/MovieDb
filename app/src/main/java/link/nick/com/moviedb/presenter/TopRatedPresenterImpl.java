package link.nick.com.moviedb.presenter;

import android.content.Context;
import android.util.Log;

import java.util.List;

import link.nick.com.moviedb.R;
import link.nick.com.moviedb.adapter.MoviesAdapter;
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
    private Context context;
    private DataModel model;
    private List<Movie> moviesList;
    private MoviesAdapter adapter;
    private TopRatedView topRatedView;

    public TopRatedPresenterImpl(Context context, TopRatedView topRatedView) {
        this.topRatedView = topRatedView;
        this.context = context;
        model = new DataModel(context);
    }

    @Override
    public void loadData() {
        topRatedView.showLoadingIndicator();

        model.loadData();
        model.getObservableMovieResponse()
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
                        moviesList = response.getResults();
                        topRatedView.setAdapter(new MoviesAdapter(moviesList, R.layout.list_item_movie, context));
                        topRatedView.hideLoadingIndicator();
                    }
                });

    }

    @Override
    public void recyclerClick(int position, int id) {

    }
}
