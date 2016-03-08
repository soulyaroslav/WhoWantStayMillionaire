package deadbird.ua.millionergame.entities;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Random;

import deadbird.ua.millionergame.R;

public class AnswersBoardHandler {

    private ArrayList<Variant> variants;
    private Drawable rightAnswer, defaultAnswer;

    public AnswersBoardHandler(final Context context) {
        this.variants = new ArrayList<Variant>();
        rightAnswer = context.getResources().getDrawable(R.mipmap.answer_text_view_bg_green);
        defaultAnswer = context.getResources().getDrawable(R.mipmap.answer_text_view_bg_default);
    }

    public void init(){
        this.variants = new ArrayList<Variant>();
    }

    public void changeTextViewBgRightAnswer(){
        for(Variant variant : variants) {
            if (variant.isCorrect()) {
                variant.changeBackground(rightAnswer);
            }
        }
    }

    public void changeTextViewBgNotRightAnswer(){
        int rand = new Random().nextInt(variants.size());
        Variant variant = variants.get(rand);
        if(variant.isCorrect()){
            changeTextViewBgNotRightAnswer();
        } else {
            variant.changeBackground(rightAnswer);
        }
    }

    public void changeTextViewBgRandomAnswer(){
        int rand = new Random().nextInt(variants.size());
        Variant variant = variants.get(rand);
        variant.changeBackground(rightAnswer);
    }

    public void show(){
        for(int i = 0; i < variants.size(); i++) {
            int percent = 0;
            for(int j = 0; j < 5; j++) {
                percent = (new Random().nextInt(4) + 1) * 10;
            }
            Variant variant = variants.get(i);
            variant.showV(String.valueOf(percent));
        }
    }

    public void setDefaultTextViewBg(){
        for(Variant variant : variants){
            variant.getTvAnswer().setBackground(defaultAnswer);
        }
    }

    public void setClickableViews(boolean b) {
        for (Variant variant : variants) {
            variant.getTvAnswer().setClickable(b);
        }
    }


    public void setVariant(Variant variant){
        this.variants.add(variant);
    }

    public ArrayList<Variant> getVariants() {
        return variants;
    }
}
