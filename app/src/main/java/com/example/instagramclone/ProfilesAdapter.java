package com.example.instagramclone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import java.util.List;

public class ProfilesAdapter extends RecyclerView.Adapter<ProfilesAdapter.ViewHolder>{
    private Context context;
    private List<Post> posts;

    public ProfilesAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ProfilesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_profile_post, parent, false);
        return new ProfilesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfilesAdapter.ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivProfilePostImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfilePostImage = itemView.findViewById(R.id.ivProfilePostImage);
        }

        public void bind(Post post) {
            ParseFile image = post.getImage();
            if(image!=null){
                Glide.with(context).load(image.getUrl()).into(ivProfilePostImage);
            }
        }
    }
}

