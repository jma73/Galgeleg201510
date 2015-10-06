package dk.jpeace.jan.galgeleg;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ForsideActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonStartSpil;
    private Button buttonHentOrdWeb;
    public static final Galgelogik galgelogik = new Galgelogik();
    private TextView textViewDateTime;
    private Button buttonIndstillinger;

    FragmentSpilslut fragmentSpilslut = new FragmentSpilslut();
    private FragmentSpillet fragmentSpillet = new FragmentSpillet();
    private FragmentOrdliste fragmentOrdliste = new FragmentOrdliste();
    private Button buttonForsideLuk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_forside);
        setContentView(R.layout.activity_forside2);

        SimpleDateFormat df = new SimpleDateFormat("dd MM yyyy, HH:mm");
        String date = df.format(Calendar.getInstance().getTime());
        textViewDateTime = (TextView)findViewById(R.id.TextViewHeaderStatus);
        String version = "lek5.0";
        textViewDateTime.setText(version + ", " + date);

        buttonIndstillinger = (Button)findViewById(R.id.buttonForside);
        buttonStartSpil = (Button)findViewById(R.id.buttonStartSpil);
        buttonStartSpil.setOnClickListener(this);
        buttonIndstillinger.setOnClickListener(this);
        buttonForsideLuk = (Button)findViewById(R.id.buttonForsideLuk);
        buttonForsideLuk.setOnClickListener(this);

//        buttonHentOrdWeb = (Button)findViewById(R.id.buttonHentOrdWeb);
//        buttonHentOrdWeb.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_forside, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Log.d("forsideAct", "Menu :: Du trykkede på id="+id);

        if (id == R.id.action_ordliste) {
            Log.d("forsideAct", "Valget svarer til R.id.action_ordliste ...Du trykkede på id=" + id);

            ShowHideIndstillingerFragment();

        }

        if (id == R.id.action_hendordfraweb) {
            Log.d("forsideAct", "Valget svarer til R.id.action_hendordfraweb ...Du trykkede på id=" + id);

            hentOrdFraWeb();
        }


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Log.d("ForsideActivity", "in onClick!");
        int id = v.getId();
        Log.d("ForsideActivity", "in onClick! view id=" + id);
        String IdAsString = GenerelUtilities.getResourceName(v);
        Log.d("ForsideActivity", "in onClick! view id=" + IdAsString);

        if(v == buttonStartSpil)
            startSpilFragmet();
            // startSpilAct();
        if(v == buttonHentOrdWeb)
        {
            //String muligeord = hentOrdFraWeb();
            hentOrdFraWeb();

        }
        if(v == buttonIndstillinger)
        {
            ShowHideIndstillingerFragment();
        }

        if(v==buttonForsideLuk)
        {
            Log.d("ForsideActivity", "in onClick! . buttonForsideLuk.");

            getFragmentManager().beginTransaction()
                    .remove(fragmentOrdliste)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                    .addToBackStack(null)
                    .commit();
        }
    }



    /*
    * Burde hedde noget med Ordliste. ListView Ordliste.
    *
     */
    private void ShowHideIndstillingerFragment() {
        if(fragmentOrdliste.isAdded()) {

            if (fragmentOrdliste.isHidden()) {

                Log.d("ForsideActivity", "fragmentOrdliste.isHidden true");

                getFragmentManager().beginTransaction()
                        .show(fragmentOrdliste)
                        // .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)

                        .hide(fragmentSpillet)
                        .addToBackStack(null)

                        .commit();
            } else {

                Log.d("ForsideActivity", "fragmentOrdliste.isHidden false");

                getFragmentManager().beginTransaction()
                        .hide(fragmentOrdliste)
                        .show(fragmentSpillet)

                        //.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out)


                        .addToBackStack(null)
                        .commit();
            }
        }
        else {

            if(fragmentSpillet.isHidden())
            {
                Log.d("ForsideActivity", "fragmentSpillet.isHidden true");
            }
            else
            {
                Log.d("ForsideActivity", "fragmentSpillet.isHidden false");
            }

            //
            getFragmentManager().beginTransaction()
                    .add(R.id.fragmentindhold, fragmentOrdliste)
                    //.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)       // note jan: umiddelbart kan jeg ikke se nogen forskel...
                    //.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .hide(fragmentSpillet)  // todo jan - just added... 5/10-2015.
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void startSpilFragmet() {

        if(fragmentSpillet.isAdded())
        {
            getFragmentManager().beginTransaction()
                    .hide(fragmentOrdliste)
                    .show(fragmentSpillet)
                    .addToBackStack(null)
                    .commit();
            return;
        }
        if(fragmentOrdliste.isAdded())
        {
            getFragmentManager().beginTransaction()
                    .hide(fragmentOrdliste)
                    //.show(fragmentSpillet)
                    //.addToBackStack(null)
                    .commit();
        }

        Bundle argumenter = new Bundle(); // Overfør data til fragmentet
        argumenter.putString("velkomst", "\n\nGod fornøjelse med Galgeleg!");
        fragmentSpillet.setArguments(argumenter);

        getFragmentManager().beginTransaction()
                .replace(R.id.fragmentindhold, fragmentSpillet)
                // .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)   // tester med FragmentTransaction.
                //.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)

                .addToBackStack(null)
                .commit();
    }

    private void hentOrdFraWeb() {

        //String titler;

        // Lint: Warning:(97, 29) Unchecked call to 'execute(Params...)' as a member of raw type 'android.os.AsyncTask'
        AsyncTask execute = new AsyncTask() {
            @Override
            protected Object doInBackground(Object... arg0) {
                try {
                    //String rssdata = hentUrl("http://www.version2.dk/it-nyheder/rss");
                    //String titler = findTitler(rssdata);
                    galgelogik.hentOrdFraDr();
                    String titler = galgelogik.muligeOrd().toString();

                    return titler;
                } catch (Exception e) {
                    e.printStackTrace();
                    return e;
                }
            }

            @Override
            protected void onPostExecute(Object titler) {
                Toast.makeText(ForsideActivity.this, "resultat: \n" + titler, Toast.LENGTH_SHORT).show();
                setProgressBarIndeterminateVisibility(false);
            }
        }.execute();


//     catch (Exception e) {
//        e.printStackTrace();
//        textView.setText("Der skete en fejl:\n" + e);
//        setProgressBarIndeterminateVisibility(false);
//        }

    }

    private void startSpilAct() {
        Intent startSpilGalgelegIntent = new Intent(getApplicationContext(), GalgelejActivity.class);
        //startSpilGalgelegIntent.setAction(Intent.ACTION_SEND);
        startActivity(startSpilGalgelegIntent);

    }
}
