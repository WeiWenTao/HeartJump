package com.cucr.myapplication.activity.comment;

import android.view.View;
import android.widget.ListView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.adapter.LvAdapter.FtCatgoryAadapter;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.dialog.DialogDaShangStyle;
import com.lidroid.xutils.view.annotation.ViewInject;

public class FtPicAndTextComment extends BaseActivity {

    @ViewInject(R.id.lv_ft_catgory)
    ListView lv_ft_catgory;

    private DialogDaShangStyle mDialogDaShangStyle;

    @Override
    protected void initChild() {
        initTitle("评论");
        initlv();
    }

    private void initlv() {
        mDialogDaShangStyle = new DialogDaShangStyle(this, R.style.ShowAddressStyleTheme);
        mDialogDaShangStyle.setCanceledOnTouchOutside(true);
        mDialogDaShangStyle.setConfirmListener(new DialogDaShangStyle.ClickconfirmListener() {
            @Override
            public void onClickConfirm(int howMuch) {
                if (howMuch != 0){
                    ToastUtils.showToast(FtPicAndTextComment.this,howMuch+""+" 星币");
                }
                mDialogDaShangStyle.dismiss();
            }
        });

        View lvHead = View.inflate(this, R.layout.item_ft_catgory_header, null);
        lvHead.findViewById(R.id.tv_dashang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogDaShangStyle.show();
            }
        });
        lv_ft_catgory.addHeaderView(lvHead,null,true);
        lv_ft_catgory.setHeaderDividersEnabled(false);

        lv_ft_catgory.setAdapter(new FtCatgoryAadapter(this));
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_ft_pic_and_text_comment;
    }
}
