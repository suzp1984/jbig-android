package lib.jacob.org.jbigandroid.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import lib.jacob.org.jbigandroid.R;
import lib.jacob.org.lib.JbigCodec;
import lib.jacob.org.lib.JbigCodecFactory;

/**
 * Created by moses on 8/28/15.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private final List<byte[]> mJbigDatas = new ArrayList<>();

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.image_item, viewGroup, false);

        ImageViewHolder vh = new ImageViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ImageViewHolder imageViewHolder, int i) {
        byte[] jbig = mJbigDatas.get(i);

        JbigCodec jbigCodec = JbigCodecFactory.getJbigCodec(JbigCodecFactory.CODEC.JNI_CODEC);

        if (jbigCodec != null) {
            Bitmap[] bms = jbigCodec.decode(jbig);
            imageViewHolder.image.setImageBitmap(bms[0]);
        }
    }

    @Override
    public int getItemCount() {
        return mJbigDatas.size();
    }

    public void addJbigImage(byte[] jbig) {
        if (jbig != null && jbig.length > 0) {
            mJbigDatas.add(jbig);
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
