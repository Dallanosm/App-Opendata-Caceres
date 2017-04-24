package dallanosm.icopendataapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import dallanosm.icopendataapp.querys.consulta1.DesfibriladoresYCentrosMedicosActivity;
import dallanosm.icopendataapp.querys.consulta2.RuidoSuperiorA70Activity;
import dallanosm.icopendataapp.querys.consulta3.PiscinasDiaDiarioParaMenoresActivity;
import dallanosm.icopendataapp.querys.consulta4.CapitulosMayorPresupuestoGastadoActivity;
import dallanosm.icopendataapp.querys.consulta5.CentrosExposicionesConEnlaceOficialActivity;
import dallanosm.icopendataapp.querys.consulta6.MonumentosCercanosEn80MALaCruzActivity;
import dallanosm.icopendataapp.querys.consulta7.ParquesYContenedoresEnLaZonaDeLaMejostilla;
import dallanosm.icopendataapp.querys.consulta8.ContenedoresQueNoSonDeVidrioActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button consulta1;

    private Button consulta2;

    private Button consulta3;

    private Button consulta4;

    private Button consulta5;

    private Button consulta6;

    private Button consulta7;

    private Button consulta8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        registerListeners();
    }

    public void findViews() {
        consulta1 = (Button) findViewById(R.id.consulta1);
        consulta2 = (Button) findViewById(R.id.consulta2);
        consulta3 = (Button) findViewById(R.id.consulta3);
        consulta4 = (Button) findViewById(R.id.consulta4);
        consulta5 = (Button) findViewById(R.id.consulta5);
        consulta6 = (Button) findViewById(R.id.consulta6);
        consulta7 = (Button) findViewById(R.id.consulta7);
        consulta8 = (Button) findViewById(R.id.consulta8);
    }

    private void registerListeners() {
        consulta1.setOnClickListener(this);
        consulta2.setOnClickListener(this);
        consulta3.setOnClickListener(this);
        consulta4.setOnClickListener(this);
        consulta5.setOnClickListener(this);
        consulta6.setOnClickListener(this);
        consulta7.setOnClickListener(this);
        consulta8.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == consulta1) {
            Intent intent = new Intent(MainActivity.this, DesfibriladoresYCentrosMedicosActivity.class);
            startActivity(intent);
        } else if (v == consulta2) {
            Intent intent = new Intent(MainActivity.this, RuidoSuperiorA70Activity.class);
            startActivity(intent);
        } else if (v == consulta3) {
            Intent intent = new Intent(MainActivity.this, PiscinasDiaDiarioParaMenoresActivity.class);
            startActivity(intent);
        } else if (v == consulta4) {
            Intent intent = new Intent(MainActivity.this, CapitulosMayorPresupuestoGastadoActivity.class);
            startActivity(intent);
        } else if (v == consulta5) {
            Intent intent = new Intent(MainActivity.this, CentrosExposicionesConEnlaceOficialActivity.class);
            startActivity(intent);
        } else if (v == consulta6) {
            Intent intent = new Intent(MainActivity.this, MonumentosCercanosEn80MALaCruzActivity.class);
            startActivity(intent);
        } else if (v == consulta7) {
            Intent intent = new Intent(MainActivity.this, ParquesYContenedoresEnLaZonaDeLaMejostilla.class);
            startActivity(intent);
        } else if (v == consulta8) {
            Intent intent = new Intent(MainActivity.this, ContenedoresQueNoSonDeVidrioActivity.class);
            startActivity(intent);
        }
    }
}
