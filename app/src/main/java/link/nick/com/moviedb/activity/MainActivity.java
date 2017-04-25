package link.nick.com.moviedb.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import link.nick.com.moviedb.R;
import link.nick.com.moviedb.adapter.MoviesAdapter;
import link.nick.com.moviedb.adapter.OnRecyclerViewItemClickListener;
import link.nick.com.moviedb.model.DataModel;
import link.nick.com.moviedb.model.Movie;
import link.nick.com.moviedb.model.MovieResponse;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements OnRecyclerViewItemClickListener{

    static String TAG = MainActivity.class.getSimpleName();
    private CoordinatorLayout coordinatorLayout;
    RecyclerView recyclerView;
    private Toolbar toolbar;
    List<Movie> moviesList;
    MoviesAdapter adapter;
    ProgressDialog dialog;
    DataModel model;
    Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.root);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        model = new DataModel(this);
        loadData();
    }

    public void loadData(){
        showLoadingIndicator();
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
                        hideLoadingIndicator();
                        Toast.makeText(MainActivity.this,
                                "Loading error -> " + throwable.getLocalizedMessage(),
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(MovieResponse response) {
                        moviesList = response.getResults();
                        adapter = new MoviesAdapter(moviesList, R.layout.list_item_movie, getApplicationContext());
                        adapter.setOnItemClickListener(MainActivity.this);
                        recyclerView.setAdapter(adapter);
                        hideLoadingIndicator();
                    }
                });
    }


    public void hideLoadingIndicator() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }

    public void showLoadingIndicator() {
        dialog = new ProgressDialog(this);
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_models_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.refresh:
                Log.d(TAG, "refresh clicked");
                loadData();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRecyclerViewItemClicked(int position, int id) {
        Snackbar.make(coordinatorLayout, "Pressed position " + position
                + " item " + id, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.try_again, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "Respect!", Toast.LENGTH_LONG).show();
                    }
                })
                .show();

    }

}
