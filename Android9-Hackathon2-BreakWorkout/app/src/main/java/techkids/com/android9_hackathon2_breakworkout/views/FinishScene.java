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

public class FinishScene extends AppCompatActivity implements View.OnTouchListener {

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
                        startActivity(new Intent(FinishScene.this, AlarmScene.class));
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
                        startActivity(new Intent(FinishScene.this, AlarmScene.class));
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
                    vBoom.setImageResource(R.drawable.untitled62_clicked);
                    break;
                case MotionEvent.ACTION_UP:
                    isclicked = true;
                    vBoom.setImageResource(R.drawable.untitled62);
                    ExplosionField explosionField = ExplosionField.attach2Window((Activity) context);
                    explosionField.explode(vBoom);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            startActivity(new Intent(FinishScene.this, AlarmScene.class));
                        }
                    }, 1000);
                    break;
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}
