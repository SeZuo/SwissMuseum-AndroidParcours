package ch.sebastienzurfluh.swissmuseum.panneauinteractif.model;

import ch.sebastienzurfluh.swissmuseum.panneauinteractif.R;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.io.File;

import ch.sebastienzurfluh.swissmuseum.panneauinteractif.control.PageActivity;
import ch.sebastienzurfluh.swissmuseum.panneauinteractif.model.DataProviderContract.*;

/**
 * This is a {@code SimpleCursorAdapter} modified to replace urls with the actual image.
 */
public class CardCursorAdapter extends SimpleCursorAdapter {
    private final Context context;

    private final String appRelativeDir = "Android/data/ch.sebastienzurfluh.swissmuseumguides/files/";

    public static String[] fromColumns = {
//            MenusContract._ID,
            MenusContract.COLUMN_NAME_TITLE,
            MenusContract.COLUMN_NAME_DESCRIPTION,
//			MenusContract.COLUMN_NAME_THUMB_IMG_URL,
            MenusContract.COLUMN_NAME_IMG_URL,
			AffiliationsContract.COLUMN_NAME_PAGE_ID //,
//            AffiliationsContract.COLUMN_TRIMMED_NAME_ORDER
    };

    public static int[] toFields = new int[] {R.id.card_title, R.id.card_description, R.id.card_image};

    public CardCursorAdapter(Context context) {
        super(
                context,
                R.layout.card,
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
            v = inflater.inflate(R.layout.card, null);
        }
        getCursor().moveToPosition(pos);

        String title =
                getCursor().getString(getCursor().getColumnIndex(MenusContract.COLUMN_NAME_TITLE));
        String description =
                getCursor().getString(getCursor().getColumnIndex(MenusContract.COLUMN_NAME_DESCRIPTION));

        String imgRelativePath =
                getCursor().getString(getCursor().getColumnIndex(MenusContract.COLUMN_NAME_IMG_URL));

        File file = new File(android.os.Environment.getExternalStorageDirectory(),
                appRelativeDir + imgRelativePath);

        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

        ImageView imageView = (ImageView) v.findViewById(R.id.card_image);
        imageView.setImageBitmap(bitmap);

        TextView titleView = (TextView) v.findViewById(R.id.card_title);
        titleView.setText(title);

        TextView descriptionView = (TextView) v.findViewById(R.id.card_description);
        descriptionView.setText(description);

        final String pageId = getCursor().getString(
                getCursor().getColumnIndex(
                        AffiliationsContract.COLUMN_NAME_PAGE_ID));

        // add the onclick listener
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showPage = new Intent(context, PageActivity.class);
                showPage.putExtra("pageId", pageId);

                context.startActivity(showPage);
            }
        });


        return(v);
    }
}
