package com.android.widget;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GridDecoration extends RecyclerView.ItemDecoration {
    private int mOffset;

    public GridDecoration(int leftOffset) {
        this.mOffset = leftOffset;
    }

    public GridDecoration(@NonNull Context context, @DimenRes int leftOffset) {
        this(context.getResources().getDimensionPixelSize(leftOffset));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getLayoutManager() instanceof GridLayoutManager) {
            GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
            int spanCount = layoutManager.getSpanCount();
            int position = parent.getChildAdapterPosition(view);

            if (position >= 0) {
                int column = position % spanCount; // item column

                outRect.left = mOffset - column * mOffset / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * mOffset / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = mOffset;
                }
                outRect.bottom = mOffset; // item bottom
            } else {
                outRect.left = 0;
                outRect.right = 0;
                outRect.top = 0;
                outRect.bottom = 0;
            }
        }
    }
}