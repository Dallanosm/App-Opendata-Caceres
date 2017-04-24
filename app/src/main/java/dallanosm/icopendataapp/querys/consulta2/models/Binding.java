
package dallanosm.icopendataapp.querys.consulta2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Binding {

    @SerializedName("lat")
    @Expose
    private Lat lat;
    @SerializedName("long")
    @Expose
    private Long _long;
    @SerializedName("ruido")
    @Expose
    private Ruido_ ruido;

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

    public Ruido_ getRuido() {
        return ruido;
    }

    public void setRuido(Ruido_ ruido) {
        this.ruido = ruido;
    }

}
