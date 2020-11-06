package com.drumdie.wallpaper.Fragments;

import android.media.tv.TvContract;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.drumdie.wallpaper.Models.Photo;
import com.drumdie.wallpaper.PhotosAdapter.PhotosAdapter;
import com.drumdie.wallpaper.R;
import com.drumdie.wallpaper.WebServices.ApiInterface;
import com.drumdie.wallpaper.WebServices.ServiceGenerator;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PhotosFragment extends Fragment {
    private final String TAG = PhotosFragment.class.getSimpleName();

    @BindView(R.id.fragment_photos_progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.fragment_photos_recyclerview)
    RecyclerView recyclerView;


    private PhotosAdapter photosAdapter;
    private List<Photo> photos = new ArrayList<>();
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photos,container,false);
        unbinder = ButterKnife.bind(this, view);
        //recyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        photosAdapter = new PhotosAdapter(getActivity(), photos);
        recyclerView.setAdapter(photosAdapter);

        showProgressBar(true);
        getPhotos();

        return view;

    }
    private void getPhotos(){
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<List<Photo>>call = apiInterface.getPhotos();
        call.enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(@NotNull Call<List<Photo>> call, Response<List<Photo>> response) {
                if (response.isSuccessful()){
                    photos.addAll(response.body());
                    photosAdapter.notifyDataSetChanged();
                }else {
                    Log.e(TAG, "fail" + response.message());
                }
                showProgressBar(false);
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                Log.e(TAG, "fail" + t.getMessage());
                showProgressBar(false);

            }
        });
    }
    private void showProgressBar(boolean isSHow){
        if(isSHow){
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else {
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }
}

