package com.cucr.myapplication.activity.video;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.LvAdapter.ViderRecommendAdapter;
import com.cucr.myapplication.constants.HttpContans;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.zackratos.ultimatebar.UltimateBar;

import tcking.github.com.giraffeplayer.GiraffePlayer;
import tv.danmaku.ijk.media.player.IMediaPlayer;

public class VideoActivity extends Activity {

    //播放器
    private GiraffePlayer player;

    //相关推荐
    @ViewInject(R.id.lv_video_recommend)
    ListView lv_video_recommend;

    //评论栏
    @ViewInject(R.id.rl_commend_bar)
    RelativeLayout rl_commend_bar;

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ViewUtils.inject(this);

        initView();

        initVideo();

        url = HttpContans.HTTP_HOST + getIntent().getStringExtra("path");

        player.setDefaultRetryTime(5 * 1000);
        player.play(url);
        player.setShowNavIcon(true);

    }

    private void initVideo() {
        player = new GiraffePlayer(this);
        player.onControlPanelVisibilityChang(new GiraffePlayer.OnControlPanelVisibilityChangeListener() {
            @Override
            public void change(boolean isShowing) {
                if (player.isPlaying()) {
                    lv_video_recommend.setSystemUiVisibility(isShowing ? View.VISIBLE : View.INVISIBLE);
                }
            }
        });
        player.onComplete(new Runnable() {
            @Override
            public void run() {
                //callback when video is finish
                Toast.makeText(getApplicationContext(), "video play completed", Toast.LENGTH_SHORT).show();
            }
        }).onInfo(new GiraffePlayer.OnInfoListener() {
            @Override
            public void onInfo(int what, int extra) {
                switch (what) {
                    case IMediaPlayer.MEDIA_INFO_BUFFERING_START:
                        Log.i("test", "buffering start" + extra);
                        //do something when buffering start
                        break;
                    case IMediaPlayer.MEDIA_INFO_BUFFERING_END:
                        //do something when buffering end
                        Log.i("test", "buffering end");
                        break;
                    case IMediaPlayer.MEDIA_INFO_NETWORK_BANDWIDTH:
                        //download speed
                        Log.i("test", "download speed");
//                        ((TextView) findViewById(R.id.tv_speed)).setText(Formatter.formatFileSize(getApplicationContext(),extra)+"/s");
                        break;
                    case IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                        //do something when video rendering
                        Log.i("test", "video rendering");
//                        findViewById(R.id.tv_speed).setVisibility(View.GONE);
                        break;
                }
            }
        }).onError(new GiraffePlayer.OnErrorListener() {
            @Override
            public void onError(int what, int extra) {
                Toast.makeText(getApplicationContext(), "video play error", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initView() {
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setImmersionBar();

        //设置导航栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.blue_black));
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) rl_commend_bar.getLayoutParams();
            layoutParams.setMargins(0, 0, 0, ultimateBar.getNavigationHeight(this));
            rl_commend_bar.setLayoutParams(layoutParams);
        }

        View headerView = View.inflate(this, R.layout.header_video_lv, null);
        ((TextView) headerView.findViewById(R.id.tv_new_title)).getPaint().setFakeBoldText(true);
        lv_video_recommend.addHeaderView(headerView, null, true);
        lv_video_recommend.setHeaderDividersEnabled(false);
        lv_video_recommend.setAdapter(new ViderRecommendAdapter());
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            player.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null) {
            player.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.onDestroy();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (player != null) {
            player.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public void onBackPressed() {
        if (player != null && player.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

}
