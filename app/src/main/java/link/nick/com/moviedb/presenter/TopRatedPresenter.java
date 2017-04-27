package link.nick.com.moviedb.presenter;

import android.content.Context;

/**
 * Created by User on 26.04.2017.
 */

public interface TopRatedPresenter {
    void loadData(Context context);
    void recyclerClick(int position, int id);
}
