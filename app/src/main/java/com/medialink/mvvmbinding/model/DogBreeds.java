package com.medialink.mvvmbinding.model;

import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.lifecycle.MutableLiveData;

import com.medialink.mvvmbinding.net.Api;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DogBreeds extends BaseObservable {
    private static final String TAG = "DogBreeds";
    private String status;
    private List<DogBreed> breedsList = new ArrayList<>();
    private MutableLiveData<List<DogBreed>> breeds = new MutableLiveData<>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void addBreed(DogBreed bd) {
        breedsList.add(bd);
    }

    public MutableLiveData<List<DogBreed>> getBreeds() {
        return breeds;
    }

    public void fetchList() {
        Callback<DogBreeds> callback = new Callback<DogBreeds>() {
            @Override
            public void onResponse(Call<DogBreeds> call, Response<DogBreeds> response) {
                DogBreeds body = response.body();
                status = body.status;
                breeds.setValue(body.breedsList);
            }

            @Override
            public void onFailure(Call<DogBreeds> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        };

        Api.getApi().getBreeds().enqueue(callback);
    }
}
