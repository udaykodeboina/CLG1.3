package com.church.clg.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.church.clg.Config;
import com.church.clg.R;
import com.church.clg.RecyclerViewOnClickListener;
import com.church.clg.Adapter.YoutubeVideoAdapter;
import com.church.clg.Model.YoutubeVideoModel;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class VideoActivity  extends YouTubeBaseActivity
        implements YouTubePlayer.OnInitializedListener {
    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;
    private MyPlayerStateChangeListener playerStateChangeListener;
    private MyPlaybackEventListener playbackEventListener;
    YouTubePlayer player;
    private RecyclerView recyclerView;
    private static final String TAG = MainActivity.class.getSimpleName();
    int mVideoType;;
    String videoid,videoid1,videotitle,videourl,videoTime;
    String url;
    private String mNextPageToken = "";




    ArrayList<YoutubeVideoModel> youtubeVideoModelArrayList;


    YoutubeVideoAdapter adapter;

    public static final SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    String niceDateStr;




    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(Config.API_KEY, this);
        youtubeVideoModelArrayList = new ArrayList<>();

        playerStateChangeListener = new MyPlayerStateChangeListener();
        playbackEventListener = new MyPlaybackEventListener();

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle(getResources().getString(R.string.app_name));



        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        String channelId =getResources().getString(R.string.url_put_chutney);

        populateRecyclerView(channelId);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(Config.API_KEY, this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubeView;
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        this.player = player;
        player.setPlayerStateChangeListener(playerStateChangeListener);

        player.setPlaybackEventListener(playbackEventListener);


    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format(getString(R.string.player_error), errorReason.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }



    private final class MyPlaybackEventListener implements YouTubePlayer.PlaybackEventListener {

        @Override
        public void onPlaying() {
            // Called when playback starts, either due to user action or call to play().
            //showMessage("Playing");
        }

        @Override
        public void onPaused() {
            // Called when playback is paused, either due to user action or call to pause().
            // showMessage("Paused");
        }

        @Override
        public void onStopped() {
            // Called when playback stops for a reason other than being paused.
            //showMessage("Stopped");
        }

        @Override
        public void onBuffering(boolean b) {
            // Called when buffering starts or ends.
        }

        @Override
        public void onSeekTo(int i) {
            // Called when a jump in playback position occurs, either
            // due to user scrubbing or call to seekRelativeMillis() or seekToMillis()
        }
    }

    private final class MyPlayerStateChangeListener implements YouTubePlayer.PlayerStateChangeListener {

        @Override
        public void onLoading() {
            // Called when the player is loading a video
            // At this point, it's not ready to accept commands affecting playback such as play() or pause()
        }

        @Override
        public void onLoaded(String s) {
            // Called when a video is done loading.
            // Playback methods such as play(), pause() or seekToMillis(int) may be called after this callback.
        }

        @Override
        public void onAdStarted() {
            // Called when playback of an advertisement starts.
        }

        @Override
        public void onVideoStarted() {
            // Called when playback of the video starts.
        }

        @Override
        public void onVideoEnded() {
            // Called when the video reaches its end.
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {
            // Called when an error occurs.
        }
    }



    /**
     * populate the recyclerview and implement the click event here
     */
    private void populateRecyclerView(final String channelId) {

        if (this.mVideoType == 2) {
            url = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet,id&fields=nextPageToken,pageInfo(totalResults),items(snippet(title,thumbnails,publishedAt,resourceId(videoId)))&key=" +Config.API_KEY + "&" + Config.PARAM_PLAYLIST_ID_YOUTUBE + channelId + "&" + Config.PARAM_PAGE_TOKEN_YOUTUBE + this.mNextPageToken + "&" + Config.PARAM_MAX_RESULT_YOUTUBE + 50;
        } else {
            url = "https://www.googleapis.com/youtube/v3/search?part=snippet,id&order=date&type=video&fields=nextPageToken,pageInfo(totalResults),items(id(videoId),snippet(title,thumbnails,publishedAt))&key=" +Config.API_KEY + "&" + Config.PARAM_CHANNEL_ID_YOUTUBE + channelId + "&" + Config.PARAM_PAGE_TOKEN_YOUTUBE + this.mNextPageToken + "&" + Config.PARAM_MAX_RESULT_YOUTUBE + 50;
        }
        final ProgressDialog pDialog = new ProgressDialog(VideoActivity.this);
        pDialog.setMessage("Please Wait...");
        pDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Url",""+Config.FINAL_URL + channelId + "&key=" + Config.API_KEY);
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("items");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        JSONObject jsonVideoId = jsonObject1.getJSONObject("id");
                        JSONObject jsonsnippet = jsonObject1.getJSONObject("snippet");
                        JSONObject jsonObjectthumbnail = jsonsnippet.getJSONObject("thumbnails");

                        JSONObject jsonObjectdefault = jsonObjectthumbnail.getJSONObject("default");
                        videoid = jsonVideoId.getString("videoId");
                        videotitle = jsonsnippet.getString("title");
                        videourl = jsonObjectdefault.getString("url");
                        videoTime = jsonsnippet.getString("publishedAt");
                        try {
                            Date date = inputFormat.parse(videoTime);
                            niceDateStr = String.valueOf(DateUtils.getRelativeTimeSpanString(date.getTime(), Calendar.getInstance().getTimeInMillis(), DateUtils.MINUTE_IN_MILLIS));


                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        youtubeVideoModelArrayList.add(new YoutubeVideoModel(videoid, videotitle, videourl, niceDateStr));



                    }
                    adapter = new YoutubeVideoAdapter(VideoActivity.this, youtubeVideoModelArrayList);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    videoid1 = "" + youtubeVideoModelArrayList.get(0).getVideoId();
                    Log.e("VideoId",videoid1);
                    player.cueVideo(videoid1);

                    pDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);


        //set click event
        recyclerView.addOnItemTouchListener(new RecyclerViewOnClickListener(this, new RecyclerViewOnClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                player.loadVideo(youtubeVideoModelArrayList.get(position).getVideoId());
            }
        }));
    }



}
