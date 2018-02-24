package com.cucr.myapplication.activity.pay;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;

public class TxRuleActivity extends BaseActivity {

    @Override
    protected void initChild() {
        initTitle("提现规则");
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_tx_rule;
    }
}
