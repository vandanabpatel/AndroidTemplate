package com.android.widget;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemDecorationVertical extends RecyclerView.ItemDecoration {
    private int mLeftOffset;
    private int mRightOffset;
    private int mTopOffset;

    public ItemDecorationVertical(int leftOffset, int rightOffset, int topOffset) {
        this.mLeftOffset = leftOffset;
        this.mTopOffset = topOffset;
        this.mRightOffset = rightOffset;
    }

    public ItemDecorationVertical(@NonNull Context context, @DimenRes int leftOffset, @DimenRes int rightOffset, @DimenRes int topOffset) {
        this(context.getResources().getDimensionPixelSize(leftOffset),
                context.getResources().getDimensionPixelSize(rightOffset),
                context.getResources().getDimensionPixelSize(topOffset));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
//        outRect.set(mLeftOffset, mTopOffset, mRightOffset, mTopOffset);

        int position = parent.getChildAdapterPosition(view);

        outRect.left = mLeftOffset;
        outRect.right = mRightOffset;

        if (position == 0) {
            outRect.top = mTopOffset;
            outRect.bottom = mTopOffset / 2;
        } else if (position == parent.getAdapter().getItemCount() - 1) {
            outRect.top = mTopOffset / 2;
            outRect.bottom = mTopOffset;
        } else {
            outRect.top = mTopOffset / 2;
            outRect.bottom = mTopOffset / 2;
        }
    }
}