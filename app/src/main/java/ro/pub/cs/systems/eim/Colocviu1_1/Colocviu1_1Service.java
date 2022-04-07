package ro.pub.cs.systems.eim.Colocviu1_1;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.sql.Timestamp;
import java.util.Random;

public class Colocviu1_1Service extends Service {
    private static class ProcessingThread extends Thread {
        Context context;
        String list;

        ProcessingThread(Context context, String list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(Constants.DELAY_TIME_MS);
                Intent intent = new Intent();
                StringBuilder builder = new StringBuilder();
                builder.append('[');
                builder.append(new Timestamp(System.currentTimeMillis()));
                builder.append(']').append(' ');
                builder.append(list);
                builder.append('\n');
                intent.putExtra(Constants.DATA, builder.toString());
                intent.setAction(Constants.ACTION);
                context.sendBroadcast(intent);
                while (true)  { Thread.sleep(Long.MAX_VALUE); }  // forever
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Colocviu1_1Service() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new ProcessingThread(this, intent.getExtras().getString(Constants.PRESS_LIST)).start();
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("SERVICE", "is destroied!");
    }
}