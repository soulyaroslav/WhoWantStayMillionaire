package deadbird.ua.millionergame.save;

import android.content.Context;
import android.content.SharedPreferences;

public class Saver {

    private static final String PREF_NAME = "JsonSaver";
    // key
    public static final String QUESTION_INDEX = "question_index";
    public static final String HALF_TIP = "half_tip";
    public static final String FRIEND_TIP = "friend_tip";
    public static final String PEOPLE_TIP = "people_tip";
    //
    private static SharedPreferences settings;
    private static SharedPreferences.Editor editor;

    public Saver(Context context){
        if(settings == null){
            settings = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        }
        //editor = settings.edit();
    }
    /** Save */
    public void setSavedObject(SavedObject object){
        editor = settings.edit();
        editor.putInt(QUESTION_INDEX, object.getIndexLastQuestion());
        editor.putBoolean(HALF_TIP, object.isHalfTip());
        editor.putBoolean(FRIEND_TIP, object.isFriendTip());
        editor.putBoolean(PEOPLE_TIP, object.isPeopleTip());
        editor.apply();
    }
    /** Get */
    public SavedObject getSavedObject(){
        int index = settings.getInt(QUESTION_INDEX, 0);
        boolean halfTip = settings.getBoolean(HALF_TIP, true);
        boolean friendTip = settings.getBoolean(FRIEND_TIP, true);
        boolean peopleTip = settings.getBoolean(PEOPLE_TIP, true);

        return new SavedObject(index, halfTip, friendTip, peopleTip);
    }
    /** Delete */
    public void deleteSavedObject(SavedObject object){
        if(object == null){
            return;
        }
        editor = settings.edit();
        editor.remove(QUESTION_INDEX);
        editor.remove(HALF_TIP);
        editor.remove(FRIEND_TIP);
        editor.remove(PEOPLE_TIP);
        editor.apply();
    }
}
