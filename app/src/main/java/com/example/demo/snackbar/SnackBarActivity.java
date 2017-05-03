package com.example.demo.snackbar;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.demo.R;

public class SnackBarActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mSnackBarBtn;
    private Button mSnackBarActionBtn;
    private Button dismissSnackBar;
    private Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snack_bar);

        mSnackBarBtn = (Button)findViewById(R.id.show_snack_bar_btn);
        mSnackBarActionBtn = (Button)findViewById(R.id.show_snack_bar_action_btn);
        dismissSnackBar = (Button)findViewById(R.id.dismiss_snack_bar);
        mSnackBarBtn.setOnClickListener(this);
        mSnackBarActionBtn.setOnClickListener(this);
        dismissSnackBar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.show_snack_bar_btn:
                snackbar = Snackbar.make(view, "Snack Bar Text", Snackbar.LENGTH_INDEFINITE);
                snackbar.show();
                break;
            case R.id.show_snack_bar_action_btn:
                Snackbar.make(view, "Snack Bar Text", Snackbar.LENGTH_LONG)
                        .setAction("Go!", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(SnackBarActivity.this,"SnackBar",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setActionTextColor(Color.BLUE).show();

                break;
            case R.id.dismiss_snack_bar:
                snackbar.dismiss();
                break;
        }
    }
}
