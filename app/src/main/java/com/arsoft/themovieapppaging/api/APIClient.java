package com.arsoft.themovieapppaging.api;

import static com.arsoft.themovieapppaging.util.Utils.API_KEY;
import static com.arsoft.themovieapppaging.util.Utils.BASE_URL;

import com.arsoft.themovieapppaging.model.Movie;
import com.arsoft.themovieapppaging.model.MovieResponse;

import io.reactivex.rxjava3.core.Single;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Singleton class for creating and managing Retrofit API client
 * Adds API key automatically to each request using an OkHttp interceptor
 */
public class APIClient {

    static APIInterface apiInterface;

    // Retrofit instance
    public static APIInterface getApiInterface(){
        if (apiInterface == null){
            OkHttpClient.Builder client = new OkHttpClient.Builder();
            client.addInterceptor(chain -> {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();
                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter("api_key", API_KEY)
                        .build();

                Request.Builder requestBuilder = original.newBuilder()
                        .url(url);
                Request request = requestBuilder.build();
                return chain.proceed(request);
            });

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();

            apiInterface = retrofit.create(APIInterface.class);

        }
        return apiInterface;
    }



    public interface APIInterface{
        @GET("movie/popular")
        Single<MovieResponse> getMoviesByPage(@Query("page") int page);
    }

}
