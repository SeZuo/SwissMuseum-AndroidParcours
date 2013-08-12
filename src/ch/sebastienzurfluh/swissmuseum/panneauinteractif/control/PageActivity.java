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
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.LinkedList;

import ch.sebastienzurfluh.swissmuseum.panneauinteractif.R;
import ch.sebastienzurfluh.swissmuseum.panneauinteractif.control.page.PageParser;
import ch.sebastienzurfluh.swissmuseum.panneauinteractif.control.page.PageToken;
import ch.sebastienzurfluh.swissmuseum.panneauinteractif.model.DataProviderContract;
import ch.sebastienzurfluh.swissmuseum.panneauinteractif.model.GalleryCursorAdapter;
import ch.sebastienzurfluh.swissmuseum.panneauinteractif.view.ExpandableHeightGridView;

public class PageActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {
	
	
	/////
	// All of those should be imported directly form the ContentProvider project
	/////
	private static final String AUTHORITY = "ch.sebastienzurfluh.swissmuseumguides.contentprovider";
	private static final String CONTENT_URI_ROOT = "content://" + AUTHORITY + "/";
	private static final String PAGES_GET = "pages/";

    private final String appRelativeDir = "Android/data/ch.sebastienzurfluh.swissmuseumguides/files/";

    private int loaderId = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_page);

		// now we have to populate the grid. this has to be done programmatically but is quite easy
		// we only need to request an adapter to the content provider, then to feed the grid with it

		// Give an empty cursor to the view
		// Get the data from the model
		// Update the cursor, then update the view

		getLoaderManager().initLoader(loaderId++, getIntent().getExtras(), this);
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

        GalleryCursorAdapter adapter = null;
        LinkedList<PageToken> tokenisedContent = PageParser.parse(description);
        descriptionView.setText("");
        for (PageToken pageToken : tokenisedContent) {
            if (pageToken.isResource()) {
                // determine what type or resource this is
                // act according to the resource type

                // let's say it's an image

                // launch an async task to get the data from the db
                // when the data comes back, add it to the view.
                // this is the same as a resource widget in the original code
                // except the element is not added to the view directly.

                ExpandableHeightGridView galleryView =
                        (ExpandableHeightGridView) findViewById(R.id.resource_gallery);

                if (adapter==null)
                    adapter = new GalleryCursorAdapter(this);
                galleryView.setAdapter(adapter);
                galleryView.setExpanded(true);


                Bundle bundleArgs = new Bundle();
                bundleArgs.putInt("resourceId", pageToken.getResourceReference());

                getLoaderManager().initLoader(loaderId++,
                        bundleArgs,
                        new ResourceLoaderManager(this, adapter));


                // We should add some text link right here in the text as reference. But only if
                // there is more text!
            } else if (pageToken.isText()) {
                // add the text to the content.
                descriptionView.append(pageToken.getText());
            }
        }
    }

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// nothing to do here?
	}

}
