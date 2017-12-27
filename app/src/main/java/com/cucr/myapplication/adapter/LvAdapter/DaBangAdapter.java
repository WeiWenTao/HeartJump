package com.cucr.myapplication.adapter.LvAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cucr.myapplication.MyApplication;
import com.cucr.myapplication.R;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.model.dabang.BangDanInfo;
import com.cucr.myapplication.utils.CommonViewHolder;
import com.cucr.myapplication.widget.dialog.DialogDaBangStyle;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by 911 on 2017/6/22.
 */

public class DaBangAdapter extends BaseAdapter {

    private DialogDaBangStyle dialog;

    private List<BangDanInfo.RowsBean> rows;

    public DaBangAdapter(Context context) {
        dialog = new DialogDaBangStyle(context, R.style.BirthdayStyleTheme);
    }

    @Override
    public int getCount() {
        return rows == null ? 0 : rows.size() - 3;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final BangDanInfo.RowsBean rowsBean = rows.get(position + 3);
        CommonViewHolder cvh = CommonViewHolder.createCVH(convertView, parent.getContext(), R.layout.item_lv_dabang, null);

        LinearLayout ll_dabang = cvh.getView(R.id.ll_dabang, LinearLayout.class);//打榜
        RelativeLayout rl_dabang = cvh.getView(R.id.rl_dabang, RelativeLayout.class);//跳转
        ImageView userHead = cvh.getView(R.id.iv_user_icon_dabang, ImageView.class); //用户头像
        TextView tv_name = cvh.getView(R.id.tv_name, TextView.class);   //用户名
        TextView tv_xingbi = cvh.getView(R.id.tv_xingbi, TextView.class);     //心跳值
        TextView tv_ranking = cvh.getTv(R.id.tv_ranking);     //排名

        ImageLoader.getInstance().displayImage(HttpContans.HTTP_HOST + rowsBean.getUserHeadPortrait(), userHead, MyApplication.getImageLoaderOptions());
        tv_name.setText(rowsBean.getRealName());
        tv_xingbi.setText(rowsBean.getUserMoney() + "");
        tv_ranking.setText(position + 4 + "");

        ll_dabang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClick != null) {
                    mOnClick.daBang(rowsBean);
                }
            }
        });

        rl_dabang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClick != null) {
                    mOnClick.clickStar(rowsBean.getId());
                }
            }
        });


        return cvh.convertView;
    }

    public void setData(List<BangDanInfo.RowsBean> rows) {
        this.rows = rows;
        notifyDataSetChanged();
    }

    public void addData(List<BangDanInfo.RowsBean> rows) {
        this.rows.addAll(rows);
        notifyDataSetChanged();
    }

    public interface OnClick {
        void daBang(BangDanInfo.RowsBean rowsBean);
        void clickStar(int starId);
    }

    private OnClick mOnClick;

    public void setOnClick(OnClick onClick) {
        mOnClick = onClick;
    }
}
