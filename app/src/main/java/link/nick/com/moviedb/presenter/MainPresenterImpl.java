package link.nick.com.moviedb.presenter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import link.nick.com.moviedb.R;
import link.nick.com.moviedb.fragments.Exit;
import link.nick.com.moviedb.fragments.Search;
import link.nick.com.moviedb.fragments.TopRated;

/**
 * Created by User on 26.04.2017.
 */

public class MainPresenterImpl implements MainPresenter {

    private String TAG = MainPresenterImpl.class.getSimpleName();
    Context context;
    android.support.v4.app.Fragment topRated, search, exit;
    MainView mainView;
    private int alertCount = 0;
    private int fragmentId = 0;

    public MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public android.support.v4.app.Fragment getFragment(int id) {
        this.fragmentId = id;
        switch (fragmentId) {
            case 0:
                topRated = new TopRated();
                mainView.showSnack("From presenter TopRated");
                return topRated;
            case R.id.action_item1:
                topRated = new TopRated();
                mainView.showSnack("From presenter TopRated");
                return topRated;
            case R.id.action_item2:
                search = new Search();
                mainView.showSnack("From presenter Search");
                return search;
            case R.id.action_item3:
                exit = new Exit();
                mainView.showSnack("From presenter Exit");
                return exit;
        }
        return null;
    }

    @Override
    public void onOptionsItemClick(int itemId) {
        switch (itemId) {
            case R.id.refresh:
                alertCount = (alertCount + 1) % 10;
                String message;
                int visibility;
                // if alert count extends into two digits, just show the red circle
                if (0 < alertCount && alertCount < 10) {
                    message = String.valueOf(alertCount);
                } else {
                    message = "";
                }
                visibility = (alertCount > 0) ? View.VISIBLE : View.GONE;
                mainView.updateAlertIcon(message, visibility);
                break;
        }
    }

    @Override
    public void saveInstanseState(Bundle outState) {
        outState.putInt("count", alertCount);
        outState.putInt("fragment", fragmentId);
    }

    @Override
    public void restoreInstanseState(Bundle savedInstanceState) {
        if(savedInstanceState != null){
            alertCount = savedInstanceState.getInt("count");
            fragmentId = savedInstanceState.getInt("fragment");
        }
        mainView.showFragment(getFragment(fragmentId));
    }

    @Override
    public void setMenuStatus() {
        if(alertCount != 0)
            mainView.updateAlertIcon(String.valueOf(alertCount), View.VISIBLE);
    }

    @Override
    public void navigationItemClick(int itemId) {
        mainView.showFragment(getFragment(itemId));
    }

    @Override
    public int getFragmentId() {
        return fragmentId;
    }

    @Override
    public int getAlertCount(){
        return this.alertCount;
    }

}
