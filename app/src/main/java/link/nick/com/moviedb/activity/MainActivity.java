package link.nick.com.moviedb.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import link.nick.com.moviedb.Const;
import link.nick.com.moviedb.R;
import link.nick.com.moviedb.adapter.MoviesAdapter;
import link.nick.com.moviedb.model.DataModel;
import link.nick.com.moviedb.model.Movie;
import link.nick.com.moviedb.model.MovieResponse;
import link.nick.com.moviedb.rest.ApiClient;
import link.nick.com.moviedb.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    static String TAG = MainActivity.class.getSimpleName();
    private Toolbar toolbar;
    DataModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieResponse> call = apiInterface.getTopRatedMovies(Const.v3auth_key);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<Movie> movies = response.body().getResults();
                Log.d(TAG, movies.toString());
                recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });


//        model
//                //.compose(bindToLifecycle()) // rxLifecycle
//                .getRecords()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        records -> {
//                            // TODO show result here
//                            recyclerView.setAdapter(new MoviesAdapter(records, R.layout.list_item_movie, MainActivity.this));
//                        },
//                        throwable -> {
//                            // TODO show error here
//                        });


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

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
