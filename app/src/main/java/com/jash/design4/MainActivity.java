package com.jash.design4;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.SwipeDismissBehavior;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SwipeDismissBehavior.OnDismissListener, View.OnClickListener {

    private CoordinatorLayout coordinator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView text = (TextView) findViewById(R.id.main_text);
        text.setOnClickListener(this);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) text.getLayoutParams();
        SwipeDismissBehavior<TextView> behavior = new SwipeDismissBehavior<>();
        behavior.setListener(this);
        params.setBehavior(behavior);
        coordinator = (CoordinatorLayout) findViewById(R.id.activity_main);
    }

    @Override
    public void onDismiss(final View view) {
        Snackbar.make(coordinator, "删除控件", Snackbar.LENGTH_LONG)
                .setAction("撤销", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewCompat.setAlpha(view, 1);
                        ViewCompat.setTranslationX(view, 0);
                    }
                })
                .setCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        if (event != Snackbar.Callback.DISMISS_EVENT_ACTION) {
                            coordinator.removeView(view);
                        }
                    }
                })
                .show();
    }

    @Override
    public void onDragStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, SwipeRecyclerActivity.class);
        startActivity(intent);
    }
}
