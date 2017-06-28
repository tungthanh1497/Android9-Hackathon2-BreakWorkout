package techkids.com.android9_hackathon2_breakworkout.views;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import pl.droidsonroids.gif.GifImageView;
import techkids.com.android9_hackathon2_breakworkout.R;
import techkids.com.android9_hackathon2_breakworkout.databases.DatabaseHandle;
import techkids.com.android9_hackathon2_breakworkout.databases.PracticeModel;

public class PracticeScene extends AppCompatActivity implements View.OnClickListener {

    TextView tvCountDown;
    Button btStart;

    String TAG = PracticeScene.class.toString();
    TextView tvName;
    GifImageView givImage;
    TextView tvHow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_scene);

        tvCountDown = (TextView) findViewById(R.id.tv_count_down);
        btStart = (Button) findViewById(R.id.bt_start);
        btStart.setOnClickListener(this);

        tvName = (TextView) findViewById(R.id.tv_name);
        givImage = (GifImageView) findViewById(R.id.giv_image);
        tvHow = (TextView) findViewById(R.id.tv_description);

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
                tvCountDown.setVisibility(View.VISIBLE);
                btStart.setText("STOP");
            } else {
                tvCountDown.setVisibility(View.INVISIBLE);
                btStart.setText("START");
            }
        }
    }
}
