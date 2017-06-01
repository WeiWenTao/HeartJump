package com.cucr.myapplication.adapter.RlAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cucr.myapplication.R;
import com.cucr.myapplication.widget.dialog.DialogCanaleFocusStyle;

/**
 * Created by 911 on 2017/5/25.
 */
public class StarClassifyRlAdapter extends RecyclerView.Adapter<StarClassifyRlAdapter.MyHolder> implements View.OnClickListener {

    private  DialogCanaleFocusStyle mDialogCanaleFocusStyle;
    private Context mContext;
    private FrameLayout mFl;

    public StarClassifyRlAdapter(Context context) {
        mContext = context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rl_star_classify,parent,false);
        MyHolder vh = new MyHolder(view);
        initDialog();
        return vh;
    }

    private void initDialog() {

        mDialogCanaleFocusStyle = new DialogCanaleFocusStyle(mContext, R.style.ShowAddressStyleTheme);
        mDialogCanaleFocusStyle.setOnClickBtListener(new DialogCanaleFocusStyle.OnClickBtListener() {
            @Override
            public void clickConfirm() {
                mFl.setVisibility(View.GONE);
                mDialogCanaleFocusStyle.dismiss();
            }

            @Override
            public void clickCancle() {

                mDialogCanaleFocusStyle.dismiss();
            }
        });
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
            holder.ll_item.setOnClickListener(this);
            holder.ll_item.setTag(holder.fl_bg);
            holder.rl_goto_starpager.setOnClickListener(this);
            holder.rl_goto_starpager.setTag(position);

    }

    @Override
    public int getItemCount() {
        return 30;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_item:
                mFl = (FrameLayout) v.getTag();
                if (mFl.getVisibility() == View.GONE) {
                    Toast.makeText(v.getContext(),"已关注 林更新",Toast.LENGTH_SHORT).show();
                    mFl.setVisibility(View.VISIBLE);
                } else if (mFl.getVisibility() == View.VISIBLE) {
                    mDialogCanaleFocusStyle.show();
                    mDialogCanaleFocusStyle.initTitle("黄晓明");

                }
                break;

            case R.id.rl_goto_starpager:
                int position = (int) v.getTag();
                Toast.makeText(v.getContext(),position+"",Toast.LENGTH_SHORT).show();
                break;
        }
    }


    public class MyHolder extends RecyclerView.ViewHolder {

        //点击跳转到明星页面
        RelativeLayout rl_goto_starpager;

        //点击显示/隐藏灰色背景
        LinearLayout ll_item;

        //灰色背景
        FrameLayout fl_bg;

        public MyHolder(View itemView) {
            super(itemView);
            rl_goto_starpager = (RelativeLayout) itemView.findViewById(R.id.rl_goto_starpager);
            ll_item = (LinearLayout) itemView.findViewById(R.id.ll_item);
            fl_bg = (FrameLayout) itemView.findViewById(R.id.fl_star_bg);
        }

    }
}
