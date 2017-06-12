package link.nick.com.moviedb.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import link.nick.com.moviedb.R;
import link.nick.com.moviedb.domain.Comments;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Nick on 25.04.2017.
 */

public class Exit extends Fragment {
    private String TAG = Exit.class.getSimpleName();
    private ProgressBar progressBar;
    private RetainDataFragment dataFragment;
    private Toast toast;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.exit_fragment, container, false);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showLoadingIndicator();
        dataFragment = (RetainDataFragment)getActivity().getSupportFragmentManager().findFragmentByTag("retain");
        dataFragment.startComments();
        dataFragment.commentObservable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Comments>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        hideLoadingIndicator();
                        showToast("Load of comments FAILED-> " +
                                throwable.getLocalizedMessage() + " items");
                    }

                    @Override
                    public void onNext(List<Comments> commentses) {
                        hideLoadingIndicator();
                        showToast("Load of comments completed, Loaded-> " +
                                commentses.size() + " items");
                    }
                });
//        apiInterface.getPhotosList().enqueue(new Callback<List<Photos>>() {
//            @Override
//            public void onResponse(Call<List<Photos>> call, Response<List<Photos>> response) {
//                hideLoadingIndicator();
//                Toast.makeText(getActivity(), "Load of photoList completed, Loaded-> " +
//                        response.body().size() + " items", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<List<Photos>> call, Throwable throwable) {
//
//            }
//        });

//        apiInterface.getCommentsList().enqueue(new Callback<List<Comments>>() {
//            @Override
//            public void onResponse(Call<List<Comments>> call, Response<List<Comments>> response) {
//                hideLoadingIndicator();
//                Toast.makeText(getActivity(), "Load of comments completed, Loaded-> " +
//                        response.body().size() + " items", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<List<Comments>> call, Throwable throwable) {
//                hideLoadingIndicator();
//                Toast.makeText(getActivity(), "Load of comments FAILED-> " +
//                        throwable.getLocalizedMessage() + " items", Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    public void showLoadingIndicator() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideLoadingIndicator() {
        progressBar.setVisibility(View.GONE);
    }

    private void showToast(String message){
        if(toast != null){
            toast.cancel();
        }
        toast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
        toast.show();
    };

}
