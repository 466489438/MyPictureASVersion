package com.yl.picture;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class SuolueDisplayActivity extends Activity {
	private ImageView imageView;
	private int index;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.suoluedisplay);
		this.imageView=(ImageView) this.findViewById(R.id.display);
		Intent intent=getIntent();
		Bundle bundle=intent.getExtras();
		int postion=bundle.getInt("ID");
		Log.i("int", postion+"----------");
		Log.i("map", SuolueActivity.getImageMaps().size()+"--------");
		this.imageView.setImageBitmap(SuolueActivity.getImageMaps().get(postion));
		this.imageView.setClickable(true);
		index=postion;
		this.imageView.setOnTouchListener(new View.OnTouchListener() {
			int beginX;
			int endX;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					beginX=(int) event.getX();
				}else if(event.getAction()==MotionEvent.ACTION_UP){
					endX=(int) event.getX();
					if(endX-beginX>0){
						if(index!=SuolueActivity.getImageMaps().size()-1){
							index++;
							imageView.setImageBitmap(SuolueActivity.getImageMaps().get(index));
						}
					}else if(endX-beginX<0){
						if(index!=0){
							index--;
							imageView.setImageBitmap(SuolueActivity.getImageMaps().get(index));
						}
					}
				}
				return false;
			}
		});
	}
	
}
