package lib.jacob.org.jbigandroid.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.FrameLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lib.jacob.org.jbigandroid.R;
import lib.jacob.org.jbigandroid.utils.ByteUtils;
import lib.jacob.org.jbigandroid.widget.PaintView;
import lib.jacob.org.lib.JbigCodec;
import lib.jacob.org.lib.JbigCodecFactory;

public class PaintViewFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private PaintView mPaintView;

    @Bind(R.id.button)
    Button mButton;

    @Bind(R.id.paint_content)
    FrameLayout mFrameLayout;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PaintViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PaintViewFragment newInstance(String param1, String param2) {
        PaintViewFragment fragment = new PaintViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public PaintViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_paint_view, container, false);
        ButterKnife.bind(this, view);

        mButton.setEnabled(false);
        mPaintView = new PaintView(getActivity());
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mPaintView.setLayoutParams(params);
        mPaintView.requestFocus();
        mPaintView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    mButton.setEnabled(true);
                }

                return false;
            }
        });

        mFrameLayout.addView(mPaintView);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);

        super.onDestroyView();
    }

    @OnClick(R.id.button)
    public void onEncodeClicked() {
        Bitmap bitmap = mPaintView.getCachebBitmap();

        Bitmap[] bitmaps = new Bitmap[1];
        bitmaps[0] = bitmap;

        JbigCodec jbigCodec = JbigCodecFactory.getJbigCodec(JbigCodecFactory.CODEC.JNI_CODEC);

        if (jbigCodec != null) {
            byte[] jbigData = jbigCodec.encode(bitmaps);

            String serializedJbig = ByteUtils.byteArray2HexString(jbigData);
            Log.e("Encode", serializedJbig);
        }
    }
}
