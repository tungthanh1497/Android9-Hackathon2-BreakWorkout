package techkids.com.android9_hackathon2_breakworkout.views;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import techkids.com.android9_hackathon2_breakworkout.BroadcastService;
import techkids.com.android9_hackathon2_breakworkout.R;

public class AlarmScene extends AppCompatActivity implements View.OnClickListener {
    Button btStart;
    Button btStop;
    public static TextView tvTimer;
    ProgressBar progressBar;

    public static String TAG = AlarmScene.class.toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_scene);

        btStart = (Button) findViewById(R.id.bt_start);
        btStart.setOnClickListener(this);

        btStop = (Button) findViewById(R.id.bt_stop);
        btStop.setOnClickListener(this);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        tvTimer = (TextView) findViewById(R.id.tv_timer);

        startProgress(progressBar);

    }

    public void startProgress(View view) {

        progressBar.setProgress(0);
        new Thread(new Task()).start();
    }

    class Task implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i <= 10; i++) {
                final int value = i;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                progressBar.setProgress(value);

            }
        }

    }


    @Override
    public void onClick(View v) {
        if (v == btStart) {
            startService(new Intent(this, BroadcastService.class));
            Log.i(TAG, "Started service");

            btStop.setVisibility(View.VISIBLE);
            btStart.setVisibility(View.INVISIBLE);

        }
        else if(v == btStop) {
            btStop.setVisibility(View.INVISIBLE);
            btStart.setVisibility(View.VISIBLE);

            stopService(new Intent(this, BroadcastService.class));
            Log.i(TAG, "Stopped service");
        }

    }
    private BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateGUI(intent);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(br, new IntentFilter(BroadcastService.COUNTDOWN_BR));
        Log.i(TAG, "Registered broacast receiver");
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(br);
        Log.i(TAG, "Unregistered broacast receiver");
    }

    @Override
    public void onStop() {
        try {
            unregisterReceiver(br);
        } catch (Exception e) {
            // Receiver was probably already stopped in onPause()
        }
        super.onStop();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void updateGUI(Intent intent) {
        if (intent.getExtras() != null) {
            long millisUntilFinished = intent.getLongExtra("countdown", 0);
            Log.i(TAG, "Countdown seconds remaining: " +  millisUntilFinished / 1000);
//            tvTimer.setText((int) millisUntilFinished);
        }
    }
}
