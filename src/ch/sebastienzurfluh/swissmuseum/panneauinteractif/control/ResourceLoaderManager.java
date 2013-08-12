package ch.sebastienzurfluh.swissmuseum.panneauinteractif.control;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.os.Bundle;

import ch.sebastienzurfluh.swissmuseum.panneauinteractif.model.GalleryCursorAdapter;

public class ResourceLoaderManager implements LoaderManager.LoaderCallbacks<Cursor> {
    /////
    // All of those should be imported directly form the ContentProvider project
    /////
    private static final String AUTHORITY = "ch.sebastienzurfluh.swissmuseumguides.contentprovider";
    private static final String CONTENT_URI_ROOT = "content://" + AUTHORITY + "/";
    private static final String RESOURCES_GET = "resources/";

    private final Context context;
    private final GalleryCursorAdapter galleryCursorAdapter;

    /**
     * @param context where we'll include the images
     * @param galleryCursorAdapter is a cursor pointing to the resource gallery
     */
    public ResourceLoaderManager(Context context, GalleryCursorAdapter galleryCursorAdapter) {
        this.context = context;
        this.galleryCursorAdapter = galleryCursorAdapter;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                context,
                Uri.parse(CONTENT_URI_ROOT + RESOURCES_GET + args.getInt("resourceId")),
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (!(cursor.moveToFirst()) || cursor.getCount()==0) {
            System.out.println("MainActivity: onLoadFinished: CURSOR IS EMPTY!!!");
        }

        Cursor currentCursor = galleryCursorAdapter.getCursor();
        Cursor galleryCursor =
                (currentCursor==null || !currentCursor.moveToFirst() || currentCursor.getCount()==0)
                ?
                cursor : new MergeCursor(new Cursor[]{currentCursor, cursor});

        galleryCursorAdapter.swapCursor(galleryCursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        galleryCursorAdapter.changeCursor(null);
    }
}
