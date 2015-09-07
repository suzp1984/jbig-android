package lib.jacob.org.jbigandroid.adapter;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.graphics.BitmapCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import lib.jacob.org.jbigandroid.R;
import lib.jacob.org.lib.JbigCodec;
import lib.jacob.org.lib.JbigCodecFactory;

/**
 * Created by moses on 8/28/15.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private final List<Bitmap> mBitmaps = new ArrayList<>();

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.image_item, viewGroup, false);

        ImageViewHolder vh = new ImageViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ImageViewHolder imageViewHolder, int i) {

        imageViewHolder.image.setImageBitmap(mBitmaps.get(i));
    }

    @Override
    public int getItemCount() {
        return mBitmaps.size();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public void addBitmap(Bitmap bitmap) {
        if (bitmap != null && !mBitmaps.contains(bitmap)) {
            for (Bitmap bm : mBitmaps) {
                if (bitmap.sameAs(bm)) {
                    return;
                }
            }

            mBitmaps.add(bitmap);
        }
    }

    // ViewHolder
    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;

        public ImageViewHolder(View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}
