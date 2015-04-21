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


public class MainActivity extends ActionBarActivity {
    Button crt,cpy;
    TextView textView;
    SeekBar seekBar;
    EditText text;
    int min=6;
    int max=25;
    int length=4;
    RadioButton a,n,an;
    CheckBox sp;
    RadioGroup grp;
    DataBase myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        crt=(Button)findViewById(R.id.crtbutton);
        cpy=(Button)findViewById(R.id.cpybutton);
        textView=(TextView)findViewById(R.id.textView);
        seekBar=(SeekBar)findViewById(R.id.seekBar);
        text=(EditText)findViewById(R.id.textarea);
        a=(RadioButton)findViewById(R.id.alpha);
        an=(RadioButton)findViewById(R.id.alphanum);
        n=(RadioButton)findViewById(R.id.numeral);
        sp=(CheckBox)findViewById(R.id.checkbox);
        grp=(RadioGroup)findViewById(R.id.group);
        crt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(a.isChecked())
           textView.setText(RandomStringUtils.randomAlphabetic(length));
                if(an.isChecked())
                    textView.setText(RandomStringUtils.randomAlphanumeric(length));
               if(n.isChecked())
                   textView.setText(RandomStringUtils.randomNumeric(length));
                if(text.getText().toString().compareTo("")!=0)
                {
                   myDb =new DataBase(getApplicationContext());
                   myDb.open();
                   myDb.insertRow(text.getText().toString(),textView.getText().toString());
                    Toast.makeText(getApplicationContext(),"PassPhrase Saved",Toast.LENGTH_SHORT).show();
                    text.setText("");
                }
                else
                    Toast.makeText(getApplicationContext(),"PassPhrase Created Enter the site name to save",Toast.LENGTH_SHORT).show();

            }
        });
        cpy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textView.getText().toString().compareTo("")!=0) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("label", textView.getText().toString());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(getApplicationContext(), "PassPhrase Copied", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(), "No phrase to copy ", Toast.LENGTH_SHORT).show();
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress<min)
                    length=progress+min;
                else if((progress+length)>max)
                    length=max;
                else
                    length=min+progress;

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
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
        else if(id==R.id.old)
        {
           Intent myIntent = new Intent(MainActivity.this, list.class);
            startActivity(myIntent);
        }

        return super.onOptionsItemSelected(item);
    }
}
