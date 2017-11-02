package com.cucr.myapplication.adapter.LvAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

        LinearLayout ll_dabang = cvh.getView(R.id.ll_dabang, LinearLayout.class);
        ImageView userHead = cvh.getView(R.id.iv_user_icon_dabang, ImageView.class); //用户头像
        TextView tv_name = cvh.getView(R.id.tv_name, TextView.class);   //用户名
        TextView tv_xingbi = cvh.getView(R.id.tv_xingbi, TextView.class);     //心跳值

        ImageLoader.getInstance().displayImage(HttpContans.HTTP_HOST+rowsBean.getUserHeadPortrait(),userHead, MyApplication.getImageLoaderOptions());
        tv_name.setText(rowsBean.getRealName());
        tv_xingbi.setText(rowsBean.getUserMoney()+"");

        ll_dabang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnDaBang != null) {
                    mOnDaBang.daBang(rowsBean);
                }
            }
        });
        return cvh.convertView;
    }

    public void setData(List<BangDanInfo.RowsBean> rows) {
        this.rows = rows;
        notifyDataSetChanged();
    }

    public interface OnDaBang {
        void daBang(BangDanInfo.RowsBean rowsBean);
    }

    private OnDaBang mOnDaBang;

    public void setOnDaBang(OnDaBang onDaBang) {
        mOnDaBang = onDaBang;
    }
}
