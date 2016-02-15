package com.example.administrator.codingmusic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.codingmusic.adapter.MyMusicListAdapter;
import com.example.administrator.codingmusic.util.MediaUtils;
import com.example.administrator.codingmusic.vo.Mp3Info;

import java.util.ArrayList;

/**
 * MyMusicListFragment
 *
 * @author: Xingkai Ren
 * @time: 2016/2/10  19:03
 */
public class MyMusicListFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    private static final String TAG = "MyMusicListFragment";

    private ListView listView_my_music;

    //装载音乐信息
    private ArrayList<Mp3Info> mp3Infos;
    private MainActivity mainActivity;
    private MyMusicListAdapter myMusicListAdapter;

    //更新组件
    private ImageView imageViewAlbum;
    private TextView textViewsinger;
    private TextView textViewsong;
    private ImageView imageViewPlayPause, imageViewNext;

    private boolean isPause = false;

    private int position = 0;//当前播放位置

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainActivity = (MainActivity) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.my_music_list_layout, null);
        listView_my_music = (ListView) view.findViewById(R.id.listView_myMusic);//初始化ListView

        imageViewAlbum = (ImageView) view.findViewById(R.id.imageView_album);
        imageViewNext = (ImageView) view.findViewById(R.id.iv_next);
        imageViewPlayPause = (ImageView) view.findViewById(R.id.iv_play_pause);
        textViewsinger = (TextView) view.findViewById(R.id.tv_singer);
        textViewsong = (TextView) view.findViewById(R.id.tv_songName);

        listView_my_music.setOnItemClickListener(this);
        imageViewPlayPause.setOnClickListener(this);
        imageViewNext.setOnClickListener(this);
        imageViewAlbum.setOnClickListener(this);
        loadData();
        //绑定播放服务
        mainActivity.bindPlayService();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //解除绑定播放服务
        mainActivity.unbindPlayService();
    }

    public static MyMusicListFragment newInstance() {
        MyMusicListFragment my = new MyMusicListFragment();
        return my;
    }

    /**
     * 加载本地音乐列表
     */
    private void loadData() {
        mp3Infos = MediaUtils.getMp3Infos(mainActivity);
        myMusicListAdapter = new MyMusicListAdapter(mainActivity, mp3Infos);
        listView_my_music.setAdapter(myMusicListAdapter);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mainActivity.playService.play(position);
    }

    /**
     * 回调播放状态下的UI设置
     *
     * @param position
     */
    public void changeUIStatusOnPlay(int position) {
        if (position >= 0 && position < mp3Infos.size()) {
            Mp3Info mp3Info = mp3Infos.get(position);
            textViewsong.setText(mp3Info.getTitle());
            textViewsinger.setText(mp3Info.getArtist());
            imageViewPlayPause.setImageResource(R.mipmap.player_btn_pause_normal);

            this.position = position;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_play_pause: {
                if (mainActivity.playService.isPlaying()) {
                    imageViewPlayPause.setImageResource(R.mipmap.player_btn_play_normal);
                    mainActivity.playService.pause();
                    isPause = true;
                } else {
                    if (isPause) {
                        imageViewPlayPause.setImageResource(R.mipmap.player_btn_pause_normal);
                        mainActivity.playService.start();
                    } else {
                        mainActivity.playService.play(0);//从头开始播放
                    }
                    isPause = false;


                }
                break;
            }
            case R.id.iv_next: {
                mainActivity.playService.next();
                break;
            }
            case R.id.imageView_album: {
                Intent intent = new Intent(mainActivity, PlayActivity.class);
                intent.putExtra("isPause", isPause);
                startActivity(intent);
                break;
            }

            default:
                break;
        }
    }
}
