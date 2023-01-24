package com.inkryptvideos.android_sample_app;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.inkryptvideos.android.InkInitParams;
import com.inkryptvideos.android.InkPlayerError;
import com.inkryptvideos.android.InkPlayerErrorCallback;
import com.inkryptvideos.android.InkPlayerFragment;
import com.inkryptvideos.android.InkPlayerFullScreenCallback;
import com.inkryptvideos.android_sample_app.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        LinearLayout container = findViewById(R.id.container);

        Button btn_fullscreen = findViewById(R.id.btn_go_fullscreen);
        Button btn_debug = findViewById(R.id.btn_debug_properties);







        InkPlayerFragment inkPlayerFragment = (InkPlayerFragment) getSupportFragmentManager().findFragmentById(R.id.ink_player_fragment);

        // preparing player parameters
        InkInitParams params = new InkInitParams.Builder()
                .setVideoId("301a0621b85041c99656e0a7597bdb4e")
                .setOtp("f523d8bb22544f189e95e6f82880dfa2881d6d9e2e07437a94a6066cea07b7bc")
                .setAutoplay(false) // set autoplay to off
                .setInitialAspectRatio(1.78f) // this is optional use it to set initial aspect ratio before any video is loaded
                //If you load another video the player will keep the same aspect ratio you don't need to call it twice
                .build();

        //Pass the created parameters to the bound player fragment
        inkPlayerFragment.init(params);



        //gofullscreen
        btn_fullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inkPlayerFragment.goFullScreen();
            }

        });

        // print properties to logcat
        btn_debug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("ink_debug", "isPlaying: " + inkPlayerFragment.isPlaying());
                Log.i("ink_debug", "isLoading: " + inkPlayerFragment.isLoading());
                Log.i("ink_debug", "video quality: " + inkPlayerFragment.getSelectedVideoQuality());
                Log.i("ink_debug", "current seek position: " + inkPlayerFragment.getCurrentSeekPosition());
                Log.i("ink_debug", "video length: " + inkPlayerFragment.getVideoLength());
            }

        });

        //Setting up error listeners
        inkPlayerFragment.setErrorCallback(new InkPlayerErrorCallback() {
            @Override
            public void onError(@NonNull InkPlayerError error) {
                switch (error.getCode()){
                    case InkPlayerError.ERROR_AUDIO_MANAGER: {
                        //todo: do something with this error
                        /* TODO: */
                    }
                    case InkPlayerError.ERROR_IO: { /* TODO: */ }
                    case InkPlayerError.ERROR_CONTENT_PARSING: { /* TODO: */ }
                    case InkPlayerError.ERROR_DECODING: { /* TODO: */ }
                    case InkPlayerError.ERROR_DRM: { /* TODO: */ Log.i("demotag", error.getMessage()); } // Error with the device DRM
                    case InkPlayerError.ERROR_MISSING_OTP: { /* TODO: */ }
                    case InkPlayerError.ERROR_MISSING_VIDEO_ID: { /* TODO: */ }
                    case InkPlayerError.ERROR_INVALID_OTP: { /* TODO: */ }
                    case InkPlayerError.ERROR_NETWORK: { /* TODO: */ }
                    case InkPlayerError.ERROR_RESOURCE_NOT_FOUND: { /* TODO: */ }
                }
            }
        });


        //Use this to hide other views so fragment can expand to the whole screen
        inkPlayerFragment.setFullscreenCallback(new InkPlayerFullScreenCallback() {
            @Override
            public void onEnterFullscreen() {
                container.setVisibility(View.GONE);
                Log.i("ink_full_screen", "fullScreen");
            }


            @Override
            public void onExitFullscreen() {
                container.setVisibility(View.VISIBLE);
                Log.i("ink_full_screen", "exited fullscreen");
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}