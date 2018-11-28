package br.com.sky.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.util.Random;
import br.com.sky.R;
import br.com.sky.domian.model.Movie;
import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailActivity extends AppCompatActivity {


    @BindView(R.id.movie_poster)
    ImageView imageViewMoviePoster;
    @BindView(R.id.movie_name)
    TextView textViewMovieName;
    @BindView(R.id.movie_description)
    TextView textViewMovieDescription;
    @BindView(R.id.movie_title)
    TextView textViewMovieTitle;
    @BindView(R.id.movie_rating)
    TextView textViewMovieRating;
    @BindView(R.id.movie_year)
    TextView textViewMovieYear;

    private Movie movie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        movie = (Movie) intent.getSerializableExtra("movie");
        Random rand = new Random();
        // Escolhe backdrops aleatoriamente entre as 2 disponíveis
        int randomNum = rand.nextInt(2);
        Picasso.get().load(movie.getBackdropsUrl().get(randomNum)).into(imageViewMoviePoster);
        textViewMovieName.setText(movie.getTitle());
        textViewMovieTitle.setText(movie.getTitle());
        textViewMovieDescription.setText(movie.getOverview());
        textViewMovieYear.setText(movie.getReleaseYear());

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
