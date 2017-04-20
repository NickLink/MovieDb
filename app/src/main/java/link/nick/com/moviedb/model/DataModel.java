package link.nick.com.moviedb.model;

import java.util.List;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by Nick on 19.04.2017.
 */

public class DataModel {

    private String TAG = DataModel.class.getSimpleName();
    private boolean cacheIsEmpty = true;
    List<Movie> movies;

    private BehaviorSubject<List<Movie>> recordsSubject = BehaviorSubject.create();

    public Observable<List<Movie>> getRecords() {
        return null; //Observable.<List<Movie>>create(
//                subscriber -> {
//            // some synchronization here - begin
//            if (cacheIsEmpty) {
//                List<Movie> records = loadRecordsFromWeb();
//                saveInDatabase(records);
//            }
//            // some synchronization here - end
//
//            List<Movie> cachedRecords = getListFromDatabase();
//
//            subscriber.onNext(cachedRecords);
//            subscriber.onCompleted();
//        })
//                .flatMap(records -> {
//                    recordsSubject.onNext(records);
//                    return recordsSubject.asObservable();
//                });
    }

    private List<Movie> loadRecordsFromWeb(){

        return movies;
    }

    private List<Movie> getListFromDatabase(){
        return movies;
    }

    private void saveInDatabase(List<Movie> list){
        this.movies = list;
    }

}
