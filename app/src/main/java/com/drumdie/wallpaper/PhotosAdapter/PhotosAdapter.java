package com.drumdie.wallpaper.PhotosAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.drumdie.wallpaper.Models.Photo;
import com.drumdie.wallpaper.R;
import com.drumdie.wallpaper.Utils.GlideApp;
import com.drumdie.wallpaper.Utils.SquareImage;

import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ViewHolder> {

    private Context context;
    private List<Photo> photos;

    public PhotosAdapter(Context context, List<Photo> photos){
        this.context = context;
        this.photos = photos;
    }


    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_photo, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        Photo photo = photos.get(position);
        holder.userName.setText(photo.getUser().getUsername());

        GlideApp
                .with(context)
                .load(photo.getUrl().getRegular())
                .placeholder(R.drawable.placeholder)
                .override(600, 600)
                .into(holder.photo);

        GlideApp
                .with(context)
                .load(photo.getUser().getProfileImage().getSmall())
                .into(holder.userAvatar);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_photo_user_avatar)
        CircleImageView userAvatar;
        @BindView(R.id.item_photo_username)
        TextView userName;
        @BindView(R.id.item_photo_photo)
        SquareImage photo;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
