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

import com.github.aakira.expandablelayout.ExpandableWeightLayout;
import com.github.lzyzsd.circleprogress.CircleProgress;
import com.skyfishjy.library.RippleBackground;

import pl.droidsonroids.gif.GifImageView;
import techkids.com.android9_hackathon2_breakworkout.R;
import techkids.com.android9_hackathon2_breakworkout.databases.DatabaseHandle;
import techkids.com.android9_hackathon2_breakworkout.databases.PracticeModel;

public class PracticeScene extends AppCompatActivity implements View.OnTouchListener {

    //    TextView tvCountDown;
    RippleBackground rbCounting;
    boolean isRuning = false;
    String TAG = PracticeScene.class.toString();
    TextView tvName;
    GifImageView givImage;
    TextView tvDescription;
    Button btHow;
    CountDownTimer countDownTimer;
    ImageView ivCenterImage;
    CircleProgress cpCountDown;
    //    ExpandableWeightLayout expandableLayout;
    long timeEnd = 10000;
    long timeBreak = 100;
    boolean isOpen = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_scene);

        addVarById();
        setupUI(DatabaseHandle.getInstance(this).getPractice());
//        tvCountDown = (TextView) findViewById(R.id.tv_count_down);
        ivCenterImage.setOnTouchListener(this);
//        btHow.setOnClickListener(this);
    }

    void addVarById() {
        ivCenterImage = (ImageView) findViewById(R.id.iv_center_image);
        tvName = (TextView) findViewById(R.id.tv_name);
        givImage = (GifImageView) findViewById(R.id.giv_image);
        tvDescription = (TextView) findViewById(R.id.tv_description);
        rbCounting = (RippleBackground) findViewById(R.id.rb_counting);
        cpCountDown = (CircleProgress) findViewById(R.id.cp_count_down);
        btHow = (Button) findViewById(R.id.bt_how);
//        expandableLayout = (ExpandableWeightLayout) findViewById(R.id.expandableLayout);
    }

    void setupUI(PracticeModel practiceModel) {
        if (practiceModel == null)
            return;
        tvName.setText(practiceModel.getName());
        int resId = PracticeScene.this.getResources().getIdentifier(practiceModel.getImage(), "drawable", PracticeScene.this.getPackageName());
        givImage.setImageResource(resId);
        tvDescription.setText(practiceModel.getHow());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v == ivCenterImage) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (isRuning) {
                        ivCenterImage.setImageResource(R.drawable.untitled5_clicked);
                    } else {
                        ivCenterImage.setImageResource(R.drawable.untitled4_clicked);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (isRuning) {
                        isRuning = false;
                        cpCountDown.setProgress(100);
                        ivCenterImage.setImageResource(R.drawable.untitled42);
                        rbCounting.stopRippleAnimation();
                        countDownTimer.cancel();
                    } else {
                        ivCenterImage.setImageResource(R.drawable.untitled52);
                        countDownTimer = new CountDownTimer(timeEnd, timeBreak) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                if (!isRuning) {
                                    isRuning = true;
                                    rbCounting.startRippleAnimation();
                                }
                                int percent = (int) ((int) (millisUntilFinished * (timeEnd / timeBreak)) / timeEnd);
                                cpCountDown.setProgress(percent);
                                Log.d(TAG, "onTick: millisUntilFinished: " + millisUntilFinished + " - percent: " + percent);
                            }

                            @Override
                            public void onFinish() {
                                cpCountDown.setProgress(0);
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


//    @Override
//    public void onClick(View v) {
//        if (v == btHow) {
////            if (isOpen) {
////                Log.d(TAG, "onClick: open -> close");
////                isOpen = false;
////                expandableLayout.collapse();
////            } else {
////                Log.d(TAG, "onClick: close -> open");
////                isOpen = true;
////                expandableLayout.expand();
////
////            }
//            expandableLayout.toggle();
//        }
//    }
}
