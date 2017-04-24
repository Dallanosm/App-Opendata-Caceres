package dallanosm.icopendataapp.httpclient;

import dallanosm.icopendataapp.querys.consulta1.models.DesfibriladoresYCentrosMedicos;
import dallanosm.icopendataapp.querys.consulta2.models.Ruido;
import dallanosm.icopendataapp.querys.consulta3.models.Piscinas;
import dallanosm.icopendataapp.querys.consulta4.models.Presupuestos;
import dallanosm.icopendataapp.querys.consulta5.models.CentrosDeExposiciones;
import dallanosm.icopendataapp.querys.consulta6.models.MonumentosCercanosALaCruz;
import dallanosm.icopendataapp.querys.consulta7.models.ParquesYContenedores;
import dallanosm.icopendataapp.querys.consulta8.models.ContenedoresNoVidrio;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    String END_POINT = "http://opendata.caceres.es/";

    @GET("sparql?default-graph-uri=&query=select%20*%20where%0A%7B%0A%7B%0Aselect%20%3Frdfs_label%20AS%20%3Fname%20%3Fgeo_lat"
            + "%20AS%20%3Flat%20%3Fgeo_long%20AS%20%3Flong%20where%7B%0A%3FURI%20a%20schema%3AMedicalOrganization"
            + ".%0A%3FURI%20rdfs%3Alabel%20%3Frdfs_label.%0A%3FURI%20geo%3Alat%20%3Fgeo_lat"
            + ".%0A%3FURI%20geo%3Along%20%3Fgeo_long"
            + ".%20%0A%7D%0A%7D%0AUNION%0A%7B%0ASELECT%20%3Fgeo_long%20as%20%3Flong%20%3Fgeo_lat%20as%20%3Flat%20%20"
            + "%3Fom_situadoEnCentro%20as%20%3Fcentro%20WHERE%20%7B%20%0A%3FURI%20a%20om%3ADesfibrilador"
            + ".%20%0A%3FURI%20geo%3Along%20%3Fgeo_long.%0A%3FURI%20geo%3Alat%20%3Fgeo_lat"
            + ".%0A%3FURI%20om%3AsituadoEnCentro%20%3Fom_situadoEnCentro.%0A%7D%0A%7D%0A%7D&format=json")
    Call<DesfibriladoresYCentrosMedicos> consulta1();

    @GET("sparql?default-graph-uri=&query=select+%3Fgeo_lat+as+%3Flat+%3Fgeo_long+as+%3Flong+%3Fom_nivelRuido+as+%3Fruido+where"
            + "%7B%0D%0A%3FURI+a+om%3AMedicionRuido.+%0D%0A%3FURI+om%3AnivelRuido+%3Fom_nivelRuido"
            + ".%0D%0A%3FURI++geo%3Alat+%3Fgeo_lat.%0D%0A%3FURI+geo%3Along+%3Fgeo_long"
            + ".%0D%0AFILTER%28+%3Fom_nivelRuido%3E70%29%0D%0A%7D&format=application%2Fsparql-results%2Bjson&timeout=0&debug=on")
    Call<Ruido> consulta2();

    @GET("sparql?default-graph-uri=&query=select+%3Frdfs_label+AS+%3Fname+%3Fom_precioNinoDiario+as+%3Fprecio++%3Fgeo_long+as"
            + "+%3Flong+%3Fgeo_lat+as+%3Flat+where%7B%0D%0A%3FURI+a+om%3APiscinaMunicipal"
            + ".%0D%0A%3FURI+rdfs%3Alabel+%3Frdfs_label.%0D%0A%3FURI+om%3AprecioNinoDiario+%3Fom_precioNinoDiario"
            + ".%0D%0A%3FURI+geo%3Along+%3Fgeo_long.%0D%0A%3FURI+geo%3Alat+%3Fgeo_lat"
            + ".%0D%0A%7D%0D%0AORDER+BY+ASC%28%3Fom_precioNinoDiario%29&format=application%2Fsparql-results%2Bjson&timeout=0"
            + "&debug=on")
    Call<Piscinas> consulta3();

    @GET("sparql?default-graph-uri=&query=select+%3FNombre_Capitulo+as+%3Fcapitulo+%3Fimporte%0D%0Awhere%7B%0D%0A"
            + "%3FUri_presupuesto+a+om%3APresupuestoEntidadLocal+"
            + ".%0D%0A%3FUri_presupuesto+om%3AentidadOrdenantePresupuesto+%22Ayuntamiento%22+"
            + ".%0D%0A%3FUri_presupuesto+om%3ApresupuestoFormadoPor+%3FCapitulos_ayto+"
            + ".%0D%0A%3FUri_presupuesto+om%3AejercicioEconomico+%222016%22+"
            + ".%0D%0A%3FCapitulos_ayto+om%3AdenominacionCapitulo+%3FNombre_Capitulo+"
            + ".%0D%0A%3FCapitulos_ayto+om%3AcantidadCapitulo+%3Fimporte+"
            + ".%0D%0A%3FCapitulos_ayto+om%3AtipoApunteCapitulo+%22Gasto%22+"
            + ".%0D%0A%7D+ORDER+BY+DESC+%28%3Fimporte%29%0D%0ALIMIT+5&format=application%2Fsparql-results%2Bjson&timeout=0"
            + "&debug=on"
            + "&debug=on")
    Call<Presupuestos> consulta4();

    @GET("sparql?default-graph-uri=&query=select+%3Fnombre+%3Flat+%3Flong+%3Fenlace_oficial+%0D%0Awhere%7B%0D%0A%3FURI+a+om"
            + "%3ACentroExposiciones.%0D%0A%3FURI+rdfs%3Alabel+%3Fnombre.%0D%0A%3FURI+schema%3Aurl+%3Fenlace_oficial"
            + ".%0D%0A%3FURI+geo%3Alat+%3Flat.%0D%0A%3FURI+geo%3Along+%3Flong"
            + ".%0D%0A%7D&format=application%2Fsparql-results%2Bjson&timeout=0&debug=on")
    Call<CentrosDeExposiciones> consulta5();

    @GET("sparql?default-graph-uri=&query=select+%3Fname+%3Flat+%3Flong+where%7B%0D%0A%3FURI+a+om%3AMonumento"
            + ".%0D%0A%3FURI+rdfs%3Alabel+%3Fname.+%0D%0A%3FURI+geo%3Alat+%3Flat.%0D%0A%3FURI+geo%3Along+%3Flong"
            + ".%0D%0AFILTER%28%3Flat+%3E+%28%2239.4684168%22+%5E%5E+xsd%3Adecimal+-+%220"
            + ".00100%22+%5E%5E+xsd%3Adecimal%29+%26%26+%3Flat+%3C+%28%2239.4684168%22%5E%5Exsd%3Adecimal+%2B+%220"
            + ".00100%22%5E%5Exsd%3Adecimal%29+%26%26+%3Flong+%3E+%28%22-6.3792846%22%5E%5Exsd%3Adecimal+-+%220"
            + ".00100%22%5E%5Exsd%3Adecimal%29+%26%26+%3Flong+%3C+%28%22-6.3792846%22%5E%5Exsd%3Adecimal+%2B+%220"
            + ".00100%22%5E%5Exsd%3Adecimal%29+%29.%0D%0A%7D%0D%0A&format=application%2Fsparql-results%2Bjson&timeout=0&debug=on")
    Call<MonumentosCercanosALaCruz> consulta6();


    @GET("sparql?default-graph-uri=&query=select+*+where+%7B%0D%0A%7B%0D%0Aselect+%3Fnombre+%3Flat+%3Flong+where%7B%0D%0A%3FURI"
            + "+a+schema%3APark.%0D%0A%3FURI+foaf%3Aname+%3Fnombre.%0D%0A%3FURI+geo%3Alat+%3Flat"
            + ".%0D%0A%3FURI+geo%3Along+%3Flong.%0D%0AFILTER%28%3Flat+%3E+%2239"
            + ".481607%22%5E%5Exsd%3Adecimal+%26%26+%3Flong+%3E+%22-6.4000%22%5E%5Exsd%3Adecimal+%29"
            + ".%0D%0A%7D%0D%0A%7D%0D%0AUNION%0D%0A%7B%0D%0Aselect+%3Fom_tipoContenedor+as+%3Ftipo+%3Flat+%3Flong+where%7B%0D"
            + "%0A%3FURI+a+om%3AContenedor.%0D%0A%3FURI+om%3AtipoContenedor+%3Fom_tipoContenedor.%0D%0A%3FURI+geo%3Alat+%3Flat"
            + ".%0D%0A%3FURI+geo%3Along+%3Flong.%0D%0AFILTER%28%3Flat+%3E+%2239"
            + ".481607%22%5E%5Exsd%3Adecimal+%26%26+%3Flong+%3E+%22-6.4000%22%5E%5Exsd%3Adecimal+%29"
            + ".%0D%0A%7D%0D%0A%7D%0D%0A%7D&format=application%2Fsparql-results%2Bjson&timeout=0&debug=on")
    Call<ParquesYContenedores> consulta7();


    @GET("sparql?default-graph-uri=&query=select+*+where+%7B%0D%0A%7B%0D%0Aselect+%3Fom_tipoContenedor+as+%3Ftipo+%3Flat"
            + "+%3Flong+where%7B%0D%0A%3FURI+a+om%3AContenedor.%0D%0A%3FURI+om%3AtipoContenedor+%3Fom_tipoContenedor"
            + ".%0D%0A%3FURI+geo%3Alat+%3Flat.%0D%0A%3FURI+geo%3Along+%3Flong"
            + ".%0D%0A%7D%0D%0A%7D%0D%0AMINUS%0D%0A%7B%0D%0Aselect+%3Fom_tipoContenedor+as+%3Ftipo+%3Flat+%3Flong+where%7B%0D"
            + "%0A%3FURI+a+om%3AContenedor.%0D%0A%3FURI+om%3AtipoContenedor+%3Fom_tipoContenedor.%0D%0A%3FURI+geo%3Alat+%3Flat"
            + ".%0D%0A%3FURI+geo%3Along+%3Flong.%0D%0AFILTER%28%3Fom_tipoContenedor+%3D+%22Vidrio%22+%29"
            + ".%0D%0A%7D%0D%0A%7D%0D%0A%7D&format=application%2Fsparql-results%2Bjson&timeout=0&debug=on")
    Call<ContenedoresNoVidrio> consulta8();

}
