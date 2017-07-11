package techkids.com.android9_hackathon2_breakworkout.views;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import net.cachapa.expandablelayout.ExpandableLayout;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;
import pl.droidsonroids.gif.GifImageView;
import techkids.com.android9_hackathon2_breakworkout.R;
import techkids.com.android9_hackathon2_breakworkout.databases.DatabaseHandle;
import techkids.com.android9_hackathon2_breakworkout.databases.PracticeModel;
import techkids.com.android9_hackathon2_breakworkout.libraries.SmoothCheckBox;

public class PracticeScreen extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener {

    //    TextView tvCountDown;
//    RippleBackground rbCounting;
    boolean isRuning = false;
    String TAG = PracticeScreen.class.toString();
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
    boolean isComfortable;
    boolean isDone = false;

    int numberTips=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_scene);

//        isComfortable = getIntent().getBooleanExtra("isComfortable", true);

        PracticeModel practiceModel = (PracticeModel) getIntent().getExtras().getSerializable("showPractice");


        addVarById();
//        setupUI(DatabaseHandle.getInstance(this).getPractice(isComfortable));
        setupUI(practiceModel);
        ivStartButton.setOnTouchListener(this);
        btHow.setOnClickListener(this);

        if (!FinishScreen.isFirttime3) {
            FinishScreen.isFirttime3 = true;
            new SimpleTooltip.Builder(this)
                    .anchorView(ivStartButton)
                    .text("Click here when you've already.")
                    .gravity(Gravity.BOTTOM)
                    .animated(true)
                    .transparentOverlay(false)
                    .build()
                    .show();
        }
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
        int resId = PracticeScreen.this.getResources().getIdentifier(practiceModel.getImage(), "drawable", PracticeScreen.this.getPackageName());
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
//                                startActivity(new Intent(PracticeScreen.this, FinishScreen.class));

                                isDone = true;
                                onBackPressed();

                            }
                        }.start();
                    }
                    break;
            }
            return true;
        }

        return false;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isDone) {
            if (ListPracticeScreen.isFirst) {
                ListPracticeScreen.ivDone1.setVisibility(View.VISIBLE);
            } else {
                ListPracticeScreen.ivDone2.setVisibility(View.VISIBLE);
            }
        }
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
