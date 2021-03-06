package techkids.com.android9_hackathon2_breakworkout.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import techkids.com.android9_hackathon2_breakworkout.R;
import tyrantgit.explosionfield.ExplosionField;

public class FinishScreen extends AppCompatActivity implements View.OnTouchListener {

    static boolean isFirttime1=false;
    static boolean isFirttime2=false;
    static boolean isFirttime3=false;

    ImageView vBoom;
    boolean isclicked = false;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_scene);

        vBoom = (ImageView) findViewById(R.id.iv_boom);
        vBoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isclicked) return;
                isclicked = true;
                ExplosionField explosionField = ExplosionField.attach2Window((Activity) context);
                explosionField.explode(vBoom);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        startActivity(new Intent(FinishScreen.this, AlarmScreen.class));
                    }
                }, 1000);
            }
        });
        CountDownTimer countDownTimer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if (isclicked) return;
                isclicked = true;
                ExplosionField explosionField = ExplosionField.attach2Window((Activity) context);
                explosionField.explode(vBoom);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        startActivity(new Intent(FinishScreen.this, AlarmScreen.class));
                    }
                }, 1000);
            }
        }.start();

        vBoom.setOnTouchListener(this);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v == vBoom && !isclicked) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    vBoom.setImageResource(R.drawable.ic_check_circle_black_24dp_copy);
                    break;
                case MotionEvent.ACTION_UP:
                    isclicked = true;
                    vBoom.setImageResource(R.drawable.ic_check_circle_black_24dp);
                    ExplosionField explosionField = ExplosionField.attach2Window((Activity) context);
                    explosionField.explode(vBoom);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            startActivity(new Intent(FinishScreen.this, AlarmScreen.class));
                        }
                    }, 1000);
                    break;
            }
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
}
