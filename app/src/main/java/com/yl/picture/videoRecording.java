package com.yl.picture;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by Lemon on 2015/5/7 0007.
 */
public class videoRecording extends Activity {
    private ImageView imageView;
    // private Button button;
    private final int VIDEO_REQUSET = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);
        imageView = (ImageView) this.findViewById(R.id.imageView);
        Intent videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        videoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File("")));
        startActivityForResult(videoIntent, VIDEO_REQUSET);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(RESULT_OK == resultCode){
            //视频录制成功
        }
        else{
            //视频录制失败
        }
        super.onActivityResult(requestCode,resultCode,data);
    }
}