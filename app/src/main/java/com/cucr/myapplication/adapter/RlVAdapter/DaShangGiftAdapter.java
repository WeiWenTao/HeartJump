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

import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.R;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.core.daShang.DaShangCore;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.bean.CommonRebackMsg;
import com.cucr.myapplication.bean.eventBus.EventContentId;
import com.cucr.myapplication.bean.eventBus.EventDsSuccess;
import com.cucr.myapplication.bean.eventBus.EventRewardGifts;
import com.cucr.myapplication.bean.fenTuan.FtGiftsInfo;
import com.cucr.myapplication.utils.MyLogger;
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

public class DaShangGiftAdapter extends RecyclerView.Adapter<DaShangGiftAdapter.GiftViewHolder> {

    private FtGiftsInfo ftGiftsInfo;
    private DaShangCore mCore;
    private Gson mGson;
    private int giftCount;

    public DaShangGiftAdapter(FtGiftsInfo ftGiftsInfo) {
        this.ftGiftsInfo = ftGiftsInfo;
        MyLogger.jLog().i("ftGiftsInfo:" + ftGiftsInfo);
        mCore = new DaShangCore();
        mGson = new Gson();
    }

    @Override
    public GiftViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dashang_gift, parent, false);
        View ll_item = view.findViewById(R.id.ll_item);
        //设置布局参数  保证 4 个item占一个屏幕
        WindowManager wm = (WindowManager) MyApplication.getInstance().getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        ViewGroup.LayoutParams layoutParams = ll_item.getLayoutParams();
        layoutParams.width = width / 4;
        ll_item.setLayoutParams(layoutParams);
        ll_item.requestLayout();

        return new GiftViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GiftViewHolder holder, final int position) {

        final FtGiftsInfo.RowsBean rowsBean = ftGiftsInfo.getRows().get(position);
//        switch (rowsBean.getId()) {
//
//            case 1:
//                holder.iv_gift_img.setImageResource(R.drawable.kiss_l);
//                break;
//
//            case 2:
//                holder.iv_gift_img.setImageResource(R.drawable.great_l);
//                break;
//
//            case 3:
//                holder.iv_gift_img.setImageResource(R.drawable.diamond_l);
//                break;
//
//            case 4:
//                holder.iv_gift_img.setImageResource(R.drawable.rocket_l);
//                break;
//
//            default:
//                holder.iv_gift_img.setImageResource(R.drawable.ic_launcher);
//                break;
//        }

        ImageLoader.getInstance().displayImage(HttpContans.HTTP_HOST+rowsBean.getPicUrl(),holder.iv_gift_img,MyApplication.getImageLoaderOptions());
        holder.tv_gift_coast.setText(rowsBean.getProportion() + "");   //礼物价值
        holder.tv_gift_name.setText(rowsBean.getName());          //礼物名称
        holder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EventContentId event = EventBus.getDefault().getStickyEvent(EventContentId.class);

                mCore.reward(event.getContentId(), 0, rowsBean.getId(), 1,  //rewardMoney 打赏数量
                        new OnCommonListener() {
                            @Override
                            public void onRequestSuccess(Response<String> response) {
                                CommonRebackMsg msg = mGson.fromJson(response.get(), CommonRebackMsg.class);
                                if (msg.isSuccess()) {
                                    EventBus.getDefault().post(new EventRewardGifts(rowsBean.getPicUrl(),rowsBean.getId()));
                                    giftCount++;
                                    ToastUtils.showToast("打赏"+ rowsBean.getName() + "成功");
                                    if (mClickDashang != null){
                                        mClickDashang.clickDaShang(rowsBean.getProportion());
                                    }
                                    EventBus.getDefault().post(new EventDsSuccess(event.getPosition()));

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
        return ftGiftsInfo == null ? 0 : ftGiftsInfo.getRows().size();
    }

    public class GiftViewHolder extends RecyclerView.ViewHolder {

        @ViewInject(R.id.iv_gift_img)
        private ImageView iv_gift_img;

        @ViewInject(R.id.tv_gift_name)
        private TextView tv_gift_name;

        @ViewInject(R.id.tv_gift_coast)
        private TextView tv_gift_coast;

        @ViewInject(R.id.ll_item)
        private LinearLayout ll_item;

        public GiftViewHolder(View itemView) {
            super(itemView);
            ViewUtils.inject(this, itemView);
        }
    }

    private OnClickDashang mClickDashang;

    public interface OnClickDashang{
        void clickDaShang(int giftCoast);
    }

    public void setClickDashang(OnClickDashang clickDashang) {
        mClickDashang = clickDashang;
    }
}
