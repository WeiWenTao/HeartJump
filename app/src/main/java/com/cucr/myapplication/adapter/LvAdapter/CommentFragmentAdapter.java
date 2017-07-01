package com.cucr.myapplication.adapter.LvAdapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.cucr.myapplication.R;
import com.cucr.myapplication.utils.CommonViewHolder;

import static com.cucr.myapplication.R.id.ll_comment_more;

/**
 * Created by 911 on 2017/4/17.
 */
public class CommentFragmentAdapter extends BaseAdapter {


    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {



        CommonViewHolder cvh = CommonViewHolder.createCVH(convertView,parent.getContext(), R.layout.____________item_comment_fragment_lv,null);
        LinearLayout mLl_comment_more = cvh.getView(ll_comment_more, LinearLayout.class);

        mLl_comment_more.setVisibility(position%2==1?View.VISIBLE:View.GONE);
        return cvh.convertView;
    }
}
