package com.example.instagramclone;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instagramclone.fragments.DetailsFragment;
import com.parse.ParseFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    private Context context;
    private List<Post> posts;

    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvUsername;
        private ImageView ivImage;
        private TextView tvDescription;
        private Post currentPost;
        private ImageView ivProfile;
        private ImageButton ibtnHeart;
        private ImageButton ibtnComment;
        private ImageButton ibtnShare;
        private TextView tvTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            ivProfile = itemView.findViewById(R.id.ivProfile);
            ibtnHeart = itemView.findViewById(R.id.ibtnHeart);
            ibtnComment = itemView.findViewById(R.id.ibtnComment);
            ibtnShare = itemView.findViewById(R.id.ibtnShare);
            tvTime = itemView.findViewById(R.id.tvTime);

            View.OnClickListener detailsListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity) context).fragmentManager.beginTransaction().replace(R.id.flContainer, DetailsFragment.newInstance(currentPost)).commit();
                }
            };
            //show details when click on image
            ivImage.setOnClickListener(detailsListener);
        }

        public void bind(Post post) {
            currentPost = post;
            //Bind the post data to the view elements
            tvUsername.setText(post.getUser().getUsername());
            tvDescription.setText(post.getDescription());
            ParseFile image = post.getImage();
            if(image!=null){
                Glide.with(context).load(post.getImage().getUrl()).into(ivImage);
            }
            ParseFile pimage = post.getProfilePic();
            if(pimage!=null){
                (Glide.with(context).load(post.getProfilePic().getUrl()).circleCrop()).into(ivProfile);
            }
            String relativeTimeAgo = getRelativeTimeAgo(post.getCreatedAt().toString());
            tvTime.setText(relativeTimeAgo);
        }

        // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
        public String getRelativeTimeAgo(String rawJsonDate) {
            String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
            SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
            sf.setLenient(true);

            String relativeDate = "";
            try {
                long dateMillis = sf.parse(rawJsonDate).getTime();
                relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                        System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return relativeDate;
        }
    }

    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }
}