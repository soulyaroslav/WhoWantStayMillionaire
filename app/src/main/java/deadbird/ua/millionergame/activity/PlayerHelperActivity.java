package deadbird.ua.millionergame.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import deadbird.ua.millionergame.R;
import deadbird.ua.millionergame.entities.Answer;
import deadbird.ua.millionergame.entities.AnswersBoardHandler;
import deadbird.ua.millionergame.entities.Variant;
import deadbird.ua.millionergame.helpers.Constans;

public class PlayerHelperActivity extends Activity {

    // Views
    private ArrayList<TextView> listOfAnswerViews;
    private TextView answerA;
    private TextView answerB;
    private TextView answerC;
    private TextView answerD;
    // Entities
    private ArrayList<Answer> answers;
    private AnswersBoardHandler answersBoardHandler;
    private int currentHelpTip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_helper_layout);
        initViews();
        getParcelableExtras();
        showCurrentTip();
    }

    private void initViews(){
        listOfAnswerViews = new ArrayList<TextView>();
        answerA = (TextView) findViewById(R.id.help_answerA);
        listOfAnswerViews.add(answerA);
        answerB = (TextView) findViewById(R.id.help_answerB);
        listOfAnswerViews.add(answerB);
        answerC = (TextView) findViewById(R.id.help_answerC);
        listOfAnswerViews.add(answerC);
        answerD = (TextView) findViewById(R.id.help_answerD);
        listOfAnswerViews.add(answerD);
    }

    private void getParcelableExtras() {
        //answers = new ArrayList<Answer>();
        answersBoardHandler = new AnswersBoardHandler(this);
        //
        Intent intent = getIntent();
        currentHelpTip = intent.getIntExtra(Constans.HELP_TIP, 0);
        ArrayList<Parcelable> answers =  intent.getParcelableArrayListExtra(Constans.ANSWERS);
        for (int i = 0; i < answers.size(); i++) {
            Answer answer = (Answer) answers.get(i);
            answersBoardHandler.setVariant(new Variant(answer, listOfAnswerViews.get(i)));
        }
    }

    private void showCurrentTip(){
        switch (currentHelpTip){
            case Constans.FIFTY_FIFTY_TIP:
                Log.d(Constans.TAG, "state - FIFTY_FIFTY_TIP");
                answersBoardHandler.changeTextViewBgRightAnswer();
                answersBoardHandler.changeTextViewBgNotRightAnswer();
                break;
            case Constans.FRIEND_CALL_TIP:
                Log.d(Constans.TAG, "state - FRIEND_CALL_TIP");
                answersBoardHandler.changeTextViewBgRandomAnswer();
                break;
            case Constans.PEOPLE_HELP_TIP:
                Log.d(Constans.TAG, "state - PEOPLE_HELP_TIP");
                answersBoardHandler.show();
                break;
        }
    }

    public void finishHelp(View view){
        finish();
    }
}
