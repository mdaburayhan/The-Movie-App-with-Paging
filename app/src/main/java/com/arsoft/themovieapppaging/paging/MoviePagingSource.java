package com.arsoft.themovieapppaging.paging;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingState;
import androidx.paging.rxjava3.RxPagingSource;

import com.arsoft.themovieapppaging.api.APIClient;
import com.arsoft.themovieapppaging.model.Movie;
import com.arsoft.themovieapppaging.model.MovieResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * This class is responsible for loading paginated movie data from a remote API using RxJava and Paging 3.
 */
public class MoviePagingSource extends RxPagingSource<Integer, Movie> {

    /**
     * This method determines the key to use when refreshing the list.
     * It's called when the data is invalidated or the user performs a swipe-to-refresh.
     *
     * Returning `null` means the initial load will start from the first page.
     */
    @Nullable
    @Override
    public Integer getRefreshKey(@NonNull PagingState<Integer, Movie> pagingState) {
        return null; // For simplicity, start from the first page on refresh
    }

    /**
     * This method loads a single page of data from the API.
     * It’s automatically called by the Paging library to fetch new pages as needed.
     *
     * @param loadParams contains information such as the current page key
     * @return a Single that emits a LoadResult (Page or Error)
     */
    @NonNull
    @Override
    public Single<LoadResult<Integer, Movie>> loadSingle(@NonNull LoadParams<Integer> loadParams) {
        try{

            int page = loadParams.getKey() != null ? loadParams.getKey():1;

            return APIClient.getApiInterface()
                    .getMoviesByPage(page)
                    .subscribeOn(Schedulers.io())
                    .map(MovieResponse::getMovies)
                    .map(movies -> toLoadResult(movies, page))
                    .onErrorReturn(LoadResult.Error::new);
        } catch (Exception e) {
            return Single.just(new LoadResult.Error(e));
        }
    }

    /**
     * Helper method to convert a list of movies and page number into a Paging 3 LoadResult.Page.
     *
     * @param movies the list of movies fetched from the API
     * @param page the current page number
     * @return LoadResult.Page for the Paging library to consume
     */
    private LoadResult<Integer, Movie> toLoadResult(List<Movie> movies, int page){
        return new LoadResult.Page(movies,page ==1 ? null : page -1 , page+1);
    }
}

