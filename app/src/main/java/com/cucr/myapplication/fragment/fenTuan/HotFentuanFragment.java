package com.cucr.myapplication.fragment.fenTuan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.fenTuan.FenTuanListActivity;
import com.cucr.myapplication.adapter.LvAdapter.HotFenTuanAdapter;

/**
 * Created by 911 on 2017/6/24.
 */
public class HotFenTuanFragment extends android.app.Fragment {

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //view的复用
        if (view == null){
            view = inflater.inflate(R.layout.fragment_hot_fentuan, container, false);
            initLV(container.getContext());
        }

        return view;
    }

    private void initLV(final Context context) {
        ListView listView = (ListView) view.findViewById(R.id.lv_fen_tuan);
        listView.setAdapter(new HotFenTuanAdapter(context));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(view.getContext(),FenTuanListActivity.class));
            }
        });
    }
}
