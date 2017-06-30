package techkids.com.android9_hackathon2_breakworkout.views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skyfishjy.library.RippleBackground;

import pl.droidsonroids.gif.GifImageView;
import techkids.com.android9_hackathon2_breakworkout.R;
import techkids.com.android9_hackathon2_breakworkout.databases.DatabaseHandle;
import techkids.com.android9_hackathon2_breakworkout.databases.PracticeModel;

public class PracticeScene extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    //    TextView tvCountDown;
    Button btStart;
    RippleBackground rbCounting;
    boolean isRuning = false;
    String TAG = PracticeScene.class.toString();
    TextView tvName;
    GifImageView givImage;
    TextView tvHow;
    CountDownTimer countDownTimer;
    ImageView ivCenterImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_scene);

//        tvCountDown = (TextView) findViewById(R.id.tv_count_down);
        btStart = (Button) findViewById(R.id.bt_start);
        ivCenterImage = (ImageView) findViewById(R.id.iv_center_image);
        tvName = (TextView) findViewById(R.id.tv_name);
        givImage = (GifImageView) findViewById(R.id.giv_image);
        tvHow = (TextView) findViewById(R.id.tv_description);
        rbCounting = (RippleBackground) findViewById(R.id.rb_counting);
        ivCenterImage.setOnTouchListener(this);

        setupUI(DatabaseHandle.getInstance(this).getPractice());

    }

    void setupUI(PracticeModel practiceModel) {
        if (practiceModel == null)
            return;
        tvName.setText(practiceModel.getName());

        int resId = PracticeScene.this.getResources().getIdentifier(practiceModel.getImage(), "drawable", PracticeScene.this.getPackageName());
        givImage.setImageResource(resId);

        tvHow.setText(practiceModel.getHow());
    }

    @Override
    public void onClick(View v) {
        if (v == btStart) {
            if (btStart.getText().equals("START")) {
//                tvCountDown.setVisibility(View.VISIBLE);
                btStart.setText("STOP");


                countDownTimer = new CountDownTimer(5000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        if (isRuning) return;
                        isRuning = true;
//                        tvCountDown.setText("seconds remaining: " + millisUntilFinished / 1000);
                        //here you can have your logic to set text to edittext
                        rbCounting.startRippleAnimation();
                    }

                    public void onFinish() {
                        isRuning = false;
                        rbCounting.stopRippleAnimation();
//                        tvCountDown.setText("done!");
                        startActivity(new Intent(PracticeScene.this, FinishScene.class));
                    }

                }.start();

            } else {
                isRuning = false;
                rbCounting.stopRippleAnimation();
//                tvCountDown.setVisibility(View.INVISIBLE);
                countDownTimer.cancel();
                btStart.setText("START");
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v == ivCenterImage) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (isRuning) {
                        ivCenterImage.setImageResource(R.drawable.stop_button_clicked);
                    } else {
                        ivCenterImage.setImageResource(R.drawable.start_button_clicked);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (isRuning) {
                        isRuning = false;
                        ivCenterImage.setImageResource(R.drawable.start_button);
                        rbCounting.stopRippleAnimation();
                        countDownTimer.cancel();
                    } else {
                        ivCenterImage.setImageResource(R.drawable.stop_button);
                        countDownTimer = new CountDownTimer(5000, 1000) {
                            public void onTick(long millisUntilFinished) {
                                if (isRuning) return;
                                isRuning = true;
                                rbCounting.startRippleAnimation();
                            }

                            public void onFinish() {
                                isRuning = false;
                                rbCounting.stopRippleAnimation();
                                startActivity(new Intent(PracticeScene.this, FinishScene.class));
                            }
                        }.start();
                    }
                    break;
            }
            return true;
        }

        return false;
    }
}
