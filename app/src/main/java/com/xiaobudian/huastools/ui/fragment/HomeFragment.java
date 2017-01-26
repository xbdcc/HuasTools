package com.xiaobudian.huastools.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewFlipper;

import com.xiaobudian.huastools.R;

import at.markushi.ui.CircleButton;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by caochang on 2016/4/15.
 */
public class HomeFragment extends Fragment implements GestureDetector.OnGestureListener, View.OnTouchListener {

    private static final int FLING_MIN_DISTANCE = 100;

    @Bind(R.id.viewflipper)
    ViewFlipper mViewflipper;
    @Bind(R.id.ry_menu)
    RecyclerView mRyMenu;

    public MenuGridAdapter mMenuGridAdapter;


    private GestureDetector mGestureDetector;
    private GridClickListener gridClickListener;
    private int[] pictures = new int[]{R.mipmap.menu_1, R.mipmap.menu_2, R.mipmap.menu_3, R.mipmap.menu_4, R.mipmap.menu_5
            , R.mipmap.menu_6, R.mipmap.menu_7, R.mipmap.menu_8, R.mipmap.menu_9};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        mGestureDetector = new GestureDetector(this);
        mViewflipper.setFlipInterval(5 * 1000);
        mViewflipper.setOnTouchListener(this);
        mViewflipper.startFlipping();

        mMenuGridAdapter = new MenuGridAdapter();

        mRyMenu.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mRyMenu.setHasFixedSize(true);
        mRyMenu.setAdapter(mMenuGridAdapter);
        mRyMenu.setNestedScrollingEnabled(false);//ScrollView 滑动顺滑

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE) {// 向左滑动
            mViewflipper.setInAnimation(getActivity(), R.anim.quit_in_from_left);
            mViewflipper.setOutAnimation(getActivity(), R.anim.quit_out_to_right);
            mViewflipper.showPrevious();
        } else if (e2.getX() - e1.getX() < -FLING_MIN_DISTANCE) {
            mViewflipper.setInAnimation(getActivity(), R.anim.enter_in_from_right);
            mViewflipper.setOutAnimation(getActivity(), R.anim.enter_out_to_left);
            mViewflipper.showNext();
        }
        mViewflipper.stopFlipping();
        mViewflipper.startFlipping();
        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        mViewflipper.requestDisallowInterceptTouchEvent(true);//拦截，此焦点优先，防止嵌套滑动冲突
        return true;
    }


    public interface GridClickListener {
        void OnGridClick(int x, int y);
    }

    public void setGridClickListener(GridClickListener gridClickListener) {

        this.gridClickListener = gridClickListener;
    }

    class MenuGridAdapter extends RecyclerView.Adapter<MenuGridAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.item_gridview, parent, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {

//            holder.circleButton.setColor(Color.BLUE);
            holder.mCirclebutton.setImageResource(pictures[position]);
            holder.mCirclebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("______________", position + "");
                    switch (position) {
                        case 0:
                            gridClickListener.OnGridClick(1, 0);
                            break;
                        case 1:
                            gridClickListener.OnGridClick(1, 1);
                            break;
                        case 2:
                            gridClickListener.OnGridClick(1, 2);
                            break;
                        case 3:
                            gridClickListener.OnGridClick(1, 3);
                            break;
                        case 4:
                            gridClickListener.OnGridClick(2, 0);
                            break;
                        case 5:
                            gridClickListener.OnGridClick(2, 1);
                            break;
                        case 6:
                            gridClickListener.OnGridClick(3, 0);
                            break;
                        case 7:
                            gridClickListener.OnGridClick(3, 1);
                            break;
                        case 8:
                            gridClickListener.OnGridClick(4, 0);
                            break;
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return 9;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.circlebutton)
            CircleButton mCirclebutton;

            public MyViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
//                circleButton = (CircleButton) itemView.findViewById(R.id.circlebutton);
            }
        }
    }

}
