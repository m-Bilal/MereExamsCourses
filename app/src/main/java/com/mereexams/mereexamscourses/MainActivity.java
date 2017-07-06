package com.mereexams.mereexamscourses;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.mereexams.mereexamscourses.helpers.FileIO;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    public static HashMap<String, String> vars;

    public final static String API_BROAD_DISCIPLINE_GROUP = "broad_discipline_group";
    public final static String API_BROAD_DISCIPLINE_GROUP_CATEGORY = "broad_discipline_group_category";
    public final static String BASE_URL = "base_url";
    public final static String TOKEN = "token";

    Button buttonSync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonSync = (Button) findViewById(R.id.button_sync);

        FileIO fileIO = new FileIO(this);
        vars = fileIO.getVars();

        setOnClickListeners();
    }

    private void setOnClickListeners() {
        buttonSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CoursesActivity.class);
                startActivity(intent);
            }
        });
    }
}
