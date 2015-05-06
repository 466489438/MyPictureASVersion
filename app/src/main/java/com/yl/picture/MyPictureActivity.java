package com.yl.picture;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MyPictureActivity extends Activity {
	private Button button1;
	private Button button2;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        this.button1=(Button) this.findViewById(R.id.button1);
        this.button2=(Button) this.findViewById(R.id.button2);
        //�����ť��������ģʽ
        this.button1.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent intent=new Intent(MyPictureActivity.this,SuolueActivity.class);
				startActivity(intent);
			}
		});
        //�����ť�������ģʽ
        this.button2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(MyPictureActivity.this,PingPuActivity.class);
				startActivity(intent);
			}
		});
    }
}