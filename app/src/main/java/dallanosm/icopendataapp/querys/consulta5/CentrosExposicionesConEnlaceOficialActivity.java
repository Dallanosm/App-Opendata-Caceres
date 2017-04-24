package dallanosm.icopendataapp.querys.consulta5;

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
import dallanosm.icopendataapp.querys.consulta5.models.Binding;
import dallanosm.icopendataapp.querys.consulta5.models.CentrosDeExposiciones;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CentrosExposicionesConEnlaceOficialActivity extends ViewActivity {

    List<Site> sites;

    @Override
    public void setViews() {
        toolbar.setTitle("Centro de Exposiciones");
        setSupportActionBar(toolbar);
    }

    @Override
    public void setItems() {
        ApiService apiService = ApiClient.createRetrofitService(ApiService.class, ApiService.END_POINT);
        Call<CentrosDeExposiciones> centrosDeExposicionesCall = apiService.consulta5();
        centrosDeExposicionesCall.enqueue(new Callback<CentrosDeExposiciones>() {
            @Override
            public void onResponse(Call<CentrosDeExposiciones> call, Response<CentrosDeExposiciones> response) {
                sites = new ArrayList<>();
                CentrosDeExposiciones centrosDeExposiciones = response.body();
                for (Binding binding : centrosDeExposiciones.getResults().getBindings()) {
                    sites.add(new Site(new LatLng(Double.parseDouble(binding.getLat().getValue()),
                            Double.parseDouble(binding.getLong().getValue())), binding.getNombre().getValue(),
                            ContextCompat.getColor(getApplicationContext(), R.color.green), BitmapDescriptorFactory.HUE_GREEN,
                            binding.getEnlaceOficial().getValue()));
                }
                show();
            }


            @Override
            public void onFailure(Call<CentrosDeExposiciones> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(CentrosExposicionesConEnlaceOficialActivity.this, "An error has ocurred", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    private void show() {
        super.setupViews();
    }

    @Override
    public QueryFragment getQueryFragment() {
        List<LeyendaItem> items = new ArrayList<>();
        items.add(new LeyendaItem(ContextCompat.getColor(getApplicationContext(), R.color.green), "Centro de Exposiciones"));
        return QueryFragment.newInstance("Centro de exposiciones con enlace oficial", "select ?nombre ?lat ?long ?enlace_oficial"
                + " \n"
                + "where{\n"
                + "?URI a om:CentroExposiciones.\n"
                + "?URI rdfs:label ?nombre.\n"
                + "?URI schema:url ?enlace_oficial.\n"
                + "?URI geo:lat ?lat.\n"
                + "?URI geo:long ?long.\n"
                + "}", items);
    }

    @Override
    public MapFragment getMapFragment() {
        return MapFragment.newInstance(sites, true);
    }

    @Override
    public ListFragment getListFragment() {
        return ListFragment.newInstance(sites);
    }
}
