package io.github.suzp1984.jbigandroid.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import javax.inject.Inject;

import io.github.suzp1984.jbigandroid.JbigApplication;
import io.github.suzp1984.jbigandroid.R;
import io.github.suzp1984.jbigandroid.controller.JbigController;
import io.github.suzp1984.jbigandroid.controller.MainController;
import io.github.suzp1984.jbigandroid.widget.PaintView;

public class PaintViewFragment extends Fragment implements
        EncoderDialogFragment.EncodeDialogListener,
        JbigController.JbigEncoderUi {

    private PaintView mPaintView;

    Button mEncodeBtn;

    Button mClearBtn;

    FrameLayout mFrameLayout;

    @Inject
    MainController mMainController;

    private JbigController.JbigUiCallback mJbigUiCallback;

    public PaintViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        ((JbigApplication)getActivity().getApplication()).getApplicationComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_paint_view, container, false);

        mEncodeBtn = view.findViewById(R.id.encoder);
        mClearBtn = view.findViewById(R.id.clear);
        mFrameLayout = view.findViewById(R.id.paint_content);

        mEncodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEncodeClicked();
            }
        });


        mClearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClearBtnClicked();
            }
        });

        mEncodeBtn.setEnabled(false);
        mPaintView = new PaintView(getActivity());
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mPaintView.setLayoutParams(params);
        mPaintView.requestFocus();
        mPaintView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    mEncodeBtn.setEnabled(true);
                }

                return false;
            }
        });

        mFrameLayout.addView(mPaintView);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore last state for checked position.
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        getController().attachUi(this);
    }

    @Override
    public void onPause() {
        getController().detachUi(this);

        super.onPause();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    // onClick listener
    public void onEncodeClicked() {
        //TODO:XXX pop up a dialog, clean the PaintView.

        showEncodeDialog();
    }

    public void onClearBtnClicked() {
        mPaintView.clear();
        mEncodeBtn.setEnabled(false);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        encodeAndSave();
        onClearBtnClicked();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        Log.e("TAG", "onDialog Negative Clicked");
    }

    private void showEncodeDialog() {
        EncoderDialogFragment dialogFragment = new EncoderDialogFragment();
        dialogFragment.setEncodeDialogListener(this);
        dialogFragment.show(getChildFragmentManager(), "encode");
    }

    private void encodeAndSave() {
        Bitmap bitmap = mPaintView.getCachebBitmap();

        mJbigUiCallback.encodeBitmap(bitmap);
    }

    // JbigEncoderUi
    @Override
    public void setCallback(JbigController.JbigUiCallback callback) {
        mJbigUiCallback = callback;
    }

    @Override
    public boolean isModal() {
        return false;
    }

    private JbigController getController() {
        return mMainController.getJbigController();
    }
}
