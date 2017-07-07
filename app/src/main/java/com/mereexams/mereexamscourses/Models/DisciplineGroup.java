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

import static com.mereexams.mereexamscourses.MainActivity.config;

/**
 * Created by Bilal on 06-Jul-17.
 */

public class DisciplineGroup extends RealmObject {

    @Ignore
    public static final String TAG = "DisciplineGroup";

    @SerializedName("id")
    @PrimaryKey
    private int id;

    @SerializedName("category_id")
    private int categoryId;

    @SerializedName("discipline_group")
    private String disciplineGroup;

    public static void sync(final Context context) {
        // Creating the progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading discipline groups");
        progressDialog.show();

        // Making the request
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<DisciplineGroupResponse> call = apiService.getDisciplineGroups(
                MainActivity.vars.get(MainActivity.API_BROAD_DISCIPLINE_GROUP),
                MainActivity.vars.get(MainActivity.TOKEN));

        call.enqueue(new Callback<DisciplineGroupResponse>() {
            @Override
            public void onResponse(Call<DisciplineGroup.DisciplineGroupResponse> call, Response<DisciplineGroupResponse> response) {
                final List<DisciplineGroup> disciplineGroups = response.body().getDisciplineGroups();
                progressDialog.dismiss();
                // TODO: remove this after testing
                Log.i(TAG, "Discipline groups size: " + disciplineGroups.size());

                // Realm
                Realm realm = Realm.getInstance(config);

                // Delete existing records from realm
                realm.beginTransaction();
                RealmResults<DisciplineGroup> realmResults = realm.where(DisciplineGroup.class).findAll();
                realmResults.deleteAllFromRealm();
                realm.commitTransaction();

                // Save to Realm
                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        for (DisciplineGroup i : disciplineGroups) {
                            int id = i.getId();
                            DisciplineGroup disciplineGroup = realm.createObject(DisciplineGroup.class, id);
                            disciplineGroup.setCategoryId(i.getCategoryId());
                            disciplineGroup.setDisciplineGroup(i.getDisciplineGroup());
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<DisciplineGroup.DisciplineGroupResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG, t.toString());
                Toast.makeText(context, "No internet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static List<DisciplineGroup> readFromRealm() {
        Realm realm = Realm.getInstance(MainActivity.config);
        List<DisciplineGroup> disciplineGroups = realm.where(DisciplineGroup.class).findAll();
        return disciplineGroups;
    }

    // This method retrieves the saved data or syncs from server if the data is not found
    public static List<DisciplineGroup> retrieveOrSync(Context context) {
        List<DisciplineGroup> disciplineGroups = readFromRealm();
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getDisciplineGroup() {
        return disciplineGroup;
    }

    public void setDisciplineGroup(String disciplineGroup) {
        this.disciplineGroup = disciplineGroup;
    }

    public class DisciplineGroupResponse {

        @SerializedName("broad_discipline_group_all")
        private List<DisciplineGroup> disciplineGroups;

        @SerializedName("status")
        private String status;

        public List<DisciplineGroup> getDisciplineGroups() {
            return disciplineGroups;
        }

        public void setDisciplineGroups(List<DisciplineGroup> disciplineGroups) {
            this.disciplineGroups = disciplineGroups;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
