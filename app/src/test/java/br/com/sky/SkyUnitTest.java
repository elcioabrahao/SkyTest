package br.com.sky;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import br.com.sky.domian.api.MovieAPI;
import br.com.sky.domian.api.MovieAPIService;
import br.com.sky.domian.model.Movie;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.*;


public class SkyUnitTest {
    MovieAPIService service;

    @Before
    public void getService(){
        service = MovieAPI.getClient().create(MovieAPIService.class);
    }


    @Test
    public void test_popular_api_call() {

        callPopularApi().enqueue(new Callback<List<Movie>>() {

            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {

                List<Movie> results = (List<Movie>) response.body();

                assertNotEquals(0,results.size());

            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                // nothing
            }
        });
    }

    private Call<List<Movie>> callPopularApi() {
        return service.getPopularMovies();
    }

}