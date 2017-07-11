package techkids.com.android9_hackathon2_breakworkout.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by tungthanh.1497 on 06/28/2017.
 */

public class DatabaseHandle {
    String TAG = DatabaseHandle.class.toString();
    Random rand = new Random();
    // 1: create instance MyDatabase
    private MyDatabase myDatabase;

    public DatabaseHandle(Context context) {
        myDatabase = new MyDatabase(context);
    }

    // 2: create instance databasehandle
    private static DatabaseHandle instance;

    public static DatabaseHandle getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHandle(context);
        }
        return instance;
    }

    // 3: create instance SQLiteDatabase
    private SQLiteDatabase practiceDatabase;

    //getRandomPractice
    public PracticeModel getPractice(Boolean isComfortable) {
        Log.d(TAG, "getPractice: jump in");
        practiceDatabase = myDatabase.getReadableDatabase();
        List<PracticeModel> practiceModelList = new ArrayList<>();
        Cursor cursor;
        if (isComfortable)
            cursor = practiceDatabase.rawQuery("select * from PRACTICES where environment = 0", null);
        else

            cursor = practiceDatabase.rawQuery("select * from PRACTICES where environment = 1", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String how = cursor.getString(2);
            boolean neck = (cursor.getInt(3) != 0);
            boolean eye = (cursor.getInt(4) != 0);
            boolean arm = (cursor.getInt(5) != 0);
            boolean leg = (cursor.getInt(6) != 0);
            boolean body = (cursor.getInt(7) != 0);
            boolean environment = (cursor.getInt(8) != 0);
            String image = cursor.getString(9);

            PracticeModel practiceModel = new PracticeModel(id, name, how, neck, eye, arm, leg, body, environment, image);
            Log.d(TAG, "getPractice: " + practiceModel);
            practiceModelList.add(practiceModel);
            cursor.moveToNext();
        }

        // int randomNum = rand.nextInt((max - min) + 1) + min;
        if (practiceModelList.size() > 1) {
            int randomNum = rand.nextInt(practiceModelList.size());
            Log.d(TAG, "getPractice: done - " + practiceModelList.get(randomNum));
            return practiceModelList.get(randomNum);
        } else return null;
    }

    public List<PracticeModel>  getPractices(Boolean isComfortable) {
        Log.d(TAG, "getPractice: jump in");
        practiceDatabase = myDatabase.getReadableDatabase();
        List<PracticeModel> practiceModelList = new ArrayList<>();
        Cursor cursor;
        if (isComfortable)
            cursor = practiceDatabase.rawQuery("select * from PRACTICES where environment = 0", null);
        else

            cursor = practiceDatabase.rawQuery("select * from PRACTICES where environment = 1", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String how = cursor.getString(2);
            boolean neck = (cursor.getInt(3) != 0);
            boolean eye = (cursor.getInt(4) != 0);
            boolean arm = (cursor.getInt(5) != 0);
            boolean leg = (cursor.getInt(6) != 0);
            boolean body = (cursor.getInt(7) != 0);
            boolean environment = (cursor.getInt(8) != 0);
            String image = cursor.getString(9);

            PracticeModel practiceModel = new PracticeModel(id, name, how, neck, eye, arm, leg, body, environment, image);
            Log.d(TAG, "getPractice: " + practiceModel);
            practiceModelList.add(practiceModel);
            cursor.moveToNext();
        }

        List<PracticeModel> practiceModelListReturn = new ArrayList<>();

        if (practiceModelList.size() > 2) {
            int randomNum = rand.nextInt(practiceModelList.size());
            practiceModelListReturn.add(practiceModelList.get(randomNum));
            int secondPractice = randomNum;
            while (secondPractice == randomNum) {
                secondPractice = rand.nextInt(practiceModelList.size());
            }
            practiceModelListReturn.add(practiceModelList.get(secondPractice));
            return practiceModelListReturn;
        } else return null;
    }

}
