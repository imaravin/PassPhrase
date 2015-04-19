package me.aravinth.passphrase;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class list extends ActionBarActivity {



    DataBase myDb;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        openDB();
        populateListViewFromDB();
        registerListClickCallback();
    }

    private void openDB() {
        myDb = new DataBase(this);
        myDb.open();
    }

    private void populateListViewFromDB() {
        Cursor cursor = myDb.getAllRows();

        // Allow activity to manage lifetime of the cursor.
        // DEPRECATED! Runs on the UI thread, OK for small/short queries.
        startManagingCursor(cursor);

        // Setup mapping from cursor to view fields:
        String[] fromFieldNames = new String[]
                {DataBase.KEY_SITE, DataBase.KEY_PASS};
        int[] toViewIDs = new int[]
                {R.id.site_name,     R.id.pass_name, };

        // Create adapter to may columns of the DB onto elemesnt in the UI.
        SimpleCursorAdapter myCursorAdapter =
                new SimpleCursorAdapter(
                        this,		// Context
                        R.layout.passphrase,	// Row layout template
                        cursor,					// cursor (set of DB records to map)
                        fromFieldNames,			// DB Column names
                        toViewIDs				// View IDs to put information in
                );

        // Set the adapter for the list view
        ListView myList = (ListView) findViewById(R.id.listView );
        myList.setAdapter(myCursorAdapter);

    }

    private void registerListClickCallback() {
        ListView myList = (ListView) findViewById(R.id.listView);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked,
                                    int position, long idInDB) {

               // updateItemForId(idInDB);
                displayToastForId(idInDB);
            }
        });
    }

    private void displayToastForId(long idInDB) {
        Cursor cursor = myDb.getRow(idInDB);
        if (cursor.moveToFirst()) {
            long idDB = cursor.getLong(DataBase.COL_ROWID);
            String name = cursor.getString(DataBase.COL_SITE);
            String pass= cursor.getString(DataBase.COL_PASS);


            String message =  "Site: " + name + "\n"
                    + "PassPhrase: " + pass+ "\n";

            Toast.makeText(list.this, message, Toast.LENGTH_LONG).show();
        }
        cursor.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }
}
