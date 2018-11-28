package br.com.sky.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import br.com.sky.R;
import br.com.sky.domian.model.Movie;
import butterknife.BindView;
import butterknife.ButterKnife;


public class PaginationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private List<Movie> movieResults;
    private List<Movie> movieResultsBackup;
    private Context context;
    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;
    private boolean reload = true;
    private PaginationAdapterCallback mCallback;
    private final OnItemClickListener clickListener;
    private String errorMsg;
    private static final String BASE_IMAGE_URL="https://image.tmdb.org/t/p/w300/";

    public PaginationAdapter(Context context, OnItemClickListener clickListener) {

        this.context = context;
        this.mCallback = (PaginationAdapterCallback) context;
        this.clickListener = clickListener;
        movieResults = new ArrayList<>();
        movieResultsBackup = new ArrayList<>();

    }

    public boolean canReload() {
        return reload;
    }

    public List<Movie> getMovies() {
        return movieResults;
    }

    public void setMovies(List<Movie> movieResults) {
        this.movieResults = movieResults;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                View viewItem = inflater.inflate(R.layout.item_list, parent, false);
                viewHolder = new MovieVH(viewItem);
                break;
            case LOADING:
                View viewLoading = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(viewLoading);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Movie result = movieResults.get(position);

        switch (getItemViewType(position)) {


            case ITEM:
                final MovieVH movieVH = (MovieVH) holder;

                movieVH.mMovieTitle.setText(result.getTitle());

                Picasso.get().load(getCorrectedImageURL(result.getCoverUrl())).placeholder(R.drawable.place_holder).into(movieVH.mPosterImg);

                break;

            case LOADING:
                LoadingVH loadingVH = (LoadingVH) holder;

                if (retryPageLoad) {
                    loadingVH.mErrorLayout.setVisibility(View.VISIBLE);
                    loadingVH.mProgressBar.setVisibility(View.GONE);

                    loadingVH.mErrorTxt.setText(
                            errorMsg != null ?
                                    errorMsg :
                                    context.getString(R.string.error_msg_unknown));

                } else {
                    loadingVH.mErrorLayout.setVisibility(View.GONE);
                    loadingVH.mProgressBar.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    private String getCorrectedImageURL(String url){

        String imageName = url.substring(url.lastIndexOf("/")+1);

        return BASE_IMAGE_URL+imageName;

    }

    @Override
    public int getItemCount() {

        if (movieResults != null) {
            return movieResults.size();
        } else {
            return 0;
        }

    }

    @Override
    public int getItemViewType(int position) {

        return (position == movieResults.size() - 1 && isLoadingAdded) ? LOADING : ITEM;

    }


    public void add(Movie r) {
        movieResults.add(r);
        notifyItemInserted(movieResults.size() - 1);
    }

    public void addAll(List<Movie> movieResults) {
        restoreData();
        for (Movie result : movieResults) {
            add(result);
        }
        copyData();
    }

    public void copyData() {
        movieResultsBackup.clear();
        movieResultsBackup.addAll(movieResults);
    }

    public void restoreData() {
        movieResults.clear();
        movieResults.addAll(movieResultsBackup);
    }

    public void remove(Movie r) {
        int position = movieResults.indexOf(r);
        if (position > -1) {
            movieResults.remove(position);
            notifyItemRemoved(position);
        }
        copyData();
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
        copyData();
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Movie());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = movieResults.size() - 1;
        Movie result = getItem(position);

        if (result != null) {
            movieResults.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Movie getItem(int position) {
        return movieResults.get(position);
    }


    public void showRetry(boolean show, @Nullable String errorMsg) {
        retryPageLoad = show;
        notifyItemChanged(movieResults.size() - 1);

        if (errorMsg != null) this.errorMsg = errorMsg;
    }


    protected class MovieVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.movie_title)
        TextView mMovieTitle;
        @BindView(R.id.imageviewMovie)
        ImageView mPosterImg;
        @BindView(R.id.movie_progress)
        ProgressBar mProgress;


        public MovieVH(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getItem(getPosition()));
        }
    }


    protected class LoadingVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.loadmore_progress)
        ProgressBar mProgressBar;
        @BindView(R.id.loadmore_retry)
        ImageButton mRetryBtn;
        @BindView(R.id.loadmore_errortxt)
        TextView mErrorTxt;
        @BindView(R.id.loadmore_errorlayout)
        LinearLayout mErrorLayout;

        public LoadingVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mRetryBtn.setOnClickListener(this);
            mErrorLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.loadmore_retry:
                case R.id.loadmore_errorlayout:
                    showRetry(false, null);
                    mCallback.retryPageLoad();
                    break;
            }
        }
    }

    public interface OnItemClickListener {
        public void onClick(View view, Movie item);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    restoreData();
                    reload = true;
                } else {
                    reload = false;
                    List<Movie> filteredList = new ArrayList<>();
                    for (Movie result : movieResultsBackup) {
                        if (result.getTitle().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(result);
                        }
                    }
                    movieResults = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = movieResults;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                movieResults = (ArrayList<Movie>) filterResults.values;

                notifyDataSetChanged();
            }
        };
    }

}
