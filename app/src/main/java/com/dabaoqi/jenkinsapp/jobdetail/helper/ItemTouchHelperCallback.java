package com.dabaoqi.jenkinsapp.jobdetail.helper;

import android.graphics.Canvas;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.dabaoqi.jenkinsapp.jobdetail.viewholder.FooterViewHolder;

/**
 * Created by zengwendong on 16/11/2.
 */
public class ItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private OnMoveAndSwipedListener onMoveAndSwipedListener;

    public ItemTouchHelperCallback(OnMoveAndSwipedListener onMoveAndSwipedListener) {
        this.onMoveAndSwipedListener = onMoveAndSwipedListener;
    }

    /**
     * 这个方法是用来设置我们拖动的方向以及侧滑的方向的
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags, swipeFlags;
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {//如果是线性布局
            //设置拖拽方向为上下
            //dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            dragFlags = 0;//不支持拖拽

            //设置侧滑方向为从左到右和从右到左都可以
            swipeFlags = ItemTouchHelper.START;
        } else {//如果网络布局
            //设置拖拽方向为上下左右
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            swipeFlags = 0;//不支持侧滑
        }
        if (viewHolder instanceof FooterViewHolder) {
            swipeFlags = 0;
        }
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    /**
     * 当我们拖动item时会回调此方法
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        //如果两个item不是一个类型的，我们让他不可以拖拽
        if (viewHolder.getItemViewType() != target.getItemViewType() || viewHolder instanceof FooterViewHolder) {
            return false;
        }
        //回调adapter中的onItemMove方法
        onMoveAndSwipedListener.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    /**
     * 当我们侧滑item时会回调此方法
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        //回调adapter中的onItemDismiss方法
        onMoveAndSwipedListener.onItemDismiss(viewHolder.getAdapterPosition());
    }

    /**
     * 这个方法可以判断当前是拖拽还是侧滑
     */
    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            //根据侧滑的位移来修改item的透明度
            final float alpha = 1 - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
            viewHolder.itemView.setAlpha(alpha);
            viewHolder.itemView.setTranslationX(dX);
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}
