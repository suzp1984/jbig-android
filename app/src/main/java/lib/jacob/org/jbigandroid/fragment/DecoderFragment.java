package lib.jacob.org.jbigandroid.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import lib.jacob.org.jbigandroid.R;
import lib.jacob.org.jbigandroid.adapter.ImageAdapter;
import lib.jacob.org.jbigandroid.realmobj.JbigItem;

public class DecoderFragment extends Fragment {

    @Bind(R.id.recycler)
    RecyclerView mRecyclerView;

    private ImageAdapter mImageAdapter;

    public static DecoderFragment newInstance() {
        DecoderFragment fragment = new DecoderFragment();

        return fragment;
    }

    public DecoderFragment() {
        // Required empty public constructor
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
        fetchJbigs();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setAdapter(mImageAdapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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

    private void fetchJbigs() {
        Realm realm = Realm.getDefaultInstance();

        RealmQuery<JbigItem> query = realm.where(JbigItem.class);
        RealmResults<JbigItem> results = query.findAll();

        for (JbigItem item : results) {
            mImageAdapter.addJbigImage(item.getJbig());
        }
    }
}
