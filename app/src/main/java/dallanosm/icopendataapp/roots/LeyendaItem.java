package dallanosm.icopendataapp.roots;

import android.os.Parcel;
import android.os.Parcelable;

public class LeyendaItem implements Parcelable {

    public static final Creator<LeyendaItem> CREATOR = new Creator<LeyendaItem>() {
        @Override
        public LeyendaItem createFromParcel(Parcel in) {
            return new LeyendaItem(in);
        }

        @Override
        public LeyendaItem[] newArray(int size) {
            return new LeyendaItem[size];
        }
    };

    String title;

    int color;

    public LeyendaItem(int color, String title) {
        this.color = color;
        this.title = title;
    }

    protected LeyendaItem(Parcel in) {
        title = in.readString();
        color = in.readInt();
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
    }
}
