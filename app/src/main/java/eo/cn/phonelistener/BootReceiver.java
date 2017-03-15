package eo.cn.phonelistener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**这个是广播接收者！让程序开机自动启动
 * Created by mk on 2017/3/14.
 */
public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context,PhoneListenerService.class);
        context.startService(intent1);

    }
}
