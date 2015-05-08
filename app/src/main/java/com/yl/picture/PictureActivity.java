package com.yl.picture;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;


import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.MemoryCacheAware;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.roamer.ui.view.SquareCenterImageView;

public class PictureActivity extends Activity {
	public static DisplayImageOptions mNormalImageOptions;
	public static final String SDCARD_PATH = Environment.getExternalStorageDirectory().toString();
	public static final String IMAGES_FOLDER = SDCARD_PATH + "/DCIM/.thumbnails/";
	private GridView mGridView;
	private GridView gridView;
	private String path;
	private String sdPath;

	private static HashMap<Integer,Bitmap> imageMaps=new HashMap<Integer,Bitmap>();
	
	public static HashMap<Integer, Bitmap> getImageMaps() {
		return imageMaps;
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.suolue);
		initImageLoader(this);
		path =ReadSDPath()+"/DCIM/100MEDIA/";
		Log.v("TAG","path:"+path);
		mGridView = (GridView) findViewById(R.id.multi_photo_grid);
		//创建一个图片URL的LIST
		List<String> datas=new ArrayList<String>();

		File file=new File(path);
		File[] files=file.listFiles();
		//Log.v("TAG","list files over");
		for(int i=0;i<files.length;i++){

			File f=files[i];
			Bitmap drawable = BitmapFactory.decodeFile(f.getPath());
			if(drawable == null){
				Log.v("Picutre format error",f.getPath());
				//Toast.makeText(this, "图片格式错误！", 0).show();
				continue;
			}
			Log.v("Picutre loaded", f.getPath());
			datas.add("file://"+f.getPath());

		}

		//用URL的list调用自己写的adapter
		mGridView.setAdapter(new ImagesInnerGridViewAdapter(datas));
	}

	private void initImageLoader(Context context) {
		int memoryCacheSize = (int) (Runtime.getRuntime().maxMemory() / 5);
		MemoryCacheAware<String, Bitmap> memoryCache;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			memoryCache = new LruMemoryCache(memoryCacheSize);
		} else {
			memoryCache = new LRULimitedMemoryCache(memoryCacheSize);
		}

		mNormalImageOptions = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565).cacheInMemory(true).cacheOnDisc(true)
				.resetViewBeforeLoading(true).build();

		// This
		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).defaultDisplayImageOptions(mNormalImageOptions)
				.denyCacheImageMultipleSizesInMemory().discCache(new UnlimitedDiscCache(new File(IMAGES_FOLDER)))
						// .discCacheFileNameGenerator(new Md5FileNameGenerator())
				.memoryCache(memoryCache)
						// .memoryCacheSize(memoryCacheSize)
				.tasksProcessingOrder(QueueProcessingType.LIFO).threadPriority(Thread.NORM_PRIORITY - 2).threadPoolSize(3).build();

		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}

	private class ImagesInnerGridViewAdapter extends BaseAdapter {

		private List<String> datas;

		public ImagesInnerGridViewAdapter(List<String> datas) {
			this.datas = datas;
		}

		@Override
		public int getCount() {
			return datas.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			final SquareCenterImageView imageView = new SquareCenterImageView(PictureActivity.this);
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			ImageLoader.getInstance().displayImage(datas.get(position), imageView);
			imageView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(PictureActivity.this, SpaceImageDetailActivity.class);
					intent.putExtra("images", (ArrayList<String>) datas);
					intent.putExtra("position", position);
					int[] location = new int[2];
					imageView.getLocationOnScreen(location);
					intent.putExtra("locationX", location[0]);
					intent.putExtra("locationY", location[1]);

					intent.putExtra("width", imageView.getWidth());
					intent.putExtra("height", imageView.getHeight());
					startActivity(intent);
					overridePendingTransition(0, 0);
				}
			});
			return imageView;
		}

	}
	//获取SD卡的跟路径
	public String ReadSDPath(){
		boolean SDExit=Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
		if(SDExit){
			return sdPath=Environment.getExternalStorageDirectory().toString();
		}else{
			return null;
		}
	}
}
