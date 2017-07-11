package techkids.com.android9_hackathon2_breakworkout.views;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.concurrent.TimeUnit;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;
import techkids.com.android9_hackathon2_breakworkout.R;

public class AlarmScreen extends AppCompatActivity implements TextWatcher, View.OnTouchListener {
    private ProgressBar progressBarCircle;
    private EditText editTextMinute;
    private TextView textViewTime;
    private Button btStartStop;
    private CountDownTimer countDownTimer;
    MaterialSpinner materialSpinner;
    int numberTips = 0;
    private static final String[] ANDROID_VERSIONS = {"Comfortable move", "Restriction move"};
    boolean isComfortable = true;
    View vTouch;
    public static String TAG = AlarmScreen.class.toString();

    private long timeCountInMilliSeconds = 0;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        try {
            int numberInput = Integer.parseInt(s.toString());
// TODO:           textViewTime.setText(hmsTimeFormatter(numberInput * 60 * 1000));
            textViewTime.setText(hmsTimeFormatter(numberInput * 60 * 100));
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), getString(R.string.message_minutes), Toast.LENGTH_LONG).show();
        }
    }


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
        materialSpinner.setItems(ANDROID_VERSIONS);

        if(!FinishScreen.isFirttime1){
            FinishScreen.isFirttime1=true;
            new SimpleTooltip.Builder(this)
                    .anchorView(materialSpinner)
                    .text("Select your kind of work environment here")
                    .gravity(Gravity.BOTTOM)
                    .animated(true)
                    .transparentOverlay(false)
                    .build()
                    .show();
        }else{
            numberTips=3;
        }

    }

    private void initViews() {
        vTouch = findViewById(R.id.v_touch);
        progressBarCircle = (ProgressBar) findViewById(R.id.progressBarCircle);
        editTextMinute = (EditText) findViewById(R.id.editTextMinute);
        textViewTime = (TextView) findViewById(R.id.textViewTime);
        btStartStop = (Button) findViewById(R.id.imageViewStartStop);
        materialSpinner = (MaterialSpinner) findViewById(R.id.ms_eviroment);
    }

    private void initListeners() {
//        btStartStop.setOnClickListener(this);
        vTouch.setOnTouchListener(this);
        btStartStop.setOnTouchListener(this);
        editTextMinute.addTextChangedListener(this);
        materialSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                if (position == 1) {
                    isComfortable = false;
                } else isComfortable = true;
            }
        });
    }


    private void startStop() {
        if (timerStatus == TimerStatus.STOPPED) {

            // call to initialize the timer values
            setTimerValues();
            if (timeCountInMilliSeconds == 0) {
                Toast.makeText(getApplicationContext(), "The input timer must be greater than Zero.", Toast.LENGTH_LONG).show();
                btStartStop.setBackgroundResource(R.drawable.rounded_button_green);
                return;
            }
            // call to start the count down timer
            startCountDownTimer();
            // call to initialize the progress bar values
            setProgressBarValues();

            btStartStop.setText("STOP");
            btStartStop.setBackgroundResource(R.drawable.rounded_button_red);
            btStartStop.setTextColor(Color.parseColor("#E783A5"));
            // making edit text not editable
            editTextMinute.setVisibility(View.INVISIBLE);
            // changing the timer status to started
            timerStatus = TimerStatus.STARTED;

        } else {
            btStartStop.setText("START");
            btStartStop.setTextColor(Color.parseColor("#3CBC5F"));
            btStartStop.setBackgroundResource(R.drawable.rounded_button_green);
            editTextMinute.setVisibility(View.VISIBLE);
            textViewTime.setText(hmsTimeFormatter(0));
            editTextMinute.setText("");
            progressBarCircle.setProgress(100);
            timeCountInMilliSeconds = 0;
            timerStatus = TimerStatus.STOPPED;
            stopCountDownTimer();

        }

    }

    private void setTimerValues() {
        int time = 0;
        if (!editTextMinute.getText().toString().isEmpty()) {
            // fetching value from edit text and type cast to integer
            time = Integer.parseInt(editTextMinute.getText().toString().trim());
//            if (time <= 0) {
//            } else {
            // assigning values after converting to milliseconds
            //TODO: timeCountInMilliSeconds = time * 60 * 1000;
            timeCountInMilliSeconds = time * 60 * 100;
//            }
        }
//        else {
//            // toast message to fill edit text
//            Toast.makeText(getApplicationContext(), getString(R.string.message_minutes), Toast.LENGTH_LONG).show();
//        }
    }


    private void startCountDownTimer() {

        countDownTimer = new CountDownTimer(timeCountInMilliSeconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                textViewTime.setText(hmsTimeFormatter(millisUntilFinished));

                int percent = (int) ((int) (millisUntilFinished * (timeCountInMilliSeconds / 1000)) / timeCountInMilliSeconds);
                progressBarCircle.setProgress(percent);

            }

            @Override
            public void onFinish() {
                progressBarCircle.setProgress(0);
//              TODO:  Intent intent = new Intent(AlarmScreen.this, PracticeScreen.class);
                Intent intent = new Intent(AlarmScreen.this, ListPracticeScreen.class);
                intent.putExtra("isComfortable", isComfortable);
                startActivity(intent);
                editTextMinute.setVisibility(View.INVISIBLE);
//                textViewTime.setText(hmsTimeFormatter(timeCountInMilliSeconds));
//                setProgressBarValues();
//                btStartStop.setText("START");
//                btStartStop.setBackgroundResource(R.drawable.rounded_button_green);
//                editTextMinute.setEnabled(true);
                timerStatus = TimerStatus.STOPPED;
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

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to go to Home Screen", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 1000);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (numberTips < 2 && event.getAction() == MotionEvent.ACTION_UP) {
            numberTips++;
            switch (numberTips) {
                case 1:
                    new SimpleTooltip.Builder(this)
                            .anchorView(editTextMinute)
                            .text("Enter number of minutes you want to have a breaktime here. (Suggest: 25mins)")
                            .gravity(Gravity.TOP)
                            .animated(true)
                            .transparentOverlay(false)
                            .build()
                            .show();
                    break;
                case 2:
                    new SimpleTooltip.Builder(this)
                            .anchorView(btStartStop)
                            .text("Click here and start your own work.")
                            .gravity(Gravity.TOP)
                            .animated(true)
                            .transparentOverlay(false)
                            .build()
                            .show();
                    break;
            }
            return false;
        }
        if (v == btStartStop) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_UP:
                    startStop();
                    break;
                case MotionEvent.ACTION_DOWN:
                    if (timerStatus == TimerStatus.STOPPED) {
                        btStartStop.setBackgroundResource(R.drawable.rounded_button_red_copy);
                    } else {
                        btStartStop.setBackgroundResource(R.drawable.rounded_button_green_copy);
                    }
                    break;
            }
            return true;
        }
        return false;
    }
}
