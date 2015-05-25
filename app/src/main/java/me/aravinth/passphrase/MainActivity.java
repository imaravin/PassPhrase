package me.aravinth.passphrase;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Date;
import java.text.SimpleDateFormat;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;


public class MainActivity extends ActionBarActivity {
    Button crt,cpy;
    TextView textView;
    SeekBar seekBar;
    EditText text;
    int length=0;

    CheckBox sp,up,lp,np;

    DBAdapter mydb;
    AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("PassPhrase");
        crt=(Button)findViewById(R.id.crtbutton);
        cpy=(Button)findViewById(R.id.cpybutton);
        textView=(TextView)findViewById(R.id.textView);
        seekBar=(SeekBar)findViewById(R.id.seekBar);
         text=(EditText)findViewById(R.id.editText);
        lp=(CheckBox)findViewById(R.id.lower);
        up=(CheckBox)findViewById(R.id.upper);
        sp=(CheckBox)findViewById(R.id.spl);
        np=(CheckBox)findViewById(R.id.num);
        mAdView = (AdView) findViewById(R.id.ad_view);
       AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        mAdView.loadAd(adRequest);
//        Analytics.tracker.send(new HitBuilders.EventBuilder("ui", "open").setLabel("mainactiviy").build());






        crt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lp.isChecked()||up.isChecked()||np.isChecked()||sp.isChecked()) {
                    if (length != 0) {
                        openDB();
                        textView.setText(Passphrase.passPhrase(length, lp.isChecked(), up.isChecked(), np.isChecked(), sp.isChecked()));
                        Date d = new Date();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String check = dateFormat.format(d);
                        if (text.getText().toString().compareTo("") == 0)
                            mydb.insertRow("Created Anonymously", "Created On " + check, textView.getText().toString());
                        else
                            mydb.insertRow("Created for " + text.getText().toString(), "Created On " + check, textView.getText().toString());
                        mydb.close();
                        Toast.makeText(getApplicationContext(), "PassPhrase Created", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getApplicationContext(), "Can't create zero length PassPhrase", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(), "Check one option atleast ", Toast.LENGTH_SHORT).show();

            }


        });

        cpy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textView.getText().toString().compareTo("") != 0) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("label", textView.getText().toString());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(getApplicationContext(), "PassPhrase Copied", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(), "No phrase to copy ", Toast.LENGTH_SHORT).show();
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                length = progress;

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void openDB() {
        mydb=new DBAdapter(this);
        mydb.open();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.help) {
            Intent myIntent = new Intent(MainActivity.this, help.class);
            startActivity(myIntent);
        }
        else if(id==R.id.about)
        {
            Intent myIntent = new Intent(MainActivity.this, about.class);
            startActivity(myIntent);
        }
        else if(id==R.id.settings)
        {
            Intent myIntent = new Intent(MainActivity.this, yourpassphrases.class);
            startActivity(myIntent);
        }


        return super.onOptionsItemSelected(item);
    }



}
