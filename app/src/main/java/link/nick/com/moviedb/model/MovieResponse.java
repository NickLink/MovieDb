package link.nick.com.moviedb.model;

import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

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
public class MovieResponse {
    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private List<Movie> results;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
