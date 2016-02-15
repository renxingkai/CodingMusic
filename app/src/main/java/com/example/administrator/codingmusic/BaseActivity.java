package com.example.administrator.codingmusic;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;

/**
 * BaseActivity
 *
 * @author: Xingkai Ren
 * @time: 2016/2/12  16:43
 */
public abstract class BaseActivity extends FragmentActivity {


    protected PlayService playService;

    private boolean isbound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindPlayService();
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            PlayService.PlayBinder playBinder = (PlayService.PlayBinder) service;
            playService = playBinder.getPlayService();
            playService.setMusicUpdateListener(musicUpdateListener);
            musicUpdateListener.onChange(playService.getCurrentPosition());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            playService = null;
            isbound = false;
        }
    };

    private PlayService.MusicUpdateListener musicUpdateListener = new PlayService.MusicUpdateListener() {
        @Override
        public void onPublish(int progress) {
            publish(progress);
        }

        @Override
        public void onChange(int position) {
            change(position);
        }
    };

    public abstract void publish(int progress);

    public abstract void change(int position);

    /**
     * 绑定服务
     */
    public void bindPlayService() {
        if (!isbound) {
            Intent intent = new Intent(this, PlayService.class);
            bindService(intent, conn, Context.BIND_AUTO_CREATE);
            isbound = true;
        }
    }

    /**
     * 解除绑定服务
     */
    public void unbindPlayService() {
        if (isbound) {
            unbindService(conn);
            isbound = false;
        }
    }

}
