package com.medialink.mvvmbinding.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.medialink.mvvmbinding.BR;
import com.medialink.mvvmbinding.model.DogBreed;
import com.medialink.mvvmbinding.viewmodel.DogBreedsViewModel;

import java.util.List;

public class DogBreedsAdapter extends RecyclerView.Adapter<DogBreedsAdapter.GenericViewHolder> {
    private int layoutId;
    private List<DogBreed> breeds;
    private DogBreedsViewModel viewModel;

    // constructor
    public DogBreedsAdapter(int layoutId, DogBreedsViewModel viewModel) {
        this.layoutId = layoutId;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public GenericViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater,
                viewType,
                parent,
                false);
        return new GenericViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull GenericViewHolder holder, int position) {
        holder.bind(viewModel, position);
    }

    @Override
    public int getItemCount() {
        return breeds == null ? 0 : breeds.size();
    }

    public void setBreeds(List<DogBreed> breeds) {
        this.breeds = breeds;
    }

    @Override
    public int getItemViewType(int position) {
        return getLayoutIdForPosition(position);
    }

    private int getLayoutIdForPosition(int position) {
        return layoutId;
    }

    class GenericViewHolder extends RecyclerView.ViewHolder {
        final ViewDataBinding binding;

        // constructor
        GenericViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(DogBreedsViewModel viewModel, Integer position) {
            viewModel.fetchDogBreedImagesAt(position);
            binding.setVariable(BR.viewModel, viewModel);
            binding.setVariable(BR.position, position);
            binding.executePendingBindings();
        }
    }
}
