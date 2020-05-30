package com.medialink.mvvmbinding;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.medialink.mvvmbinding.databinding.ActivityMainBinding;
import com.medialink.mvvmbinding.model.DogBreed;
import com.medialink.mvvmbinding.viewmodel.DogBreedsViewModel;

public class MainActivity extends AppCompatActivity {
    private DogBreedsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupBindings(savedInstanceState);
    }

    private void setupBindings(Bundle savedInstanceState) {
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_main
        );
        viewModel = new ViewModelProvider(this).get(DogBreedsViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init();
        }
        activityMainBinding.setModel(viewModel);
        setupListUpdate();

    }

    private void setupListUpdate() {
        viewModel.loading.set(View.VISIBLE);
        viewModel.fetchList();
        viewModel.getBreeds().observe(this, dogBreeds -> {
            viewModel.loading.set(View.GONE);
            if (dogBreeds.size() == 0) {
                viewModel.showEmpty.set(View.VISIBLE);
            } else {
                viewModel.showEmpty.set(View.GONE);
                viewModel.setDogBreedsInAdapter(dogBreeds);
            }
        });

        setupListClick();
    }

    private void setupListClick() {
        viewModel.getSelected().observe(this, dogBreed -> {
            if (dogBreed != null) {
                Toast.makeText(MainActivity.this, "You selected a " + dogBreed.getBreed(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}