
package dallanosm.icopendataapp.querys.consulta2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Lat {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("datatype")
    @Expose
    private String datatype;
    @SerializedName("value")
    @Expose
    private String value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
