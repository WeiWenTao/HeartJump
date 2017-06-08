package com.cucr.myapplication.adapter.RlAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cucr.myapplication.R;
import com.cucr.myapplication.bean.starClassify.StarClassif_all;
import com.cucr.myapplication.widget.dialog.DialogCanaleFocusStyle;

import java.io.File;
import java.util.List;

/**
 *
 */
public class StarClassifyAdapter_all extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private File cacheDir;
    public static final int TYPE_HEAD = 0;
    public static final int TYPE_CONTENT = 1;
    private List<StarClassif_all> list = null;

    //对话框
    private DialogCanaleFocusStyle mDialogCanaleFocusStyle;

    //点击时候的背景
    private FrameLayout mFl;

    private Context mContext;
    private OnItemListeren onItemListeren;

    public StarClassifyAdapter_all(Context context, List<StarClassif_all> list) {
        this.mContext = context;
        this.list = list;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        if (viewType == TYPE_HEAD) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_star_classif_head, parent, false);
            HeadView headView = new HeadView(view);

            return headView;
        } else if (viewType == TYPE_CONTENT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_tast, parent, false);
            mFl = (FrameLayout) view.findViewById(R.id.fl_star_bg);
            MyHoldView contentHold = new MyHoldView(view);

            //初始化取消关注对话框
            initDialog();

            return contentHold;
        }
        return null;
    }

    private void initDialog() {
        mDialogCanaleFocusStyle = new DialogCanaleFocusStyle(mContext, R.style.ShowAddressStyleTheme);
        mDialogCanaleFocusStyle.setOnClickBtListener(new DialogCanaleFocusStyle.OnClickBtListener() {
            @Override
            public void clickConfirm() {
                mFl.setVisibility(View.GONE);
                mDialogCanaleFocusStyle.dismiss();
            }

            @Override
            public void clickCancle() {

                mDialogCanaleFocusStyle.dismiss();
            }
        });
    }

    /*
    判断位置
     */
    public boolean isHeader(int position) {
        return position == 0;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            StarClassif_all info = list.get(i);
            List<String> list1 = info.getDataList();

            //头部数据
            if (position == count) {
                ((HeadView) holder).item_tast_head_title.setText(info.getHeader());
            }


            count++;
            for (int j = 0; j < list1.size(); j++) {
                if (position == count) {

                    //设置标记 防止错乱
                    ((MyHoldView) holder).item_tast_linearlayout.setTag(((MyHoldView) holder).fl_bg);
                    ((MyHoldView) holder).item_tast_linearlayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //获取标签
                            mFl = (FrameLayout) view.getTag();
                            if (mFl.getVisibility() == View.GONE) {
                                Toast.makeText(view.getContext(), "已关注 林更新", Toast.LENGTH_SHORT).show();
                                mFl.setVisibility(View.VISIBLE);
                            } else if (mFl.getVisibility() == View.VISIBLE) {
                                mDialogCanaleFocusStyle.show();
                                mDialogCanaleFocusStyle.initTitle("林更新");

                            }

                        }
                    });

                    ((MyHoldView) holder).rl_goto_starpager.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            int position = holder.getLayoutPosition();
                            onItemListeren.OnItemClick(view, position);
                        }
                    });
                }

                count++;
            }

        }

    }


    /**
     * 获取类型
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        //循环遍历所有的数据判断position==0执行头部布局。否则内容
        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            StarClassif_all info = list.get(i);
            List<String> list1 = info.getDataList();
            if (position == count) {
                return TYPE_HEAD;
            }
            count++;

            for (int j = 0; j < list1.size(); j++) {
                if (position == count) {
                    return TYPE_CONTENT;
                }
                count++;
            }
        }
        return 0;
    }

    //   通过循环实体类中的数据获取所有的长度和
    @Override
    public int getItemCount() {//通过获取的list长度判断
        int count = list.size();
        for (int i = 0; i < list.size(); i++) {
            StarClassif_all bean = list.get(i);
            List<String> dataList = bean.getDataList();
            count += dataList.size();
        }
        return count;
    }

    class MyHoldView extends RecyclerView.ViewHolder {
        private RelativeLayout rl_goto_starpager;
        private LinearLayout item_tast_linearlayout;
        //灰色背景
        private FrameLayout fl_bg;

        public MyHoldView(View itemView) {
            super(itemView);
            fl_bg = (FrameLayout) itemView.findViewById(R.id.fl_star_bg);
            rl_goto_starpager = (RelativeLayout) itemView.findViewById(R.id.rl_goto_starpager_);
            item_tast_linearlayout = (LinearLayout) itemView.findViewById(R.id.item_tast_linearlayout);
        }
    }

    class HeadView extends RecyclerView.ViewHolder {
        private TextView item_tast_head_title;
        private LinearLayout item_tast_head_linearlayout;

        public HeadView(View itemView) {
            super(itemView);
            item_tast_head_title = (TextView) itemView.findViewById(R.id.item_tast_head_title);
            item_tast_head_linearlayout = (LinearLayout) itemView.findViewById(R.id.item_tast_head_linearlayout);
        }
    }

    public interface OnItemListeren {
        void OnItemClick(View view, int position);
    }

    public void setOnItemListeren(OnItemListeren onItemListeren) {
        this.onItemListeren = onItemListeren;
    }

}
