package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.List;
import java.util.zip.Inflater;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder>{
    Context context;
    List<Tweet> tweets;

    public TweetsAdapter(Context context,List<Tweet> tweets){
        this.context=context;
        this.tweets=tweets;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(context).inflate(R.layout.tweet_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tweet tweet = tweets.get(position);

        holder.bind(tweet);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    public void clear(){
        tweets.clear();
        notifyDataSetChanged();
    }
    public void addAll(List<Tweet> tweetList){
        tweets.addAll(tweetList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView profileImage;
        TextView tweetBody;
        TextView userName;
        TextView timestamp;
        TextView screenName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.profileImage);
            tweetBody = itemView.findViewById(R.id.tweet_body_text_view);
            userName = itemView.findViewById(R.id.userNameTextView);
            screenName =itemView.findViewById(R.id.atName_text_view);
            timestamp = itemView.findViewById(R.id.time_stamp_text_view);
        }
        public void bind(Tweet tweet){
            tweetBody.setText(tweet.body);
            userName.setText(tweet.user.userName);
            screenName.setText("@"+tweet.user.name);
            timestamp.setText(TimeFormatter.getTimeDifference(tweet.createdAt));
            Glide.with(context).load(tweet.user.profileImageUrl).transform(new CenterCrop(),new RoundedCorners(15)).into(profileImage);
        }
    }
}
