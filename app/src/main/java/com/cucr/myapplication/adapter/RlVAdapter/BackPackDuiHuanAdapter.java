package com.cucr.myapplication.adapter.RlVAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.R;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.bean.fenTuan.FtBackpackInfo;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by cucr on 2017/11/13.
 */

public class BackPackDuiHuanAdapter extends RecyclerView.Adapter<BackPackDuiHuanAdapter.BpDuiHuanHolder> {

    private FtBackpackInfo mFtBackpackInfo;

    public void setData(FtBackpackInfo mFtBackpackInfo) {
        this.mFtBackpackInfo = mFtBackpackInfo;
        notifyDataSetChanged();
    }

    @Override
    public BpDuiHuanHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bp_duihuan, parent, false);
        return new BpDuiHuanHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final BpDuiHuanHolder holder, int position) {

        final FtBackpackInfo.ObjBean.ListBean listBean = mFtBackpackInfo.getObj().getList().get(position);


        holder.tv_num.setText("X " + listBean.getBalance());
        holder.tv_result.setText(listBean.getBalance() + "");
        ImageLoader.getInstance().displayImage(HttpContans.IMAGE_HOST +
                        listBean.getUserAccountType().getPicUrl(),
                holder.iv_gift_pic, MyApplication.getImageLoaderOptions());


        //添加
        holder.iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int balance = listBean.getBalance();
                int result = Integer.parseInt(holder.tv_result.getText().toString()) + 1;
                if (result > balance) {
                    result = balance;
                } else {
                    mOnResultChange.onResultAdd(listBean.getUserAccountType().getId(), listBean.getUserAccountType().getProportion(),result);
                }
                holder.tv_result.setText(result + "");
            }
        });

        //删减
        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = Integer.parseInt(holder.tv_result.getText().toString()) - 1;
                if (result < 0) {
                    result = 0;
                } else {
                    mOnResultChange.onResultDel(listBean.getUserAccountType().getId(), listBean.getUserAccountType().getProportion(),result);
                }
                holder.tv_result.setText(result + "");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFtBackpackInfo == null ? 0 : mFtBackpackInfo.getObj().getList().size();
    }

    public class BpDuiHuanHolder extends RecyclerView.ViewHolder {

        //礼物图片
        @ViewInject(R.id.iv_gift_pic)
        private ImageView iv_gift_pic;

        //兑换数量
        @ViewInject(R.id.tv_result)
        private TextView tv_result;

        //礼物数量
        @ViewInject(R.id.tv_num)
        private TextView tv_num;

        //删减
        @ViewInject(R.id.iv_delete)
        private ImageView iv_delete;

        //添加
        @ViewInject(R.id.iv_add)
        private ImageView iv_add;


        public BpDuiHuanHolder(View itemView) {
            super(itemView);
            ViewUtils.inject(this, itemView);
        }
    }

    private OnResultChange mOnResultChange;

    public void setOnResultChange(OnResultChange onResultChange) {
        mOnResultChange = onResultChange;
    }

    public interface OnResultChange {
        void onResultAdd(int giftId, int howMuch, int giftCount);

        void onResultDel(int giftId, int howMuch, int giftCount);
    }
}
