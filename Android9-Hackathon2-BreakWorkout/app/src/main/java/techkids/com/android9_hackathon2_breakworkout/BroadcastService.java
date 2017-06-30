package techkids.com.android9_hackathon2_breakworkout;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;

import techkids.com.android9_hackathon2_breakworkout.views.AlarmScene;

public class BroadcastService extends Service {

    private final static String TAG = "BroadcastService";

    public static final String COUNTDOWN_BR = "techkids.com.android9_hackathon2_breakworkout.countdown_br";
    Intent bi = new Intent(COUNTDOWN_BR);

    CountDownTimer cdt = null;

    TextView tvTimer;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(TAG, "Starting timer...");

        cdt = new CountDownTimer(40000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                Log.i(TAG, "Countdown seconds remaining: " + millisUntilFinished / 1000);
//                AlarmScene.tvTimer.setText((int) millisUntilFinished); // <---- LỖI CHỖ NÀY NÀY!!!
                bi.putExtra("countdown", millisUntilFinished);
                sendBroadcast(bi);
            }

            @Override
            public void onFinish() {
                Log.i(TAG, "Timer finished");
            }
        };

        cdt.start();
    }

    @Override
    public void onDestroy() {

        cdt.cancel();
        Log.i(TAG, "Timer cancelled");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}