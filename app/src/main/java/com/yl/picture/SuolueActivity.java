package com.yl.picture;

import java.io.File;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class SuolueActivity extends Activity {
	private GridView gridView;
	private String path;
	private String sdPath;
//	private int imagesID[]=new int[]{R.drawable.a1,R.drawable.a2,R.drawable.a3,
//									R.drawable.a4,R.drawable.a5,R.drawable.a6,
//									R.drawable.a7,R.drawable.a8};
	private static HashMap<Integer,Bitmap> imageMaps=new HashMap<Integer,Bitmap>();
	
	public static HashMap<Integer, Bitmap> getImageMaps() {
		return imageMaps;
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.suolue);
		this.gridView=(GridView) this.findViewById(R.id.gridView1);
//		for(int i=0;i<imagesID.length;i++){
//			Bitmap bm=BitmapFactory.decodeResource(getResources(), imagesID[i]);
//			imageMaps.put(i, bm);
//		}
		//ͼƬ�洢��׼ȷ�ļ���
		path=ReadSDPath()+"/DCIM/Camera/";
		Log.i("init", path+"========");
//		Matrix matrix = new Matrix();
//		matrix.postScale(10, 10);
		//����ͼƬ�Ĵ�С
		BitmapFactory.Options opt=new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		//������С4
		opt.inSampleSize=4;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		//����·���ҵ����е��ļ���·���浽Bitmap������
		File file=new File(path);
		File[] files=file.listFiles();
		for(int i=0;i<files.length;i++){
			File f=files[i];
			Log.i("path", f.getPath()+"----------");
			Bitmap bm=BitmapFactory.decodeFile(f.getPath(),opt);
//			Bitmap fBitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
//                    bm.getHeight(), matrix, true);
			imageMaps.put(i, bm);
		}
		//���Զ�������������ص�gridView��
		this.gridView.setAdapter(new MyAdapter(SuolueActivity.this));
		//�������ͼƬ��ʾ
		this.gridView.setOnItemClickListener(new GridView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int postion,
					long arg3) {
				Intent intent=new Intent();
				intent.setClass(SuolueActivity.this, SuolueDisplayActivity.class);
				Bundle bundle=new Bundle();
				bundle.putInt("ID", postion);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}
	//��ȡSD���ĸ�·��
	public String ReadSDPath(){
		boolean SDExit=Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
		if(SDExit){
			return sdPath=Environment.getExternalStorageDirectory().toString();
		}else{
			return null;
		}
	}
	//�Զ���������ʵ��BaseAdapter
public class MyAdapter extends BaseAdapter{
	private Context context;
	//���幹�췽������Context����
	public MyAdapter(Context context){
		this.context=context;
	}
	@Override
	//����ֵΪ����ͼƬ�ĸ���
	public int getCount() {
		
		return imageMaps.size();
	}

	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//����һ��ImageView��ʾͼƬ
		ImageView imageView;
		if(convertView==null){
			imageView=new ImageView(context);
			imageView.setLayoutParams(new GridView.LayoutParams(90,90));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(8, 8, 8, 8);
		}else{
			imageView=(ImageView)convertView;
		}
		imageView.setImageBitmap(imageMaps.get(position));
		return imageView;
		}
	}
}
