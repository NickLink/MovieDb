package link.nick.com.moviedb.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import link.nick.com.moviedb.R;
import link.nick.com.moviedb.adapter.MoviesAdapter;
import link.nick.com.moviedb.adapter.RecyclerClick;
import link.nick.com.moviedb.model.DataModel;
import link.nick.com.moviedb.presenter.TopRatedPresenter;
import link.nick.com.moviedb.presenter.TopRatedPresenterImpl;
import link.nick.com.moviedb.presenter.TopRatedView;

/**
 * Created by Nick on 25.04.2017.
 */

public class TopRated extends Fragment implements RecyclerClick, TopRatedView{

    private String TAG = TopRated.class.getSimpleName();
    RecyclerView recyclerView;
    DataModel model;

    MoviesAdapter adapter;

    TopRatedPresenter topRatedPresenter;

    public TopRated() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topRatedPresenter = new TopRatedPresenterImpl();
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

    }

    @Override
    public void onClick(int position, int id) {
        Snackbar.make(recyclerView, "Pressed position " + position
                + " item " + id, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.try_again, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("Respect!");
                    }
                })
                .show();
    }

    @Override
    public void showLoadingIndicator() {

    }

    @Override
    public void hideLoadingIndicator() {

    }

    @Override
    public void setAdapter(MoviesAdapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
