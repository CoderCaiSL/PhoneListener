package eo.cn.phonelistener;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by mk on 2017/3/14.
 */
public class PhoneListenerService2 extends Service {
    //电话管理
    private TelephonyManager tm;
    private myListener listener;
    //录音机
    private MediaRecorder mediaRecorder;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        tm = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
        listener = new myListener();
        tm.listen(listener,PhoneStateListener.LISTEN_CALL_STATE);//第一个参数是监听器！第二个是监听呼叫的状态
        super.onCreate();
    }
    private class myListener extends PhoneStateListener{
        //监听手机状态的变化
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            Log.i("电话状态输出",state+"");
            try {
                switch (state){
                    case TelephonyManager.CALL_STATE_IDLE://空闲状态
                        Log.i("电话状态输出","空闲状态");
                        //8.停止录音
                        if (mediaRecorder!=null){
                            mediaRecorder.stop();
                            //9.释放资源
                            mediaRecorder.release();
                            mediaRecorder = null;
                            Log.i("电话状态输出","停止录音");
                            //上传到服务器
                        }

                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK://通话状态
                        Log.i("电话状态输出","通话状态");
                        //1.实例化一个录音机
                        mediaRecorder = new MediaRecorder();
                        //2.指定录音机的声音源
                        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                        Log.i("电话状态输出","指定录音机的声音源");
                        //3.指定录制文件的输出格式
                        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                        Log.i("电话状态输出","指定录音文件的文件名称");
                        //5.设置音频编码
                        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                        //4.指定录音文件的文件名称Environment.getDataDirectory(),
                        File fileName = new File(Environment.getExternalStorageDirectory(),System.currentTimeMillis()+".3gp");
                        mediaRecorder.setOutputFile(fileName.getAbsolutePath());
                        Log.i("电话状态输出", Environment.getExternalStorageState()+""+fileName.getAbsolutePath());
                        Log.i("电话状态输出","设置音频编码");
                        //6.准备开始录音
                        mediaRecorder.prepare();
                        Log.i("电话状态输出","准备开始录音");
                        //7. Recording is now started
                        mediaRecorder.start();
                        Log.i("电话状态输出","开始录音");
                        break;
                    case TelephonyManager.CALL_STATE_RINGING://响铃状态
                        Log.i("电话状态输出","响铃状态");
                        break;
                }
            } catch (IOException e) {
                Log.i("电话状态输出",e+"");
                e.printStackTrace();

            }
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        tm.listen(listener,PhoneStateListener.LISTEN_NONE);//注销监听事件
        Log.i("电话状态输出","注销");
        Intent intent = new Intent(this,PhoneListenerService.class);
        startService(intent);
    }
}

