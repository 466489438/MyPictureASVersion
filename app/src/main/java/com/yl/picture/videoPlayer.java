package com.yl.picture;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Lemon on 2015/5/6 0006.
 */
public class videoPlayer extends ListActivity {
    private GridView gridView;//�Ź�����ͼ
    private String path;
    ArrayList<VideoInfo> videoList = new ArrayList<videoPlayer.VideoInfo>();
    private static HashMap<Integer,Bitmap> imageMaps=new HashMap<Integer,Bitmap>();
    public static HashMap<Integer, Bitmap> getImageMaps() {
       return imageMaps;
    }
    private Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.video);
        init();
        //���ô��ڸ�ʽΪ��͸��
        getWindow().setFormat(PixelFormat.TRANSLUCENT);

        //this.gridView=(GridView) this.findViewById(R.id.gridView2);

        //��Ƶ�洢��׼ȷ�ļ���
        path = ReadSDPath() + "/DCIM/100MEDIA/eryw.mp4";
        Log.i("init", path + "========");



        /*//����videoView���ţ�Ч�����ã�������
        File video = new File(path);
        if (video.exists()) {
            videoView.setVideoPath(video.getAbsolutePath());
            //����videoView��mController��������
            videoView.setMediaController(mController);
            //����mController��videoView��������
            mController.setMediaPlayer(videoView);
            //��videoView��ȡ����
            videoView.requestFocus();
        }*/
    }

    //��ȡSD���ĸ�·��
    public String ReadSDPath() {
        boolean SDExit = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (SDExit) {
            return Environment.getExternalStorageDirectory().toString();
        } else {
            return null;
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
//        video=list.get(position);
//        play(video);
        Log.v("TAG","click path"+videoList.get(position).filePath);
        path = videoList.get(position).filePath;
        try {
            //�����ⲿ������
            Intent videoIntent = new Intent(Intent.ACTION_VIEW);
            //���ò�����Ƶ��Ŀ��·��
            videoIntent.setDataAndType(Uri.fromFile(new File(path)), "video/*");
            //������Ƶ����
            startActivity(Intent.createChooser(videoIntent, "Play Video"));
            Log.i("Play Video", path + "========");
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void init(){
        String[] thumbColumns = new String[]{
                MediaStore.Video.Thumbnails.DATA,
                MediaStore.Video.Thumbnails.VIDEO_ID
        };

        String[] mediaColumns = new String[]{
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.MIME_TYPE
        };

        //���ȼ���SDcard�����е�video
        cursor = this.managedQuery(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, mediaColumns, null, null, null);

       // ArrayList<VideoInfo> videoList = new ArrayList<videoPlayer.VideoInfo>();

        if(cursor.moveToFirst()){
            do{
                VideoInfo info = new VideoInfo();

                info.filePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                info.mimeType = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE));
                info.title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));

                //��ȡ��ǰVideo��Ӧ��Id��Ȼ����ݸ�ID��ȡ��Thumb
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
                String selection = MediaStore.Video.Thumbnails.VIDEO_ID +"=?";
                String[] selectionArgs = new String[]{
                        id+""
                };
                Cursor thumbCursor = this.managedQuery(MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI, thumbColumns, selection, selectionArgs, null);

                if(thumbCursor.moveToFirst()){
                    info.thumbPath = thumbCursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.DATA));
                    Log.v("thumbPath!!!!",info.thumbPath);
                }

                //Ȼ������뵽videoList
                videoList.add(info);

            }while(cursor.moveToNext());
        }

        //Ȼ����Ҫ����ListView��Adapter�ˣ�ʹ�������Զ����Adatper
        VideoAdapter adapter = new VideoAdapter(this, videoList);
        this.getListView().setAdapter(adapter);
    }

static class VideoInfo{
    String filePath;
    String mimeType;
    String thumbPath;
    String title;
}

/**
 * ����һ��Adapter����ʾ����ͼ����Ƶtitle��Ϣ
 * @author Administrator
 *
 */
static class VideoAdapter extends BaseAdapter {

    private Context context;
    private List<VideoInfo> videoItems;

    public VideoAdapter(Context context, List<VideoInfo> data){
        this.context = context;
        this.videoItems = data;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return videoItems.size();
    }
    @Override
    public Object getItem(int p) {
        // TODO Auto-generated method stub
        return videoItems.get(p);
    }
    @Override
    public long getItemId(int p) {
        // TODO Auto-generated method stub
        return p;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.video_item, null);
            holder.thumbImage = (ImageView)convertView.findViewById(R.id.thumb_image);
            holder.titleText = (TextView)convertView.findViewById(R.id.video_title);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        //��ʾ��Ϣ
        holder.titleText.setText(videoItems.get(position).title);
        String path = videoItems.get(position).thumbPath;
        Log.v("TAG", "thumb:" + path);
        Bitmap bitmap = BitmapFactory.decodeFile(path);
//        if(videoItems.get(position).thumbPath != null){
//            holder.thumbImage.setImageURI(Uri.parse(videoItems.get(position).thumbPath));
//        }
        if(bitmap!=null){
            Log.v("TAG","bitmap");
            holder.thumbImage.setImageBitmap(bitmap);
        }

        return convertView;
    }

    class ViewHolder{
        ImageView thumbImage;
        TextView titleText;
    }

}
}
