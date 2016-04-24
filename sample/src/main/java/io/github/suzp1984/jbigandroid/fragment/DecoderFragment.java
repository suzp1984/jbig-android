package io.github.suzp1984.jbigandroid.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.suzp1984.jbigandroid.JbigApplication;
import io.github.suzp1984.jbigandroid.R;
import io.github.suzp1984.jbigandroid.adapter.ImageAdapter;
import io.github.suzp1984.jbigandroid.controller.JbigController;
import io.github.suzp1984.jbigandroid.controller.MainController;

public class DecoderFragment extends Fragment implements
        JbigController.JbigDecoderUi {

    @Bind(R.id.recycler)
    RecyclerView mRecyclerView;

    @Inject
    MainController mMainController;

    private ImageAdapter mImageAdapter;

    private JbigController.JbigUiCallback mJbigUiCallback;

    public static DecoderFragment newInstance() {
        DecoderFragment fragment = new DecoderFragment();

        return fragment;
    }

    public DecoderFragment() {
        // Required empty public constructor
        ((JbigApplication) getActivity().getApplication()).getApplicationComponent().inject(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_decoder, container, false);
        ButterKnife.bind(this, view);

        mImageAdapter = new ImageAdapter();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setAdapter(mImageAdapter);

        return view;
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
    public void onDestroyView() {
        ButterKnife.unbind(this);

        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    // JbigDecoderUi
    @Override
    public void showJbig(byte[] jbig) {

    }

    @Override
    public void showBitmaps(Bitmap[] bitmaps) {
        Log.d("DecoderFragment", "show bitmaps");
        mImageAdapter.addBitmap(bitmaps[0]);
        mImageAdapter.notifyDataSetChanged();
    }

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