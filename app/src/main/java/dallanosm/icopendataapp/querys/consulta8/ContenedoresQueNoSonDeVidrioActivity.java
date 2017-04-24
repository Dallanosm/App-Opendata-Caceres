package dallanosm.icopendataapp.querys.consulta8;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

import android.support.v4.content.ContextCompat;

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
import dallanosm.icopendataapp.querys.consulta8.models.Binding;
import dallanosm.icopendataapp.querys.consulta8.models.ContenedoresNoVidrio;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContenedoresQueNoSonDeVidrioActivity extends ViewActivity {

    List<Site> sites;

    @Override
    public void setViews() {
        toolbar.setTitle("Contenenedores que no son de vidrio");
        setSupportActionBar(toolbar);
    }

    @Override
    public void setItems() {
        ApiService apiService = ApiClient.createRetrofitService(ApiService.class, ApiService.END_POINT);
        Call<ContenedoresNoVidrio> contenedoresNoVidrioCall = apiService.consulta8();
        contenedoresNoVidrioCall.enqueue(new Callback<ContenedoresNoVidrio>() {
            @Override
            public void onResponse(Call<ContenedoresNoVidrio> call, Response<ContenedoresNoVidrio> response) {
                sites = new ArrayList<>();
                ContenedoresNoVidrio contenedoresNoVidrio = response.body();
                for (Binding binding : contenedoresNoVidrio.getResults().getBindings()) {
                    int color = R.color.colorPrimary;
                    float icon = BitmapDescriptorFactory.HUE_BLUE;
                    switch (binding.getTipo().getValue()) {
                        case "Aceite":
                            color = R.color.pink;
                            icon = BitmapDescriptorFactory.HUE_MAGENTA;
                            break;
                        case "Papel/Carton":
                            color = R.color.colorPrimary;
                            icon = BitmapDescriptorFactory.HUE_BLUE;
                            break;
                        case "Ropa":
                            color = R.color.ropa;
                            icon = BitmapDescriptorFactory.HUE_ORANGE;
                            break;
                        case "Vidrio":
                            color = R.color.green;
                            icon = BitmapDescriptorFactory.HUE_GREEN;
                            break;
                        case "Envases":
                            color = R.color.yellow;
                            icon = BitmapDescriptorFactory.HUE_YELLOW;
                            break;
                        case "Organica":
                            color = R.color.orange;
                            icon = BitmapDescriptorFactory.HUE_ORANGE;
                            break;
                    }
                    sites.add(new Site(new LatLng(Double.parseDouble(binding.getLat().getValue()), Double.parseDouble(binding
                            .getLong().getValue())), binding.getTipo().getValue(),
                            ContextCompat.getColor(getApplicationContext(), color),
                            icon, null));
                }

                show();
            }

            @Override
            public void onFailure(Call<ContenedoresNoVidrio> call, Throwable t) {

            }
        });
    }

    private void show() {
        super.setupViews();
    }

    @Override
    public QueryFragment getQueryFragment() {
        List<LeyendaItem> items = new ArrayList<>();
        items.add(new LeyendaItem(ContextCompat.getColor(getApplicationContext(), R.color.orange), "Contenedor organico"));
        items.add(new LeyendaItem(ContextCompat.getColor(getApplicationContext(), R.color.yellow), "Contenedor envases"));
        items.add(new LeyendaItem(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary),
                "Contenedor papel/carton"));
        items.add(new LeyendaItem(ContextCompat.getColor(getApplicationContext(), R.color.pink), "Aceite"));
        items.add(new LeyendaItem(ContextCompat.getColor(getApplicationContext(), R.color.ropa), "Ropa"));
        return QueryFragment.newInstance("Contenedores que no son de vidrio", "select * where {\n"
                + "{\n"
                + "select ?om_tipoContenedor as ?tipo ?lat ?long where{\n"
                + "?URI a om:Contenedor.\n"
                + "?URI om:tipoContenedor ?om_tipoContenedor.\n"
                + "?URI geo:lat ?lat.\n"
                + "?URI geo:long ?long.\n"
                + "}\n"
                + "}\n"
                + "MINUS\n"
                + "{\n"
                + "select ?om_tipoContenedor as ?tipo ?lat ?long where{\n"
                + "?URI a om:Contenedor.\n"
                + "?URI om:tipoContenedor ?om_tipoContenedor.\n"
                + "?URI geo:lat ?lat.\n"
                + "?URI geo:long ?long.\n"
                + "FILTER(?om_tipoContenedor = \"Vidrio\" ).\n"
                + "}\n"
                + "}\n"
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
