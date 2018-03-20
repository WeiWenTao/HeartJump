package com.cucr.myapplication.activity.yuyue;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.fuli.QiYeHuoDongInfo;
import com.cucr.myapplication.constants.HttpContans;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.ImageLoader;

public class YuYueResultCatgoryActivity extends FragmentActivity {

    //报价
    @ViewInject(R.id.tv_price)
    private TextView tv_price;

    //地区
    @ViewInject(R.id.tv_active_local)
    private TextView tv_active_local;

    //明星姓名
    @ViewInject(R.id.tv_star_name)
    private TextView tv_star_name;

    //详细地区
    @ViewInject(R.id.et_local_catgory)
    private TextView et_local_catgory;

    //活动名称
    @ViewInject(R.id.et_activename)
    private TextView et_activename;

    //活动内容
    @ViewInject(R.id.et_active_content)
    private TextView et_active_content;

    //活动人数
    @ViewInject(R.id.et_people)
    private TextView et_people;

    //室内iv
    @ViewInject(R.id.iv_shi_nei)
    private ImageView iv_shi_nei;

    //室外iv
    @ViewInject(R.id.iv_shi_wai)
    private ImageView iv_shi_wai;

    //头像
    @ViewInject(R.id.iv_head)
    private ImageView iv_head;

    //开始时间
    @ViewInject(R.id.tv_time_star)
    private TextView tv_time_star;

    //结束时间
    @ViewInject(R.id.tv_time_end)
    private TextView tv_time_end;

    private QiYeHuoDongInfo.RowsBean mRowsBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yu_yue_result_catgory);

        ViewUtils.inject(this);

        initViews();

    }

    private void initViews() {

        mRowsBean = (QiYeHuoDongInfo.RowsBean) getIntent().getSerializableExtra("date");

        //回显数据
        //-----------------------------------------价格-------------------------------------
        String price = mRowsBean.getStartCost() + " 万";
        SpannableString sp = new SpannableString("参考费用 " + price + " /场");
        //设置高亮样式二
        sp.setSpan(new ForegroundColorSpan(Color.parseColor("#ff4f49")), 4, 4 + price.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        sp.setSpan(new AbsoluteSizeSpan(15, true), 4, 4 + price.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        sp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD_ITALIC), 4, 4 + price.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        //SpannableString对象设置给TextView
        tv_price.setText(sp);
        //设置TextView可点击
        tv_price.setMovementMethod(LinkMovementMethod.getInstance());
        //-----------------------------------------价格-------------------------------------
        tv_star_name.setText(mRowsBean.getAppStartUser().getRealName());
        ImageLoader.getInstance().displayImage(HttpContans.IMAGE_HOST + mRowsBean.getAppStartUser().getUserHeadPortrait(), iv_head, MyApplication.getImageLoaderOptions());
        et_activename.setText(mRowsBean.getActiveName());
        tv_active_local.setText(mRowsBean.getActivePlace());
        et_local_catgory.setText(mRowsBean.getActiveAdress());
        tv_time_star.setText(mRowsBean.getActiveStartTime().substring(0,16));
        tv_time_end.setText(mRowsBean.getActiveEndTime().substring(0,16));
        if (mRowsBean.getActiveScene() == 0) { //室内
            iv_shi_nei.setImageResource(R.drawable.pc_sel);
            iv_shi_wai.setImageResource(R.drawable.pc_nor);
        } else {                               //室外
            iv_shi_wai.setImageResource(R.drawable.pc_sel);
            iv_shi_nei.setImageResource(R.drawable.pc_nor);
        }
        et_active_content.setText(mRowsBean.getActiveInfo());
        et_people.setText(mRowsBean.getPeopleCount()+"");
    }


    //返回
    @OnClick(R.id.iv_myyuyue_back)
    public void back(View view) {
        finish();
    }

}
