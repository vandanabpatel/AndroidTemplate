package com.android.widget;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GridItemDecorationHorizontal extends RecyclerView.ItemDecoration {
    private int mLeftOffset;
    private int mTopBottomOffset;

    public GridItemDecorationHorizontal(int leftOffset, int topBottomOffset) {
        this.mLeftOffset = leftOffset;
        this.mTopBottomOffset = topBottomOffset;
    }

    public GridItemDecorationHorizontal(@NonNull Context context, @DimenRes int leftOffset, @DimenRes int topBottomOffset) {
        this(context.getResources().getDimensionPixelSize(leftOffset),
                context.getResources().getDimensionPixelSize(topBottomOffset));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildAdapterPosition(view);

        outRect.top = mTopBottomOffset;
        outRect.bottom = mTopBottomOffset;
        outRect.left = mLeftOffset;
        outRect.right = mLeftOffset;
    }
}