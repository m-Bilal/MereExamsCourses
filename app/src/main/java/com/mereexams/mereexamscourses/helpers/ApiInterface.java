package com.mereexams.mereexamscourses.helpers;

import com.mereexams.mereexamscourses.MainActivity;
import com.mereexams.mereexamscourses.Models.DisciplineGroup;
import com.mereexams.mereexamscourses.Models.DisciplineGroupCategory;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Url;

/**
 * Created by Bilal on 06-Jul-17.
 */

public interface ApiInterface {

    // Api endpoint to get Discipline Groups
    @GET
    Call<DisciplineGroup.DisciplineGroupResponse> getDisciplineGroups(
            @Url String url,
            @Header(MainActivity.TOKEN) String token
    );

    // Api endpoint to get Discipline Group Categories
    @GET
    Call<DisciplineGroupCategory.DisciplineGroupCategoryResponse> getDisciplineGroupCategories(
            @Url String url,
            @Header(MainActivity.TOKEN) String token
    );
}
