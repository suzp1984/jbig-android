package lib.jacob.org.jbigandroid.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import lib.jacob.org.jbigandroid.R;

/**
 * Created by moses on 9/1/15.
 */
public class EncoderDialogFragment extends DialogFragment {

    private EncodeDialogListener mEncodeDialogListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setMessage("Do you want encode those?")
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // ok
                        if (mEncodeDialogListener != null) {
                            mEncodeDialogListener.onDialogPositiveClick(EncoderDialogFragment.this);
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // cancel
                        if (mEncodeDialogListener != null) {
                            mEncodeDialogListener.onDialogNegativeClick(EncoderDialogFragment.this);
                        }
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mEncodeDialogListener = (EncodeDialogListener) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    public void setEncodeDialogListener(EncodeDialogListener listener) {
        mEncodeDialogListener = listener;
    }

    public interface EncodeDialogListener {
        void onDialogPositiveClick(DialogFragment dialog);
        void onDialogNegativeClick(DialogFragment dialog);
    }

}
