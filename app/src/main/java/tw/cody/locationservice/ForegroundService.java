package tw.cody.locationservice;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class ForegroundService extends Service {

    IBinder binder;
    String Channel_ID = "ServicesInAndroid";
    String Channel_Name = "ForeGroundService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    private void notificationChannel(){

        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel notificationChannel = new NotificationChannel(Channel_ID, Channel_Name,    //NotificationChannel要用version管理
                    NotificationManager.IMPORTANCE_HIGH);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0, new Intent[]{intent}, 0);
        Notification notification = new NotificationCompat.Builder(this, Channel_ID)
                .setContentTitle("Test Foreground Service")
                .setContentText("Running Foreground Service")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        notificationChannel();
        if (intent.getAction()!=null && intent.getAction().equals("Stop Service")){
            stopForeground(true);
            stopSelf();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

