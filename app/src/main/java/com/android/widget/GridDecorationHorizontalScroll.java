package com.android.widget;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GridDecorationHorizontalScroll extends RecyclerView.ItemDecoration {
    private int mPadding;
    private int mLeftOffset;
    private int mTopOffset;

    public GridDecorationHorizontalScroll(int padding, int leftOffset, int topOffset) {
        this.mPadding = padding;
        this.mLeftOffset = leftOffset;
        this.mTopOffset = topOffset;
    }

    public GridDecorationHorizontalScroll(@NonNull Context context, @DimenRes int padding, @DimenRes int leftOffset, @DimenRes int mTopOffset) {
        this(context.getResources().getDimensionPixelSize(padding),
                context.getResources().getDimensionPixelSize(leftOffset),
                context.getResources().getDimensionPixelSize(mTopOffset));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildAdapterPosition(view);

        outRect.top = mTopOffset;
        outRect.bottom = mTopOffset;

        if (position == 0) {
            outRect.left = mLeftOffset + mPadding;
            outRect.right = mLeftOffset;
        } else if (position == parent.getAdapter().getItemCount() - 1) {
            outRect.left = mLeftOffset;
            outRect.right = mLeftOffset + mPadding;
        } else {
            outRect.left = mLeftOffset;
            outRect.right = mLeftOffset;
        }
    }
}