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
        //��ȡ������VideoView���
        videoView = (VideoView) findViewById(R.id.video);
        //����MediaController����
        mController = new MediaController(this);
        path = ReadSDPath() + "/DCIM/100MEDIA/VIDEO0013.mp4";
        //�����ⲿ������
        Intent videoIntent = new Intent(Intent.ACTION_VIEW);
        //���ò�����Ƶ��Ŀ��·��
        videoIntent.setDataAndType(Uri.fromFile(new File(path)),"video/*");
        //������Ƶ����
        startActivity(Intent.createChooser(videoIntent,"Play Video"));
        /*//����videoView���ţ�Ч�����ã�������
        File video = new File(path);
        if (video.exists()) {
            videoView.setVideoPath(video.getAbsolutePath());
            //����videoView��mController��������
            videoView.setMediaController(mController);
            //����mController��videoView��������
            mController.setMediaPlayer(videoView);
            //��videoView��ȡ����
            videoView.requestFocus();
        }*/
    }

    //��ȡSD���ĸ�·��
    public String ReadSDPath() {
        boolean SDExit = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (SDExit) {
            return Environment.getExternalStorageDirectory().toString();
        } else {
            return null;
        }
    }
}