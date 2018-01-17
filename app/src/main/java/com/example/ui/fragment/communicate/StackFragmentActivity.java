package com.example.ui.fragment.communicate;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.demo.R;
import com.example.ui.fragment.pager.BasePagerFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class StackFragmentActivity extends AppCompatActivity {


    public int mTitleIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stack_fragment);
        ButterKnife.bind(this);
    }

    @OnClick({ R.id.push_fragment_btn,R.id.pop_fragment_btn })
    public void onClick(View view){
        int id = view.getId();
        switch (id){
            case R.id.push_fragment_btn:
                pushFragment();
                break;

            case R.id.pop_fragment_btn:
                popFragment();
                break;

            default:
                break;
        }
    }

    public void pushFragment(){
        BasePagerFragment basePagerFragment =
                StackFragment.newInstance("Fragment#" + (mTitleIndex++));

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.stack_fragment_container,basePagerFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();

    }

    public void popFragment(){
        getSupportFragmentManager().popBackStack();
    }
}
