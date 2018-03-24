package com.cucr.myapplication.adapter.RlVAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.user.LoadUserInfos;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.SpUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by cucr on 2017/12/21.
 */

public class AccountListAdapter extends RecyclerView.Adapter<AccountListAdapter.AccountHolder> {

    private List<LoadUserInfos> infos;


    public void setData(List<LoadUserInfos> infos) {
        this.infos = infos;
        MyLogger.jLog().i("LoadInfos:" + infos);
        notifyDataSetChanged();
    }

    @Override
    public AccountHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(MyApplication.getInstance()).inflate(R.layout.item_account, parent, false);
        return new AccountHolder(view);
    }

    @Override
    public void onBindViewHolder(AccountHolder holder, int position) {
        final LoadUserInfos loadUserInfos = infos.get(position);
        if (loadUserInfos.getUserId() == ((int) SpUtil.getParam(SpConstant.USER_ID, -1))) {
            holder.mark.setVisibility(View.VISIBLE);
        } else {
            holder.mark.setVisibility(View.GONE);
        }
        holder.name.setText(loadUserInfos.getName());
        ImageLoader.getInstance().displayImage(loadUserInfos.getUserHeadPortrait(), holder.userHead,
                MyApplication.getImageLoaderOptions());
        holder.item_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickItem != null) {
                    mOnClickItem.onClickItem(loadUserInfos);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return infos == null ? 0 : infos.size();
    }

    public class AccountHolder extends RecyclerView.ViewHolder {
        //条目
        @ViewInject(R.id.item_account)
        private RelativeLayout item_account;
        //头像
        @ViewInject(R.id.iv_account_icon)
        private ImageView userHead;
        //账号标记
        @ViewInject(R.id.iv_mark)
        private ImageView mark;
        //昵称
        @ViewInject(R.id.tv_name)
        private TextView name;

        public AccountHolder(View itemView) {
            super(itemView);
            ViewUtils.inject(this, itemView);
        }
    }

    private OnClickItem mOnClickItem;

    public void setOnClickItem(OnClickItem onClickItem) {
        mOnClickItem = onClickItem;
    }

    public interface OnClickItem {
        void onClickItem(LoadUserInfos loadUserInfos);
    }
}
