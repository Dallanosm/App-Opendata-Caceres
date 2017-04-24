
package dallanosm.icopendataapp.querys.consulta6.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Binding {

    @SerializedName("name")
    @Expose
    private Name name;
    @SerializedName("lat")
    @Expose
    private Lat lat;
    @SerializedName("long")
    @Expose
    private Long _long;

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Lat getLat() {
        return lat;
    }

    public void setLat(Lat lat) {
        this.lat = lat;
    }

    public Long getLong() {
        return _long;
    }

    public void setLong(Long _long) {
        this._long = _long;
    }

}
