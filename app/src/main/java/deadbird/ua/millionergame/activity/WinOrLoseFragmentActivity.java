package deadbird.ua.millionergame.activity;

import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import deadbird.ua.millionergame.R;
import deadbird.ua.millionergame.fragments.LoseFragment;
import deadbird.ua.millionergame.fragments.WinFragment;
import deadbird.ua.millionergame.helpers.Constans;

public class WinOrLoseFragmentActivity extends FragmentActivity {

    public static final int LAYOUT = R.layout.win_or_lose_layout;

    private int currentFragment;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    // fragments
    private WinFragment winFragment;
    private LoseFragment loseFragment;
    //
    private ArrayList<TextView> moneyTvs;
    //
    private int money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        // init
        getExtras();
        initFragments();
        initViews();
        showFragment();
        setMoneyTextViewBg();
    }

    private void getExtras(){
        Intent intent = getIntent();
        currentFragment = intent.getIntExtra(Constans.FRAGMENTS, 0);
        money = intent.getIntExtra(Constans.MONEY, 0);
    }

    private void initFragments(){
        winFragment = new WinFragment();
        loseFragment = new LoseFragment();
    }

    private void initViews(){
        moneyTvs = new ArrayList<TextView>();
        moneyTvs.add((TextView) findViewById(R.id.money11));
        moneyTvs.add((TextView) findViewById(R.id.money10));
        moneyTvs.add((TextView) findViewById(R.id.money9));
        moneyTvs.add((TextView) findViewById(R.id.money8));
        moneyTvs.add((TextView) findViewById(R.id.money7));
        moneyTvs.add((TextView) findViewById(R.id.money6));
        moneyTvs.add((TextView) findViewById(R.id.money5));
        moneyTvs.add((TextView) findViewById(R.id.money4));
        moneyTvs.add((TextView) findViewById(R.id.money3));
        moneyTvs.add((TextView) findViewById(R.id.money2));
        moneyTvs.add((TextView) findViewById(R.id.money1));
    }

    private void showFragment(){
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        switch (currentFragment){
            case Constans.WIN_FRAGMENT:
                transaction.replace(R.id.container, winFragment);
                break;
            case Constans.LOSE_FRAGMENT:
                transaction.replace(R.id.container, loseFragment);
                break;
        }
        transaction.commit();
    }

    private void setMoneyTextViewBg(){
        int count = GameActivity.questionCount;
        setBackgrounds(count);
    }

    private void setBackgrounds(int count){
        Drawable currentBackground = getResources().getDrawable(R.mipmap.money_tv_bg);
        if(currentFragment == Constans.WIN_FRAGMENT) {
            for (int i = 0; i < moneyTvs.size(); i++) {
                TextView textView = moneyTvs.get(i);
                if ((count - 1) == i) {
                    textView.setBackground(currentBackground);
                    textView.setTextColor(getResources().getColor(R.color.color_bg_answer_view));
                } else {
                    textView.setBackground(null);
                }
            }
        } else {
            for (int i = 0; i < moneyTvs.size(); i++) {
                TextView textView = moneyTvs.get(i);
                textView.setBackground(null);
                textView.setTextColor(getResources().getColor(R.color.color_bold));
            }
        }
    }

    public void onContinue(View view){
        Intent intent = new Intent();
        switch (currentFragment) {
            case Constans.WIN_FRAGMENT:
                intent.putExtra(Constans.RESULT, true);
                break;
            case Constans.LOSE_FRAGMENT:
                intent.putExtra(Constans.RESULT, false);
                break;
        }
        setResult(RESULT_OK, intent);
        finish();
    }
}
