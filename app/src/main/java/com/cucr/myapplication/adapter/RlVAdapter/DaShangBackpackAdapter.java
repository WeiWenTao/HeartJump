package com.cucr.myapplication.adapter.RlVAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cucr.myapplication.MyApplication;
import com.cucr.myapplication.R;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.core.daShang.DaShangCore;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.model.CommonRebackMsg;
import com.cucr.myapplication.model.eventBus.EventContentId;
import com.cucr.myapplication.model.eventBus.EventDsSuccess;
import com.cucr.myapplication.model.eventBus.EventRewardGifts;
import com.cucr.myapplication.model.fenTuan.FtBackpackInfo;
import com.cucr.myapplication.utils.ToastUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by cucr on 2017/11/7.
 */

public class DaShangBackpackAdapter extends RecyclerView.Adapter<DaShangBackpackAdapter.BackpackViewHolder> {

    private FtBackpackInfo ftBackpackInfo;
    private DaShangCore mCore;
    private Gson mGson;
    private int giftCount; //打赏个数

    public DaShangBackpackAdapter(FtBackpackInfo ftBackpackInfo) {
        this.ftBackpackInfo = ftBackpackInfo;
        mCore = new DaShangCore();
        mGson = new Gson();
    }

    @Override
    public BackpackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dashang_backpack, parent, false);
        View ll_item = view.findViewById(R.id.ll_item);
        //设置布局参数  保证 4 个item占一个屏幕
        WindowManager wm = (WindowManager) MyApplication.getInstance().getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        ViewGroup.LayoutParams layoutParams = ll_item.getLayoutParams();
        layoutParams.width = width / 4;
        ll_item.setLayoutParams(layoutParams);
        ll_item.requestLayout();

        return new BackpackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BackpackViewHolder holder, int position) {

        final FtBackpackInfo.ObjBean.ListBean listBean = ftBackpackInfo.getObj().getList().get(position);

//        switch (listBean.getUserAccountType().getId()) {
//
//            case 1:
//                holder.iv_bp_img.setImageResource(R.drawable.kiss_l);
//                break;
//
//            case 2:
//                holder.iv_bp_img.setImageResource(R.drawable.great_l);
//                break;
//
//            case 3:
//                holder.iv_bp_img.setImageResource(R.drawable.diamond_l);
//                break;
//
//            case 4:
//                holder.iv_bp_img.setImageResource(R.drawable.rocket_l);
//                break;
//
//            default:
//                holder.iv_bp_img.setImageResource(R.drawable.ic_launcher);
//                break;
//        }

        ImageLoader.getInstance().displayImage(HttpContans.HTTP_HOST + listBean.getUserAccountType().getPicUrl(), holder.iv_bp_img, MyApplication.getImageLoaderOptions());
        holder.tv_bp_save.setText(listBean.getBalance() + "");   //礼物个数
        holder.tv_bp_name.setText(listBean.getUserAccountType().getName());          //礼物名称
        holder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EventContentId event = EventBus.getDefault().getStickyEvent(EventContentId.class);

                mCore.reward(event.getContentId(), listBean.getUserAccountType().getId(), listBean.getUserAccountType().getId(), 1, //rewardMoney 打赏数量
                        new OnCommonListener() {
                            @Override
                            public void onRequestSuccess(Response<String> response) {
                                CommonRebackMsg msg = mGson.fromJson(response.get(), CommonRebackMsg.class);
                                if (msg.isSuccess()) {
                                    giftCount++;
                                    ToastUtils.showToast("打赏" + giftCount + "个" + listBean.getUserAccountType().getName() + "成功");
                                    //打赏成功eventvus传值  粉团播放动画
                                    EventBus.getDefault().post(new EventRewardGifts(listBean.getUserAccountType().getPicUrl(),listBean.getUserAccountType().getId()));
                                    listBean.setBalance(listBean.getBalance() - 1);
                                    if (mClickDashang != null) {
                                        mClickDashang.clickDaShang(listBean.getUserAccountType().getProportion());
                                    }
                                    EventBus.getDefault().post(new EventDsSuccess(event.getPosition()));
                                    notifyDataSetChanged();
                                } else {
                                    ToastUtils.showToast(msg.getMsg());
                                }
                            }
                        });

            }
        });
    }

    @Override
    public int getItemCount() {
        return ftBackpackInfo == null ? 0 : ftBackpackInfo.getObj().getList().size();
    }

    public class BackpackViewHolder extends RecyclerView.ViewHolder {
        @ViewInject(R.id.iv_bp_img)
        private ImageView iv_bp_img;

        @ViewInject(R.id.tv_bp_name)
        private TextView tv_bp_name;

        @ViewInject(R.id.tv_bp_save)
        private TextView tv_bp_save;

        @ViewInject(R.id.ll_item)
        private LinearLayout ll_item;

        public BackpackViewHolder(View itemView) {
            super(itemView);
            ViewUtils.inject(this, itemView);
        }
    }

    private OnClickDashang mClickDashang;

    public interface OnClickDashang {
        void clickDaShang(int giftCoast);
    }

    public void setClickDashang(OnClickDashang clickDashang) {
        mClickDashang = clickDashang;
    }
}
