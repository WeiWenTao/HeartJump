package com.cucr.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.utils.CommonViewHolder;

import org.zackratos.ultimatebar.UltimateBar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PlayerListActivity extends Activity implements Serializable {

    private List<Music> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_list);

        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setColorBar(getResources().getColor(R.color.white), 0);
        initData();

        ListView lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(new MyPlayerAdapter());
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PlayerListActivity.this, PlayerActivity.class);
                intent.putExtra("data", mList.get(position));
                startActivity(intent);
            }
        });
        findViewById(R.id.iv_base_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        mList = new ArrayList<>();
        mList.add(new Music(R.raw.choubaguai, "丑八怪"));
        mList.add(new Music(R.raw.nhywzy, "你还要我怎样"));
        mList.add(new Music(R.raw.shenshi, "绅士"));
        mList.add(new Music(R.raw.yanyuan, "演员"));
        mList.add(new Music(R.raw.aimei, "暧昧"));
    }

    class MyPlayerAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mList.size();
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
            CommonViewHolder cvh = CommonViewHolder.createCVH(convertView, parent.getContext(), R.layout.item_player, null);
            TextView tv = cvh.getTv(R.id.tv_name);
            tv.setText(mList.get(position).getName());
            return cvh.convertView;
        }
    }

    static class Music implements Serializable {
        private int res;
        private String name;

        public Music(int res, String name) {
            this.res = res;
            this.name = name;
        }

        public int getRes() {
            return res;
        }

        public void setRes(int res) {
            this.res = res;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
