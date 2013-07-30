package ch.sebastienzurfluh.swissmuseum.panneauinteractif.control;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import ch.sebastienzurfluh.swissmuseum.panneauinteractif.R;
import ch.sebastienzurfluh.swissmuseum.panneauinteractif.model.DataProviderContract;

public class PageActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {
	
	
	/////
	// All of those should be imported directly form the ContentProvider project
	/////
	private static final String AUTHORITY = "ch.sebastienzurfluh.swissmuseumguides.contentprovider";
	private static final String CONTENT_URI_ROOT = "content://" + AUTHORITY + "/";
	private static final String PAGES_GET = "pages/";

    private final String appRelativeDir = "Android/data/ch.sebastienzurfluh.swissmuseumguides/files/";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_page);

		// now we have to populate the grid. this has to be done programmatically but is quite easy
		// we only need to request an adapter to the content provider, then to feed the grid with it

		// Give an empty cursor to the view
		// Get the data from the model
		// Update the cursor, then update the view

		getLoaderManager().initLoader(0, getIntent().getExtras(), this);
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
				Uri.parse(CONTENT_URI_ROOT + PAGES_GET + args.getString("pageId")),
                null,
				null,
				null,
				null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        if (!(cursor.moveToFirst()) || cursor.getCount() ==0) {
            System.out.println("MainActivity: onLoadFinished: CURSOR IS EMPTY!!!");
        }

        String title = cursor.getString(cursor.getColumnIndex(
                DataProviderContract.MenusContract.COLUMN_NAME_TITLE));
        String description = cursor.getString(cursor.getColumnIndex(
                DataProviderContract.PagesContract.COLUMN_NAME_CONTENT));

        String imgRelativePath = cursor.getString(cursor.getColumnIndex(
                DataProviderContract.MenusContract.COLUMN_NAME_IMG_URL));

        File file = new File(android.os.Environment.getExternalStorageDirectory(),
                appRelativeDir + imgRelativePath);

        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

        ImageView imageView = (ImageView) findViewById(R.id.page_image);
        imageView.setImageBitmap(bitmap);

        TextView titleView = (TextView) findViewById(R.id.page_title);
        titleView.setText(title);

        TextView descriptionView = (TextView) findViewById(R.id.page_content);
        descriptionView.setText(description);
    }

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// nothing to do here?
	}

}
