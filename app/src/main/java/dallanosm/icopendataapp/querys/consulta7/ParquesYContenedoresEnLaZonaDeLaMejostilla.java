package dallanosm.icopendataapp.querys.consulta7;

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
import dallanosm.icopendataapp.querys.consulta7.models.Binding;
import dallanosm.icopendataapp.querys.consulta7.models.ParquesYContenedores;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParquesYContenedoresEnLaZonaDeLaMejostilla extends ViewActivity {

    List<Site> sites;

    @Override
    public void setViews() {
        toolbar.setTitle("Parques y contenedores");
        setSupportActionBar(toolbar);
    }

    @Override
    public void setItems() {
        ApiService apiService = ApiClient.createRetrofitService(ApiService.class, ApiService.END_POINT);
        Call<ParquesYContenedores> parquesYContenedoresCall = apiService.consulta7();
        parquesYContenedoresCall.enqueue(new Callback<ParquesYContenedores>() {
            @Override
            public void onResponse(Call<ParquesYContenedores> call, Response<ParquesYContenedores> response) {
                sites = new ArrayList<>();
                ParquesYContenedores parquesYContenedores = response.body();
                for (Binding binding : parquesYContenedores.getResults().getBindings()) {
                    if (binding.getNombre() != null) {
                        sites.add(new Site(new LatLng(Double.parseDouble(binding.getLat().getValue()), Double.parseDouble(binding
                                .getLong().getValue())), binding.getNombre().getValue(),
                                ContextCompat.getColor(getApplicationContext(), R.color.purple),
                                BitmapDescriptorFactory.HUE_VIOLET, null));
                    } else if (binding.getTipo() != null) {
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

                }

                show();
            }

            @Override
            public void onFailure(Call<ParquesYContenedores> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(ParquesYContenedoresEnLaZonaDeLaMejostilla.this, "An error has ocurred", Toast.LENGTH_SHORT)
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
        items.add(new LeyendaItem(ContextCompat.getColor(getApplicationContext(), R.color.purple), "Parque"));
        items.add(new LeyendaItem(ContextCompat.getColor(getApplicationContext(), R.color.orange), "Contenedor organico"));
        items.add(new LeyendaItem(ContextCompat.getColor(getApplicationContext(), R.color.yellow), "Contenedor envases"));
        items.add(new LeyendaItem(ContextCompat.getColor(getApplicationContext(), R.color.green), "Contenedor vidrio"));
        items.add(new LeyendaItem(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary),
                "Contenedor papel/carton"));
        items.add(new LeyendaItem(ContextCompat.getColor(getApplicationContext(), R.color.pink), "Aceite"));
        items.add(new LeyendaItem(ContextCompat.getColor(getApplicationContext(), R.color.ropa), "Ropa"));

        return QueryFragment.newInstance("Parques y contenedores en la zona de la Mejostilla", "select * where {\n"
                + "{\n"
                + "select ?nombre ?lat ?long where{\n"
                + "?URI a schema:Park.\n"
                + "?URI foaf:name ?nombre.\n"
                + "?URI geo:lat ?lat.\n"
                + "?URI geo:long ?long.\n"
                + "FILTER(?lat > \"39.481607\"^^xsd:decimal && ?long > \"-6.4000\"^^xsd:decimal ).\n"
                + "}\n"
                + "}\n"
                + "UNION\n"
                + "{\n"
                + "select ?om_tipoContenedor as ?tipo ?lat ?long where{\n"
                + "?URI a om:Contenedor.\n"
                + "?URI om:tipoContenedor ?om_tipoContenedor.\n"
                + "?URI geo:lat ?lat.\n"
                + "?URI geo:long ?long.\n"
                + "FILTER(?lat > \"39.481607\"^^xsd:decimal && ?long > \"-6.4000\"^^xsd:decimal ).\n"
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
