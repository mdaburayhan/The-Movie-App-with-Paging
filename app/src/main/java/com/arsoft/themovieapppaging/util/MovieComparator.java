package com.arsoft.themovieapppaging.util;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.arsoft.themovieapppaging.model.Movie;

/**
 * MovieComparator is used by the PagingDataAdapter to efficiently determine
 * changes between two Movie objects in a RecyclerView.
 *
 * This improves performance by updating only the changed items
 * instead of refreshing the whole list.
 */
public class MovieComparator extends DiffUtil.ItemCallback<Movie> {


    /**
     * This method checks if two Movie items represent the same entity.
     * Usually, this means they have the same unique ID.
     * If true, then RecyclerView assumes it's the same item and may check for content changes.
     */
    @Override
    public boolean areItemsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
        return oldItem.getId() == newItem.getId();
    }

    /**
     * This method checks whether the contents of two Movie items are exactly the same.
     * If true, no visual update will occur in the RecyclerView for that item.
     *
     * ⚠️ Note: Currently, it's checking only the ID, so even if other fields (like posterPath)
     * change, RecyclerView won’t notice. You should update this method if you want
     * to reflect visual changes when Movie details change.
     */
    @Override
    public boolean areContentsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
        return oldItem.getId() == newItem.getId();
    }
}
