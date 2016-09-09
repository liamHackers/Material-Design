package com.liamhacker.com.material_design;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.view.View;

/**
 * Created by lilingyong on 16/9/8.
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };

    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    private int mOrientation;
    private Drawable mDivider;

    public DividerItemDecoration(Context context,int orientation) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS );
        mDivider = a.getDrawable(0);
        a.recycle();
        setOrientation(orientation);
    }

    private void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException( "invalid orientation");
        }
        mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }
    /**
     * 画竖直方向的的分割线（当orientation为 Horizontal 时）
     *
     * @param c canvas
     * @param parent recycleview
     *
     */
    private void drawHorizontal(Canvas c, RecyclerView parent) {
        /*
            当orientation为 Horizontal 时，Item的分割线为多条竖直的条形
            所以，分割线的Top和Bottom就比较容易确定
            top = parent.top = parent.paddingTop
            bottom = parent.getHeight() - parent.getPaddingBottom()
            分割线的 left 和 right 则需要计算出有多少个Item
            根据Item的位置获取到child的位置坐标
            所以分割线的left = child的右边的坐标 + child的外边距的距离
            left = child.right + parms.rightMargin
            然后根据左边 + 分割线的宽度 得到右边的坐标
            right = left + mDivider.getIntrinsicHeight()
            为了统一分割线的间隔，故共同使用Height的数值作为间隔的距离
         */

        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    /**
     * 画水平方向的的分割线（当orientation为 Vertical 时）
     *
     * @param c canvas
     * @param parent recycleview
     *
     */
    private void drawVertical(Canvas c, RecyclerView parent) {
        /*
            当orientation为 Vertical 时，Item的分割线为多条水平的条形
            所以，分割线的Left和Right就比较容易确定
            Left = parent.left = parent.paddingLeft
            right = parent.getWidth() - parent.getPaddingRight
            分割线的 Top 和 Bottom 则需要计算出有多少个Item
            根据Item的位置获取到child的位置坐标
            所以分割线的Top = child的下边的坐标 + child的外边距的距离
            top = child.getBottom() + parms.bottomMargin
            然后根据上边 + 分割线的高度 得到右边的坐标
            bottom = top + mDivider.getIntrinsicHeight()
            为了统一分割线的间隔，故共同使用Height的数值作为间隔的距离
         */
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        /*
     *  outRect.set(left, top, right, bottom);
     *  在Item的四周设定距离
     *  所以当Orientation为垂直时，我们只需要在每个Item的下方预留出分割线的高度就可以了
     *  同理当Orientation为水平时，我们只需要在每个Item的右方预留出分割线的宽度就可以了
     *  但通常我们使用分割线的style都是统一的，这样我们在attrs中只需要定义一个即可，即共同使用Height
     */
        if (mOrientation == VERTICAL_LIST) {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        }else{
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }
    }
}