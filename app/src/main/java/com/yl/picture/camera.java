package com.yl.picture;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import java.io.File;

/**
 * Created by Lemon on 2015/5/7 0007.
 */
public class camera extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.video);
        //
        Intent videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        //
        videoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File("")));
        //
        //startActivityForResult(videoIntent, VIDEO_REQUEST_CODE);
    }
}