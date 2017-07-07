package com.mereexams.mereexamscourses;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.mereexams.mereexamscourses.Models.DisciplineGroup;
import com.mereexams.mereexamscourses.Models.DisciplineGroupCategory;
import com.mereexams.mereexamscourses.helpers.FileIO;

import java.util.HashMap;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {

    public static HashMap<String, String> vars;

    public final static String API_BROAD_DISCIPLINE_GROUP = "broad_discipline_group";
    public final static String API_BROAD_DISCIPLINE_GROUP_CATEGORY = "broad_discipline_group_category";
    public final static String BASE_URL = "base_url";
    public final static String TOKEN = "token";

    private final static String TAG = "MainActivity";

    Button buttonCourses;
    Realm realm;
    public static RealmConfiguration config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FileIO fileIO = new FileIO(this);
        vars = fileIO.getVars();

        buttonCourses = (Button) findViewById(R.id.button_courses);
        Log.d(TAG, "Button Courses: " + buttonCourses.toString());
        createRealmConfig();
        readRealm();
        setOnClickListeners();
    }

    void createRealmConfig() {
        // initialize Realm
        Realm.init(getApplicationContext());

        // create your Realm configuration
        config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .name("myCustomRealm")
                .build();
        Realm.setDefaultConfiguration(config);
    }

    void readRealm() {
        try {
            // Create a realm
            realm = Realm.getInstance(config);
            Log.i(TAG, realm.getConfiguration().getRealmFileName());

            List<DisciplineGroup> disciplineGroups = realm.where(DisciplineGroup.class).findAll();
            Log.i(TAG, "Discipline Groups saved size: " + disciplineGroups.size());

            List<DisciplineGroupCategory> disciplineGroupCategories = realm.where(DisciplineGroupCategory.class).findAll();
            Log.i(TAG, "Discipline Groups Categories saved size: " + disciplineGroupCategories.size());
        } catch (Exception e) {
            Log.e(TAG, "Realm Error: " + e.toString());
        }
    }

    private void setOnClickListeners() {
        buttonCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CoursesActivity.class);
                startActivity(intent);
            }
        });
    }
}
