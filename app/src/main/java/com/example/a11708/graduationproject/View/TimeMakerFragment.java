package com.example.a11708.graduationproject.View;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Toast;


import com.example.a11708.graduationproject.Beans.TimeUsingBean;
import com.example.a11708.graduationproject.Config.Constant;
import com.example.a11708.graduationproject.R;
import com.example.a11708.graduationproject.Utils.DeviceUtil;
import com.example.a11708.graduationproject.Utils.MD5Util;
import com.example.a11708.graduationproject.Utils.SharedPreferencesUtils;
import com.example.a11708.graduationproject.Utils.SqliteUtil;
import com.example.a11708.graduationproject.activity.ItemContext;
import com.example.a11708.graduationproject.adapter.TimeMakerAdapter;
import com.example.a11708.graduationproject.model.SqlDaoModel;
import com.example.a11708.graduationproject.model.TimeUsingModel;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemMoveListener;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemStateChangedListener;
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimeMakerFragment extends Fragment {
    private View mView;
    private SwipeMenuRecyclerView mRecyclerView;
    private List<TimeUsingBean> mdataList = new ArrayList<>();
    private TimeMakerAdapter mAdapter;
    private FloatingActionButton mFloatingActionButton;
    private String mUsername;
    private static boolean canRefash = false;

    private SwipeMenuCreator mSwipeMenuCreator = (leftMenu, rightMenu, position) -> {

        int width = getResources().getDimensionPixelSize(R.dimen.dp_50);
        int height = getResources().getDimensionPixelSize(R.dimen.dp_50);
        SwipeMenuItem deleteItem = new SwipeMenuItem(getContext()).setBackground(
                R.drawable.selector_red)
                .setText("删除")
                .setTextColor(Color.WHITE)
                .setWidth(width)
                .setHeight(height);
        rightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。
    };
    private SwipeMenuItemClickListener mMenuItemClickListener = (menuBridge, position) -> {
        // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
        menuBridge.closeMenu();
        // 左侧还是右侧菜单：
        int direction = menuBridge.getDirection();
        // 菜单在Item中的Position：
        int menuPosition = menuBridge.getPosition();
        if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
            int mid = mdataList.get(position - 1).getmID();
            mdataList.remove(position-1);
            SqlDaoModel model = new SqlDaoModel(getContext());
            model.delete(mid);
            mAdapter.notifyItemRemoved(position);
            //Toast.makeText(getContext(), "list第" + position + "; 右侧菜单第" + menuPosition, Toast.LENGTH_SHORT)
            //        .show();
        } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
            //Toast.makeText(getContext(), "list第" + position + "; 左侧菜单第" + menuPosition, Toast.LENGTH_SHORT)
            //        .show();
        }
    };


    public TimeMakerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_time_maker, container, false);
        } else {
            ViewParent viewParent = mView.getParent();
            if (viewParent != null) {
                ((ViewGroup) viewParent).removeAllViews();
            }
        }
        initData();
        initView();
        //getUserInfo();
        return mView;
    }

    private void initData() {

        mUsername = Constant.UserName;
        if(mUsername.equals("")) {
            mUsername = DeviceUtil.getAndroidId(getContext());
        }
        SqlDaoModel sqlDaoModel = new SqlDaoModel(getActivity());
        mdataList = sqlDaoModel.findAll(mUsername);
        if(mAdapter!=null) {
            //mAdapter = new TimeMakerAdapter(getContext(),mdataList);
            mAdapter.setList(mdataList);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        /*if(isVisibleToUser){
            if(Constant.ClearUp == true){
                mdataList = createDataList();
                Constant.ClearUp = false;
                initData();
            }
        }*/
    }


    @Override
    public void onResume() {
        super.onResume();
        /*if(Constant.ClearUp == true){
            mdataList = createDataList();
            Constant.ClearUp = false;
        }*/
        if(mdataList != null && canRefash == true) {
            initData();
        }
        canRefash = true;

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if ((!hidden) && Constant.ClearUp == true) {
            onResume();
            Constant.ClearUp = false;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bindEvent();
        initAdapter();
    }

    private void initAdapter() {

        mRecyclerView.setAdapter(mAdapter);
    }

    private void bindEvent() {
        mdataList = createDataList();
        mAdapter = new TimeMakerAdapter(getContext(),mdataList);
        mRecyclerView.addItemDecoration(new DefaultItemDecoration(ContextCompat.getColor(getContext(), R.color.divider_color)));
        mRecyclerView.setSwipeMenuCreator(mSwipeMenuCreator);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);

        //mRecyclerView.setItemViewSwipeEnabled(true); // 侧滑删除，默认关闭。
        mRecyclerView.setSwipeMenuItemClickListener(mMenuItemClickListener);

        mRecyclerView.setLongPressDragEnabled(true); // 拖拽排序，默认关闭。
        mRecyclerView.setOnItemMoveListener(new OnItemMoveListener() {
            @Override
            public boolean onItemMove(RecyclerView.ViewHolder srcHolder, RecyclerView.ViewHolder targetHolder) {
                // 不同的ViewType不能拖拽换位置。
                if (srcHolder.getItemViewType() != targetHolder.getItemViewType()) return false;

                // 真实的Position：通过ViewHolder拿到的position都需要减掉HeadView的数量。
                int fromPosition = srcHolder.getAdapterPosition() - mRecyclerView.getHeaderItemCount();
                int toPosition = targetHolder.getAdapterPosition() - mRecyclerView.getHeaderItemCount();

                Collections.swap(mdataList, fromPosition - 1, toPosition - 1);
                 mAdapter.notifyItemMoved(fromPosition, toPosition);
                return true;// 返回true表示处理了并可以换位置，返回false表示你没有处理并不能换位置。
            }

            @Override
            public void onItemDismiss(RecyclerView.ViewHolder srcHolder) {

            }
        });
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    int mid = 0;
                    Intent intent = new Intent(getActivity(), ItemContext.class);
                    intent.putExtra("mid", mid);
                    startActivity(intent);
            }
        });


    }

    private List<TimeUsingBean> createDataList() {
        if(mdataList.size() == 0) {
            for (int i = 0; i < 6; i++) {
                TimeUsingBean timeUsingBean = new TimeUsingBean();
                //timeUsingBean.setmID(i);
                timeUsingBean.setmTitle("请输入需要完成的时间");
                timeUsingBean.setmContext(String.valueOf(""));
                timeUsingBean.setmUsingTime("预计完成耗时：0小时0分钟");
                timeUsingBean.setmUsername(Constant.UserName);
                timeUsingBean.setmData(Constant.TIME);
                timeUsingBean.setCompleted(0);
                //timeUsingBean.save();
                mdataList.add(i, timeUsingBean);
                SqlDaoModel model = new SqlDaoModel(getContext());
                model.add("请输入需要完成的时间","","预计完成耗时：0小时0分钟"
                        ,Constant.TIME, Constant.UserName,0);

            }
            SqlDaoModel model = new SqlDaoModel(getContext());
            List<TimeUsingBean> lists = model.findAll(Constant.UserName);
            for(TimeUsingBean list : lists){
                Log.e("id", String.valueOf(list.getmID()));
            }
        }
        return mdataList;
    }

    private void initView() {
        mRecyclerView = mView.findViewById(R.id.time_RecyclerView);
        mFloatingActionButton = mView.findViewById(R.id.fab_add);
    }

}
