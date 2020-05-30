package com.medialink.mvvmbinding.net;

import com.medialink.mvvmbinding.model.DogBreedImages;
import com.medialink.mvvmbinding.model.DogBreeds;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {
    @GET("/api/breeds/list/all")
    Call<DogBreeds> getBreeds();

    @GET("/api/breed/{breed}/images")
    Call<DogBreedImages> getImagesByBreed(@Path("breed") String breed);
}
