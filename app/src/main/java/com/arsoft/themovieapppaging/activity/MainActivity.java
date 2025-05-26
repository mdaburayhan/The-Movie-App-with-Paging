package com.arsoft.themovieapppaging.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.arsoft.themovieapppaging.R;
import com.arsoft.themovieapppaging.adapter.MoviesAdapter;
import com.arsoft.themovieapppaging.adapter.MoviesLoadStateAdapter;
import com.arsoft.themovieapppaging.databinding.ActivityMainBinding;
import com.arsoft.themovieapppaging.util.GridSpace;
import com.arsoft.themovieapppaging.util.MovieComparator;
import com.arsoft.themovieapppaging.util.Utils;
import com.arsoft.themovieapppaging.viewmodel.MovieViewModel;
import com.bumptech.glide.RequestManager;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    MovieViewModel mainActivityViewModel;
    ActivityMainBinding binding;
    MoviesAdapter moviesAdapter;

    @Inject
    RequestManager requestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (Utils.API_KEY == null || Utils.API_KEY.isEmpty()){
            Toast.makeText(this, "Error in API Key", Toast.LENGTH_SHORT).show();
        }

        moviesAdapter = new MoviesAdapter(new MovieComparator(), requestManager);
        mainActivityViewModel = new ViewModelProvider(this).get(MovieViewModel.class);

        initRecyclerViewAndAdapter();

        // Subscribe to paging data
        mainActivityViewModel.moviePagingDataFlowable.subscribe(moviePagingData -> {
            moviesAdapter.submitData(getLifecycle(), moviePagingData);
        });



    }

    private void initRecyclerViewAndAdapter() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        binding.recyclerViewMovies.setLayoutManager(gridLayoutManager);

        binding.recyclerViewMovies.addItemDecoration(new GridSpace(2,20,true));

        binding.recyclerViewMovies.setAdapter(
                moviesAdapter.withLoadStateFooter(
                        new MoviesLoadStateAdapter(view -> {
                            moviesAdapter.retry();
                        })
                )
        );


        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return moviesAdapter.getItemViewType(position) == MoviesAdapter.LOADING_ITEM ? 1 : 2;
            }
        });

    }
}