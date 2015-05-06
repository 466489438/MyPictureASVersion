package com.yl.picture;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.File;

/**
 * Created by Lemon on 2015/5/6 0006.
 */
public class videoPlayer extends Activity {
    private String path;
    VideoView videoView;
    MediaController mController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        this.setContentView(R.layout.video);
        //获取界面上VideoView组件
        videoView = (VideoView) findViewById(R.id.video);
        //创建MediaController对象
        mController = new MediaController(this);
        path = ReadSDPath() + "/DCIM/100MEDIA/VIDEO0013.mp4";
        //调用外部播放器
        Intent videoIntent = new Intent(Intent.ACTION_VIEW);
        //设置播放视频的目标路径
        videoIntent.setDataAndType(Uri.fromFile(new File(path)),"video/*");
        //请求视频播放
        startActivity(Intent.createChooser(videoIntent,"Play Video"));
        /*//调用videoView播放，效果不好，不采用
        File video = new File(path);
        if (video.exists()) {
            videoView.setVideoPath(video.getAbsolutePath());
            //设置videoView与mController建立关联
            videoView.setMediaController(mController);
            //设置mController与videoView建立关联
            mController.setMediaPlayer(videoView);
            //让videoView获取焦点
            videoView.requestFocus();
        }*/
    }

    //获取SD卡的跟路径
    public String ReadSDPath() {
        boolean SDExit = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (SDExit) {
            return Environment.getExternalStorageDirectory().toString();
        } else {
            return null;
        }
    }
}