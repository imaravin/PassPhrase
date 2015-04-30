package me.aravinth.passphrase;


import android.app.ListActivity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class yourpassphrases extends ActionBarActivity  {
    DBAdapter mydb;
    AdView myadview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yourpassphrases);
        final ListView listview = (ListView) findViewById(R.id.listView);
        setTitle("Your PassPhrases");
        openDB();
        populateListViewFromDB();
     //   mydb.close();
        registerForContextMenu(listview);
        myadview = (AdView) findViewById(R.id.view);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        myadview.loadAd(adRequest);
    }


    /** This will be invoked when a menu item is selected */



    private void openDB() {
        mydb = new DBAdapter(this);
        mydb.open();
    }

    private void populateListViewFromDB() {
        Cursor cursor = mydb.getAllRows();

        // Allow activity to manage lifetime of the cursor.
        // DEPRECATED! Runs on the UI thread, OK for small/short queries.
        startManagingCursor(cursor);

        // Setup mapping from cursor to view fields:
        String[] fromFieldNames = new String[]
                {DBAdapter.KEY_DATE, DBAdapter.KEY_TASK,DBAdapter.KEY_NAME};
        int[] toViewIDs=new int[]{R.id.id_view,R.id.key_view,R.id.usr};
        // Create adapter to may columns of the DB onto elemesnt in the UI.
        SimpleCursorAdapter myCursorAdapter =
                new SimpleCursorAdapter(
                        getBaseContext(),		// Context
                        R.layout.item_layout,	// Row layout template
                        cursor,					// cursor (set of DB records to map)
                        fromFieldNames,			// DB Column names
                        toViewIDs,
                        0				// View IDs to put information in
                );

        // Set the adapter for the list view
        final ListView myList = (ListView) findViewById(R.id.listView);
        myList.setAdapter(myCursorAdapter);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                 mydb.open();
                Cursor sam=mydb.getRow(id);
                String x=sam.getString(sam.getColumnIndex(DBAdapter.KEY_DATE));
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", x);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(),"Content copied to Clipboard", Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_yourpassphrases, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
         return true;


    }
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        // super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.actions, menu);
    }
    public boolean onContextItemSelected(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch(item.getItemId())
        {
            case R.id.flt_del:
                mydb.open();
                mydb.deleteRow(info.id);
              //  mydb.close();
                populateListViewFromDB();
                Toast.makeText(this, "PassPhrase removed"  , Toast.LENGTH_SHORT).show();
                break;
            case R.id.flt_cpy:
                mydb.open();
                Cursor sam=mydb.getRow(info.id);
                String x=sam.getString(sam.getColumnIndex(DBAdapter.KEY_DATE));
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", x);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(this, "Content copied to Clipboard" , Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }


}
