package com.example.lenovo.cnews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Lenovo on 16-08-2016.
 */
public class ItemDecoratorAdapterDekhNews extends RecyclerView.ItemDecoration {
    private Drawable mDivider;
    Context mContext;

    public ItemDecoratorAdapterDekhNews(Context context) {
        mContext = context;
        mDivider = ResourcesCompat.getDrawable(context.getResources(), R.drawable.divider, null);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin + 5;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}
