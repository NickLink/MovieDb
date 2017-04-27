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
import android.widget.ProgressBar;
import android.widget.Toast;

import link.nick.com.moviedb.R;
import link.nick.com.moviedb.adapter.MoviesAdapter;
import link.nick.com.moviedb.adapter.RecyclerClick;
import link.nick.com.moviedb.presenter.TopRatedPresenter;
import link.nick.com.moviedb.presenter.TopRatedPresenterImpl;
import link.nick.com.moviedb.presenter.TopRatedView;

/**
 * Created by Nick on 25.04.2017.
 */

public class TopRated extends Fragment implements RecyclerClick, TopRatedView{

    private String TAG = TopRated.class.getSimpleName();
    private RecyclerView recyclerView;
    private TopRatedPresenter topRatedPresenter = new TopRatedPresenterImpl(this);;
    private ProgressBar progressBar;

//    DataModel model;
//    MoviesAdapter adapter;

    public TopRated() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.toprated_fragment, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.movies_recycler_view);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return v;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        topRatedPresenter.loadData(getActivity());
    }

    @Override
    public void onClick(int position, int id) {
        topRatedPresenter.recyclerClick(position, id);
    }

    @Override
    public void showLoadingIndicator() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setAdapter(MoviesAdapter adapter) {
        adapter.setOnClick(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showSnack(String message) {
        Snackbar.make(recyclerView, message, Snackbar.LENGTH_SHORT)
                .setAction(R.string.try_again, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("Respect!");
                    }
                })
                .show();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }


}
