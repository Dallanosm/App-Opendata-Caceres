package dallanosm.icopendataapp.querys.consulta2;

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
import dallanosm.icopendataapp.querys.consulta2.models.Binding;
import dallanosm.icopendataapp.querys.consulta2.models.Ruido;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RuidoSuperiorA70Activity extends ViewActivity {

    List<Site> sites;

    @Override
    public void setViews() {
        toolbar.setTitle("Puntos con más de 70db");
        setSupportActionBar(toolbar);
    }

    @Override
    public void setItems() {
        ApiService apiService = ApiClient.createRetrofitService(ApiService.class, ApiService.END_POINT);
        Call<Ruido> ruidoCall = apiService.consulta2();
        ruidoCall.enqueue(new Callback<Ruido>() {
            @Override
            public void onResponse(Call<Ruido> call, Response<Ruido> response) {
                Ruido ruido = response.body();
                sites = new ArrayList<>();
                for (Binding binding : ruido.getResults().getBindings()) {
                    sites.add(new Site(new LatLng(Double.parseDouble(binding.getLat().getValue()),
                            Double.parseDouble(binding.getLong().getValue())),
                            binding.getRuido().getValue() + " db",
                            ContextCompat.getColor(getApplicationContext(), R.color.yellow), BitmapDescriptorFactory.HUE_YELLOW,
                            null));
                }
                show();
            }

            @Override
            public void onFailure(Call<Ruido> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(RuidoSuperiorA70Activity.this, "An error has ocurred", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public QueryFragment getQueryFragment() {
        List<LeyendaItem> items = new ArrayList<>();
        items.add(new LeyendaItem(ContextCompat.getColor(getApplicationContext(), R.color.yellow), "Punto con más de 70db"));
        return QueryFragment.newInstance("Puntos con más de 70db", "select ?geo_lat as ?lat ?geo_long as ?long ?om_nivelRuido as"
                + " ?ruido where{\n"
                + "?URI a om:MedicionRuido. \n"
                + "?URI om:nivelRuido ?om_nivelRuido.\n"
                + "?URI  geo:lat ?geo_lat.\n"
                + "?URI geo:long ?geo_long.\n"
                + "FILTER( ?om_nivelRuido>70)\n"
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
