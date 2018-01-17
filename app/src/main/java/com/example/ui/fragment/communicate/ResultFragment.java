package com.example.ui.fragment.communicate;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResultFragment extends Fragment {

    @BindView(R.id.result_input)
    public EditText mResultInput;
    @BindView(R.id.back_fragment_btn)
    public Button mBackFragmentBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @OnClick({ R.id.back_fragment_btn })
    public void onClick(View view){
        int id = view.getId();
        switch (id){
            case R.id.back_fragment_btn:
                String resultData = mResultInput.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("DATA",resultData);
                getActivity().setResult(ReplaceFragment.REQUEST_ACTIVITY_FRAGMENT,intent);
                getActivity().finish();
                break;
        }
    }

}
