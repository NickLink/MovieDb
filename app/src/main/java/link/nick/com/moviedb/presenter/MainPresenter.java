package link.nick.com.moviedb.presenter;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by User on 26.04.2017.
 */

public interface MainPresenter {
    Fragment getFragment(int id);
    void onOptionsItemClick(int itemId);
    void saveInstanseState(Bundle outState);
    void restoreInstanseState(Bundle savedInstanceState);
    void setMenuStatus();
    void navigationItemClick(int itemId);
}
