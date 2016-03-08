package deadbird.ua.millionergame.entities;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

public class Variant {

    private Answer answer;
    private TextView tvAnswer;

    public Variant(Answer answer, TextView tvAnswer) {
        this.answer = answer;
        this.tvAnswer = tvAnswer;
        tvAnswer.setText(answer.getBody());
    }

    public int getId(){
        return answer.getId();
    }

    public boolean isCorrect(){
        return answer.isCorrect();
    }

    public void changeBackground(Drawable rightAnswer) {
        tvAnswer.setBackground(rightAnswer);
    }

    public void showV(String s){
        tvAnswer.setText(answer.getBody() + " - " + s + "%");
    }

    public TextView getTvAnswer() {
        return tvAnswer;
    }
}
