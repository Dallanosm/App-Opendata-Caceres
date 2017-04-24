
package dallanosm.icopendataapp.querys.consulta8.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Binding {

    @SerializedName("tipo")
    @Expose
    private Tipo tipo;
    @SerializedName("lat")
    @Expose
    private Lat lat;
    @SerializedName("long")
    @Expose
    private Long _long;

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
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
