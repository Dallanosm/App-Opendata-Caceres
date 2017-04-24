package dallanosm.icopendataapp.roots;


import com.google.android.gms.maps.model.LatLng;

import android.os.Parcel;
import android.os.Parcelable;

public class Site implements Parcelable {

    public static final Creator<Site> CREATOR = new Creator<Site>() {
        @Override
        public Site createFromParcel(Parcel in) {
            return new Site(in);
        }

        @Override
        public Site[] newArray(int size) {
            return new Site[size];
        }
    };

    private LatLng latLng;

    private String title;

    private String description;

    private int color;

    private float icon;


    public Site(LatLng latLng, String title, int color, float icon, String description) {
        this.latLng = latLng;
        this.title = title;
        this.color = color;
        this.icon = icon;
        this.description = description;
    }

    protected Site(Parcel in) {
        latLng = in.readParcelable(LatLng.class.getClassLoader());
        title = in.readString();
        description = in.readString();
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
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
        dest.writeString(description);
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float getIcon() {
        return icon;
    }

    public void setIcon(float icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
