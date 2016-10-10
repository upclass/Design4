package com.jash.design4;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.SwipeDismissBehavior;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class SwipeAdapter extends RecyclerView.Adapter<SwipeAdapter.ViewHolder> implements SwipeDismissBehavior.OnDismissListener {
    private Context context;
    private List<String> list;
    private RecyclerView recyclerView;
    private CoordinatorLayout layout;

    public SwipeAdapter(Context context, List<String> list) {
        this.context = context;
        layout = ((CoordinatorLayout) ((Activity) context).findViewById(R.id.activity_swipe_recycler));
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item, parent, false));
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) holder.text.getLayoutParams();
        SwipeDismissBehavior<TextView> behavior = new SwipeDismissBehavior<>();
        behavior.setListener(this);
        params.setBehavior(behavior);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.text.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDismiss(View view) {
        int position = recyclerView.getChildAdapterPosition((View) view.getParent());
        String remove = list.remove(position);
        ViewCompat.setTranslationX(view, 0);
        ViewCompat.setAlpha(view, 1);
        notifyItemRemoved(position);
        Snackbar.make(layout, remove + " 被删除了", Snackbar.LENGTH_LONG)
                .setAction("撤销", new reset(position, remove))
                .show();
    }

    private class reset implements View.OnClickListener {
        private int position;
        private String data;

        public reset(int position, String data) {
            this.position = position;
            this.data = data;
        }

        @Override
        public void onClick(View v) {
            list.add(position, data);
            notifyItemInserted(position);
        }
    }
    @Override
    public void onDragStateChanged(int state) {

    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            text = ((TextView) itemView.findViewById(R.id.item_text));
        }
    }
}
