package com.example.instagramclone.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.instagramclone.Post;
import com.example.instagramclone.R;
import com.parse.ParseFile;

import org.parceler.Parcels;


public class DetailsFragment extends Fragment {
    ImageView ivDetailProfile;
    ImageView ivDetailPic;
    TextView tvDetailUsername;
    TextView tvCaption;
    TextView tvTimestamp;
    Post currentPost;

    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment newInstance(Post currentPost) {
        DetailsFragment fragmentDetail = new DetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable("currentPost", Parcels.wrap(currentPost));
        fragmentDetail.setArguments(args);
        return fragmentDetail;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivDetailProfile = view.findViewById(R.id.ivDetailProfile);
        ivDetailPic = view.findViewById(R.id.ivDetailPic);
        tvDetailUsername = view.findViewById(R.id.tvDetailUsername);
        tvCaption = view.findViewById(R.id.tvCaption);
        tvTimestamp = view.findViewById(R.id.tvTimestamp);

        currentPost = Parcels.unwrap(getArguments().getParcelable("currentPost"));
        tvDetailUsername.setText(currentPost.getUser().getUsername());
        tvCaption.setText(currentPost.getDescription());
        tvTimestamp.setText(currentPost.getCreatedAt().toString());
        ParseFile image = currentPost.getImage();
        if(image!=null){
            Glide.with(getContext()).load(currentPost.getImage().getUrl()).into(ivDetailPic);
        }
        image = currentPost.getProfilePic();
        if(image!=null){
            Glide.with(getContext()).load(currentPost.getProfilePic().getUrl()).circleCrop().into(ivDetailProfile);
        }
    }
}