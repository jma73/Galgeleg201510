package dk.jpeace.jan.galgeleg;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class GalgelejActivity extends Activity implements OnClickListener {

    public final static String EXTRA_MESSAGE = "dk.jpeace.jan.galgeleg.MESSAGE";
    public final static String ER_SPIL_VUNDET = "dk.jpeace.jan.galgeleg.ER_SPIL_VUNDET";
    public final static String SPILLE_TID = "dk.jpeace.jan.galgeleg.SPILLE_TID";

    //ForsideActivity.galgelogik ForsideActivity.galgelogik;

    private Button buttonSpil;
    //private TextView editviewTest;
    private ImageView imageView1;
    private Button buttonGæt;
    private EditText editTextBogstav;
    private TextView textView2Show;
    private TextView textViewDebug;
    private TextView textViewSynligtOrd;
    private TextView textViewBrugteBogst;
    private Button buttonGætOrdet;
    private EditText editTextGætOrdet;
    private TextView textViewFinishedStatus;

    Date startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galgelej);

        //editviewTest = (TextView)findViewById(R.id.textView);
        textView2Show = (TextView)findViewById(R.id.textViewOrdet);
        textViewDebug = (TextView)findViewById(R.id.textViewDebug);
        textViewSynligtOrd = (TextView)findViewById(R.id.textViewSynligtOrd);
        textViewBrugteBogst = (TextView)findViewById(R.id.textViewBrugteBogst);
        textViewFinishedStatus = (TextView)findViewById(R.id.textViewFinishedStatus);

        editTextBogstav = (EditText)findViewById(R.id.editTextBogstav);
        editTextGætOrdet = (EditText)findViewById(R.id.editTextGætOrdet);

        editTextBogstav.setError("du skal taste noget.");
        editTextBogstav.setOnClickListener(this);

        buttonGæt = (Button) findViewById(R.id.buttonGætBogstav);
        buttonSpil = (Button) findViewById(R.id.buttonSpil);
        buttonGætOrdet = (Button) findViewById(R.id.buttonGætOrdet);
        buttonGæt.setOnClickListener(this);
        buttonSpil.setOnClickListener(this);
        buttonGætOrdet.setOnClickListener(this);
        buttonGæt.setEnabled(false);

        imageView1 =  (ImageView)findViewById(R.id.imageViewet);
        imageView1.setImageResource(R.drawable.forkert6);
        //ForsideActivity.galgelogik = new ForsideActivity.galgelogik();
        textViewFinishedStatus.setText("Vælg start spil.");
        textViewFinishedStatus.setTextColor(Color.DKGRAY);

        // disse skal egentlig slet ikke være med...
        buttonGætOrdet.setVisibility(View.INVISIBLE);
        editTextGætOrdet.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_galgelej, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v== buttonSpil)
            StartSpil();
        if(v==buttonGæt)
            BogstavTest(editTextBogstav.getText());
        if(v==buttonGætOrdet)
            GætOrdet();

    }

    private void GætOrdet() {
        // denne skal egentlig ikke med i den endelige løsning...
        Editable gæt = editTextGætOrdet.getText();
        if(gæt.toString().equals(ForsideActivity.galgelogik.getOrdet()))
            Toast.makeText(this, "Juhuuu - du gættede rigtigt!!!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Neeej det er forkert!!! Ready to hang??? '" + gæt.toString() + "'", Toast.LENGTH_SHORT).show();

    }

    private void BogstavTest(Editable text) {
        String text1 = "Du tastede: " + text;
        //Toast.makeText(this, text1, Toast.LENGTH_SHORT).show();

        textViewDebug.setText(text1);

        if(text.length() != 1)
        {
            imageView1.setImageResource(R.drawable.forkert3);

            Log.d("jj", "det indtastede passer ikke. " + text1);
            Toast.makeText(this, "Fejl: Du skal taste præcis eet bogstav!", Toast.LENGTH_SHORT).show();

        }
        else
        {

            ForsideActivity.galgelogik.gætBogstav(text.toString());
            String syngligtOrd = ForsideActivity.galgelogik.getSynligtOrd();
            textViewSynligtOrd.setText(syngligtOrd);

            VisBrugteBogstaver();

            editTextBogstav.setText("");
        }

        OpdaterGalgeBillede();
        SpilStatus();

    }

    /**
     * Get a diff between two dates
     * @param date1 the oldest date
     * @param date2 the newest date
     * @param timeUnit the unit in which you want the diff
     * @return the diff value, in the provided unit
     */
    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    private void SpilStatus() {
        if(ForsideActivity.galgelogik.erSpilletSlut()) {

            Date spilSlut = new Date();
            long spilleTid = getDateDiff(startTime, spilSlut, TimeUnit.SECONDS);

            Intent spilletErSlutIntent = new Intent(getApplicationContext(), SpilSlutActivity.class);

            if(ForsideActivity.galgelogik.erSpilletTabt())
            {
                String statusText = "Spillet er tabt!!!";
                textViewFinishedStatus.setText(statusText);
                textViewFinishedStatus.setTextColor(Color.RED);

                Toast.makeText(this, statusText, Toast.LENGTH_LONG).show();
                Log.d("jj", "spillet er tabt!");
                buttonGæt.setEnabled(false);

                spilletErSlutIntent.putExtra(EXTRA_MESSAGE, statusText);
                spilletErSlutIntent.putExtra(ER_SPIL_VUNDET, "false");
                spilletErSlutIntent.putExtra(SPILLE_TID, spilleTid);
                startActivity(spilletErSlutIntent);
            }
            else if(ForsideActivity.galgelogik.erSpilletVundet())
            {
                String statusText = "Spillet er vundet!!!";
                textViewFinishedStatus.setText(statusText);
                textViewFinishedStatus.setTextColor(Color.GREEN);
                Toast.makeText(this, statusText, Toast.LENGTH_LONG).show();
                Log.d("jj", statusText);
                buttonGæt.setEnabled(false);

                // public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
                spilletErSlutIntent.putExtra(EXTRA_MESSAGE, statusText);
                spilletErSlutIntent.putExtra(ER_SPIL_VUNDET, "true");
                spilletErSlutIntent.putExtra(SPILLE_TID, spilleTid);

                //spilletErSlutIntent.setAction()
                startActivity(spilletErSlutIntent);
                this.finish();


            }
        }
    }


    private void OpdaterGalgeBillede()
    {
        int antalForkerte = ForsideActivity.galgelogik.getAntalForkerteBogstaver();
        Log.v("jj", (String.valueOf(antalForkerte)));
        switch (antalForkerte)
        {
            case 0:
                imageView1.setImageResource(R.drawable.galge);
                break;
            case 1:
                imageView1.setImageResource(R.drawable.forkert1);
                break;
            case 2:
                imageView1.setImageResource(R.drawable.forkert2);
                break;
            case 3:
                imageView1.setImageResource(R.drawable.forkert3);
                break;
            case 4:
                imageView1.setImageResource(R.drawable.forkert4);
                break;
            case 5:
                imageView1.setImageResource(R.drawable.forkert5);
                break;
            case 6:
                imageView1.setImageResource(R.drawable.forkert6);
                break;
            default:
                imageView1.setImageResource(R.drawable.forkert6);
        }
    }

    private void VisBrugteBogstaver() {
        String display = "";
        ArrayList<String> bb = ForsideActivity.galgelogik.getBrugteBogstaver();
        for (String ii:bb  ) {
           display += ii;
        }
        textViewBrugteBogst.setText(display);
    }

    private void StartSpil() {

        startTime = new Date();

        ForsideActivity.galgelogik.nulstil();   // når spillet starter
        String ordet = ForsideActivity.galgelogik.getOrdet();
        textView2Show.setText(ordet);
        Toast.makeText(this, "Spillet er startet!!!", Toast.LENGTH_LONG).show();

        imageView1.setImageResource(R.drawable.galge);
        buttonSpil.setText("Nyt spil");
        buttonGæt.setEnabled(true);
        textViewFinishedStatus.setText("Spillet er i gang.");

        textViewBrugteBogst.setText("");
        textViewSynligtOrd.setText(ForsideActivity.galgelogik.getSynligtOrd());
    }


}