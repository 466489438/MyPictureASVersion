package com.yl.picture;
import java.io.File;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;


public class PingPuActivity extends Activity {
	private Gallery gallery;
	private String SDpath;
	private String path;
	private ImageView xianshi;
	private int index;
	private HashMap<Integer,Bitmap> imageMaps=new HashMap<Integer,Bitmap>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.pingpu);
		this.gallery=(Gallery) this.findViewById(R.id.gallery1);
		this.xianshi=(ImageView) this.findViewById(R.id.pingpuview);
		this.xianshi.setClickable(true);
		path=ReadSDPath()+"/DCIM/Camera/";
		BitmapFactory.Options opt=new BitmapFactory.Options();
		opt.inPreferredConfig=Config.RGB_565;
		opt.inSampleSize=4;
		opt.inPurgeable=true;
		opt.inInputShareable=true;
		File file=new File(path);
		File[] files=file.listFiles();
		for(int i=0;i<files.length;i++){
			File f=files[i];
			Bitmap bm=BitmapFactory.decodeFile(f.getPath(), opt);
			imageMaps.put(i, bm);
		}
		this.gallery.setAdapter(new MyAdapter(PingPuActivity.this));
		this.gallery.setOnItemClickListener(new Gallery.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int postion,
					long arg3) {
				index=postion;
				xianshi.setVisibility(View.VISIBLE);
				xianshi.setImageBitmap(imageMaps.get(postion));
				xianshi.setOnTouchListener(new View.OnTouchListener() {
					private int beginX;
					private int endX;
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						if(event.getAction()==MotionEvent.ACTION_DOWN){
							beginX=(int) event.getX();
						}else if(event.getAction()==MotionEvent.ACTION_UP){
							endX=(int) event.getX();
							Log.i("x", beginX+"----------"+endX);
							if(endX-beginX>0){
								if(index!=imageMaps.size()-1){
									index++;
									xianshi.setImageBitmap(imageMaps.get(index));
								}
							}else if(endX-beginX<0){
								if(index!=0){
									index--;
									xianshi.setImageBitmap(imageMaps.get(index));
								}
							}
						}
						return false;
					}
				});
			}
			
		});
	}
	public String ReadSDPath(){
		boolean SDExit=Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
		if(SDExit){
			return SDpath=Environment.getExternalStorageDirectory().toString();
		}else{
			return SDpath=null;
		}
	}
	public class MyAdapter extends BaseAdapter{
		private Context context;
		public MyAdapter(Context context){
			this.context=context;
		}
		@Override
		public int getCount() {
			return imageMaps.size();
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView;
			if(convertView==null){
				imageView=new ImageView(context);
				imageView.setLayoutParams(new Gallery.LayoutParams(90,90));
				imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
				imageView.setPadding(8, 8, 8, 8);
			}else{
				imageView=(ImageView)convertView;
			}
			imageView.setImageBitmap(imageMaps.get(position));
			return imageView;
		}
		
	}
	
	
}
