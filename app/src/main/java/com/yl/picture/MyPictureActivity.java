package com.yl.picture;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MyPictureActivity extends Activity {
	private Button button1;
	private Button button2;
	private Button button3;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        this.button1=(Button) this.findViewById(R.id.button1);
        this.button2=(Button) this.findViewById(R.id.button2);
		this.button3=(Button) this.findViewById(R.id.button3);
        //点击按钮进入缩略模式
        this.button1.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent intent=new Intent(MyPictureActivity.this,SuolueActivity.class);
				startActivity(intent);
			}
		});
		//点击按钮进入视频播放
		this.button2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent=new Intent(MyPictureActivity.this,videoPlayer.class);
				startActivity(intent);
			}
		});
		//点击按钮进入拍照模式
		this.button3.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent intent=new Intent(MyPictureActivity.this,camera.class);
				startActivity(intent);
			}
		});
    }
}