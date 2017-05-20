package com.cucr.myapplication.adapter.LvAdapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.cucr.myapplication.R;
import com.cucr.myapplication.bean.LocationData;
import com.cucr.myapplication.utils.CommonViewHolder;

import java.util.List;

/**
 * Created by 911 on 2017/4/28.
 */
public class LocationAdapter extends BaseAdapter {

    private List<LocationData> provinces;
    private boolean needShow;

    public LocationAdapter(List<LocationData> provinces,boolean needShow) {
        this.provinces = provinces;
        this.needShow = needShow;
    }

    @Override
    public int getCount() {
        return provinces == null ? 0 : provinces.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        LocationData provinceInfo = provinces.get(position);
        CommonViewHolder cvh = CommonViewHolder.createCVH(convertView,parent.getContext(), R.layout.item_province_lv,null);
        //是否需要显示小箭头
        cvh.getIv(R.id.iv_go).setVisibility(needShow?View.VISIBLE:View.INVISIBLE);
        cvh.getTv(R.id.tv_item_provice).setText(provinceInfo.getName());
        return cvh.convertView;
    }
}
