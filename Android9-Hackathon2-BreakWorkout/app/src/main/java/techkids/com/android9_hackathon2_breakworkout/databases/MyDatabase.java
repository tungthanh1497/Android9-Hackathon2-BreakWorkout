package techkids.com.android9_hackathon2_breakworkout.databases;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by tungthanh.1497 on 06/27/2017.
 */

public class MyDatabase extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "hackathon.db";
    private static final int DATABASE_VERSION = 1;

    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}