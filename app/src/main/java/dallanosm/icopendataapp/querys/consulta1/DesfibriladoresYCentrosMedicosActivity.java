package dallanosm.icopendataapp.querys.consulta1;

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
import dallanosm.icopendataapp.querys.consulta1.models.Binding;
import dallanosm.icopendataapp.querys.consulta1.models.DesfibriladoresYCentrosMedicos;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DesfibriladoresYCentrosMedicosActivity extends ViewActivity {

    private List<Site> sites;

    @Override
    public void setViews() {
        toolbar.setTitle("Desfibriladores y centros de salud");
        setSupportActionBar(toolbar);
    }

    @Override
    public void setItems() {
        ApiService apiService = ApiClient.createRetrofitService(ApiService.class, ApiService.END_POINT);
        Call<DesfibriladoresYCentrosMedicos> call = apiService.consulta1();
        call.enqueue(new Callback<DesfibriladoresYCentrosMedicos>() {
            @Override
            public void onResponse(Call<DesfibriladoresYCentrosMedicos> call, Response<DesfibriladoresYCentrosMedicos> response) {
                sites = new ArrayList<>();
                DesfibriladoresYCentrosMedicos body = response.body();
                List<Binding> bindings = body.getResults().getBindings();
                for (Binding binding : bindings) {
                    if (binding.getName() != null) {
                        sites.add(new Site(new LatLng(Double.parseDouble(binding.getLat().getValue()),
                                Double.parseDouble(binding.getLong().getValue())), binding.getName().getValue(),
                                ContextCompat.getColor(getApplicationContext(), R.color.green),
                                BitmapDescriptorFactory.HUE_GREEN, "Centro médico"));
                    } else if (binding.getCentro() != null) {
                        sites.add(new Site(new LatLng(Double.parseDouble(binding.getLat().getValue()),
                                Double.parseDouble(binding.getLong().getValue())), binding.getCentro().getValue(),
                                ContextCompat.getColor(getApplicationContext(), R.color.orange),
                                BitmapDescriptorFactory.HUE_ORANGE, "Desfibrilador"));
                    }
                }
                show();
            }

            @Override
            public void onFailure(Call<DesfibriladoresYCentrosMedicos> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(DesfibriladoresYCentrosMedicosActivity.this, "An error has ocurred", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public QueryFragment getQueryFragment() {

        List<LeyendaItem> items = new ArrayList<>();
        items.add(new LeyendaItem(ContextCompat.getColor(getApplicationContext(), R.color.green), "Centro médico"));
        items.add(new LeyendaItem(ContextCompat.getColor(getApplicationContext(), R.color.orange), "Desfibrilador"));
        return QueryFragment.newInstance("Desfibriladores y centros de salud", "select * where\n"
                + "{\n"
                + "{\n"
                + "select ?rdfs_label AS ?name ?geo_lat AS ?lat ?geo_long AS ?long where{\n"
                + "?URI a schema:MedicalOrganization.\n"
                + "?URI rdfs:label ?rdfs_label.\n"
                + "?URI geo:lat ?geo_lat.\n"
                + "?URI geo:long ?geo_long. \n"
                + "}\n"
                + "}\n"
                + "UNION\n"
                + "{\n"
                + "SELECT ?geo_long as ?long ?geo_lat as ?lat  ?om_situadoEnCentro as ?centro WHERE { \n"
                + "?URI a om:Desfibrilador. \n"
                + "?URI geo:long ?geo_long.\n"
                + "?URI geo:lat ?geo_lat.\n"
                + "?URI om:situadoEnCentro ?om_situadoEnCentro.\n"
                + "}\n"
                + "}\n"
                + "}", items);
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
        return ListFragment.newInstance(sites);
    }

}
