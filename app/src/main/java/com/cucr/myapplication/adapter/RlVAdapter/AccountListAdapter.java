package com.cucr.myapplication.adapter.RlVAdapter;

import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cucr.myapplication.MyApplication;
import com.cucr.myapplication.R;
import com.cucr.myapplication.model.login.UserAccountInfo;
import com.cucr.myapplication.utils.SpUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by cucr on 2017/12/21.
 */

public class AccountListAdapter extends RecyclerView.Adapter<AccountListAdapter.AccountHolder> {

    private List<String> keys;
    private final SharedPreferences sp;
    private final Gson mGson;
    private int select;

    public void setSelect(int position) {
        this.select = position;
        notifyDataSetChanged();
    }

    public AccountListAdapter(List<String> keys) {
        this.keys = keys;
        sp = SpUtil.getAccountSp();
        mGson = MyApplication.getGson();
    }

    @Override
    public AccountHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(MyApplication.getInstance()).inflate(R.layout.item_account, parent, false);
        return new AccountHolder(view);
    }

    @Override
    public void onBindViewHolder(AccountHolder holder, final int position) {
        holder.mark.setVisibility(position == select ? View.VISIBLE : View.GONE);
        String string = sp.getString(keys.get(position), "");
        UserAccountInfo accountInfo = mGson.fromJson(string, UserAccountInfo.class);
        holder.name.setText(accountInfo.getNickName());
        ImageLoader.getInstance().displayImage(accountInfo.getUserAddress(), holder.userHead, MyApplication.getImageLoaderOptions());
        holder.item_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickItem != null) {
                    mOnClickItem.onClickItem(v, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return keys == null ? 0 : keys.size();
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
        void onClickItem(View view, int position);
    }
}
