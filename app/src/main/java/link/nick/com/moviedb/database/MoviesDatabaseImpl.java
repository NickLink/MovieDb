package link.nick.com.moviedb.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import link.nick.com.moviedb.model.MovieResponse;

/**
 * Created by User on 25.04.2017.
 */

public class MoviesDatabaseImpl implements MoviesDatabase {
    private Context context;
    private MovieResponse response;
    private static final String PREF_MY_OBJECT = "pref_my_object";
    private SharedPreferences prefs;
    private Gson gson;

    public MoviesDatabaseImpl(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        gson = new GsonBuilder().create();
    }

    @Override
    public void saveMovies(MovieResponse response) {
        if (response == null) {
            prefs.edit().putString(PREF_MY_OBJECT, "").commit();
        } else {
            prefs.edit().putString(PREF_MY_OBJECT, gson.toJson(response)).commit();
        }
        this.response = response;
    }

    @Override
    public MovieResponse getMovies() {
        if (response == null) {
            String savedValue = prefs.getString(PREF_MY_OBJECT, "");
            if (savedValue.equals("")) {
                response = null;
            } else {
                response = gson.fromJson(savedValue, MovieResponse.class);
            }
        }
        return response;
    }
}
