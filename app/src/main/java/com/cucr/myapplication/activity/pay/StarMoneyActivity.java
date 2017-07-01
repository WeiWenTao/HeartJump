package com.cucr.myapplication.activity.pay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.LvAdapter.LvExpenseCalendarAdapter;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class StarMoneyActivity extends Activity {

    @ViewInject(R.id.lv_expense_calendar)
    ListView lv_expense_calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star_money);
        ViewUtils.inject(this);

        lv_expense_calendar.setAdapter(new LvExpenseCalendarAdapter());
    }

    @OnClick(R.id.tv_goto_pay)
    public void goToPay(View view){
        startActivity(new Intent(this,PayCenterActivity.class));
    }

    @OnClick(R.id.iv_star_money_back)
    public void back(View view){
        finish();
    }
}
