package techkids.com.android9_hackathon2_breakworkout.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import techkids.com.android9_hackathon2_breakworkout.R;
import tyrantgit.explosionfield.ExplosionField;

public class FinishScene extends AppCompatActivity {

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
                        startActivity(new Intent(FinishScene.this, PracticeScene.class));
                    }
                }, 1000);
            }
        });

    }

}
