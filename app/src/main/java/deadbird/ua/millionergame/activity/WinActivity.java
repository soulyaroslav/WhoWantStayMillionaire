package deadbird.ua.millionergame.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import deadbird.ua.millionergame.R;

public class WinActivity extends Activity {

    public static final int LAYOUT = R.layout.win_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
    }

    public void onMenu(View view){
        finish();
    }
}
