package dallanosm.icopendataapp.querys.consulta6;

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
import dallanosm.icopendataapp.querys.consulta6.models.Binding;
import dallanosm.icopendataapp.querys.consulta6.models.MonumentosCercanosALaCruz;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MonumentosCercanosEn80MALaCruzActivity extends ViewActivity {

    List<Site> sites;

    @Override
    public void setViews() {
        toolbar.setTitle("Monumentos cercanos en 80m a la Cruz");
        setSupportActionBar(toolbar);
    }

    @Override
    public void setItems() {
        ApiService apiService = ApiClient.createRetrofitService(ApiService.class, ApiService.END_POINT);
        Call<MonumentosCercanosALaCruz> monumentosCercanosALaCruzCall = apiService.consulta6();
        monumentosCercanosALaCruzCall.enqueue(new Callback<MonumentosCercanosALaCruz>() {
            @Override
            public void onResponse(Call<MonumentosCercanosALaCruz> call, Response<MonumentosCercanosALaCruz> response) {
                sites = new ArrayList<>();
                MonumentosCercanosALaCruz monumentosCercanosALaCruz = response.body();
                for (Binding binding : monumentosCercanosALaCruz.getResults().getBindings()) {
                    sites.add(new Site(new LatLng(Double.parseDouble(binding.getLat().getValue()),
                            Double.parseDouble(binding.getLong().getValue())), binding.getName().getValue(),
                            ContextCompat.getColor(getApplicationContext(), R.color.orange),
                            BitmapDescriptorFactory.HUE_ORANGE, null));
                }
                show();

            }

            @Override
            public void onFailure(Call<MonumentosCercanosALaCruz> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(MonumentosCercanosEn80MALaCruzActivity.this, "An error has ocurred", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void show() {
        super.setupViews();
    }

    @Override
    public QueryFragment getQueryFragment() {
        List<LeyendaItem> items = new ArrayList<>();
        items.add(new LeyendaItem(ContextCompat.getColor(getApplicationContext(), R.color.orange), "Monumento"));
        return QueryFragment.newInstance("Monumentos cercanos en 80m a la Cruz", "select ?name ?lat ?long where{\n"
                + "?URI a om:Monumento.\n"
                + "?URI rdfs:label ?name. \n"
                + "?URI geo:lat ?lat.\n"
                + "?URI geo:long ?long.\n"
                + "FILTER(?lat > (\"39.4684168\" ^^ xsd:decimal - \"0.00100\" ^^ xsd:decimal) && ?lat < (\"39"
                + ".4684168\"^^xsd:decimal + \"0.00100\"^^xsd:decimal) && ?long > (\"-6.3792846\"^^xsd:decimal - \"0"
                + ".00100\"^^xsd:decimal) && ?long < (\"-6.3792846\"^^xsd:decimal + \"0.00100\"^^xsd:decimal) ).\n"
                + "}", items);
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
