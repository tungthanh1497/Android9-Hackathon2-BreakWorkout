package techkids.com.android9_hackathon2_breakworkout.views;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import techkids.com.android9_hackathon2_breakworkout.R;

public class AlarmScene extends AppCompatActivity implements View.OnClickListener {
    private ProgressBar progressBarCircle;
    private EditText editTextMinute;
    private TextView textViewTime;
    private Button btStartStop;
    private CountDownTimer countDownTimer;

    public static String TAG = AlarmScene.class.toString();

    private long timeCountInMilliSeconds = 1 * 60000;

    private enum TimerStatus {
        STARTED,
        STOPPED
    }

    private TimerStatus timerStatus = TimerStatus.STOPPED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_scene);

        initViews();
        initListeners();

    }

    private void initViews() {
        progressBarCircle = (ProgressBar) findViewById(R.id.progressBarCircle);
        editTextMinute = (EditText) findViewById(R.id.editTextMinute);
        textViewTime = (TextView) findViewById(R.id.textViewTime);
        btStartStop = (Button) findViewById(R.id.imageViewStartStop);
    }

   private void initListeners() {
        btStartStop.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewStartStop:
                startStop();
                break;
        }
    }

    private void startStop() {
        if (timerStatus == TimerStatus.STOPPED) {

            // call to initialize the timer values
            setTimerValues();
            // call to initialize the progress bar values
            setProgressBarValues();

            btStartStop.setText("STOP");
            btStartStop.setBackgroundResource(R.drawable.rounded_button_red);
            // making edit text not editable
            editTextMinute.setEnabled(false);
            // changing the timer status to started
            timerStatus = TimerStatus.STARTED;
            // call to start the count down timer
            startCountDownTimer();

        } else {
            btStartStop.setText("START");
            btStartStop.setBackgroundResource(R.drawable.rounded_button_green);
            editTextMinute.setEnabled(true);
            timerStatus = TimerStatus.STOPPED;
            stopCountDownTimer();

        }

    }

    private void setTimerValues() {
        int time = 0;
        if (!editTextMinute.getText().toString().isEmpty()) {
            time = Integer.parseInt(editTextMinute.getText().toString().trim());
        }
          else {
//            Toast.makeText(getApplicationContext(), getString(R.string.message_minutes), Toast.LENGTH_LONG).show();
        }
        // TODO: timeCountInMilliSeconds = time * 60 * 1000;
        timeCountInMilliSeconds = time * 60 * 100;
    }


    private void startCountDownTimer() {

        countDownTimer = new CountDownTimer(timeCountInMilliSeconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                textViewTime.setText(hmsTimeFormatter(millisUntilFinished));

                progressBarCircle.setProgress((int) (millisUntilFinished / 1000));

            }

            @Override
            public void onFinish() {
                startActivity(new Intent(AlarmScene.this, PracticeScene.class));
//                textViewTime.setText(hmsTimeFormatter(timeCountInMilliSeconds));
//                setProgressBarValues();
//                btStartStop.setText("START");
//                btStartStop.setBackgroundResource(R.drawable.rounded_button_green);
//                editTextMinute.setEnabled(true);
//                timerStatus = TimerStatus.STOPPED;
            }

        }.start();
//        countDownTimer.start();
    }


    private void stopCountDownTimer() {
        countDownTimer.cancel();
    }


    private void setProgressBarValues() {

        progressBarCircle.setMax((int) timeCountInMilliSeconds / 1000);
        progressBarCircle.setProgress((int) timeCountInMilliSeconds / 1000);
    }


    private String hmsTimeFormatter(long milliSeconds) {

        String hms = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(milliSeconds),
                TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliSeconds)),
                TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)));

        return hms;

    }

}
