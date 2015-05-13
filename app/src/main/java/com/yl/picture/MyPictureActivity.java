package com.yl.picture;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
<<<<<<< HEAD
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TabHost;

public class MyPictureActivity extends TabActivity implements OnCheckedChangeListener{

	private TabHost mTabHost;
	private Intent mAIntent;
	private Intent mBIntent;
	private Intent mCIntent;
	private Intent mDIntent;
	private Intent mEIntent;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.maintabs);

		this.mAIntent = new Intent(this,PictureActivity.class);
		this.mBIntent = new Intent(this,videoPlayer.class);
		this.mCIntent = new Intent(this,camera.class);
		this.mDIntent = new Intent(this,videoRecording.class);
		this.mEIntent = new Intent(this,share.class);

		((RadioButton) findViewById(R.id.radio_button0))
				.setOnCheckedChangeListener(this);
		((RadioButton) findViewById(R.id.radio_button1))
				.setOnCheckedChangeListener(this);
		((RadioButton) findViewById(R.id.radio_button2))
				.setOnCheckedChangeListener(this);
		((RadioButton) findViewById(R.id.radio_button3))
				.setOnCheckedChangeListener(this);
		((RadioButton) findViewById(R.id.radio_button4))
				.setOnCheckedChangeListener(this);

		setupIntent();
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if(isChecked){
			switch (buttonView.getId()) {
				case R.id.radio_button0:
					this.mTabHost.setCurrentTabByTag("A_TAB");
					break;
				case R.id.radio_button1:
					this.mTabHost.setCurrentTabByTag("B_TAB");
					break;
				case R.id.radio_button2:
					this.mTabHost.setCurrentTabByTag("C_TAB");
					break;
				case R.id.radio_button3:
					this.mTabHost.setCurrentTabByTag("D_TAB");
					break;
				case R.id.radio_button4:
					this.mTabHost.setCurrentTabByTag("MORE_TAB");
					break;
=======
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
>>>>>>> parent of 4712248... V2.5
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