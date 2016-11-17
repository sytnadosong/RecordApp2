package com.sds.study.recordapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.Manifest;

public class Record_main extends AppCompatActivity {
    String TAG;
    MediaRecorder recorder;
    ImageView switchz;
    static final int REQUEST_RECORD_PERMISSION=1;
    boolean isRun=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG=this.getClass().getName();
        setContentView(R.layout.activity_record_main);
        switchz=(ImageView)findViewById(R.id.switchz);
        init();
    }

    public void init(){
        recorder=new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
    }

    /*저장파일 구하기*/
    public String getSaveFile(){
        File dir = new File(Environment.getExternalStorageDirectory(), "iot_record");
        Date d=new Date(); //
        String currentTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);
        //long currentTime=System.currentTimeMillis();
        Log.d(TAG,"현재시간은"+currentTime);
        File saveFile = new File(dir,currentTime+".mp4");
        return saveFile.getAbsolutePath();
    }

    public void showList(){
        Intent intent=new Intent(this,FileListActivity.class);
        startActivity(intent);
    }


    public void startRecord(){

        if(isRun){//실행중이라면
            switchz.setImageResource(R.drawable.stop);
            try {
                init();
                recorder.setOutputFile(getSaveFile());
                recorder.prepare();
                recorder.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{//정지중이라면
            recorder.stop();
            recorder.reset();
            switchz.setImageResource(R.drawable.record);
            /*녹음 완료된 화면 보여주자*/
            showList();
        }

    }

    /*유저의 처리결과 받기*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {//permissions 0보다 크면 수락, int[] grantResults 는 무엇인가?수락 0 거부 -1
        Log.d(TAG, "requestCode :"+requestCode+",grantResults:"+grantResults[0]+",grantResults:"+grantResults[1]);
         switch(requestCode){
             case REQUEST_RECORD_PERMISSION:
                 if(permissions.length>0&& grantResults[0]==PackageManager.PERMISSION_DENIED) {
                     Toast.makeText(this, "앱사용을 위해서는 미디어 접근권한을 주셔야 합니다.", Toast.LENGTH_SHORT).show();
                 }else if(permissions.length>0&& grantResults[0]==PackageManager.PERMISSION_DENIED){
                    Toast.makeText(this, "앱사용을 위해서는 오디오 접근권한을 주셔야 합니다.", Toast.LENGTH_SHORT).show();
                 }
         }
    }

    /*각종 권한을 체크하자*/
    public void btnClick(View view){

        int writePermission=ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int recordPermission=ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO);

        if(writePermission== PackageManager.PERMISSION_DENIED||recordPermission==PackageManager.PERMISSION_DENIED){
            //요청
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.RECORD_AUDIO
                    },REQUEST_RECORD_PERMISSION);
        }else{
            startRecord();
            isRun=!isRun;
        }
    }
}
