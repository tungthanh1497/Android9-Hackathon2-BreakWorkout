package techkids.com.android9_hackathon2_breakworkout;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import techkids.com.android9_hackathon2_breakworkout.views.AlarmScene;
import techkids.com.android9_hackathon2_breakworkout.views.PracticeScene;

public class MainActivity extends AppCompatActivity {

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_scene);
        Intent intentChange = new Intent(context, AlarmScene.class);
        context.startActivity(intentChange);
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}
