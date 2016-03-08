package deadbird.ua.millionergame.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import deadbird.ua.millionergame.R;
import deadbird.ua.millionergame.entities.Question;
import deadbird.ua.millionergame.helpers.Constans;

public class MainActivity extends Activity {

    private ArrayList<Question> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        getQuestions();
    }

    private void getQuestions(){
        Intent in = getIntent();
        questions = new ArrayList<Question>();
        ArrayList<Parcelable> parcelables = in.getParcelableArrayListExtra(Constans.QUESTIONS);
        for(Parcelable parcelable : parcelables){
            Question question = (Question) parcelable;
            questions.add(question);
        }
    }

    public void onButtonStart(View view){
        final Intent intent = new Intent(MainActivity.this, GameActivity.class);
        intent.putExtra(Constans.QUESTIONS, questions);
        startActivity(intent);
        Log.d(Constans.TAG, "onButtonStart()");
    }

    public void onButtonQuit(View view){
        Log.d(Constans.TAG, "onButtonQuit()");
        System.exit(0);
    }
}
