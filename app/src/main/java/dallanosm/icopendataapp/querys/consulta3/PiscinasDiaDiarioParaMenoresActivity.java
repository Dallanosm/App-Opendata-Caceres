package dallanosm.icopendataapp.querys.consulta3;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dallanosm.icopendataapp.R;
import dallanosm.icopendataapp.roots.LeyendaItem;
import dallanosm.icopendataapp.roots.ListFragment;
import dallanosm.icopendataapp.roots.MapFragment;
import dallanosm.icopendataapp.roots.QueryFragment;
import dallanosm.icopendataapp.roots.Site;
import dallanosm.icopendataapp.roots.ViewActivity;
import dallanosm.icopendataapp.httpclient.ApiClient;
import dallanosm.icopendataapp.httpclient.ApiService;
import dallanosm.icopendataapp.querys.consulta3.models.Binding;
import dallanosm.icopendataapp.querys.consulta3.models.Piscinas;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PiscinasDiaDiarioParaMenoresActivity extends ViewActivity {

    List<Site> sites;

    @Override
    public void setViews() {
        toolbar.setTitle("Piscina para menores más baratas");
        setSupportActionBar(toolbar);
    }

    @Override
    public void setItems() {
        ApiService apiService = ApiClient.createRetrofitService(ApiService.class, ApiService.END_POINT);
        Call<Piscinas> piscinasCall = apiService.consulta3();
        piscinasCall.enqueue(new Callback<Piscinas>() {
            @Override
            public void onResponse(Call<Piscinas> call, Response<Piscinas> response) {
                sites = new ArrayList<>();
                Piscinas piscinas = response.body();
                for (Binding binding : piscinas.getResults().getBindings()) {
                    sites.add(new Site(new LatLng(Double.parseDouble(binding.getLat().getValue()),
                            Double.parseDouble(binding.getLong().getValue())), binding.getName().getValue(),
                            ContextCompat.getColor(getApplicationContext(), R.color.blue), BitmapDescriptorFactory.HUE_BLUE,
                            "Precio: " + binding.getPrecio().getValue() + "€"));
                }
                show();
            }

            @Override
            public void onFailure(Call<Piscinas> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(PiscinasDiaDiarioParaMenoresActivity.this, "An error has ocurred", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public QueryFragment getQueryFragment() {
        List<LeyendaItem> items = new ArrayList<>();
        items.add(new LeyendaItem(ContextCompat.getColor(getApplicationContext(), R.color.blue), "Piscina"));
        return QueryFragment.newInstance("Piscinas ordenadas por precio para menores un día de diario",
                "select ?rdfs_label AS ?name ?om_precioNinoDiario as ?precio  ?geo_long as ?long ?geo_lat as ?lat where{\n"
                        + "?URI a om:PiscinaMunicipal.\n"
                        + "?URI rdfs:label ?rdfs_label.\n"
                        + "?URI om:precioNinoDiario ?om_precioNinoDiario.\n"
                        + "?URI geo:long ?geo_long.\n"
                        + "?URI geo:lat ?geo_lat.\n"
                        + "}\n"
                        + "ORDER BY ASC(?om_precioNinoDiario)\t", items);
    }

    public void show() {
        super.setupViews();
    }

    @Override
    public MapFragment getMapFragment() {
        return MapFragment.newInstance(sites, false);
    }

    @Override
    public ListFragment getListFragment() {
        List<Site> points = new ArrayList<>();
        for (Site site : this.sites) {
            points.add(new Site(site.getLatLng(), site.getTitle() + " - " + site.getDescription(), site.getColor(),
                    site.getIcon(), site.getDescription()));
        }
        return ListFragment.newInstance(points);
    }
}
