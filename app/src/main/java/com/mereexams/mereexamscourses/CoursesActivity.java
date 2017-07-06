package com.mereexams.mereexamscourses;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mereexams.mereexamscourses.Models.DisciplineGroup;
import com.mereexams.mereexamscourses.Models.DisciplineGroupCategory;
import com.mereexams.mereexamscourses.helpers.ApiClient;
import com.mereexams.mereexamscourses.helpers.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoursesActivity extends AppCompatActivity {

    List<DisciplineGroup> disciplineGroups;
    List<DisciplineGroupCategory> disciplineGroupCategories;

    public static final String TAG = "CoursesActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        disciplineGroupCategories = new ArrayList<>();
        disciplineGroups = new ArrayList<>();

        syncDisciplineGroupCategories();
        syncDisciplineGroups();
    }

    public void syncDisciplineGroups() {
        DisciplineGroup.sync(this);
    }

    public void syncDisciplineGroupCategories() {
        DisciplineGroupCategory.sync(this);
    }
}
