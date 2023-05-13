package com.damlacim.fitme.feature.dashboard.adapter.decoration;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class HorizontalSpaceItemDecoration extends RecyclerView.ItemDecoration {
    private final int horizontalSpaceWidth;

    public HorizontalSpaceItemDecoration(int horizontalSpaceWidth, Context context) {
        this.horizontalSpaceWidth = (int) (horizontalSpaceWidth * context.getResources().getDisplayMetrics().density);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
            outRect.right = horizontalSpaceWidth;
        }
    }
}