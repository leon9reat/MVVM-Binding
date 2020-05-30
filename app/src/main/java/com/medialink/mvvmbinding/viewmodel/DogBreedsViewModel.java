package com.medialink.mvvmbinding.viewmodel;

import android.util.Log;
import android.view.View;

import androidx.databinding.ObservableArrayMap;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.medialink.mvvmbinding.R;
import com.medialink.mvvmbinding.adapter.DogBreedsAdapter;
import com.medialink.mvvmbinding.model.DogBreed;
import com.medialink.mvvmbinding.model.DogBreedImages;
import com.medialink.mvvmbinding.model.DogBreeds;
import com.medialink.mvvmbinding.net.DogImagesCallback;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class DogBreedsViewModel extends ViewModel {
    private static final String TAG = "DogBreedsViewModel";
    private DogBreeds dogBreeds;
    private DogBreedsAdapter adapter;

    public MutableLiveData<DogBreed> selected;
    public ObservableArrayMap<String, String> images;
    public ObservableInt loading;
    public ObservableInt showEmpty;

    public void init() {
        dogBreeds = new DogBreeds();
        selected = new MutableLiveData<>();
        adapter = new DogBreedsAdapter(R.layout.dog_item, this);
        images = new ObservableArrayMap<>();
        loading = new ObservableInt(View.GONE);
        showEmpty = new ObservableInt(View.GONE);
    }

    public void fetchList() {
        dogBreeds.fetchList();
    }

    public MutableLiveData<List<DogBreed>> getBreeds() {
        return dogBreeds.getBreeds();
    }

    public DogBreedsAdapter getAdapter() {
        return adapter;
    }

    public void setDogBreedsInAdapter(List<DogBreed> breeds) {
        this.adapter.setBreeds(breeds);
        this.adapter.notifyDataSetChanged();
    }

    public MutableLiveData<DogBreed> getSelected() {
        return selected;
    }

    public void onItemClick(Integer index) {
        DogBreed db = getDogBreedAt(index);
        selected.setValue(db);
    }

    public DogBreed getDogBreedAt(Integer index) {
        if (dogBreeds.getBreeds().getValue() != null
                && index != null
                && dogBreeds.getBreeds().getValue().size() > index) {
            return dogBreeds.getBreeds().getValue().get(index);
        }
        return null;
    }

    public void fetchDogBreedImagesAt(Integer index) {
        DogBreed db = getDogBreedAt(index);
        if (db != null
                && !images.containsKey(db.getBreed())) {
            // ada dogbreed tapi gambar belum ada
            db.fetchImages(new DogImagesCallback(db.getBreed()) {
                @Override
                public void onResponse(Call<DogBreedImages> call, Response<DogBreedImages> response) {
                    DogBreedImages body = response.body();

                    if (body.getImages() != null
                            && body.getImages().length > 0) {
                        String thumbnailUrl = body.getImages()[0];
                        images.put(getBreed(), thumbnailUrl);

//                        Log.d(TAG, "onResponse: "+body.getImages()[0]);
                    }
                }

                @Override
                public void onFailure(Call<DogBreedImages> call, Throwable t) {
                    Log.e(TAG, t.getMessage(), t);
                }
            });
        }
    }
}
