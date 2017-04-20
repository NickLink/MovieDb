package link.nick.com.moviedb.model;

import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Nick on 18.04.2017.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @SerializedName("poster_path")
    public String posterPath;
    @SerializedName("adult")
    public Boolean adult;
    @SerializedName("overview")
    public String overview;
    @SerializedName("release_date")
    public String releaseDate;
    @SerializedName("genre_ids")
    public List<Integer> genreIds = new ArrayList<Integer>();
    @SerializedName("id")
    public Integer id;
    @SerializedName("original_title")
    public String originalTitle;
    @SerializedName("original_language")
    public String originalLanguage;
    @SerializedName("title")
    public String title;
    @SerializedName("backdrop_path")
    public String backdropPath;
    @SerializedName("popularity")
    public Float popularity;
    @SerializedName("vote_count")
    public Integer voteCount;
    @SerializedName("video")
    public Boolean video;
    @SerializedName("vote_average")
    public Float voteAverage;

    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this);
    }

}

