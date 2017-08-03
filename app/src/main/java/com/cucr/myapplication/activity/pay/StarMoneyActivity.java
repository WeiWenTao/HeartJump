package com.cucr.myapplication.activity.pay;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.adapter.LvAdapter.LvExpenseCalendarAdapter;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class StarMoneyActivity extends BaseActivity {

    @ViewInject(R.id.lv_expense_calendar)
    ListView lv_expense_calendar;

    @Override
    protected void initChild() {
        initTitle("星币");
        lv_expense_calendar.setAdapter(new LvExpenseCalendarAdapter());
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_star_money;
    }

    @OnClick(R.id.tv_goto_pay)
    public void goToPay(View view){
        startActivity(new Intent(this,PayCenterActivity.class));
    }

}
