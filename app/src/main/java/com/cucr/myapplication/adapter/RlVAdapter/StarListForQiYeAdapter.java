package com.cucr.myapplication.adapter.RlVAdapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cucr.myapplication.MyApplication;
import com.cucr.myapplication.R;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.core.focus.FocusCore;
import com.cucr.myapplication.model.eventBus.EventOnClickCancleFocus;
import com.cucr.myapplication.model.eventBus.EventOnClickFocus;
import com.cucr.myapplication.model.starList.StarListInfos;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.widget.dialog.DialogCanaleFocusStyle;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by cucr on 2017/11/24.
 */

public class StarListForQiYeAdapter extends RecyclerView.Adapter<StarListForQiYeAdapter.StarListForQiYeHolder> {

    private Context mContext;
    private Context activity;
    private DialogCanaleFocusStyle mDialogCanaleFocusStyle;
    private List<StarListInfos.RowsBean> rows;
    private FocusCore mCore;
    private int newPosition;
    private WindowManager mWm;
    private int mValue; //屏幕宽度

    public StarListForQiYeAdapter(Context activity) {
        this.activity = activity;
        mContext = MyApplication.getInstance();
        mCore = new FocusCore();
        mWm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mValue = mWm.getDefaultDisplay().getWidth() - CommonUtils.dip2px(mContext, 6.0f);
        initDialog();
    }

    private void initDialog() {
        mDialogCanaleFocusStyle = new DialogCanaleFocusStyle(activity, R.style.ShowAddressStyleTheme);
        mDialogCanaleFocusStyle.setOnClickBtListener(new DialogCanaleFocusStyle.OnClickBtListener() {
            @Override
            public void clickConfirm() {
                EventBus.getDefault().post(new EventOnClickCancleFocus());
//                mFl.setVisibility(View.GONE);
                StarListInfos.RowsBean rowsBean = rows.get(newPosition);
                mCore.cancaleFocus(rowsBean.getId());
                rowsBean.setIsfollow(0);
                notifyDataSetChanged();
                mDialogCanaleFocusStyle.dismiss();

            }

            @Override
            public void clickCancle() {
                mDialogCanaleFocusStyle.dismiss();
            }
        });
    }

    public void setData(List<StarListInfos.RowsBean> rows) {
        this.rows = rows;
        notifyDataSetChanged();
    }

    public void addData(List<StarListInfos.RowsBean> rows) {
        this.rows.addAll(rows);
        notifyDataSetChanged();
    }

    @Override
    public StarListForQiYeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rlv_star_list, parent, false);

        LinearLayout ll_rlvitem = (LinearLayout) view.findViewById(R.id.ll_rlvitem);
        ViewGroup.LayoutParams layoutParams = ll_rlvitem.getLayoutParams();
        layoutParams.width = mValue / 2;
        ll_rlvitem.setLayoutParams(layoutParams);

        return new StarListForQiYeHolder(view);
    }

    @Override
    public void onBindViewHolder(StarListForQiYeHolder holder, final int position) {
        final StarListInfos.RowsBean rowsBean = rows.get(position);
        Resources resources = mContext.getResources();
        //显示明星列表图片
        ImageLoader.getInstance().displayImage(HttpContans.HTTP_HOST + rowsBean.getStartShowPic(), holder.iv_pic);
        //粉丝数量
        holder.tv_star_fans.setText(rowsBean.getFansCount());
        //明星姓名
        String realName = rowsBean.getRealName();
        final int starId = rowsBean.getId();
        holder.tv_star_name.setText(realName);
        //是否关注  0：未关注      1：已关注
        final int isfollow = rowsBean.getIsfollow();

        MyLogger.jLog().i("position:" + position + ",isfollow" + isfollow);
        if (isfollow == 0) {
            holder.tv_focus.setText("加关注");
            holder.tv_focus.setTextColor(resources.getColor(R.color.white));
            holder.tv_focus.setBackgroundDrawable(resources.getDrawable(R.drawable.care_nor));
        } else {
            holder.tv_focus.setText("已关注");
            holder.tv_focus.setTextColor(resources.getColor(R.color.pink));
            holder.tv_focus.setBackgroundDrawable(resources.getDrawable(R.drawable.shape_focud_bg));
        }

        holder.tv_focus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isfollow == 0) {
                    EventBus.getDefault().post(new EventOnClickFocus());
                    MyLogger.jLog().i("关注。。。");
                    mCore.toFocus(starId);
                    rowsBean.setIsfollow(1);
                } else {
                    mDialogCanaleFocusStyle.show();
                    mDialogCanaleFocusStyle.initTitle(rows.get(position).getRealName());
                    newPosition = position;
                    MyLogger.jLog().i("取消关注。。。");

//                    mCore.cancaleFocus(starId);
//                    rowsBean.setIsfollow(0);
                }
                notifyDataSetChanged();
            }
        });
        //点击事件
        holder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onClickItems(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        MyLogger.jLog().i("getItemCount:" + (rows == null ? 0 : rows.size()));
        return rows == null ? 0 : rows.size();
    }

    public class StarListForQiYeHolder extends RecyclerView.ViewHolder {
        //条目
        @ViewInject(R.id.rl_item)
        RelativeLayout rl_item;

        //关注
        @ViewInject(R.id.tv_focus)
        TextView tv_focus;

        //明星姓名
        @ViewInject(R.id.tv_star_name)
        TextView tv_star_name;

        //粉丝数量
        @ViewInject(R.id.tv_star_fans)
        TextView tv_star_fans;

        //明星图片
        @ViewInject(R.id.iv_pic)
        ImageView iv_pic;


        public StarListForQiYeHolder(View itemView) {
            super(itemView);
            ViewUtils.inject(this, itemView);
        }
    }

    public void stop() {
        mCore.stopRequest();
    }

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onClickItems(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}