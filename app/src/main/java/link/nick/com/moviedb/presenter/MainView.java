package link.nick.com.moviedb.presenter;

import android.support.v4.app.Fragment;

/**
 * Created by User on 26.04.2017.
 */

public interface MainView {
    void showSnack(String message);
    void updateAlertIcon(String count, int visible);
    void showFragment(Fragment fragment, String tag);
    void startShare(String message);
}
