package com.example.ui.fragment.communicate;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyDialogFragment extends DialogFragment {
    public static final String TAG = "MyDialogFragment";

    @BindView(R.id.input_text)
    public EditText mInputText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_dialog, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @OnClick({ R.id.ok_btn })
    public void onClick(View view){
        int id = view.getId();
        switch (id){
            case R.id.ok_btn:
                String input = mInputText.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("DATA",input);
                getTargetFragment().onActivityResult(ReplaceFragment.REQUEST_DIALOG,
                        Activity.RESULT_OK,intent);
                this.dismiss();
                break;
        }

    }

}
