package br.com.sky.domian.api;


import java.util.List;

import br.com.sky.domian.model.Movie;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface MovieAPIService {

    @GET("/api/Movies")
    Call<List<Movie>> getPopularMovies();

}
