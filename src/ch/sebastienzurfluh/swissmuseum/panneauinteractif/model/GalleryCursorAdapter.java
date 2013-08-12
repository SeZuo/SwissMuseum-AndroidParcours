package ch.sebastienzurfluh.swissmuseum.panneauinteractif.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.io.File;

import ch.sebastienzurfluh.swissmuseum.panneauinteractif.R;
import ch.sebastienzurfluh.swissmuseum.panneauinteractif.model.DataProviderContract.*;

/**
 * This is a {@code SimpleCursorAdapter} modified to replace urls with the actual image.
 */
public class GalleryCursorAdapter extends SimpleCursorAdapter {
    private final Context context;

    private final String appRelativeDir = "Android/data/ch.sebastienzurfluh.swissmuseumguides/files/";


    public static String[] fromColumns = {
            ResourcesContract.COLUMN_NAME_TITLE,
            ResourcesContract.COLUMN_NAME_DESCRIPTION
    };

    public static int[] toFields = new int[] {
            R.id.resource_title,
            R.id.resource_description
    };

    public GalleryCursorAdapter(Context context) {
        super(
                context,
                R.layout.resource_image_frame,
                null,
                fromColumns,
                toFields,
                0);
        this.context = context;
    }

    public View getView(int pos, View inView, ViewGroup parent) {
        View v = inView;
        if (v == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.resource_image_frame, null);
        }
        getCursor().moveToPosition(pos);

        String title =
                getCursor().getString(getCursor().getColumnIndex(ResourcesContract.COLUMN_NAME_TITLE));
        String description =
                getCursor().getString(getCursor().getColumnIndex(ResourcesContract.COLUMN_NAME_DESCRIPTION));

        String imgRelativePath =
                getCursor().getString(getCursor().getColumnIndex(ResourcesContract.COLUMN_NAME_URL));

        File file = new File(android.os.Environment.getExternalStorageDirectory(),
                appRelativeDir + imgRelativePath);

        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

        ImageView imageView = (ImageView) v.findViewById(R.id.resource_image);
        imageView.setImageBitmap(bitmap);

        TextView titleView = (TextView) v.findViewById(R.id.resource_title);
        titleView.setText(title);

        TextView descriptionView = (TextView) v.findViewById(R.id.resource_description);
        descriptionView.setText(description);

        // add the onclick listener
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO show the image in full screen
            }
        });


        return(v);
    }
}

