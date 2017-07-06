package com.mereexams.mereexamscourses.helpers;

import com.mereexams.mereexamscourses.MainActivity;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Bilal on 06-Jul-17.
 */

public class ApiClient {
    public static final String baseURL = MainActivity.vars.get(MainActivity.BASE_URL);
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
