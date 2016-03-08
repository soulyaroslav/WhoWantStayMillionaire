package deadbird.ua.millionergame.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class Answer implements Parcelable {

    private final int id;
    private final String body;
    private final boolean isCorrect;

    public Answer(final int id, final String body, final boolean isCorrect) {
        this.id = id;
        this.body = body;
        this.isCorrect = isCorrect;
    }

    public Answer(Parcel in){
        this.id = in.readInt();
        this.body = in.readString();
        this.isCorrect = in.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(body);
        dest.writeByte((byte) (isCorrect ? 1 : 0));
    }

    public static final Parcelable.Creator<Answer> CREATOR = new Parcelable.Creator<Answer>(){
        @Override
        public Answer createFromParcel(Parcel source) {
            return new Answer(source);
        }

        @Override
        public Answer[] newArray(int size) {
            return new Answer[0];
        }
    };

    public int getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    @Override
    public String toString() {
        return "id : " + id + " " + "body : " + body + " " + "is correct : " + isCorrect + "\n";
    }
}
