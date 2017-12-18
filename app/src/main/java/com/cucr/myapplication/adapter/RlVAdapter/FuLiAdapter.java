package com.cucr.myapplication.adapter.RlVAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.model.fuli.ActiveInfo;
import com.cucr.myapplication.utils.CommonUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by 911 on 2017/6/23.
 */
public class FuLiAdapter extends RecyclerView.Adapter<FuLiAdapter.FiLiHolder> {

    private Context mContext;
    private List<ActiveInfo.RowsBean> activeInfos;

    public FuLiAdapter(Context context, List<ActiveInfo.RowsBean> activeInfos) {
        this.activeInfos = activeInfos;
        mContext = context;
    }

    public void setDate(List<ActiveInfo.RowsBean> activeInfos){
        this.activeInfos = activeInfos;
        notifyDataSetChanged();
    }

    @Override
    public FiLiHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rl_fuli, parent, false);
        FiLiHolder holder = new FiLiHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(FiLiHolder holder, final int position) {
        final ActiveInfo.RowsBean bean = activeInfos.get(position);

        //点击事件
        holder.rl_fuli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemListener != null) {
                    onItemListener.OnItemClick(v, bean.getId());
                }
            }
        });

        //如果集合不为空
        if (activeInfos != null) {
            //获取对象
            ActiveInfo.RowsBean rowsBean = activeInfos.get(position);

            //显示图片
            ImageLoader.getInstance().displayImage(HttpContans.HTTP_HOST + rowsBean.getPicUrl(), holder.iv_active_bg);

            //显示进行状态
            String endTime = rowsBean.getEndTime();
            holder.tv_isopen.setText(CommonUtils.IsGone(endTime) ? "进行中" : "已结束");

            //跳转链接
//            String locationUrl = rowsBean.getLocationUrl();
        }
    }

    @Override
    public int getItemCount() {
        return activeInfos == null ? 0 : activeInfos.size();
    }

    class FiLiHolder extends RecyclerView.ViewHolder {
        private RelativeLayout rl_fuli;
        private TextView tv_isopen;
        private ImageView iv_active_bg;

        public FiLiHolder(View itemView) {
            super(itemView);
            rl_fuli = (RelativeLayout) itemView.findViewById(R.id.rl_fuli);
            tv_isopen = (TextView) itemView.findViewById(R.id.tv_isopen);
            iv_active_bg = (ImageView) itemView.findViewById(R.id.iv_active_bg);
        }
    }

    private OnItemListener onItemListener;


    public interface OnItemListener {
        void OnItemClick(View view, int activeId);
    }

    public void setOnItemListener(OnItemListener onItemListener) {
        this.onItemListener = onItemListener;
    }

}
