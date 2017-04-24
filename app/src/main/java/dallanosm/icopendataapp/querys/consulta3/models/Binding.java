
package dallanosm.icopendataapp.querys.consulta3.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Binding {

    @SerializedName("name")
    @Expose
    private Name name;
    @SerializedName("precio")
    @Expose
    private Precio precio;
    @SerializedName("long")
    @Expose
    private Long _long;
    @SerializedName("lat")
    @Expose
    private Lat lat;

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Precio getPrecio() {
        return precio;
    }

    public void setPrecio(Precio precio) {
        this.precio = precio;
    }

    public Long getLong() {
        return _long;
    }

    public void setLong(Long _long) {
        this._long = _long;
    }

    public Lat getLat() {
        return lat;
    }

    public void setLat(Lat lat) {
        this.lat = lat;
    }

}
