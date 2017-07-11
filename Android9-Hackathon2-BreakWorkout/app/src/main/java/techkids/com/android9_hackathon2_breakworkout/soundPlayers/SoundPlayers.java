package techkids.com.android9_hackathon2_breakworkout.soundPlayers;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;

/**
 * Created by tungthanh.1497 on 07/11/2017.
 */

public class SoundPlayers {
    public static SoundPool soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 1);
    public static HashMap<String, Integer> noteMap = new HashMap<>();

    public static void loadSounds(Context context) {
        loadSound("1", "notify", context);
        loadSound("2", "happy", context);
        loadSound("3", "success", context);
    }

    public static int loadSound(String note, String fileName, Context context) {
        int id = context.getResources().getIdentifier(fileName, "raw", context.getPackageName());
        int soundID = soundPool.load(context, id, 1);
        noteMap.put(note, soundID);
        return id;
    }

    public static int playSound(String note) {
        int soundID = noteMap.get(note);
        return soundPool.play(soundID, 1, 1, 1, 0, 1);
    }
}
