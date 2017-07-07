package com.mereexams.mereexamscourses.Models;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.annotations.SerializedName;
import com.mereexams.mereexamscourses.MainActivity;
import com.mereexams.mereexamscourses.helpers.ApiClient;
import com.mereexams.mereexamscourses.helpers.ApiInterface;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Bilal on 06-Jul-17.
 */

public class DisciplineGroupCategory extends RealmObject {

    @Ignore
    public static final String TAG = "DisciplineCategory";

    @SerializedName("id")
    @PrimaryKey
    private int id;

    @SerializedName("discipline_group_category")
    private String disciplineGroupCategory;

    public static void sync(final Context context) {
        // Creating the progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading discipline groups");
        progressDialog.show();

        // Making the request
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<DisciplineGroupCategoryResponse> call = apiService.getDisciplineGroupCategories(
                MainActivity.vars.get(MainActivity.API_BROAD_DISCIPLINE_GROUP_CATEGORY),
                MainActivity.vars.get(MainActivity.TOKEN));

        call.enqueue(new Callback<DisciplineGroupCategoryResponse>() {
            @Override
            public void onResponse(Call<DisciplineGroupCategory.DisciplineGroupCategoryResponse> call, Response<DisciplineGroupCategoryResponse> response) {
                final List<DisciplineGroupCategory> disciplineGroupCategories = response.body().getDisciplineGroupCategories();
                progressDialog.dismiss();
                // TODO: remove this after testing
                Log.i(TAG, "Discipline categories size: " + disciplineGroupCategories.size());

                // Realm
                Realm realm = Realm.getInstance(MainActivity.config);

                // Delete existing records from realm
                realm.beginTransaction();
                RealmResults<DisciplineGroupCategory> realmResults = realm.where(DisciplineGroupCategory.class).findAll();
                realmResults.deleteAllFromRealm();
                realm.commitTransaction();

                // Save to Realm
                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        for (DisciplineGroupCategory i : disciplineGroupCategories) {
                            int id = i.getId();
                            DisciplineGroupCategory disciplineGroupCategory = realm.createObject(DisciplineGroupCategory.class, id);
                            disciplineGroupCategory.setDisciplineGroupCategory(i.getDisciplineGroupCategory());
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<DisciplineGroupCategory.DisciplineGroupCategoryResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG, t.toString());
                Toast.makeText(context, "No internet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static List<DisciplineGroupCategory> readFromRealm() {
        Realm realm = Realm.getInstance(MainActivity.config);
        List<DisciplineGroupCategory> disciplineGroupCategories = realm.where(DisciplineGroupCategory.class).findAll();
        return disciplineGroupCategories;
    }

    // This method retrieves the saved data or syncs from server if the data is not found
    public static List<DisciplineGroupCategory> retrieveOrSync(Context context) {
        List<DisciplineGroupCategory> disciplineGroups = readFromRealm();
        if (disciplineGroups.size() == 0) {
            sync(context);
        }
        return readFromRealm();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDisciplineGroupCategory() {
        return disciplineGroupCategory;
    }

    public void setDisciplineGroupCategory(String disciplineGroupCategory) {
        this.disciplineGroupCategory = disciplineGroupCategory;
    }

    public class DisciplineGroupCategoryResponse {
        @SerializedName("broad_discipline_group_category_all")
        private List<DisciplineGroupCategory> disciplineGroupCategories;

        @SerializedName("status")
        private String success;

        public List<DisciplineGroupCategory> getDisciplineGroupCategories() {
            return disciplineGroupCategories;
        }

        public void setDisciplineGroupCategories(List<DisciplineGroupCategory> disciplineGroupCategories) {
            this.disciplineGroupCategories = disciplineGroupCategories;
        }

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }
    }
}
