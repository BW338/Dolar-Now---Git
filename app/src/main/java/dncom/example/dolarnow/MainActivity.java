package dncom.example.dolarnow;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.dolarnow.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    TextView dn, datodb, datodn, datoda, datodm, datodl,sonof;
    TextView dnc, dac, dbc, dlc, dmc, dmv;
    TextView dnv, dav, dbv, dlv,dlt;
    TextView tituloa, titulob, titulo;
    TextView hora;
    String horatx, flechadb, flechadn, flechada, flechadm, flechadl;
    String dnctx, dnvtx, davtx, dbctx, dbvtx, dlctx, dlvtx, dmctx, dmvtx,dltjtx, s_on_off;
    Button enter, button;
    Bitmap bitmap;
    ImageView mImageView;
    ActionBar ab;
    BottomNavigationView bottomNavigationView;
    SoundPool touch;
    int sound, on_off, back = 0;

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
    }

    @SuppressLint({"WrongViewCast", "WrongConstant"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        setTitle("");
        Objects.requireNonNull(getSupportActionBar()).hide();
      //  getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
      //  getSupportActionBar().setCustomView(R.layout.action_bar);


        touch = new SoundPool(1, AudioManager.STREAM_MUSIC, 1);
        sound = touch.load(this, R.raw.touch, 1);

        dn = findViewById(R.id.dn);

        dnc = findViewById(R.id.dnc);
        dnv = findViewById(R.id.dnv);

        dav = findViewById(R.id.dav);

        dlc = findViewById(R.id.dlc);
        dlv = findViewById(R.id.dlv);

        dbc = findViewById(R.id.dbc);
        dbv = findViewById(R.id.dbv);

        dmc = findViewById(R.id.dmc);
        dmv = findViewById(R.id.dmv);

        dlt = findViewById(R.id.dlt);

        enter = findViewById(R.id.enter);

        tituloa = findViewById(R.id.tituloa);
        titulob = findViewById(R.id.titulob);

       // datodb = findViewById(R.id.dbflecha);
       // datodn = findViewById(R.id.dnflecha);
       // datoda = findViewById(R.id.daflecha);
       // datodm = findViewById(R.id.dmflecha);
       // datodl = findViewById(R.id.dlflecha);

        titulo = findViewById(R.id.textView);

        Content cont = new Content();
        cont.execute();

        if (on_off % 2 == 1) {
            bottomNavigationView.getMenu().getItem(2).setIcon(R.drawable.sound_off);

        } else {
            bottomNavigationView.getMenu().getItem(2).setIcon(R.drawable.sound_on);
        }
/////////////////////////////////////


        Content cnt = new Content();
        cnt.execute();


        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (AppStatus.getInstance(MainActivity.this).isOnline()) {


                    new Content().execute();

                    if (dnctx != null) {

                        dnc.setText(dnctx);
                        dnv.setText(dnvtx);

                        dav.setText(davtx);

                        dbc.setText(dbctx);
                        dbv.setText(dbvtx);

                        dlc.setText(dlctx);
                        dlv.setText(dlvtx);

                        dmc.setText(dmctx);
                        dmv.setText(dmvtx);

                        dlt.setText(dltjtx);

                        tituloa.setText("DolarNow | ");
                        titulob.setText("valor al: " + horatx);



                        //   datodb.setText(flechadb);
                        //   datodn.setText(flechadn);
                        //   datoda.setText(flechada);
                        //   datodl.setText(flechadl);


                    }

                } else {
                    if (horatx != null) {

                        String ulth = hora.getText().toString();
                        String ulth1 = ulth.substring(24, 29);

                        Toast.makeText(MainActivity.this, "SIN CONEXION, Ultima cotizacion-" + ulth1 + " HS", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "SIN CONEXION A INTERNET", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        ////////////////////------CLICK AUTOMATICO----------------
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                enter.performClick();
                tituloa.setText("DolarNow | ");
                //    titulob.setText("valor al: " + horatx);
            }
        }, 1000);
        //--------------------------------------------------------------

        ////////////////////------CLICK AUTOMATICO----------------
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                enter.performClick();
                tituloa.setText("DolarNow | ");
            }
        }, 2600);
        //--------------------------------------------------------------

        do{
            enter.performClick();
        }while(dmctx != null);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @SuppressLint("ResourceType")
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.calculadora:
                                back++;
                                if (on_off % 2 == 0) {
                                    touch.play(sound, 1, 1, 1, 0, 0);
                                }
                                s_on_off = String.valueOf(on_off);
                                Intent calculadora = new Intent(MainActivity.this, calculadora.class);
                                calculadora.putExtra("dato", s_on_off);
                                startActivity(calculadora);
                                overridePendingTransition(R.anim.slide_left, R.anim.slide_right);

                                break;
                            case R.id.refresh:
                                if (on_off % 2 == 0) {
                                    touch.play(sound, 1, 1, 1, 0, 0);
                                }

                              //  if (AppStatus.getInstance(MainActivity.this).isOnline()) {

                                ConnectivityManager cm = (ConnectivityManager) MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
                                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                                if (activeNetwork != null) {
                                    // connected to the internet

                                    tituloa.setText("DolarNow | ");
                                    titulob.setText("valor al: " + horatx);

                                    new Content().execute();

                                    if (dnctx != null) {

                                        dnc.setText(dnctx);
                                        dnv.setText(dnvtx);

                                        dav.setText(davtx);

                                        dbc.setText(dbctx);
                                        dbv.setText(dbvtx);

                                        dlc.setText(dlctx);
                                        dlv.setText(dlvtx);

                                        dmc.setText(dmctx);
                                        dmv.setText(dmvtx);

                                        dlt.setText(dltjtx);


                                    } switch (activeNetwork.getType()) {
                                        case ConnectivityManager.TYPE_WIFI:
                                            // connected to wifi
                                            break;
                                        case ConnectivityManager.TYPE_MOBILE:
                                            Toast.makeText(MainActivity.this, "Datos Mobiles", Toast.LENGTH_SHORT).show();

                                            break;
                                        default:
                                            break;
                                    }

                                } else {
                                    if (horatx != null && hora != null) {

                                        String ulth = hora.getText().toString();
                                        String ulth1 = ulth.substring(24, 29);

                                        Toast.makeText(MainActivity.this, "SIN CONEXION, Ultima cotizacion-" + ulth1 + " HS", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(MainActivity.this, "SIN CONEXION A INTERNET", Toast.LENGTH_SHORT).show();
                                    }

                                }
                                break;
                            case R.id.sonido:



                                if (on_off % 2 == 0) {
                                    bottomNavigationView.getMenu().getItem(2).setIcon(R.drawable.sound_off);

                                    on_off++;

                                } else {

                                    touch.play(sound, 1, 1, 1, 0, 0);

                                    bottomNavigationView.getMenu().getItem(2).setIcon(R.drawable.sound_on);
                                    on_off++;

                                }

                                break;
                        }

                        return true;
                    }
                });


    } @Override
        protected void onStart(){
        super.onStart();
        enter.performClick();

        ////////////////////------CLICK AUTOMATICO----------------
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                enter.performClick();
                tituloa.setText("DolarNow | ");
            }
        }, 2600);
        //--------------------------------------------------------------
    }

    @Override
    protected void onResume() {
        super.onResume();

        enter.performClick();
        tituloa.setText("DolarNow | ");
        if (on_off % 2 == 0) {
            bottomNavigationView.getMenu().getItem(2).setIcon(R.drawable.sound_on);
        } else {
            bottomNavigationView.getMenu().getItem(2).setIcon(R.drawable.sound_off);

        }
        ////////////////////------CLICK AUTOMATICO----------------
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                enter.performClick();
                tituloa.setText("DolarNow | ");
            }
        }, 2600);
        //--------------------------------------------------------------

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // String s1_on_off = getIntent().getExtras().getString("dato", "0");
        // on_off = Integer.parseInt(s1_on_off);

        if (on_off % 2 == 1) {
            bottomNavigationView.getMenu().getItem(2).setIcon(R.drawable.sound_off);

        } else {
            touch.play(sound, 1, 1, 1, 0, 0);

            bottomNavigationView.getMenu().getItem(2).setIcon(R.drawable.sound_on);
        }

    }

    public class Content extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {

        }


        @Override
        protected Void doInBackground(Void... voids) {

            if (AppStatus.getInstance(MainActivity.this).isOnline()) {

                org.jsoup.nodes.Document documento1 = null;
                org.jsoup.nodes.Document documento2 = null;
                org.jsoup.nodes.Document documento3 = null;
                org.jsoup.nodes.Document documento4 = null;
                org.jsoup.nodes.Document documento5 = null;
                org.jsoup.nodes.Document documento6 = null;
                org.jsoup.nodes.Document documento6b = null;
                org.jsoup.nodes.Document documento6c = null;
                org.jsoup.nodes.Document documento7 = null;
                org.jsoup.nodes.Document documento8 = null;
                org.jsoup.nodes.Document documento9 = null;


                try {

                    documento1 = Jsoup.connect("https://www.cronista.com/MercadosOnline/moneda.html?id=ARS").get();
                    documento2 = Jsoup.connect("https://www.cronista.com/MercadosOnline/moneda.html?id=ARSSOL").get();
                    documento3 = Jsoup.connect("https://www.cronista.com/MercadosOnline/moneda.html?id=ARSB").get();
                    documento4 = Jsoup.connect("https://www.cronista.com/MercadosOnline/moneda.html?id=ARSCONT").get();
                    documento7 = Jsoup.connect("https://www.cronista.com/MercadosOnline/moneda.html?id=ARSMEP0").get();

                    documento5 = Jsoup.connect("https://www.ambito.com/contenidos/dolar.html").get();
                    documento6 = Jsoup.connect("https://www.cronista.com/MercadosOnline/dolar.html").get();
                    documento8 = Jsoup.connect("https://www.cronista.com/MercadosOnline/moneda.html?id=ARSMEP0").get();
                    documento9 = Jsoup.connect("https://www.cronista.com/MercadosOnline/moneda.html?id=ARSTAR").get();
                    documento6b = Jsoup.connect("https://www.cronista.com/MercadosOnline/moneda.html?id=ARSB").get();
                    documento6c = Jsoup.connect("https://news.agrofy.com.ar/economia-politica/dolar").get();

                } catch (IOException e) {
                    e.printStackTrace();
                }


                Elements dato1 = documento1.select("div.buy-value");
                Elements dato2 = documento1.select("div.sell-value");
                Elements datoflechabn = documento1.select("td.percentage.up");
                Elements dato4 = documento2.select("div.sell-value");


                Elements dato6 = documento3.select("div.buy-value");

                Elements dato7 = documento3.select("div.sell-value");

                Elements dato8 = documento4.select("div.buy-value");
                Elements dato9 = documento4.select("div.sell-value");


                Elements dato5 = documento2.select("td.date");

               // Elements fldb = documento6.select("span.percentage.up.col");
               // Elements fldn = documento6.select("td.percentage.up");
               // Elements fldm = documento8.select("td.percentage.down");
               // Elements fldl = documento6c.select("div.down-icon.arrow.tab-item.d-flex.var-item");


                Elements dato11 = documento7.select("div.buy-value");
                Elements dato12 = documento7.select("div.sell-value");

                Elements dato13 = documento9.select("div.sell-value");

                dnctx = dato1.text();
                dnvtx = dato2.text();

                davtx = dato4.text();

                if (documento6 != null) {
                    dbctx = dato6.text();
                }
                dbvtx = dato7.text();

                dlctx = dato8.text();
                dlvtx = dato9.text();

                if (dato11 != null) {
                    dmctx = dato7.text();
                } else {
                    dmctx = ("000");
                }
                if (dato12 != null) {
                    dmvtx = dato8.text();
                } else {
                    dmvtx = ("000");
                }

                ///// HORARIO DE COTIZACION
                if(dato5 != null) {
                    horatx = dato5.text().substring(13, 29);
                }else{
                    horatx = ("Error de conexion");
                }
                ////////////////////////////

                dltjtx = dato13.text();



              //  flechadb = fldb.text();
              //  if (fldb.isEmpty()) {
              //      Elements fldb1 = documento6b.select("span.percentage.down.col");
              //      flechadb = fldb1.text();
              //  }
            //    flechadn = fldn.text().substring(0, 6);
            //    flechada = fldn.text();
            //    flechadm = fldm.text();
            //    flechadl = fldl.text();

                return null;
            }
            return null;
        }

    }

}