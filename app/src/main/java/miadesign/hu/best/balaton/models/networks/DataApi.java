package miadesign.hu.best.balaton.models.networks;

import miadesign.hu.best.balaton.entities.Attraction;
import retrofit2.Call;
import retrofit2.http.GET;

public interface DataApi {

    @GET("/Balaton/balaton.json")
    Call<Attraction[]> get();

}
