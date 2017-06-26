package com.cucr.myapplication.adapter.GvAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;

import com.cucr.myapplication.R;
import com.cucr.myapplication.utils.CommonViewHolder;
import com.cucr.myapplication.widget.dialog.DialogCanaleFocusStyle;

/**
 * Created by 911 on 2017/5/22.
 */

public class StarRecommendAdapter extends BaseAdapter {
    Context mContext;

    int checked = -1;
    private DialogCanaleFocusStyle mDialogCanaleFocusStyle;
    private FrameLayout mFl;

    public void setCheck(int position) {
        checked = position;
        notifyDataSetChanged();
    }

    public StarRecommendAdapter(Context context) {
        this.mContext = context;
        initDialog();
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
    public int getCount() {
        return 30;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private int mConut;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        CommonViewHolder cvh = CommonViewHolder.createCVH(convertView, mContext, R.layout.item_gv_star_recommend, null);

//        RelativeLayout rl_goto_starpager = cvh.getView(R.id.rl_goto_starpager, RelativeLayout.class);
//        rl_goto_starpager.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(v.getContext(), position + "", Toast.LENGTH_SHORT).show();
//            }
//        });


    /*    //解决item重复调用 getView 方法的bug
        if (parent.getChildCount() == position || position != 0) {
            if (position == checked) {
//            条目复用导致的混乱 可用对象存储字段解决 TODO
//            mFl.setVisibility(mFl.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                mFl = cvh.getView(R.id.fl_star_bg, FrameLayout.class);

                if (mFl.getVisibility() == View.GONE) {
                    Toast.makeText(mContext,"已关注 林更新",Toast.LENGTH_SHORT).show();
                    mFl.setVisibility(View.VISIBLE);
                } else if (mFl.getVisibility() == View.VISIBLE) {
                    mDialogCanaleFocusStyle.show();
                    mDialogCanaleFocusStyle.initTitle("林更新");

                }
            }
        }*/

        return cvh.convertView;
    }
}
