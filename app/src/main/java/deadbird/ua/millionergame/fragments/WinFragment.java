package deadbird.ua.millionergame.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import deadbird.ua.millionergame.R;
import deadbird.ua.millionergame.activity.GameActivity;
import deadbird.ua.millionergame.helpers.Constans;

public class WinFragment extends Fragment {

    private TextView tvMoney;
    private String s;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.win_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        tvMoney = (TextView) view.findViewById(R.id.tv_money);
        setText();
    }

    private void setText() {
        s = "$" + GameActivity.moneyWin;
        tvMoney.setText(s);
    }
}
