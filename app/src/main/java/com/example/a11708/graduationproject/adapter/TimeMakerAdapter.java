package com.example.a11708.graduationproject.adapter;



import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.audiofx.DynamicsProcessing;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a11708.graduationproject.Beans.TimeUsingBean;
import com.example.a11708.graduationproject.Config.Constant;
import com.example.a11708.graduationproject.R;
import com.example.a11708.graduationproject.Service.AlarmService;
import com.example.a11708.graduationproject.activity.ItemContext;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;


public class TimeMakerAdapter extends SwipeMenuRecyclerView.Adapter<RecyclerView.ViewHolder> {
    private SwipeMenuRecyclerView mMenuRecyclerView;
    private int ITEM_TYPE = 1;
    private int HEAD_TYPE = 2;
    private Context mcontext;
    private volatile static TimeMakerAdapter instance = null;
    public void setList(List<TimeUsingBean> list) {
        this.list = list;
    }

    private List<TimeUsingBean> list = new ArrayList<>();
    private int mItemCount = getItemCount();
    public TimeMakerAdapter(Context context, List<TimeUsingBean> list) {
        mcontext = context;
        this.list = list;
    }

    /*public void TimeMakerAdapter getInstance(Context context, List<TimeUsingBean> list){
        if(instance == null){
            synchronized (TimeMakerAdapter.class) {
                if (instance == null) {
                    instance = new TimeMakerAdapter(context, list);
                }
            }
        }
        return instance;
    }*/
    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return HEAD_TYPE;
        }else if(position > 0){
            return ITEM_TYPE;
        }else
        {
            return super.getItemViewType(position);
        }
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(i == ITEM_TYPE) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycle_time_item, viewGroup, false);
            ItemViewHolder viewHolder = new ItemViewHolder(view);
            return viewHolder;
        }else{
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycle_head_item, viewGroup, false);
            TouziViewHolder touziViewHolder = new TouziViewHolder(view);
            return touziViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (list == null || i < 0 || i > list.size()) {
            return;
        }
        if(i >= 1) {
            TimeUsingBean data = list.get(i - 1);
            if (viewHolder instanceof ItemViewHolder) {
                ((ItemViewHolder) viewHolder).bindView(data,i);
            }
        }
        else{

            ((TouziViewHolder) viewHolder).bindView(list);
        }


    }





    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView WorkTitle;
        TextView WorkTime;
        ImageView WorkSwitch;
        LinearLayout linearLayout;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            WorkTitle = (TextView)itemView.findViewById(R.id.work_title);
            WorkTime = (TextView)itemView.findViewById(R.id.work_time);
            WorkSwitch = (ImageView)itemView.findViewById(R.id.work_switch);
            linearLayout = (LinearLayout)itemView.findViewById(R.id.linearLayout);

        }

        public void bindView(TimeUsingBean data,int position) {
            WorkTitle.setText(data.getmTitle());
            WorkTime.setText(data.getmUsingTime());
            if(list.get(position - 1).getCompleted() == 0) {
                WorkSwitch.setImageResource(R.drawable.start);
            }else{
                WorkSwitch.setImageResource(R.drawable.pause);
            }

            WorkSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!list.get(position - 1).getmUsingTime().equals("预计完成耗时：0小时0分钟")) {
                        if (list.get(position - 1).getCompleted() == 0) {
                            if(Constant.config.isCountDown == false) {
                                list.get(position - 1).setCompleted(1);
                                WorkSwitch.setImageResource(R.drawable.pause);
                                Intent intent = new Intent(mcontext, AlarmService.class);
                                intent.putExtra("starttime", list.get(position - 1).getmUsingTime());
                                Constant.config.isCountDown = true;
                                mcontext.startService(intent);
                            }
                        } else {
                            Constant.config.isCountDown = false;
                            list.get(position - 1).setCompleted(0);
                            Intent intent = new Intent(mcontext, AlarmService.class);
                            intent.putExtra("starttime", list.get(position - 1).getmUsingTime());
                            mcontext.stopService(intent);
                            WorkSwitch.setImageResource(R.drawable.start);
                        }
                    }
                }
            });
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent= new Intent(mcontext, ItemContext.class);
                    int mid = list.get(position - 1).getmID();
                    intent.putExtra("mid",mid);
                    mcontext.startActivity(intent);
                }
            });
        }
    }
     class TouziViewHolder extends RecyclerView.ViewHolder {
        TextView TouziTime;
        public TouziViewHolder(@NonNull View itemView) {
            super(itemView);
            TouziTime = (TextView)itemView.findViewById(R.id.touzi_time_text);


        }

        public void bindView(List<TimeUsingBean> list) {
            int[] time = {0,0};
            for(int i = 0;i<list.size();i++){
                String a= list.get(i).getmUsingTime();
                String b = a.replace("预计完成耗时：", "");
                String c = b.replace("分钟", "");
                String e = c.replace("小时", ",");
                String[] str = e.split(",");
                time[0] = Integer.parseInt(str[0])+time[0];
                time[1] = Integer.parseInt(str[1])+time[1];
                if(time[1] >= 60){
                    time[1] = time[1] - 60;
                    time[0] = time[0] + 1;
                }
            }
            TouziTime.setText("已投资时间：" +time[0]+"小时"+time[1]+"分钟");
        }
    }

}
