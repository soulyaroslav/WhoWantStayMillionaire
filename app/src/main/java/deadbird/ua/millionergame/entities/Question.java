package deadbird.ua.millionergame.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.LinkedList;

public class Question implements Parcelable {

    private final int id;
    private final String body;
    private final ArrayList<Answer> answers;

    public Question(int id, String body, ArrayList<Answer> answers) {
        this.id = id;
        this.body = body;
        this.answers = answers;
    }

    public Question(Parcel in){
        this.answers = new ArrayList<Answer>();
        this.id = in.readInt();
        this.body = in.readString();
        in.readTypedList(answers, Answer.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(body);
        dest.writeTypedList(answers);
    }

    public static Parcelable.Creator<Question> CREATOR = new Parcelable.Creator<Question>(){

        @Override
        public Question createFromParcel(Parcel source) {
            return new Question(source);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[0];
        }
    };

    public int getQuestionId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    @Override
    public String toString() {
        return "[ id : " + id + " " + "body : " + body + " " + "\n answers: \n " + answers.toString() + "]";
    }
}
