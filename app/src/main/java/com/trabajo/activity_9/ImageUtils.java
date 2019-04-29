package com.trabajo.activity_9;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import java.io.IOException;
import java.io.InputStream;
public class ImageUtils {
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
    public static Bitmap getScaledBitmapFromUri(Context context, Uri uri, int desiredWidth) throws IOException {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, options);
        InputStream inputStream = context.getContentResolver().openInputStream(uri);
        float scale = Math.max((float) desiredWidth / (float) options.outWidth, (float) desiredWidth / (float) options.outHeight);
        float newWidth = (float) options.outWidth * scale;
        float newHeight = (float) options.outHeight * scale;
        options.inSampleSize = calculateInSampleSize(options, (int) newWidth, (int) newHeight);
        options.inJustDecodeBounds = false;
        Bitmap sampleBitmap = BitmapFactory.decodeStream(inputStream, null, options);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(sampleBitmap, (int) newWidth, (int) newHeight, false);
        return scaledBitmap;
    }
}