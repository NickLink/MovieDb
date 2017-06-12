package link.nick.com.moviedb.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import link.nick.com.moviedb.R;
import link.nick.com.moviedb.adapter.RecyclerClick;
import link.nick.com.moviedb.fragments.RetainDataFragment;
import link.nick.com.moviedb.presenter.MainPresenter;
import link.nick.com.moviedb.presenter.MainPresenterImpl;
import link.nick.com.moviedb.presenter.MainView;

public class MainActivity extends AppCompatActivity implements RecyclerClick, MainView {

    private String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.root) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.toolbar) Toolbar toolbar;

    private BottomNavigationView navigationView;
    private FrameLayout redCircle;
    private TextView countTextView;
    private MainPresenter presenter;
    private RetainDataFragment dataFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.root);
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (dataFragment == null) {
            dataFragment = new RetainDataFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(dataFragment, "retain")
                    .commit();

        }


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

    public RetainDataFragment getDataFragment() {
        return dataFragment;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        final MenuItem alertMenuItem = menu.findItem(R.id.refresh);
        FrameLayout rootView = (FrameLayout) alertMenuItem.getActionView();
//        redCircle = (FrameLayout) rootView.findViewById(R.id.view_alert_red_circle);
//        countTextView = (TextView) rootView.findViewById(R.id.view_alert_count_textview);
        redCircle = ButterKnife.findById(rootView, R.id.view_alert_red_circle);
        countTextView = ButterKnife.findById(rootView, R.id.view_alert_count_textview);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(alertMenuItem);
            }
        });

        final MenuItem shareMenuItem = menu.findItem(R.id.share);
        FrameLayout shareView = (FrameLayout) shareMenuItem.getActionView();
        shareView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(shareMenuItem);
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
        Log.e(TAG, "-->> onOptionsItemSelected " + item.getItemId());
        presenter.onOptionsItemClick(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    public void showFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().
                beginTransaction()
                .replace(R.id.container, fragment, tag)
                .commit();
    }

    @Override
    public void startShare(String message) {
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setChooserTitle("Choose")
                .setType("text/plain")
                .setText(message)
                .getIntent();
        try {
            startActivity(shareIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "None to share", Toast.LENGTH_SHORT).show();
        }


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
