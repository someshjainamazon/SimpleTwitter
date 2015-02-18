package com.codepath.apps.mysimpletweets;

/**
 * Created by somesh on 2/16/15.
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

/**
 * Created by somesh on 1/31/15.
 */
public class RetweetDialog extends DialogFragment implements View.OnClickListener {

    Button retweetButton;
    Button undoRetweetButton;

    @Override
    public void onClick(View v) {


        RetweetListener listener = (RetweetListener) getActivity();
        listener.onFinishRetweetDialog(true);
        dismiss();
    }

    public interface RetweetListener {
        void onFinishRetweetDialog(Boolean returnBool);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_retweet, container);

        retweetButton = (Button)view.findViewById(R.id.btnRtwt);
        undoRetweetButton = (Button)view.findViewById(R.id.btnRtwtUndo);

        retweetButton.setOnClickListener(this);


        undoRetweetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RetweetListener listener = (RetweetListener) getActivity();
                listener.onFinishRetweetDialog(false);
                dismiss();
            }
        });

        return view;



    }

    public static RetweetDialog newInstance() {
        RetweetDialog frag = new RetweetDialog();
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }



    public RetweetDialog() {
        // Empty constructor required for DialogFragment
    }


}
