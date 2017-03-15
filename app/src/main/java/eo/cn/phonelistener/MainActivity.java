package eo.cn.phonelistener;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import cn.bmob.v3.Bmob;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //第一：默认初始化
        Bmob.initialize(this, "bdb140bc77b9e907912d052f2ea075c6");
    }
    public void start(View view){
        Log.i("开始监听","监听开始");
        Intent intent = new Intent(MainActivity.this,PhoneListenerService.class);
        startService(intent);

    }
    public void stop(View view){
        Log.i("结束监听","监听结束");
        Toast.makeText(this,"结束监听",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this,PhoneListenerService.class);
        stopService(intent);
    }

}
