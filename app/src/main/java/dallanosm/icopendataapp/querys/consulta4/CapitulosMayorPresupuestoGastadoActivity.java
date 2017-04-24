package dallanosm.icopendataapp.querys.consulta4;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;

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
import dallanosm.icopendataapp.roots.ViewPagerAdapter;
import dallanosm.icopendataapp.httpclient.ApiClient;
import dallanosm.icopendataapp.httpclient.ApiService;
import dallanosm.icopendataapp.querys.consulta4.models.Binding;
import dallanosm.icopendataapp.querys.consulta4.models.Presupuestos;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CapitulosMayorPresupuestoGastadoActivity extends ViewActivity {

    List<Site> capitulos;

    @Override
    public void setViews() {
        toolbar.setTitle("5 capítulos mayor presupuesto gastado");
        setSupportActionBar(toolbar);
    }

    @Override
    public void setItems() {
        ApiService apiService = ApiClient.createRetrofitService(ApiService.class, ApiService.END_POINT);
        Call<Presupuestos> presupuestosCall = apiService.consulta4();
        presupuestosCall.enqueue(new Callback<Presupuestos>() {
            @Override
            public void onResponse(Call<Presupuestos> call, Response<Presupuestos> response) {
                List<Binding> bindings = response.body().getResults().getBindings();
                capitulos = new ArrayList<>();
                for (Binding binding : bindings) {
                    capitulos.add(new Site(null,
                            binding.getCapitulo().getValue() + "  -  " + binding.getImporte().getValue() + "€",
                            ContextCompat.getColor(getApplicationContext(), R.color.orange), BitmapDescriptorFactory.HUE_ORANGE,
                            null));
                }
                show();
            }

            @Override
            public void onFailure(Call<Presupuestos> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(CapitulosMayorPresupuestoGastadoActivity.this, "An error has ocurred", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public QueryFragment getQueryFragment() {
        List<LeyendaItem> items = new ArrayList<>();
        items.add(new LeyendaItem(ContextCompat.getColor(getApplicationContext(), R.color.orange), "Capitulo"));
        return QueryFragment
                .newInstance("5 capítulos mayor presupuesto gastado", "select ?Nombre_Capitulo as ?capitulo ?importe\n"
                        + "where{\n"
                        + "?Uri_presupuesto a om:PresupuestoEntidadLocal .\n"
                        + "?Uri_presupuesto om:entidadOrdenantePresupuesto \"Ayuntamiento\" .\n"
                        + "?Uri_presupuesto om:presupuestoFormadoPor ?Capitulos_ayto .\n"
                        + "?Uri_presupuesto om:ejercicioEconomico \"2016\" .\n"
                        + "?Capitulos_ayto om:denominacionCapitulo ?Nombre_Capitulo .\n"
                        + "?Capitulos_ayto om:cantidadCapitulo ?importe .\n"
                        + "?Capitulos_ayto om:tipoApunteCapitulo \"Gasto\" .\n"
                        + "} ORDER BY DESC (?importe)\n"
                        + "LIMIT 5", items);
    }

    private void show() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment("Query", getQueryFragment());
        viewPagerAdapter.addFragment("List", getListFragment());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public MapFragment getMapFragment() {
        return null;
    }

    @Override
    public ListFragment getListFragment() {
        return ListFragment.newInstance(capitulos);
    }
}
