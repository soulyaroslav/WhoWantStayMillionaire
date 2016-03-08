package deadbird.ua.millionergame.save;

public class SavedObject {

    // index last question what player not answered
    private int indexLastQuestion;
    // value to detect if helps button was pressed or no
    private boolean halfTip;
    private boolean friendTip;
    private boolean peopleTip;

    public SavedObject(int indexLastQuestion, boolean halfTip, boolean friendTip, boolean peopleTip) {
        this.indexLastQuestion = indexLastQuestion;
        this.halfTip = halfTip;
        this.friendTip = friendTip;
        this.peopleTip = peopleTip;
    }

    public int getIndexLastQuestion() {
        return indexLastQuestion;
    }

    public boolean isHalfTip() {
        return halfTip;
    }

    public boolean isFriendTip() {
        return friendTip;
    }

    public boolean isPeopleTip() {
        return peopleTip;
    }
}
