package com.cucr.myapplication.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.LvAdapter.HomeAdapter;
import com.cucr.myapplication.utils.ToastUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.zackratos.ultimatebar.UltimateBar;

public class HomeSearchActivity extends Activity {

    @ViewInject(R.id.lv_search)
    ListView lv_search;

    @ViewInject(R.id.edit_search)
    EditText edit_search;

    @ViewInject(R.id.head)
    RelativeLayout head;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_search);
        ViewUtils.inject(this);

        initHead();
        initLV();
        watchSearch();

    }

    private void initHead() {

        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setColorBar(getResources().getColor(R.color.blue_black), 0);
    }

    public void watchSearch() {
        edit_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) edit_search.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(HomeSearchActivity.this
                                            .getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    // 搜索，进行自己要的操作...
                    ToastUtils.showToast(HomeSearchActivity.this,edit_search.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    private void initLV() {
        View headerView = View.inflate(this, R.layout.header_home_search, null);
        lv_search.addHeaderView(headerView,null,true);
        lv_search.setHeaderDividersEnabled(false);
        lv_search.setAdapter(new HomeAdapter(this));
    }


    @OnClick(R.id.iv_search_back)
    public void clickBack(View view){
        finish();
    }
}
