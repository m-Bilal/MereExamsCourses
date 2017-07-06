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
        // Creating the progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading discipline groups");
        progressDialog.show();

        // Making the request
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<DisciplineGroup.DisciplineGroupResponse> call = apiService.getDisciplineGroups(
                MainActivity.vars.get(MainActivity.API_BROAD_DISCIPLINE_GROUP),
                MainActivity.vars.get(MainActivity.TOKEN));

        call.enqueue(new Callback<DisciplineGroup.DisciplineGroupResponse>() {
            @Override
            public void onResponse(Call<DisciplineGroup.DisciplineGroupResponse> call, Response<DisciplineGroup.DisciplineGroupResponse> response) {
                disciplineGroups = response.body().getDisciplineGroups();
                progressDialog.dismiss();
                // TODO: remove this after testing
                Log.i(TAG, "Discipline groups size: " + disciplineGroups.size());
            }

            @Override
            public void onFailure(Call<DisciplineGroup.DisciplineGroupResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG, t.toString());
                Toast.makeText(getApplicationContext(), "No internet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void syncDisciplineGroupCategories() {
        // Creating the progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading discipline groups");
        progressDialog.show();

        // Making the request
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<DisciplineGroupCategory.DisciplineGroupCategoryResponse> call = apiService.getDisciplineGroupCategories(
                MainActivity.vars.get(MainActivity.API_BROAD_DISCIPLINE_GROUP_CATEGORY),
                MainActivity.vars.get(MainActivity.TOKEN));

        call.enqueue(new Callback<DisciplineGroupCategory.DisciplineGroupCategoryResponse>() {
            @Override
            public void onResponse(Call<DisciplineGroupCategory.DisciplineGroupCategoryResponse> call, Response<DisciplineGroupCategory.DisciplineGroupCategoryResponse> response) {
                disciplineGroupCategories = response.body().getDisciplineGroupCategories();
                progressDialog.dismiss();
                // TODO: remove this after testing
                Log.i(TAG, "Discipline categories size: " + disciplineGroupCategories.size());
            }

            @Override
            public void onFailure(Call<DisciplineGroupCategory.DisciplineGroupCategoryResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG, t.toString());
                Toast.makeText(getApplicationContext(), "No internet", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
