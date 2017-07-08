package techkids.com.android9_hackathon2_breakworkout.views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableWeightLayout;
import com.github.lzyzsd.circleprogress.CircleProgress;
import com.skyfishjy.library.RippleBackground;

import net.cachapa.expandablelayout.ExpandableLayout;

import pl.droidsonroids.gif.GifImageView;
import techkids.com.android9_hackathon2_breakworkout.R;
import techkids.com.android9_hackathon2_breakworkout.databases.DatabaseHandle;
import techkids.com.android9_hackathon2_breakworkout.databases.PracticeModel;

public class PracticeScene extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener {

    //    TextView tvCountDown;
//    RippleBackground rbCounting;
    boolean isRuning = false;
    String TAG = PracticeScene.class.toString();
    TextView tvName;
    ProgressBar progressBar;
    GifImageView givImage;
    TextView tvDescription;
    Button btHow;
    CountDownTimer countDownTimer;
    ImageView ivStartButton;
    //    ImageView ivCenterImage;
//    CircleProgress cpCountDown;
    ExpandableLayout expandableLayout;
    ImageView ivDropDown;
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
//        ivCenterImage.setOnTouchListener(this);
        ivStartButton.setOnTouchListener(this);
        btHow.setOnClickListener(this);
    }

    void addVarById() {
//        ivCenterImage = (ImageView) findViewById(R.id.iv_center_image);
        ivStartButton = (ImageView) findViewById(R.id.iv_play);
        tvName = (TextView) findViewById(R.id.tv_name);
        givImage = (GifImageView) findViewById(R.id.giv_image);
        tvDescription = (TextView) findViewById(R.id.tv_description);
        progressBar = (ProgressBar) findViewById(R.id.progress);

        progressBar.getProgressDrawable().setColorFilter(
                Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);

//        rbCounting = (RippleBackground) findViewById(R.id.rb_counting);
//        cpCountDown = (CircleProgress) findViewById(R.id.cp_count_down);
        btHow = (Button) findViewById(R.id.bt_how);
        expandableLayout = (ExpandableLayout) findViewById(R.id.expandable_layout);
        ivDropDown = (ImageView) findViewById(R.id.iv_drop_down);
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
        if (v == ivStartButton) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (isRuning) {
                        ivStartButton.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp_copy);
                    } else {
                        ivStartButton.setImageResource(R.drawable.ic_play_circle_filled_black_24dp_copy);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (isRuning) {
                        isRuning = false;
                        progressBar.setProgress(0);
                        ivStartButton.setImageResource(R.drawable.ic_play_circle_filled_black_24dp);
//                        rbCounting.stopRippleAnimation();

                        countDownTimer.cancel();
                    } else {
                        ivStartButton.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);
                        countDownTimer = new CountDownTimer(timeEnd, timeBreak) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                if (!isRuning) {
                                    isRuning = true;
//                                    rbCounting.startRippleAnimation();
                                }
                                int percent = 100 - (int) ((int) (millisUntilFinished * (timeEnd / timeBreak)) / timeEnd);
                                progressBar.setProgress(percent);
                                Log.d(TAG, "onTick: millisUntilFinished: " + millisUntilFinished + " - percent: " + percent);
                            }

                            @Override
                            public void onFinish() {
                                progressBar.setProgress(100);
                                isRuning = false;
//                                rbCounting.stopRippleAnimation();
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

    boolean showUp = true;

    @Override
    public void onClick(View v) {
        if (v == btHow) {
            if (showUp) {
                expandableLayout.setExpanded(false);
//                expandableLayout.collapse();
                ivDropDown.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
                showUp = false;
            } else {
                expandableLayout.setExpanded(true);
                ivDropDown.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
                showUp = true;
            }
        }
    }
}
