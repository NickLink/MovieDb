package link.nick.com.moviedb.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import link.nick.com.moviedb.R;
import link.nick.com.moviedb.adapter.RecyclerClick;
import link.nick.com.moviedb.presenter.MainPresenter;
import link.nick.com.moviedb.presenter.MainPresenterImpl;
import link.nick.com.moviedb.presenter.MainView;

public class MainActivity extends AppCompatActivity implements RecyclerClick, MainView {

    private String TAG = MainActivity.class.getSimpleName();
    private CoordinatorLayout coordinatorLayout;
    private Toolbar toolbar;
    private BottomNavigationView navigationView;
    private FrameLayout redCircle;
    private TextView countTextView;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.root);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (BottomNavigationView) findViewById(R.id.navigation);
        setSupportActionBar(toolbar);

        presenter = new MainPresenterImpl(this);
        presenter.restoreInstanseState(savedInstanceState);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView
                .OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                presenter.navigationItemClick(item.getItemId());
                return true;
            }
        });
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        final MenuItem alertMenuItem = menu.findItem(R.id.refresh);
        FrameLayout rootView = (FrameLayout) alertMenuItem.getActionView();
        redCircle = (FrameLayout) rootView.findViewById(R.id.view_alert_red_circle);
        countTextView = (TextView) rootView.findViewById(R.id.view_alert_count_textview);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(alertMenuItem);
            }
        });
        presenter.setMenuStatus();
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_models_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        presenter.onOptionsItemClick(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    public void showFragment(Fragment fragment) {
        getSupportFragmentManager().
                beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    public void updateAlertIcon(String count, int visible) {
        countTextView.setText(count);
        redCircle.setVisibility(visible);

    }

    @Override
    public void onClick(int position, int id) {
        showSnack("Pressed position " + position
                + " item " + id);
    }

    @Override
    public void showSnack(String message) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.try_again, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "Respect!", Toast.LENGTH_LONG).show();
                    }
                }).show();
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.saveInstanseState(outState);
    }

}
