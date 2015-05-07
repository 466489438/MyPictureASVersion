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
    private GridView gridView;//九宫格视图
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
        //设置窗口格式为半透明
        getWindow().setFormat(PixelFormat.TRANSLUCENT);

        //this.gridView=(GridView) this.findViewById(R.id.gridView2);

        //视频存储的准确文件夹
        path = ReadSDPath() + "/DCIM/100MEDIA/eryw.mp4";
        Log.i("init", path + "========");



        /*//调用videoView播放，效果不好，不采用
        File video = new File(path);
        if (video.exists()) {
            videoView.setVideoPath(video.getAbsolutePath());
            //设置videoView与mController建立关联
            videoView.setMediaController(mController);
            //设置mController与videoView建立关联
            mController.setMediaPlayer(videoView);
            //让videoView获取焦点
            videoView.requestFocus();
        }*/
    }

    //获取SD卡的跟路径
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
            //调用外部播放器
            Intent videoIntent = new Intent(Intent.ACTION_VIEW);
            //设置播放视频的目标路径
            videoIntent.setDataAndType(Uri.fromFile(new File(path)), "video/*");
            //请求视频播放
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

        //首先检索SDcard上所有的video
        cursor = this.managedQuery(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, mediaColumns, null, null, null);

       // ArrayList<VideoInfo> videoList = new ArrayList<videoPlayer.VideoInfo>();

        if(cursor.moveToFirst()){
            do{
                VideoInfo info = new VideoInfo();

                info.filePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                info.mimeType = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE));
                info.title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));

                //获取当前Video对应的Id，然后根据该ID获取其Thumb
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
                String selection = MediaStore.Video.Thumbnails.VIDEO_ID +"=?";
                String[] selectionArgs = new String[]{
                        id+""
                };
                Cursor thumbCursor = this.managedQuery(MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI, thumbColumns, selection, selectionArgs, null);

                if(thumbCursor.moveToFirst()){
                    info.thumbPath = thumbCursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.DATA));

                }

                //然后将其加入到videoList
                videoList.add(info);

            }while(cursor.moveToNext());
        }

        //然后需要设置ListView的Adapter了，使用我们自定义的Adatper
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
 * 定义一个Adapter来显示缩略图和视频title信息
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

        //显示信息
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
