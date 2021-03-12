package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class ComposeActivity extends AppCompatActivity {

    EditText tweetCompose;
    TextView charCounter;
    Button tweetButton;
    TwitterClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        client= TwitterApp.getRestClient(this);
        tweetButton = findViewById(R.id.tweet);
        tweetCompose = findViewById(R.id.tweetCompose);
        charCounter = findViewById(R.id.characterCounter);

        tweetCompose.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                charCounter.setText(tweetCompose.getText().toString().length()+"/280");
                if(tweetCompose.getText().toString().length()>280){
                    charCounter.setTextColor(Color.parseColor("#FF0000"));
                    tweetButton.setVisibility(View.INVISIBLE);
                }else{
                    charCounter.setTextColor(Color.parseColor("#14171A"));
                    tweetButton.setVisibility(View.VISIBLE);
                }
            }
        });

        tweetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String tweetContent = tweetCompose.getText().toString();
               if(tweetContent.isEmpty()){
                   Toast.makeText(ComposeActivity.this,"Sorry, your tweet can not be empty",Toast.LENGTH_SHORT).show();
                   return;
               }
               if(tweetContent.length()>280){
                   Toast.makeText(ComposeActivity.this,"Sorry, your tweet is too long",Toast.LENGTH_SHORT).show();
                   return;
               }
                Log.d("ADMAS",tweetContent);
                client.publishTweet(tweetContent, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        Tweet tweet = Tweet.fromJson(json.jsonObject);
                        Intent intent = new Intent();
                        intent.putExtra("tweet", Parcels.wrap(tweet));
                        setResult(RESULT_OK,intent);
                        finish();

                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.e("fail",String.valueOf(throwable));
                    }
                });
            }
        });

    }
}