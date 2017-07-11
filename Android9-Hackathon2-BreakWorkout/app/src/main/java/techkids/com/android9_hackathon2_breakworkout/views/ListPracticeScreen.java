package techkids.com.android9_hackathon2_breakworkout.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;
import pl.droidsonroids.gif.GifImageView;
import techkids.com.android9_hackathon2_breakworkout.R;
import techkids.com.android9_hackathon2_breakworkout.databases.DatabaseHandle;
import techkids.com.android9_hackathon2_breakworkout.databases.PracticeModel;
import techkids.com.android9_hackathon2_breakworkout.libraries.SmoothCheckBox;
import tyrantgit.explosionfield.ExplosionField;

public class ListPracticeScreen extends AppCompatActivity implements View.OnClickListener {

    public static String TAG = AlarmScreen.class.toString();

    int numberTips = 0;

    Context context = this;
    ImageView ivDemo1;
    TextView tvName1;
    TextView tvMuscle1;
    static SmoothCheckBox scbDone1;
    static ImageView ivDone1;
    ProgressBar progressBar1;
    ImageView ivDemo2;
    TextView tvName2;
    TextView tvMuscle2;
    static SmoothCheckBox scbDone2;
    static ImageView ivDone2;
    ProgressBar progressBar2;
    ImageView ivFinish;
    boolean finish;

    static boolean isFirst;

    RelativeLayout rlPractice1;
    RelativeLayout rlPractice2;

    boolean isComfortable;
    List<PracticeModel> practiceModelList;

    boolean isClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_practice_screen);

        isComfortable = getIntent().getBooleanExtra("isComfortable", true);
        practiceModelList = new ArrayList<>();
        practiceModelList = DatabaseHandle.getInstance(this).getPractices(isComfortable);
        initViews();
        setUI();
        rlPractice1.setOnClickListener(this);
        rlPractice2.setOnClickListener(this);
        if (!FinishScreen.isFirttime2) {
            FinishScreen.isFirttime2 = true;
            new SimpleTooltip.Builder(this)
                    .anchorView(rlPractice1)
                    .text("Click here to start practice")
                    .gravity(Gravity.BOTTOM)
                    .animated(true)
                    .transparentOverlay(false)
                    .build()
                    .show();
        } else {
            numberTips = 3;
        }
    }


    private void setUI() {
        if (practiceModelList.size() < 2)
            return;

        PracticeModel practiceModel1 = practiceModelList.get(0);
        Log.d(TAG, "setUI: " + practiceModel1);
        int resId = ListPracticeScreen.this.getResources().getIdentifier(practiceModel1.getImage() + "png", "drawable", ListPracticeScreen.this.getPackageName());
        ivDemo1.setImageResource(resId);
        tvName1.setText(practiceModel1.getName());
        if (practiceModel1.isEye())
            tvMuscle1.setText(tvMuscle1.getText() + "Eye ");
        if (practiceModel1.isNeck())
            tvMuscle1.setText(tvMuscle1.getText() + "Neck ");
        if (practiceModel1.isArm())
            tvMuscle1.setText(tvMuscle1.getText() + "Arm ");
        if (practiceModel1.isLeg())
            tvMuscle1.setText(tvMuscle1.getText() + "Leg ");
        if (practiceModel1.isBody())
            tvMuscle1.setText(tvMuscle1.getText() + "Body ");

        PracticeModel practiceModel2 = practiceModelList.get(1);
        resId = ListPracticeScreen.this.getResources().getIdentifier(practiceModel2.getImage() + "png", "drawable", ListPracticeScreen.this.getPackageName());
        ivDemo2.setImageResource(resId);
        tvName2.setText(practiceModel2.getName());
        if (practiceModel2.isEye())
            tvMuscle2.setText(tvMuscle2.getText() + "Eye ");
        if (practiceModel2.isNeck())
            tvMuscle2.setText(tvMuscle2.getText() + "Neck ");
        if (practiceModel2.isArm())
            tvMuscle2.setText(tvMuscle2.getText() + "Arm ");
        if (practiceModel2.isLeg())
            tvMuscle2.setText(tvMuscle2.getText() + "Leg ");
        if (practiceModel2.isBody())
            tvMuscle2.setText(tvMuscle2.getText() + "Body ");
    }

    private void initViews() {
        ivDemo1 = (ImageView) findViewById(R.id.iv_demo1);
        tvName1 = (TextView) findViewById(R.id.tv_name1);
        tvMuscle1 = (TextView) findViewById(R.id.tv_muscle1);
        scbDone1 = (SmoothCheckBox) findViewById(R.id.scb_done1);
        ivDone1 = (ImageView) findViewById(R.id.iv_done1);
        progressBar1 = (ProgressBar) findViewById(R.id.progress1);
        ivDemo2 = (ImageView) findViewById(R.id.iv_demo2);
        tvName2 = (TextView) findViewById(R.id.tv_name2);
        tvMuscle2 = (TextView) findViewById(R.id.tv_muscle2);
        scbDone2 = (SmoothCheckBox) findViewById(R.id.scb_done2);
        ivDone2 = (ImageView) findViewById(R.id.iv_done2);
        progressBar2 = (ProgressBar) findViewById(R.id.progress2);
        ivFinish = (ImageView) findViewById(R.id.iv_boom);
        rlPractice1 = (RelativeLayout) findViewById(R.id.rl_practice1);
        rlPractice2 = (RelativeLayout) findViewById(R.id.rl_practice2);
    }


    @Override
    public void onClick(View v) {
        if (numberTips < 1) {
            numberTips++;
            new SimpleTooltip.Builder(this)
                    .anchorView(ivFinish)
                    .text("When you finish all practice. Click here to back your work.")
                    .gravity(Gravity.TOP)
                    .animated(true)
                    .transparentOverlay(false)
                    .build()
                    .show();
            return;
        }
        if (v == rlPractice1 && ivDone1.getVisibility() == View.INVISIBLE) {
            isFirst = true;
            Bundle bundle = new Bundle();
            bundle.putSerializable("showPractice", practiceModelList.get(0));
            Intent intent = new Intent(ListPracticeScreen.this, PracticeScreen.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (v == rlPractice2 && ivDone2.getVisibility() == View.INVISIBLE) {
            isFirst = false;
            Bundle bundle = new Bundle();
            bundle.putSerializable("showPractice", practiceModelList.get(1));
            Intent intent = new Intent(ListPracticeScreen.this, PracticeScreen.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (v == ivFinish && finish) {
            if (isClicked) return;
            isClicked = true;
            ExplosionField explosionField = ExplosionField.attach2Window((Activity) context);
            explosionField.explode(ivFinish);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    startActivity(new Intent(ListPracticeScreen.this, AlarmScreen.class));
                }
            }, 1000);
        }

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

    private void finishAll() {
        ivFinish.setImageResource(R.drawable.ic_check_circle_black_24dp);
        finish = true;
        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if (isClicked) return;
                isClicked = true;
                ExplosionField explosionField = ExplosionField.attach2Window((Activity) context);
                explosionField.explode(ivFinish);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        startActivity(new Intent(ListPracticeScreen.this, AlarmScreen.class));
                    }
                }, 1000);
            }
        }.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ivDone1.getVisibility() == View.VISIBLE && ivDone2.getVisibility() == View.VISIBLE) {
            finishAll();
        }

    }
}
