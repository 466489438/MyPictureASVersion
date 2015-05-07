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
public class camera extends Activity {
    private ImageView imageView;
   // private Button button;
    private final int CAMREA_REQUSET = 1;
    private final int VIDEO_REQUSET = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);
        imageView = (ImageView) this.findViewById(R.id.imageView);
        Intent intent = new Intent(
                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMREA_REQUSET);
        //
        //Intent videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        //videoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File("")));
        //startActivityForResult(videoIntent, VIDEO_REQUSET);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CAMREA_REQUSET:
                if (resultCode == RESULT_OK) {
                    if(data !=null){ //������δָ��intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        //����������ͼ
                        if(data.hasExtra("data")){
                            Log.v("TAG", "return with thumbnail");
                            Bitmap thumbnail = data.getParcelableExtra("data");
                            imageView.setImageBitmap(thumbnail);
                            //�õ�bitmap��Ĳ���
                            super.onActivityResult(requestCode, resultCode, data);
                            //super.setActivityResultCallback.onActivityResult(requestCode, resultCode, data);
                        }
                    }else{
                        Log.v("TAG", "return without thumbnail");
                        //����ָ����Ŀ��uri���洢��Ŀ��uri��intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        // ͨ��Ŀ��uri���ҵ�ͼƬ
                        // ��ͼƬ�����Ŵ���
                        // ����
                    }
                }
        }
    }
}