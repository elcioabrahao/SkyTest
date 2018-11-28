package br.com.sky.domian.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Movie implements Serializable
{

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("release_year")
    @Expose
    private String releaseYear;
    @SerializedName("cover_url")
    @Expose
    private String coverUrl;
    @SerializedName("backdrops_url")
    @Expose
    private List<String> backdropsUrl = null;
    @SerializedName("id")
    @Expose
    private String id;
    private final static long serialVersionUID = -1471415534645714332L;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public List<String> getBackdropsUrl() {
        return backdropsUrl;
    }

    public void setBackdropsUrl(List<String> backdropsUrl) {
        this.backdropsUrl = backdropsUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}