package dncom.example.dolarnow;
import static java.lang.Integer.parseInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dolarnow.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.concurrent.Delayed;

public class calculadora extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner tipo;
    String dbctx, dbvtx, dnctx, dnvtx, dlctx, dlvtx, dmctx, dmvtx,dltjtx,
            datx, horatx, Ndbc, Ndbv, Nda, Ndnc, Ndnv, Ndmc, Ndmv, Ndlc, Ndlv,Ndtj, s1_on_off;
    TextView montodlbl, hora, valor, marg, musa, mres, minput, tituloa, titulob, titulo;
    EditText input;
    BottomNavigationView bottomNavigationView2;
    ImageView arg, usa;
    Integer chg = 0, sound, on_off = 0;
    SoundPool touch;
    ActionBar ab;
    CardView cv1;

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
    }



    @SuppressLint({"WrongConstant", "Range"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);


        getSupportActionBar().hide();
        //getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        //getSupportActionBar().setCustomView(R.layout.action_bar);


        mres = findViewById(R.id.textView11);
        minput = findViewById(R.id.textView5);
        arg = findViewById(R.id.arg);
        usa = findViewById(R.id.usa);
        marg = findViewById(R.id.textView9);
        musa = findViewById(R.id.textView10);
        input = findViewById(R.id.input);
        valor = findViewById(R.id.valor);
        tipo = findViewById(R.id.tipo);
        montodlbl = findViewById(R.id.montodlbl);
        bottomNavigationView2 = findViewById(R.id.bottom_navigation2);
        tituloa = findViewById(R.id.tituloa);
        titulob = findViewById(R.id.titulob);
        titulo = findViewById(R.id.titulo);
        cv1 = findViewById(R.id.cv1);

        cv1.setAlpha(100);

        touch = new SoundPool(1, AudioManager.STREAM_MUSIC, 1);
        sound = touch.load(this, R.raw.touch, 1);

        String[] cambio = {"Tipo de cambio", "Dolar Blue", "Dolar Nacion", "Dolar Ahorro", "Dolar Mep", "Dolar cdo. liqui","Dolar Tarjeta"};
        ArrayAdapter<String> adaptador1 = new ArrayAdapter<>(this, R.layout.spinner, cambio);
        tipo.setAdapter(adaptador1);

        tipo.setOnItemSelectedListener(this);

        new Content().execute();

        String s_on_off = getIntent().getExtras().getString("dato", "");
        on_off = Integer.parseInt(s_on_off);

        if (on_off % 2 == 1) {
            bottomNavigationView2.getMenu().getItem(2).setIcon(R.drawable.sound_off);

        } else {
            touch.play(sound, 1, 1, 1, 0, 0);

            bottomNavigationView2.getMenu().getItem(2).setIcon(R.drawable.sound_on);
        }


        ////////////////////------CLICK AUTOMATICO----------------
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               new Content().execute();
                tituloa.setText("DolarNow | ");
                //    titulob.setText("valor al: " + horatx);
            }
        }, 1000);
        //--------------------------------------------------------------


        bottomNavigationView2.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.back:

                                if (on_off % 2 == 0) {
                                    touch.play(sound, 1, 1, 1, 0, 0);
                                }
                                Intent main = new Intent(calculadora.this, MainActivity.class);
                                    overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                                    startActivity(main);


                                break;
                            case R.id.cotizar:

                                new Content().execute();

                                if (on_off % 2 == 0) {
                                    touch.play(sound, 1, 1, 1, 0, 0);
                                }

                                if (AppStatus.getInstance(calculadora.this).isOnline()) {


                                    if (input.getText().toString().equals("")) {
                                        Toast.makeText(calculadora.this, "Ingrese importe a cotizar", Toast.LENGTH_SHORT).show();
                                        valor.setText("0,00");
                                    } else {

                                        tituloa.setText("DolarNow | ");
                                        titulob.setText("valor al: " + horatx);

                                        if (tipo.getSelectedItem() == "Tipo de cambio") {
                                            Toast.makeText(calculadora.this, "Selecciona tipo de cambio", Toast.LENGTH_SHORT).show();



                                        }if (tipo.getSelectedItem() == "Dolar Blue") {
                                            if (chg % 2 == 0) {
                                                double Ddbv = Double.parseDouble(Ndbv.replace(",", "."));
                                                int Ninput = parseInt(input.getText().toString());
                                                double Cdbv = (Ninput / Ddbv);
                                                double DRdbv = Double.parseDouble(String.valueOf(Cdbv));
                                                DecimalFormat df = new DecimalFormat("###,###.##");
                                                String DRdbvd = df.format(DRdbv);
                                                valor.setText(DRdbvd);

                                            } else {
                                                double Ddbc = Double.parseDouble(Ndbc.replace(",", "."));
                                                int Ninput = parseInt(input.getText().toString());
                                                double Cdbc = (Ninput * Ddbc);
                                                double DRdbc = Double.parseDouble(String.valueOf(Cdbc));
                                                DecimalFormat df = new DecimalFormat("###,###.##");
                                                String DRdbcd = df.format(DRdbc);
                                                valor.setText(DRdbcd);
                                            }

                                        }if (tipo.getSelectedItem() == "Dolar Ahorro") {

                                            if (chg % 2 == 0) {
                                                double Ddav = Double.parseDouble(Nda.replace(",", "."));
                                                int Ninput = parseInt(input.getText().toString());
                                                double Cdav = (Ninput / Ddav);
                                                double DRdav = Double.parseDouble(String.valueOf(Cdav));
                                                DecimalFormat df = new DecimalFormat("###,###.##");
                                                String DRdavd = df.format(DRdav);
                                                DRdavd.replace(".", ".");
                                                valor.setText(DRdavd);

                                            } else {
                                                double Ddav = Double.parseDouble(Nda.replace(",", "."));
                                                int Ninput = parseInt(input.getText().toString());
                                                double Cdav = (Ninput * Ddav);
                                                double DRdav = Double.parseDouble(String.valueOf(Cdav));
                                                DecimalFormat df = new DecimalFormat("###,###.##");
                                                String DRdavd = df.format(DRdav);
                                                valor.setText(DRdavd);


                                            }
                                        }
                                        if (tipo.getSelectedItem() == "Dolar Nacion") {
                                            if (chg % 2 == 0) {
                                                double Ddnv = Double.parseDouble(Ndnv.replace(",", "."));
                                                int Ninput = parseInt(input.getText().toString());
                                                double Cdnv = (Ninput / Ddnv);
                                                double DRdnv = Double.parseDouble(String.valueOf(Cdnv));
                                                DecimalFormat df = new DecimalFormat("###,###.##");
                                                String DRdnvd = df.format(DRdnv);
                                                valor.setText(DRdnvd);

                                            } else {
                                                double Ddnc = Double.parseDouble(Ndnc.replace(",", "."));
                                                int Ninput = parseInt(input.getText().toString());
                                                double Cdnc = (Ninput * Ddnc);
                                                double DRdnc = Double.parseDouble(String.valueOf(Cdnc));
                                                DecimalFormat df = new DecimalFormat("###,###.##");
                                                String DRdncd = df.format(DRdnc);
                                                valor.setText(DRdncd);

                                            }
                                        }
                                        if (tipo.getSelectedItem() == "Dolar Mep") {
                                            if (chg % 2 == 0) {
                                                double Ddmv = Double.parseDouble(Ndmv.replace(",", "."));
                                                int Ninput = parseInt(input.getText().toString());
                                                double Cdnv = (Ninput / Ddmv);
                                                double DRdmv = Double.parseDouble(String.valueOf(Cdnv));
                                                DecimalFormat df = new DecimalFormat("###,###.##");
                                                String DRdmvd = df.format(DRdmv);
                                                valor.setText(DRdmvd);
                                            } else {
                                                double Ddmc = Double.parseDouble(Ndmc.replace(",", "."));
                                                int Ninput = parseInt(input.getText().toString());
                                                double Cdnc = (Ninput * Ddmc);
                                                double DRdmc = Double.parseDouble(String.valueOf(Cdnc));
                                                DecimalFormat df = new DecimalFormat("###,###.##");
                                                String DRdmcd = df.format(DRdmc);
                                                valor.setText(DRdmcd);
                                            }
                                        }
                                        if (tipo.getSelectedItem() == "Dolar cdo. liqui") {

                                            if (chg % 2 == 0) {
                                                double Ddlv = Double.parseDouble(Ndlv.replace(",", "."));
                                                int Ninput = parseInt(input.getText().toString());
                                                double Cdlv = (Ninput / Ddlv);
                                                double DRdlv = Double.parseDouble(String.valueOf(Cdlv));
                                                DecimalFormat df = new DecimalFormat("###,###.##");
                                                String DRdlvd = df.format(DRdlv);
                                                valor.setText(DRdlvd);
                                            } else {
                                                double Ddlc = Double.parseDouble(Ndlc.replace(",", "."));
                                                int Ninput = parseInt(input.getText().toString());
                                                double Cdlc = (Ninput * Ddlc);
                                                double DRdlc = Double.parseDouble(String.valueOf(Cdlc));
                                                DecimalFormat df = new DecimalFormat("###,###.##");
                                                String DRdlcd = df.format(DRdlc);
                                                valor.setText(DRdlcd);

                                            }
                                        }if (tipo.getSelectedItem() == "Dolar Tarjeta") {

                                            if (chg % 2 == 0) {
                                                double Ddltj = Double.parseDouble(Ndtj.replace(",", "."));
                                                int Ninput = parseInt(input.getText().toString());
                                                double Cdltj = (Ninput / Ddltj);
                                                double DRdtj = Double.parseDouble(String.valueOf(Cdltj));
                                                DecimalFormat df = new DecimalFormat("###,###.##");
                                                String DRdltj = df.format(DRdtj);
                                                valor.setText(DRdltj);

                                            } else {
                                                double Ddltj = Double.parseDouble(Ndtj.replace(",", "."));
                                                int Ninput = parseInt(input.getText().toString());
                                                double Cdltj = (Ninput * Ddltj);
                                                double DRdtj = Double.parseDouble(String.valueOf(Cdltj));
                                                DecimalFormat df = new DecimalFormat("###,###.##");
                                                String DRdltj = df.format(DRdtj);
                                                valor.setText(DRdltj);
                                            }
                                        }
                                    }
                                } else {
                                    Toast.makeText(calculadora.this, "Sin conexion a internet", Toast.LENGTH_SHORT).show();
                                }
                                break;

                            case R.id.sonido:
                                if (on_off % 2 == 0) {
                                    bottomNavigationView2.getMenu().getItem(2).setIcon(R.drawable.sound_off);
                                    on_off++;

                                } else {
                                    touch.play(sound, 1, 1, 1, 0, 0);

                                    bottomNavigationView2.getMenu().getItem(2).setIcon(R.drawable.sound_on);
                                    on_off++;
                                }

                                break;


                        }
                        return true;
                    }
                });

        ////////////////////////////////////////
        input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {

                    new Content().execute();

                    if (on_off % 2 == 0) {
                        touch.play(sound, 1, 1, 1, 0, 0);
                    }

                    if (AppStatus.getInstance(calculadora.this).isOnline()) {
                        tituloa.setText("DolarNow | ");
                        titulob.setText("valor al: " + horatx);

                        if (input.getText().toString().equals("")) {
                            Toast.makeText(calculadora.this, "Ingrese importe a cotizar", Toast.LENGTH_SHORT).show();
                            valor.setText("0,00");
                        } else {

                            if (tipo.getSelectedItem() == "Tipo de cambio") {
                                Toast.makeText(calculadora.this, "Selecciona tipo de cambio", Toast.LENGTH_SHORT).show();
                            }
                            if (tipo.getSelectedItem() == "Dolar Blue") {

                                if (chg % 2 == 0) {
                                    double Ddbv = Double.parseDouble(Ndbv.replace(",", "."));
                                    int Ninput = parseInt(input.getText().toString());
                                    double Cdbv = (Ninput / Ddbv);
                                    double DRdbv = Double.parseDouble(String.valueOf(Cdbv));
                                    DecimalFormat df = new DecimalFormat("###,###.##");
                                    String DRdbvd = df.format(DRdbv);
                                    valor.setText(DRdbvd);

                                } else {
                                    double Ddbc = Double.parseDouble(Ndbc.replace(",", "."));
                                    int Ninput = parseInt(input.getText().toString());
                                    double Cdbc = (Ninput * Ddbc);
                                    double DRdbc = Double.parseDouble(String.valueOf(Cdbc));
                                    DecimalFormat df = new DecimalFormat("###,###.##");
                                    String DRdbcd = df.format(DRdbc);
                                    valor.setText(DRdbcd);
                                }

                            }
                            if (tipo.getSelectedItem() == "Dolar Ahorro") {

                                if (chg % 2 == 0) {
                                    double Ddav = Double.parseDouble(Nda.replace(",", "."));
                                    int Ninput = parseInt(input.getText().toString());
                                    double Cdav = (Ninput / Ddav);
                                    double DRdav = Double.parseDouble(String.valueOf(Cdav));
                                    DecimalFormat df = new DecimalFormat("###,###.##");
                                    String DRdavd = df.format(DRdav);
                                    DRdavd.replace(".", ".");
                                    valor.setText(DRdavd);

                                } else {
                                    double Ddav = Double.parseDouble(Nda.replace(",", "."));
                                    int Ninput = parseInt(input.getText().toString());
                                    double Cdav = (Ninput * Ddav);
                                    double DRdav = Double.parseDouble(String.valueOf(Cdav));
                                    DecimalFormat df = new DecimalFormat("###,###.##");
                                    String DRdavd = df.format(DRdav);
                                    valor.setText(DRdavd);


                                }
                            }
                            if (tipo.getSelectedItem() == "Dolar Nacion") {
                                if (chg % 2 == 0) {
                                    double Ddnv = Double.parseDouble(Ndnv.replace(",", "."));
                                    int Ninput = parseInt(input.getText().toString());
                                    double Cdnv = (Ninput / Ddnv);
                                    double DRdnv = Double.parseDouble(String.valueOf(Cdnv));
                                    DecimalFormat df = new DecimalFormat("###,###.##");
                                    String DRdnvd = df.format(DRdnv);
                                    valor.setText(DRdnvd);

                                } else {
                                    double Ddnc = Double.parseDouble(Ndnc.replace(",", "."));
                                    int Ninput = parseInt(input.getText().toString());
                                    double Cdnc = (Ninput * Ddnc);
                                    double DRdnc = Double.parseDouble(String.valueOf(Cdnc));
                                    DecimalFormat df = new DecimalFormat("###,###.##");
                                    String DRdncd = df.format(DRdnc);
                                    valor.setText(DRdncd);

                                }
                            }
                            if (tipo.getSelectedItem() == "Dolar Mep") {
                                if (chg % 2 == 0) {
                                    double Ddmv = Double.parseDouble(Ndmv.replace(",", "."));
                                    int Ninput = parseInt(input.getText().toString());
                                    double Cdnv = (Ninput / Ddmv);
                                    double DRdmv = Double.parseDouble(String.valueOf(Cdnv));
                                    DecimalFormat df = new DecimalFormat("###,###.##");
                                    String DRdmvd = df.format(DRdmv);
                                    valor.setText(DRdmvd);
                                } else {
                                    double Ddmc = Double.parseDouble(Ndmc.replace(",", "."));
                                    int Ninput = parseInt(input.getText().toString());
                                    double Cdnc = (Ninput * Ddmc);
                                    double DRdmc = Double.parseDouble(String.valueOf(Cdnc));
                                    DecimalFormat df = new DecimalFormat("###,###.##");
                                    String DRdmcd = df.format(DRdmc);
                                    valor.setText(DRdmcd);
                                }
                            }
                            if (tipo.getSelectedItem() == "Dolar cdo. liqui") {

                                if (chg % 2 == 0) {
                                    double Ddlv = Double.parseDouble(Ndlv.replace(",", "."));
                                    int Ninput = parseInt(input.getText().toString());
                                    double Cdlv = (Ninput / Ddlv);
                                    double DRdlv = Double.parseDouble(String.valueOf(Cdlv));
                                    DecimalFormat df = new DecimalFormat("###,###.##");
                                    String DRdlvd = df.format(DRdlv);
                                    valor.setText(DRdlvd);
                                } else {
                                    double Ddlc = Double.parseDouble(Ndlc.replace(",", "."));
                                    int Ninput = parseInt(input.getText().toString());
                                    double Cdlc = (Ninput * Ddlc);
                                    double DRdlc = Double.parseDouble(String.valueOf(Cdlc));
                                    DecimalFormat df = new DecimalFormat("###,###.##");
                                    String DRdlcd = df.format(DRdlc);
                                    valor.setText(DRdlcd);
                                }
                            }  if (tipo.getSelectedItem() == "Dolar Tarjeta") {

                                if (chg % 2 == 0) {
                                    double Ddltj = Double.parseDouble(Ndtj.replace(",", "."));
                                    int Ninput = parseInt(input.getText().toString());
                                    double Cdltj = (Ninput / Ddltj);
                                    double DRdtj = Double.parseDouble(String.valueOf(Cdltj));
                                    DecimalFormat df = new DecimalFormat("###,###.##");
                                    String DRdltj = df.format(DRdtj);
                                    valor.setText(DRdltj);

                                } else {
                                    double Ddltj = Double.parseDouble(Ndtj.replace(",", "."));
                                    int Ninput = parseInt(input.getText().toString());
                                    double Cdltj = (Ninput * Ddltj);
                                    double DRdtj = Double.parseDouble(String.valueOf(Cdltj));
                                    DecimalFormat df = new DecimalFormat("###,###.##");
                                    String DRdltj = df.format(DRdtj);
                                    valor.setText(DRdltj);
                                }
                            }
                        }
                    } else {
                        Toast.makeText(calculadora.this, "Sin conexion a internet", Toast.LENGTH_SHORT).show();
                    }

                    return true;

                }
                return false;
            }
        });


        ////////////////////////////////

    //////////////////////////////////
} public class Content extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {

        }




        @Override
        protected Void doInBackground(Void... voids) {

            if (AppStatus.getInstance(calculadora.this).isOnline()) {

                org.jsoup.nodes.Document documento1 = null;
                org.jsoup.nodes.Document documento2 = null;
                org.jsoup.nodes.Document documento3 = null;
                org.jsoup.nodes.Document documento4 = null;
                org.jsoup.nodes.Document documento5 = null;
                org.jsoup.nodes.Document documento6 = null;
                org.jsoup.nodes.Document documento7 = null;

                org.jsoup.nodes.Document documento9 = null;



                try {

                    documento1 = Jsoup.connect("https://www.cronista.com/MercadosOnline/moneda.html?id=ARS").get();
                    documento2 = Jsoup.connect("https://www.cronista.com/MercadosOnline/moneda.html?id=ARSSOL").get();
                    documento3 = Jsoup.connect("https://www.cronista.com/MercadosOnline/moneda.html?id=ARSB").get();
                    documento4 = Jsoup.connect("https://www.cronista.com/MercadosOnline/moneda.html?id=ARSCONT").get();
                    documento5 = Jsoup.connect("https://www.ambito.com/contenidos/dolar.html").get();
                    documento6 = Jsoup.connect("https://www.cronista.com/MercadosOnline/dolar.html").get();
                    documento7 = Jsoup.connect("https://www.cronista.com/MercadosOnline/moneda.html?id=ARSMEP0").get();

                    documento9 = Jsoup.connect("https://www.cronista.com/MercadosOnline/moneda.html?id=ARSTAR").get();



                } catch (IOException e) {
                    e.printStackTrace();
                }


                    Elements dato1 = documento1.select("div.buy-value");
                    Elements dato2 = documento1.select("div.sell-value");


                Elements dato3 = documento3.select("div.buy-value");
                Elements dato4 = documento3.select("div.sell-value");

                Elements dato5 = documento4.select("div.buy-value");
                Elements dato6 = documento4.select("div.sell-value");

                Elements dato7 = documento7.select("div.buy-value");
                Elements dato8 = documento7.select("div.sell-value");

                Elements dato9 = documento2.select("div.sell-value");

                Elements dato10 = documento2.select("td.date");



                Elements dato13 = documento9.select("div.sell-value");


                ////DOLAR AHORRO
                if(dato9 != null) {
                    datx = dato9.text();
                    Nda = datx.substring(1,7);
                }else{
                    datx = ("000");
                }
              //////-----------

                ////DOLAR NACION
                if(dato1 != null) {
                    dnctx = dato1.text();
                    Ndnc = dnctx.substring(1,7);
                }else{
                    dnctx = ("000");

               } if(dato2 != null) {
                    dnvtx = dato2.text();
                    Ndnv = dnvtx.substring(1,7);
                }else{
                    dnvtx = ("000");
                }

                //////-----------

            /////DOLAR BLUE
                if((dato3 != null)) {
                    dbctx = dato3.text();
                    Ndbc = dbctx.substring(1,7);
                }else{
                    dbctx = ("000");
                }
                if(dato4 != null) {
                    dbvtx = dato4.text();
                    Ndbv = dbvtx.substring(1,7);

                }else{
                    dbvtx = ("000");
                }
            /////------------------

            /////DOLAR LIQUI
                if(dato5 != null) {
                    dlctx = dato5.text();
                    Ndlc = dlctx.substring(1,7);

                }else{
                    dlctx = ("000");
                }
               if(dato6 != null) {
                   dlvtx = dato6.text();
                   Ndlv = dlvtx.substring(1,7);

               }else{
                   dlvtx = ("000");
               }
               ///-----------------------

            /////DOLAR MEP
                if(dato7 != null) {
                    dmctx = dato7.text();
                    Ndmc = dmctx.substring(1,7);

                }else{
                    dmctx = ("000");
                }
                if(dato7 != null) {
                    dmvtx = dato8.text();
                    Ndmv = dmvtx.substring(1,7);

                }else {
                    dmvtx = ("000");
                }
            ///////-------------

             ///// HORARIO DE COTIZACION
               if(dato10 != null) {
                   horatx = dato10.text().substring(13, 29);
               }else{
                   horatx = ("Error de conexion");
               }

               dltjtx = dato13.text();
               Ndtj = dltjtx.substring(1,7);

            }
            return null;
        }
    }


    @SuppressLint("ShowToast")
    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {

            case 0:

                montodlbl.setText("$000,00 - $000,00");
                valor.setText("000,00");


                break;
            case 1:
                if (on_off % 2 == 0) {
                    touch.play(sound, 1, 1, 1, 0, 0);
                }
                if (AppStatus.getInstance(calculadora.this).isOnline()) {

                    new Content().execute();
                    montodlbl.setText(dbctx + " - " + dbvtx);

                    tituloa.setText("DolarNow | ");
                    titulob.setText("valor al: " + horatx);

                    if (!input.getText().toString().equals("")) {
                        if (chg % 2 == 0) {
                            double Ddbv = Double.parseDouble(Ndbv.replace(",", "."));
                            int Ninput = parseInt(input.getText().toString());
                            double Cdbv = (Ninput / Ddbv);
                            double DRdbv = Double.parseDouble(String.valueOf(Cdbv));
                            DecimalFormat df = new DecimalFormat("###,###.##");
                            String DRdbvd = df.format(DRdbv);
                            valor.setText(DRdbvd);

                        } else {
                            double Ddbc = Double.parseDouble(Ndbc.replace(",", "."));
                            int Ninput = parseInt(input.getText().toString());
                            double Cdbc = (Ninput * Ddbc);
                            double DRdbc = Double.parseDouble(String.valueOf(Cdbc));
                            DecimalFormat df = new DecimalFormat("###,###.##");
                            String DRdbcd = df.format(DRdbc);
                            valor.setText(DRdbcd);
                        }
                    }
                } else {
                    Toast.makeText(calculadora.this, "SIN CONEXION A INTERNET", Toast.LENGTH_SHORT).show();
                }
                break;

            case 2:
                if (on_off % 2 == 0) {
                    touch.play(sound, 1, 1, 1, 0, 0);
                }
                if (AppStatus.getInstance(calculadora.this).isOnline()) {
                    new Content().execute();
                    montodlbl.setText(dnctx + " - " + dnvtx);
                    tituloa.setText("DolarNow | ");
                    titulob.setText("valor al: " + horatx);

                    if (!input.getText().toString().equals("")) {
                        if (chg % 2 == 0) {
                            double Ddnv = Double.parseDouble(Ndnv.replace(",", "."));
                            int Ninput = parseInt(input.getText().toString());
                            double Cdnv = (Ninput / Ddnv);
                            double DRdnv = Double.parseDouble(String.valueOf(Cdnv));
                            DecimalFormat df = new DecimalFormat("###,###.##");
                            String DRdnvd = df.format(DRdnv);
                            valor.setText(DRdnvd);

                        } else {
                            double Ddnc = Double.parseDouble(Ndnc.replace(",", "."));
                            int Ninput = parseInt(input.getText().toString());
                            double Cdnc = (Ninput * Ddnc);
                            double DRdnc = Double.parseDouble(String.valueOf(Cdnc));
                            DecimalFormat df = new DecimalFormat("###,###.##");
                            String DRdncd = df.format(DRdnc);
                            valor.setText(DRdncd);
                        }
                    }
                } else {
                    Toast.makeText(calculadora.this, "SIN CONEXION A INTERNET", Toast.LENGTH_SHORT).show();
                }

                break;

            case 3:
                if (on_off % 2 == 0) {
                    touch.play(sound, 1, 1, 1, 0, 0);
                }
                if (AppStatus.getInstance(calculadora.this).isOnline()) {
                    new Content().execute();

                    montodlbl.setText(datx);
                    tituloa.setText("DolarNow | ");
                    titulob.setText("valor al: " + horatx);

                    if (!input.getText().toString().equals("")) {
                        if (chg % 2 == 0) {
                            double Ddav = Double.parseDouble(Nda.replace(",", "."));
                            int Ninput = parseInt(input.getText().toString());
                            double Cdav = (Ninput / Ddav);
                            double DRdav = Double.parseDouble(String.valueOf(Cdav));
                            DecimalFormat df2 = new DecimalFormat("###,###.##");
                            String DRdavd = df2.format(DRdav);
                            valor.setText(DRdavd);
                        } else {
                            double Ddav = Double.parseDouble(Nda.replace(",", "."));
                            int Ninput = parseInt(input.getText().toString());
                            double Cdav = (Ninput * Ddav);
                            double DRdav = Double.parseDouble(String.valueOf(Cdav));
                            DecimalFormat df2 = new DecimalFormat("###,###.##");
                            String DRdavd = df2.format(DRdav);
                            valor.setText(DRdavd);

                        }
                    }
                } else {
                    Toast.makeText(calculadora.this, "SIN CONEXION A INTERNET", Toast.LENGTH_SHORT).show();
                }
                break;

            case 4:
                touch.play(sound, 1, 1, 1, 0, 0);

                if (AppStatus.getInstance(calculadora.this).isOnline()) {
                    new Content().execute();

                    montodlbl.setText(dmctx + " - " + dmvtx);
                    tituloa.setText("DolarNow | ");
                    titulob.setText("valor al: " + horatx);

                    if (!input.getText().toString().equals("")) {
                        if (chg % 2 == 0) {
                            double Ddmv = Double.parseDouble(Ndmv.replace(",", "."));
                            int Ninput = parseInt(input.getText().toString());
                            double Cdnv = (Ninput / Ddmv);
                            double DRdmv = Double.parseDouble(String.valueOf(Cdnv));
                            DecimalFormat df = new DecimalFormat("###,###.##");
                            String DRdmvd = df.format(DRdmv);
                            valor.setText(DRdmvd);
                        } else {
                            double Ddmc = Double.parseDouble(Ndmc.replace(",", "."));
                            int Ninput = parseInt(input.getText().toString());
                            double Cdnc = (Ninput * Ddmc);
                            double DRdmc = Double.parseDouble(String.valueOf(Cdnc));
                            DecimalFormat df = new DecimalFormat("###,###.##");
                            String DRdmcd = df.format(DRdmc);
                            valor.setText(DRdmcd);
                        }
                    }
                } else {
                    Toast.makeText(calculadora.this, "SIN CONEXION A INTERNET", Toast.LENGTH_SHORT).show();
                }
                break;

            case 5:
                touch.play(sound, 1, 1, 1, 0, 0);
                if (AppStatus.getInstance(calculadora.this).isOnline()) {
                    new Content().execute();

                    montodlbl.setText(dlctx + " - " + dlvtx);
                    tituloa.setText("DolarNow | ");
                    titulob.setText("valor al: " + horatx);

                    if (!input.getText().toString().equals("")) {
                        if (chg % 2 == 0) {
                            double Ddlv = Double.parseDouble(Ndlv.replace(",", "."));
                            int Ninput = parseInt(input.getText().toString());
                            double Cdlv = (Ninput / Ddlv);
                            double DRdlv = Double.parseDouble(String.valueOf(Cdlv));
                            DecimalFormat df = new DecimalFormat("###,###.##");
                            String DRdlvd = df.format(DRdlv);
                            valor.setText(DRdlvd);
                        } else {
                            double Ddlc = Double.parseDouble(Ndlc.replace(",", "."));
                            int Ninput = parseInt(input.getText().toString());
                            double Cdlc = (Ninput * Ddlc);
                            double DRdlc = Double.parseDouble(String.valueOf(Cdlc));
                            DecimalFormat df = new DecimalFormat("###,###.##");
                            String DRdlcd = df.format(DRdlc);
                            valor.setText(DRdlcd);
                        }
                    }
                } else {
                    Toast.makeText(calculadora.this, "SIN CONEXION A INTERNET", Toast.LENGTH_SHORT).show();
                }
                break;

            case 6:
                touch.play(sound, 1, 1, 1, 0, 0);
                if (AppStatus.getInstance(calculadora.this).isOnline()) {
                    new Content().execute();

                    montodlbl.setText(dltjtx);
                    tituloa.setText("DolarNow | ");
                    titulob.setText("valor al: " + horatx);

                    if (!input.getText().toString().equals("")) {

                        if (chg % 2 == 0) {
                            double Ddltj = Double.parseDouble(Ndtj.replace(",", "."));
                            int Ninput = parseInt(input.getText().toString());
                            double Cdltj = (Ninput / Ddltj);
                            double DRdtj = Double.parseDouble(String.valueOf(Cdltj));
                            DecimalFormat df = new DecimalFormat("###,###.##");
                            String DRdltj = df.format(DRdtj);
                            valor.setText(DRdltj);

                        } else {
                            double Ddltj = Double.parseDouble(Ndtj.replace(",", "."));
                            int Ninput = parseInt(input.getText().toString());
                            double Cdltj = (Ninput * Ddltj);
                            double DRdtj = Double.parseDouble(String.valueOf(Cdltj));
                            DecimalFormat df = new DecimalFormat("###,###.##");
                            String DRdltj = df.format(DRdtj);
                            valor.setText(DRdltj);
                        }
                    }
                } else {
                    Toast.makeText(calculadora.this, "SIN CONEXION A INTERNET", Toast.LENGTH_SHORT).show();
                }

                break;
        }

    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    } public void change(View v) {
        if(on_off %2 == 0) {
            touch.play(sound, 1, 1, 1, 0, 0);
        }


        if(chg%2 == 0) {
            arg.setImageResource(R.drawable.usa);
            usa.setImageResource(R.drawable.arg);
            marg.setText("USA$");
            musa.setText("AR$");
            mres.setText("AR$");
            minput.setText("Importe: USD$");

            if(tipo.getSelectedItem().equals("Dolar Blue") &&  !input.getText().toString().equals("") && Ndbc !=null){
                double Ddbc = Double.parseDouble(Ndbc.replace(",", "."));
                int Ninput = parseInt(input.getText().toString());
                double Cdbc = (Ninput * Ddbc);
                double DRdbc = Double.parseDouble(String.valueOf(Cdbc));
                DecimalFormat df = new DecimalFormat("###,###.##");
                String DRdbcd = df.format(DRdbc);
                valor.setText(DRdbcd);

            }if(tipo.getSelectedItem().equals("Dolar Nacion")&&  !input.getText().toString().equals("")  && Ndnc !=null){
                double Ddnc = Double.parseDouble(Ndnc.replace(",", "."));
                int Ninput = parseInt(input.getText().toString());
                double Cdnc = (Ninput * Ddnc);
                double DRdnc = Double.parseDouble(String.valueOf(Cdnc));
                DecimalFormat df = new DecimalFormat("###,###.##");
                String DRdncd = df.format(DRdnc);
                valor.setText(DRdncd);

            }if(tipo.getSelectedItem().equals("Dolar Mep")&&  !input.getText().toString().equals("")  && Ndmc !=null){
                double Ddmc = Double.parseDouble(Ndmc.replace(",", "."));
                int Ninput = parseInt(input.getText().toString());
                double Cdnc = (Ninput * Ddmc);
                double DRdmc = Double.parseDouble(String.valueOf(Cdnc));
                DecimalFormat df = new DecimalFormat("###,###.##");
                String DRdmcd = df.format(DRdmc);
                valor.setText(DRdmcd);

            }if(tipo.getSelectedItem().equals("Dolar cdo. liqui")&&  !input.getText().toString().equals("") && Ndlc !=null){
                double Ddlc = Double.parseDouble(Ndlc.replace(",", "."));
                int Ninput = parseInt(input.getText().toString());
                double Cdlc = (Ninput * Ddlc);
                double DRdlc = Double.parseDouble(String.valueOf(Cdlc));
                DecimalFormat df = new DecimalFormat("###,###.##");
                String DRdlcd = df.format(DRdlc);
                valor.setText(DRdlcd);

            }if(tipo.getSelectedItem().equals("Dolar Ahorro")&&  !input.getText().toString().equals("")  && Nda !=null){
                double Ddac = Double.parseDouble(Nda.replace(",", "."));
                int Ninput = parseInt(input.getText().toString());
                double Cdac = (Ninput * Ddac);
                double DRdac = Double.parseDouble(String.valueOf(Cdac));
                DecimalFormat df = new DecimalFormat("###,###.##");
                String DRdacd = df.format(DRdac);
                valor.setText(DRdacd);

            }if(tipo.getSelectedItem().equals("Dolar Tarjeta") &&  !input.getText().toString().equals("")  && Ndtj !=null){
            double Ddltj = Double.parseDouble(Ndtj.replace(",", "."));
            int Ninput = parseInt(input.getText().toString());
            double Cdltj = (Ninput * Ddltj);
            double DRdtj = Double.parseDouble(String.valueOf(Cdltj));
            DecimalFormat df = new DecimalFormat("###,###.##");
            String DRdltj = df.format(DRdtj);
            valor.setText(DRdltj);
        }
            chg++;

        }else {
            arg.setImageResource(R.drawable.arg);
            usa.setImageResource(R.drawable.usa);
            marg.setText("AR$");
            musa.setText("USA$");
            mres.setText("USD$");
            minput.setText("Importe: AR$");

            if(tipo.getSelectedItem().equals("Dolar Blue") &&  !input.getText().toString().equals("")  && Ndbv !=null) {
                double Ddbv = Double.parseDouble(Ndbv.replace(",", "."));
                int Ninput = parseInt(input.getText().toString());
                double Cdbv = (Ninput / Ddbv);
                double DRdbv = Double.parseDouble(String.valueOf(Cdbv));
                DecimalFormat df = new DecimalFormat("###,###.##");
                String DRdbvd = df.format(DRdbv);
                valor.setText(DRdbvd);

            }if(tipo.getSelectedItem().equals("Dolar Nacion") &&  !input.getText().toString().equals("")  && Ndnv !=null) {
                double Ddnv = Double.parseDouble(Ndnv.replace(",", "."));
                int Ninput = parseInt(input.getText().toString());
                double Cdnv = (Ninput / Ddnv);
                double DRdnv = Double.parseDouble(String.valueOf(Cdnv));
                DecimalFormat df = new DecimalFormat("###,###.##");
                String DRdnvd = df.format(DRdnv);
                valor.setText(DRdnvd);

            }if(tipo.getSelectedItem().equals("Dolar Mep") &&  !input.getText().toString().equals("")  && Ndmv !=null) {
                double Ddmv = Double.parseDouble(Ndmv.replace(",", "."));
                int Ninput = parseInt(input.getText().toString());
                double Cdnv = (Ninput / Ddmv);
                double DRdmv = Double.parseDouble(String.valueOf(Cdnv));
                DecimalFormat df = new DecimalFormat("###,###.##");
                String DRdmvd = df.format(DRdmv);
                valor.setText(DRdmvd);

            }if(tipo.getSelectedItem().equals("Dolar cdo. liqui") &&  !input.getText().toString().equals("")  && Ndlv !=null) {
                double Ddlv = Double.parseDouble(Ndlv.replace(",", "."));
                int Ninput = parseInt(input.getText().toString());
                double Cdlv = (Ninput / Ddlv);
                double DRdlv = Double.parseDouble(String.valueOf(Cdlv));
                DecimalFormat df = new DecimalFormat("###,###.##");
                String DRdlvd = df.format(DRdlv);
                valor.setText(DRdlvd);

            }if(tipo.getSelectedItem().equals("Dolar Ahorro") &&  !input.getText().toString().equals("")  && Nda !=null){
                double Ddac = Double.parseDouble(Nda.replace(",", "."));
                int Ninput = parseInt(input.getText().toString());
                double Cdac = (Ninput / Ddac);
                double DRdac = Double.parseDouble(String.valueOf(Cdac));
                DecimalFormat df = new DecimalFormat("###,###.##");
                String DRdacd = df.format(DRdac);
                valor.setText(DRdacd);

            }if(tipo.getSelectedItem().equals("Dolar Tarjeta") &&  !input.getText().toString().equals("")  && Ndtj !=null){
                double Ddltj = Double.parseDouble(Ndtj.replace(",", "."));
                int Ninput = parseInt(input.getText().toString());
                double Cdltj = (Ninput / Ddltj);
                double DRdtj = Double.parseDouble(String.valueOf(Cdltj));
                DecimalFormat df = new DecimalFormat("###,###.##");
                String DRdltj = df.format(DRdtj);
                valor.setText(DRdltj);
            }
            chg++;

        }
    }
}