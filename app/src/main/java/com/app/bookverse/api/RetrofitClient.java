package com.app.bookverse.api;

import android.content.Context;
import androidx.core.content.ContextCompat;
import com.app.bookverse.R; // Pastikan impor ini ada
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.concurrent.TimeUnit;

public class RetrofitClient {
    private static Retrofit retrofit;
    private static String API_KEY;
    private static String BASE_URL;

    public static Retrofit getClient(Context context) {
        if (retrofit == null) {
            API_KEY = ContextCompat.getString(context, R.string.supabase_api_key);
            BASE_URL = ContextCompat.getString(context, R.string.supabase_url);

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(chain -> {
                        okhttp3.Request newRequest = chain.request().newBuilder()
                                .addHeader("apikey", API_KEY)
                                .addHeader("Authorization", "Bearer " + API_KEY)
                                .addHeader("Content-Type", "application/json")
                                .build();
                        return chain.proceed(newRequest);
                    })
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}