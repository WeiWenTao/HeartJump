package com.cucr.myapplication.adapter.PagerAdapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.Hyt.BigPadInfo;
import com.cucr.myapplication.constants.HttpContans;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by cucrx on 2018/1/24.
 */

public class BigPadPagerAdapter extends PagerAdapter {

    private List<BigPadInfo.RowsBean> rows;
    private int mChildCount = 0;

    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        if (mChildCount > 0) {
            mChildCount--;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }

    public void setData(List<BigPadInfo.RowsBean> rows) {
        this.rows = rows;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return rows == null ? 0 : rows.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final BigPadInfo.RowsBean rowsBean = rows.get(position);
        View itemView = View.inflate(MyApplication.getInstance(), R.layout.viewpage_yyhd_bigpad, null);
        ImageView iv_bigpad = (ImageView) itemView.findViewById(R.id.iv_bigpad);
        ImageView iv_pad_sel = (ImageView) itemView.findViewById(R.id.iv_pad_sel);
        TextView tv_yy_use = (TextView) itemView.findViewById(R.id.tv_yy_use);
        TextView tv_yy_address = (TextView) itemView.findViewById(R.id.tv_yy_address);
        TextView tv_yy_space = (TextView) itemView.findViewById(R.id.tv_yy_space);
        TextView tv_yy_flow = (TextView) itemView.findViewById(R.id.tv_yy_flow);
        TextView tv_yy_price = (TextView) itemView.findViewById(R.id.tv_yy_price);
        itemView.findViewById(R.id.card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClick != null) {
                    mOnItemClick.onClickItem(position);
                }
            }
        });
        ImageLoader.getInstance().displayImage(HttpContans.IMAGE_HOST + rowsBean.getPicUrl(),
                iv_bigpad, MyApplication.getImageLoaderOptions());
        tv_yy_use.setText(rowsBean.getPurpose());
        tv_yy_address.setText(rowsBean.getAddress());
        tv_yy_space.setText(rowsBean.getSpec());
        tv_yy_flow.setText(rowsBean.getJtll());
        tv_yy_price.setText("¥ " + rowsBean.getPrice() + "/天");

        boolean sel = rowsBean.isSel();
        iv_pad_sel.setImageResource(sel ? R.drawable.pad_sel : R.drawable.pad_nor);

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    private OnItemClick mOnItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        mOnItemClick = onItemClick;
    }

    public interface OnItemClick {
        void onClickItem(int position);
    }
}
