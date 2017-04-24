
package dallanosm.icopendataapp.querys.consulta5.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Binding {

    @SerializedName("nombre")
    @Expose
    private Nombre nombre;
    @SerializedName("lat")
    @Expose
    private Lat lat;
    @SerializedName("long")
    @Expose
    private Long _long;
    @SerializedName("enlace_oficial")
    @Expose
    private EnlaceOficial enlaceOficial;

    public Nombre getNombre() {
        return nombre;
    }

    public void setNombre(Nombre nombre) {
        this.nombre = nombre;
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

    public EnlaceOficial getEnlaceOficial() {
        return enlaceOficial;
    }

    public void setEnlaceOficial(EnlaceOficial enlaceOficial) {
        this.enlaceOficial = enlaceOficial;
    }

}
