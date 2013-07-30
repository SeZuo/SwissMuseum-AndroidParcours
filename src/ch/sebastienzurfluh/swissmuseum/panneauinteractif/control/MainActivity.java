package ch.sebastienzurfluh.swissmuseum.panneauinteractif.control;

import ch.sebastienzurfluh.swissmuseum.panneauinteractif.R;
import ch.sebastienzurfluh.swissmuseum.panneauinteractif.model.CardCursorAdapter;
import ch.sebastienzurfluh.swissmuseum.panneauinteractif.model.DataProviderContract.*;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {
	
	
	/////
	// All of those should be imported directly form the ContentProvider project
	/////
	private static final String AUTHORITY = "ch.sebastienzurfluh.swissmuseumguides.contentprovider";
	private static final String CONTENT_URI_ROOT = "content://" + AUTHORITY + "/";
	private static final String MENUS_LISTALLPAGEMENUS = "menus/listAll";


	private CardCursorAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// now we have to populate the grid. this has to be done programmatically but is quite easy
		// we only need to request an adapter to the content provider, then to feed the grid with it

		// Give an empty cursor to the view
		// Get the data from the model
		// Update the cursor, then update the view

		getLoaderManager().initLoader(0, null, this);

		// add a cursor adapter to the view, it will be updated with the right content when it is
		// available (see onLoadFinished)
		adapter = new CardCursorAdapter(
				this,
				null
				);

		ListView listView = (ListView) findViewById(R.id.maListe);
		listView.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return new CursorLoader(
				this,
				Uri.parse(CONTENT_URI_ROOT + MENUS_LISTALLPAGEMENUS),
                null,
				null,
				null,
				null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        if (!(cursor.moveToFirst()) || cursor.getCount() ==0){
            System.out.println("MainActivity: onLoadFinished: CURSOR IS EMPTY!!!");
        }

		adapter.changeCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		adapter.changeCursor(null);
	}

}
