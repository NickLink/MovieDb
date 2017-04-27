package link.nick.com.moviedb.presenter;

import link.nick.com.moviedb.adapter.MoviesAdapter;

/**
 * Created by User on 26.04.2017.
 */

public interface TopRatedView {
    void showLoadingIndicator();
    void hideLoadingIndicator();
    void setAdapter(MoviesAdapter adapter);
    void showSnack(String message);
    void showToast(String message);
}
