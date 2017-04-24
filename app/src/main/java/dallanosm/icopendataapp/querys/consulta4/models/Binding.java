
package dallanosm.icopendataapp.querys.consulta4.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Binding {

    @SerializedName("capitulo")
    @Expose
    private Capitulo capitulo;
    @SerializedName("importe")
    @Expose
    private Importe importe;

    public Capitulo getCapitulo() {
        return capitulo;
    }

    public void setCapitulo(Capitulo capitulo) {
        this.capitulo = capitulo;
    }

    public Importe getImporte() {
        return importe;
    }

    public void setImporte(Importe importe) {
        this.importe = importe;
    }

}
