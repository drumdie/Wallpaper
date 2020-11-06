package com.drumdie.wallpaper.WebServices;

import com.drumdie.wallpaper.Models.Photo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("photos")
    Call<List<Photo>>getPhotos();
}
