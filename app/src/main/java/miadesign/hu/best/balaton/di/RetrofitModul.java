package miadesign.hu.best.balaton.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import miadesign.hu.best.balaton.entities.Attraction;
import miadesign.hu.best.balaton.models.networks.AttractionDeserializer;
import miadesign.hu.best.balaton.models.networks.DataApi;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RetrofitModul {

    private final static String BALATON_DATA = "http://miadesign.hu/";

    @Singleton
    @Provides
    public DataApi provideData() {

        Gson gson = new GsonBuilder().registerTypeAdapter(Attraction[].class, new AttractionDeserializer()).create();
        Retrofit retrofit = getRetrofit(gson);
        return retrofit.create(DataApi.class);

    }

    private Retrofit getRetrofit(Gson gson) {

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new ResponseInterceptor())
                .build();

        return new Retrofit.Builder()
                .baseUrl(BALATON_DATA)
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

    }

    private class ResponseInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Response response = chain.proceed(chain.request());
            MediaType m = MediaType.parse("application/json; charset=windows-1252");
            ResponseBody body = response.body();
            if (body != null) {
                ResponseBody r = ResponseBody.create(m, body.bytes());
                return response.newBuilder()
                        .body(r)
                        .build();
            }
            return response;
        }

    }

}
