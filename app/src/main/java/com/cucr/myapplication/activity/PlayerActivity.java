package com.cucr.myapplication.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cleveroad.play_widget.PlayLayout;
import com.cucr.myapplication.R;

import org.zackratos.ultimatebar.UltimateBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PlayerActivity extends AppCompatActivity implements PlayLayout.OnButtonsClickListener, PlayLayout.OnProgressChangedListener {

    private PlayLayout mPlayLayout;
    private MediaPlayer mMediaPlayer;
    private List<PlayerListActivity.Music> mList;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_player);

        //沉浸栏
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setImmersionBar();

        initData();

        mPlayLayout = (PlayLayout) findViewById(R.id.revealView);
        mPlayLayout.setOnButtonsClickListener(this);
        mPlayLayout.setOnProgressChangedListener(this);
        PlayerListActivity.Music data = (PlayerListActivity.Music) getIntent().getSerializableExtra("data");
        mMediaPlayer = MediaPlayer.create(this, data.getRes());
        try {
            mMediaPlayer.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
        startTrackingPosition();

    }

    private void initData() {
        mList = new ArrayList<>();
        mList.add(new PlayerListActivity.Music(R.raw.choubaguai, "丑八怪"));
        mList.add(new PlayerListActivity.Music(R.raw.nhywzy, "你还要我怎样"));
        mList.add(new PlayerListActivity.Music(R.raw.shenshi, "绅士"));
        mList.add(new PlayerListActivity.Music(R.raw.yanyuan, "演员"));
        mList.add(new PlayerListActivity.Music(R.raw.aimei, "暧昧"));
    }

    @Override
    public void onShuffleClicked() {

    }

    @Override
    public void onSkipPreviousClicked() {
        if (position == 0) {
            position = mList.size();
        } else {

            position--;
        }
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer = MediaPlayer.create(this, mList.get(position).getRes());
//            mMediaPlayer.prepareAsync();
            mMediaPlayer.start();
            startTrackingPosition();

        }
    }

    @Override
    public void onSkipNextClicked() {
        if (position == mList.size()) {
            position = 0;
        } else {
            position++;
        }

        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer = MediaPlayer.create(this, mList.get(position).getRes());
//            mMediaPlayer.prepareAsync();
            mMediaPlayer.start();
            startTrackingPosition();

        }

    }

    @Override
    public void onRepeatClicked() {

    }

    @Override
    public void onPlayButtonClicked() {
        if (mPlayLayout == null) {
            return;
        }
        if (mPlayLayout.isOpen()) {
            mMediaPlayer.pause();
            mPlayLayout.startDismissAnimation();
        } else {
            mMediaPlayer.start();
            mPlayLayout.startRevealAnimation();
        }
    }

    @Override
    public void onPreSetProgress() {
        if (timer == null)
            return;
        timer.cancel();
        timer.purge();
        timer = null;
    }

    @Override
    public void onProgressChanged(float progress) {
        mMediaPlayer.seekTo((int) (mMediaPlayer.getDuration() * progress));
        startTrackingPosition();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMediaPlayer.stop();
    }

    private Timer timer;

    private void startTrackingPosition() {
        timer = new Timer("MainActivity Timer");
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                MediaPlayer tempMediaPlayer = mMediaPlayer;
                if (tempMediaPlayer != null && tempMediaPlayer != null && tempMediaPlayer.isPlaying()) {

                    mPlayLayout.setPostProgress((float) tempMediaPlayer.getCurrentPosition() / tempMediaPlayer.getDuration());
                }

            }
        }, 20, 20);
    }
}
