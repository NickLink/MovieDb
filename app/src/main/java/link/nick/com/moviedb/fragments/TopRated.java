package link.nick.com.moviedb.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import link.nick.com.moviedb.R;
import link.nick.com.moviedb.adapter.MoviesAdapter;
import link.nick.com.moviedb.adapter.RecyclerClick;
import link.nick.com.moviedb.model.DataModel;
import link.nick.com.moviedb.model.Movie;
import link.nick.com.moviedb.model.MovieResponse;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Nick on 25.04.2017.
 */

public class TopRated extends Fragment implements RecyclerClick{

    private String TAG = TopRated.class.getSimpleName();
    RecyclerView recyclerView;
    DataModel model;
    List<Movie> moviesList;
    MoviesAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new DataModel(getActivity());
        loadData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.toprated_fragment, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.movies_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        return v;
    }


    public void loadData(){
//        showLoadingIndicator();
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
//                        hideLoadingIndicator();
                        Toast.makeText(getActivity(),
                                "Loading error -> " + throwable.getLocalizedMessage(),
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(MovieResponse response) {
                        moviesList = response.getResults();
                        adapter = new MoviesAdapter(moviesList, R.layout.list_item_movie, getActivity());
                        adapter.setOnClick(TopRated.this);
                        recyclerView.setAdapter(adapter);
//                        hideLoadingIndicator();
                    }
                });
    }

    @Override
    public void onClick(int position, int id) {
        Snackbar.make(recyclerView, "Pressed position " + position
                + " item " + id, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.try_again, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "Respect!", Toast.LENGTH_LONG).show();
                    }
                })
                .show();
    }
}
