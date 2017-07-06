package com.mereexams.mereexamscourses;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.mereexams.mereexamscourses.helpers.FileIO;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    public static HashMap<String, String> vars;

    public final static String API_BROAD_DISCIPLINE_GROUP = "broad_discipline_group";
    public final static String API_BROAD_DISCIPLINE_GROUP_CATEGORY = "broad_discipline_group_category";
    public final static String BASE_URL = "base_url";
    public final static String TOKEN = "token";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FileIO fileIO = new FileIO(this);
        vars = fileIO.getVars();
    }
}
