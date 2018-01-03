package com.practice.snaperhelperpractice;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    SnapHelper snapHelper;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        myAdapter = new MyAdapter();
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        for (int i = 0; i < 100; ++i) {
            myAdapter.addData(DummyDataUtil.imageUrlArray[i]);
        }
        myAdapter.bindToRecyclerView(recyclerView);
        snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view);
                if (position == 0 || position == parent.getAdapter().getItemCount() - 1) {
                    RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
                    layoutParams.width = (parent.getWidth() - 200) / 2;
                    view.setLayoutParams(layoutParams);
                    outRect.set(0, 0, 0, 0);
                    view.setVisibility(View.INVISIBLE);
                } else {
                    RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
                    layoutParams.width = 200;
                    view.setLayoutParams(layoutParams);
                    outRect.set(5, 5, 5, 5);
                    view.setVisibility(View.VISIBLE);
                }
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int firstPosition = linearLayoutManager.findFirstVisibleItemPosition();
                int lastPosition = linearLayoutManager.findLastVisibleItemPosition();

                for (int p = firstPosition; p <= lastPosition; ++p) {
                    View view = linearLayoutManager.findViewByPosition(p);
                    int viewCenter = view.getLeft() + view.getWidth() / 2;
                    float scaleFactor = (float) Math.abs(viewCenter - (recyclerView.getLeft() + recyclerView.getWidth() / 2)) / recyclerView.getWidth();
                    view.setScaleX(1 - scaleFactor);
                    view.setScaleY(1 - scaleFactor);
                }

            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int position = recyclerView.getChildAdapterPosition(snapHelper.findSnapView(linearLayoutManager));
                    Log.e(TAG, "onScrollStateChanged:position=" + position);
                }
            }
        });
    }
}
