package link.nick.com.moviedb.activity;

import android.app.ProgressDialog;
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
import android.widget.Toast;

import link.nick.com.moviedb.R;
import link.nick.com.moviedb.adapter.RecyclerClick;
import link.nick.com.moviedb.fragments.Exit;
import link.nick.com.moviedb.fragments.Search;
import link.nick.com.moviedb.fragments.TopRated;

public class MainActivity extends AppCompatActivity implements RecyclerClick {

    static String TAG = MainActivity.class.getSimpleName();
    private CoordinatorLayout coordinatorLayout;

    private Toolbar toolbar;
    BottomNavigationView navigationView;

    ProgressDialog dialog;
    Fragment topRated, search, exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.root);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (BottomNavigationView) findViewById(R.id.navigation);
        setSupportActionBar(toolbar);

        topRated = getSupportFragmentManager().findFragmentById(R.id.container);
        if (topRated == null) {
            topRated = new TopRated();
            getSupportFragmentManager().
                    beginTransaction().add(R.id.container, topRated)
                    .commit();
        }

        search = new Search();
        exit = new Exit();


        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                switch (id) {
                    case R.id.action_item1:
                        getSupportFragmentManager().
                                beginTransaction().replace(R.id.container, topRated)
                                .commit();
//                        showSnack(getString(R.string.item_1));
                        return true;
                    case R.id.action_item2:
                        getSupportFragmentManager().
                                beginTransaction().replace(R.id.container, search)
                                .commit();
//                        showSnack(getString(R.string.item_2));
                        return true;
                    case R.id.action_item3:
                        getSupportFragmentManager().
                                beginTransaction().replace(R.id.container, exit)
                                .commit();
//                        showSnack(getString(R.string.item_3));
                        return true;
                }

                return true;
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
                showSnack(getString(R.string.refresh));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(int position, int id) {
        showSnack("Pressed position " + position
                + " item " + id);

    }

    private void showSnack(String message){
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.try_again, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "Respect!", Toast.LENGTH_LONG).show();
                    }
                })
                .show();
    }

}
