package link.nick.com.moviedb.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by User on 04.05.2017.
 */
@Getter
@Setter
@NoArgsConstructor
public class Comments {
    @SerializedName("postId")
    @Expose
    public Integer postId;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("body")
    @Expose
    public String body;

}