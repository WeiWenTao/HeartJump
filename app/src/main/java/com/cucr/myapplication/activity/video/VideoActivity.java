package com.cucr.myapplication.activity.video;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.LvAdapter.ViderRecommendAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.fenTuan.QueryFtInfos;
import com.cucr.myapplication.constants.HttpContans;
import com.danikula.videocache.HttpProxyCacheServer;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.vanniktech.emoji.EmojiEditText;

import org.zackratos.ultimatebar.UltimateBar;

import tcking.github.com.giraffeplayer.GiraffePlayer;

public class VideoActivity extends Activity {

    //播放器
    private GiraffePlayer player;

    //相关推荐
    @ViewInject(R.id.lv_video_recommend)
    private ListView lv_video_recommend;

    //评论列表
    @ViewInject(R.id.lv_ft_catgory)
    private ListView lv_ft_catgory;

    //点赞
    @ViewInject(R.id.iv_zan)
    private ImageView iv_zan;

    //点赞数量
    @ViewInject(R.id.tv_givecount)
    private TextView tv_givecount;

    //评论框
    @ViewInject(R.id.et_comment)
    private EmojiEditText et_comment;

    //评论数量
    @ViewInject(R.id.tv_comment_count)
    private TextView tv_comment_count;

    //评论和点赞
    @ViewInject(R.id.ll_comment_good)
    private LinearLayout ll_comment_good;

    //表情和发送
    @ViewInject(R.id.ll_emoji_send)
    private LinearLayout ll_emoji_send;

    //表情按钮
    @ViewInject(R.id.iv_emoji)
    private ImageView iv_emoji;

    private String url;
    private QueryFtInfos.RowsBean rowsBean;
    private HttpProxyCacheServer mProxy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ViewUtils.inject(this);

        initView();

//        initVideo();

        //获取视频信息
        rowsBean = (QueryFtInfos.RowsBean) getIntent().getSerializableExtra("rowsBean");
        url = HttpContans.IMAGE_HOST + rowsBean.getAttrFileList().get(0).getFileUrl();
//        url = HttpContans.IMAGE_HOST + getIntent().getStringExtra("path");

    }





    private void initView() {
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setImmersionBar();
        View headerView = View.inflate(MyApplication.getInstance(), R.layout.header_video_lv, null);
        ((TextView) headerView.findViewById(R.id.tv_new_title)).getPaint().setFakeBoldText(true);
        lv_video_recommend.addHeaderView(headerView, null, true);
        lv_video_recommend.setHeaderDividersEnabled(false);
        lv_video_recommend.setAdapter(new ViderRecommendAdapter());
    }



}
