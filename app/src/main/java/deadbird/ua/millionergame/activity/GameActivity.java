package deadbird.ua.millionergame.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import deadbird.ua.millionergame.R;
import deadbird.ua.millionergame.entities.Answer;
import deadbird.ua.millionergame.entities.AnswersBoardHandler;
import deadbird.ua.millionergame.entities.Question;
import deadbird.ua.millionergame.entities.Variant;
import deadbird.ua.millionergame.helpers.Constans;
import deadbird.ua.millionergame.save.SavedObject;
import deadbird.ua.millionergame.save.Saver;

public class GameActivity extends AppCompatActivity {

    public static final int LAYOUT = R.layout.game_layout;
    // list of questions
    private ArrayList<Question> questions;
    // views
    private Toolbar toolbar;
    private ArrayList<TextView> listOfAnswerViews;
    private TextView tvQuestion;
    private TextView answerA;
    private TextView answerB;
    private TextView answerC;
    private TextView answerD;
    // buttons
    private Button btnFiftyFiftyTip;
    private Button btnFriendCallTip;
    private Button btnPeopleHelpTip;
    // entities
    private Question currentQuestion;
    private AnswersBoardHandler answersBoardHandler;
    // Saver
    private Saver saver;
    private SavedObject object;
    // index to take question from list
    private int index;
    //
    private int currentQuestionIndex;
    // value to save player money
    public static int moneyWin;
    // value to detect how much question was locked
    public static int questionCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        // init Saver instance
        saver = new Saver(this.getApplicationContext());
        index = 0;
        moneyWin = 0;
        // init
        initToolbar();
        initEntities();
        initViews();
        getQuestions();
        // if object is not null load saves
        // else set default options
        loadSaves();
    }

    private void initToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);
    }

    private void initViews(){
        listOfAnswerViews = new ArrayList<TextView>();
        tvQuestion = (TextView) findViewById(R.id.tvQuestion);
        // add TextViews into list
        answerA = (TextView) findViewById(R.id.answerA);
        answerB = (TextView) findViewById(R.id.answerB);
        answerC = (TextView) findViewById(R.id.answerC);
        answerD = (TextView) findViewById(R.id.answerD);
        listOfAnswerViews.add(answerA);
        listOfAnswerViews.add(answerB);
        listOfAnswerViews.add(answerC);
        listOfAnswerViews.add(answerD);
        // buttons init
        btnFiftyFiftyTip = (Button) findViewById(R.id.btn_help_fifty_fifty);
        btnFriendCallTip = (Button) findViewById(R.id.btn_help_friend_call);
        btnPeopleHelpTip = (Button) findViewById(R.id.btn_help_people_helps);
    }

    private void initEntities(){
        Intent intent = getIntent();
        // init list of questions
        questions = intent.getParcelableArrayListExtra(Constans.QUESTIONS);
        // init answer handler
        answersBoardHandler = new AnswersBoardHandler(this);
    }

    private void getQuestions(){
        Intent in = getIntent();
        questions = in.getParcelableArrayListExtra(Constans.QUESTIONS);
    }

    private void setQuestion(){
        String question = null;
        //
        if(index < questions.size() - 1){
            Log.d(Constans.TAG, "question index - " + index);
            questionCount++;
            currentQuestionIndex = index;
            currentQuestion = questions.get(index);
            question = currentQuestion.getBody();
            index++;
        }
        // set question
        tvQuestion.setText(question);
        // set answers
        setAnswers();
    }

    private void setQuestion(int questionIndex){
        String question = null;
        index = questionIndex;
        //
        if(index < questions.size() - 1){
            Log.d(Constans.TAG, "question index - " + index);
            questionCount++;
            currentQuestionIndex = index;
            currentQuestion = questions.get(index);
            question = currentQuestion.getBody();
            index++;
        }
        // set question
        tvQuestion.setText(question);
        // set answers
        setAnswers();
    }

    private void setAnswers(){
        // set answers
        answersBoardHandler.init();
        ArrayList<Answer> list = new ArrayList<Answer>(currentQuestion.getAnswers());
        for(int i = 0; i < listOfAnswerViews.size(); i++){
            Answer answer = null;
            TextView view = listOfAnswerViews.get(i);
            int rand = new Random().nextInt(list.size());
            answer = list.get(rand);
            list.remove(rand);
            // init Variant instance
            Variant variant = new Variant(answer, view);
            answersBoardHandler.setVariant(variant);
            Log.d(Constans.TAG, "question id - " + currentQuestion.getQuestionId() + " is correct - " + variant.isCorrect());
        }
    }
    private void loadSaves(){
        object = read();
        if(object.getIndexLastQuestion() == 0){
            Log.d(Constans.TAG, "don`t have saves");
            questionCount = 0;
            setQuestion();
            setClickableHelpButtons(true);
        } else {
            questionCount = object.getIndexLastQuestion();
            Log.d(Constans.TAG, "last question index " + object.getIndexLastQuestion());
            setQuestion(object.getIndexLastQuestion());
            btnFiftyFiftyTip.setEnabled(object.isHalfTip());
            if(!object.isHalfTip()) {
                btnFiftyFiftyTip.setBackground(getResources().getDrawable(R.mipmap.support1_pressed));
            }
            btnFriendCallTip.setEnabled(object.isFriendTip());
            if(!object.isFriendTip()) {
                btnFriendCallTip.setBackground(getResources().getDrawable(R.mipmap.support2_pressed));
            }
            btnPeopleHelpTip.setEnabled(object.isPeopleTip());
            if(!object.isPeopleTip()) {
                btnPeopleHelpTip.setBackground(getResources().getDrawable(R.mipmap.support3_pressed));
            }
        }
    }

    public void onAnswerClick(View view) {
        Drawable choise = getResources().getDrawable(R.mipmap.answer_text_view_bg_yellow);
        Variant variant = null;
        switch(view.getId()){
            case R.id.answerA:
                answerA.setBackground(choise);
                // set variant
                variant = answersBoardHandler.getVariants().get(0);
                break;
            case R.id.answerB:
                answerB.setBackground(choise);
                // set variant
                variant = answersBoardHandler.getVariants().get(1);
                break;
            case R.id.answerC:
                answerC.setBackground(choise);
                // set variant
                variant = answersBoardHandler.getVariants().get(2);
                break;
            case R.id.answerD:
                answerD.setBackground(choise);
                // set variant
                variant = answersBoardHandler.getVariants().get(3);
                break;
        }
        // set un-clickable text views
        answersBoardHandler.setClickableViews(false);
        // set un-clickable buttons
        setClickableHelpButtons(false);
        // change right text views
        answersBoardHandler.changeTextViewBgRightAnswer();
        setMoney();
        checkWinOrLose(variant);
    }

    public void onButtonHelpClick(View view){
        ArrayList<Answer> answers = new ArrayList<>(currentQuestion.getAnswers());
        Intent intent = null;
        Bundle bundle = new Bundle();
        bundle.putInt("Money", moneyWin);
        switch(view.getId()){
            case R.id.btn_help_fifty_fifty:
                intent = new Intent(GameActivity.this, PlayerHelperActivity.class);
                intent.putExtra(Constans.HELP_TIP, Constans.FIFTY_FIFTY_TIP);
                intent.putExtra(Constans.ANSWERS, answers);
                startActivity(intent);
                btnFiftyFiftyTip.setBackground(getResources().getDrawable(R.mipmap.support1_pressed));
                btnFiftyFiftyTip.setEnabled(false);
                break;
            case R.id.btn_help_friend_call:
                intent = new Intent(GameActivity.this, PlayerHelperActivity.class);
                intent.putExtra(Constans.HELP_TIP, Constans.FRIEND_CALL_TIP);
                intent.putExtra(Constans.ANSWERS, answers);
                startActivity(intent);
                btnFriendCallTip.setBackground(getResources().getDrawable(R.mipmap.support2_pressed));
                btnFriendCallTip.setEnabled(false);
                break;
            case R.id.btn_help_people_helps:
                intent = new Intent(GameActivity.this, PlayerHelperActivity.class);
                intent.putExtra(Constans.HELP_TIP, Constans.PEOPLE_HELP_TIP);
                intent.putExtra(Constans.ANSWERS, answers);
                startActivity(intent);
                btnPeopleHelpTip.setBackground(getResources().getDrawable(R.mipmap.support3_pressed));
                btnPeopleHelpTip.setEnabled(false);
                break;
        }
    }

    private void checkWinOrLose(Variant variant){
        Bundle bundle = new Bundle();
        Intent intent = null;
        if(variant.isCorrect()){
            Log.d(Constans.TAG, "You are win!");
            intent = new Intent(GameActivity.this, WinOrLoseFragmentActivity.class);
            intent.putExtra(Constans.FRAGMENTS, Constans.WIN_FRAGMENT);
            int money = bundle.getInt("Money");
            if(money != 0){
                Log.d(Constans.TAG, "money is not null");
                intent.putExtra(Constans.MONEY, money);
            } else{
                intent.putExtra(Constans.MONEY, moneyWin);
            }
            startActivityForResult(intent, Constans.REQUEST_CODE);
        } else {
            Log.d(Constans.TAG, "You are lose!");
            intent = new Intent(GameActivity.this, WinOrLoseFragmentActivity.class);
            intent.putExtra(Constans.FRAGMENTS, Constans.LOSE_FRAGMENT);
            startActivityForResult(intent, Constans.REQUEST_CODE);
            questionCount = 0;
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.action_save:
                if(object.getIndexLastQuestion() == 0) {
                    Log.d(Constans.TAG, "save");
                    save();
                } else {
                    Log.d(Constans.TAG, "delete and save");
                    saver.deleteSavedObject(object);
                    save();
                }
                finish();
                return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // set default TextView background
        answersBoardHandler.setDefaultTextViewBg();
        // set clickable views
        setClickableHelpButtons(true);
        if(resultCode == RESULT_OK){
            boolean next = data.getBooleanExtra(Constans.RESULT, false);
            if(next) {
                answersBoardHandler.setClickableViews(true);
                setQuestion();
            } else {
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        save();
    }

    private void setClickableHelpButtons(boolean b){
        btnFiftyFiftyTip.setClickable(b);
        btnPeopleHelpTip.setClickable(b);
        btnFriendCallTip.setClickable(b);
    }

    private void setMoney(){
        switch(questionCount){
            case 1:
                moneyWin = 500;
                break;
            case 2:
                moneyWin = 1000;
                break;
            case 3:
                moneyWin = 5000;
                break;
            case 4:
                moneyWin = 25000;
                break;
            case 5:
                moneyWin = 50000;
                break;
            case 6:
                moneyWin = 100000;
                break;
            case 7:
                moneyWin = 150000;
                break;
            case 8:
                moneyWin = 250000;
                break;
            case 9:
                moneyWin = 500000;
                break;
            case 10:
                moneyWin = 750000;
                break;
            case 11:
                moneyWin = 1000000;
                win();
                restart();
                break;
        }
    }

    private void win(){
        if(questionCount == 11){
            Intent intent = new Intent(GameActivity.this, WinActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void restart(){
        index = 0;
        questionCount = 0;
        btnFiftyFiftyTip.setBackground(getResources().getDrawable(R.mipmap.support1));
        btnFriendCallTip.setEnabled(true);
        btnFriendCallTip.setBackground(getResources().getDrawable(R.mipmap.support2));
        btnPeopleHelpTip.setEnabled(true);
        btnPeopleHelpTip.setBackground(getResources().getDrawable(R.mipmap.support3));
        btnPeopleHelpTip.setEnabled(true);
        saver.deleteSavedObject(object);
    }

    private void save(){
        // init SavedGame instance
        SavedObject savedObject = new SavedObject(
                currentQuestionIndex,
                btnFiftyFiftyTip.isEnabled(),
                btnFriendCallTip.isEnabled(),
                btnPeopleHelpTip.isEnabled()
        );
        Log.d(Constans.TAG, "saves = " + savedObject.getIndexLastQuestion() + " - " +
                savedObject.isHalfTip() + " - " + savedObject.isFriendTip() + " - " + savedObject.isPeopleTip());
        // save data
        saver.setSavedObject(savedObject);
    }

    private SavedObject read(){
        return saver.getSavedObject();
    }
}
