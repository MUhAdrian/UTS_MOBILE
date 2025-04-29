package com.example.focus;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class SidebarActivity extends AppCompatActivity {

    private LinearLayout sideBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sidebar);

        sideBar = findViewById(R.id.side_bar);

        // Set visibilitas sidebar berdasarkan data yang diteruskan
        if (getIntent().getBooleanExtra("sidebarVisible", false)) {
            sideBar.setVisibility(View.VISIBLE);
        }

        // Toggle sidebar visibility
        sideBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sideBar.getVisibility() == View.GONE) {
                    sideBar.setVisibility(View.VISIBLE);
                } else {
                    sideBar.setVisibility(View.GONE);
                }
            }
        });
    }
}
