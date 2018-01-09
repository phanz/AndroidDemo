package com.example.widgets.custom.surfaceview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.demo.R;

import java.util.ArrayList;
import java.util.List;

public class SurfaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //SurfaceVideoView view = new SurfaceVideoView(this);
        setContentView(R.layout.activity_surface_test);
        ListView listView = (ListView)findViewById(R.id.surface_view_list);
        SurfaceTestAdapter adapter = new SurfaceTestAdapter(this);
        List<String> nameList = new ArrayList<>();
        nameList.add("yangx");
        adapter.setList(nameList);
        listView.setAdapter(adapter);

        //SurfaceVideoView videoView = (SurfaceVideoView) findViewById(R.id.surface_video_test);
    }
}
