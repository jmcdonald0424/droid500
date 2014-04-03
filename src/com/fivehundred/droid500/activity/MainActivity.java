package com.fivehundred.droid500.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import com.fivehundred.droid500.activity.fragments.MainFragment;
import com.fivehundred.droid500.application.MainApplication;
import com.fivehundred.droid500.R;
import com.fivehundred.droid500.game.MainGame;
import com.fivehundred.droid500.game.controllers.GameController;
import com.fivehundred.droid500.view.GLSurf;
import javax.inject.Inject;

public class MainActivity extends Activity {

    private GLSurfaceView glSurfaceView; // Our OpenGL Surfaceview    
    private MainGame game; // Main game will be held and referenced here    
    private MainFragment fragment; // MainFragment allows persistence 

    // Controllers
    @Inject GameController gameController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Turn off the window's title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Super
        super.onCreate(savedInstanceState);
        
        // Inject into object graph
        MainApplication app = (MainApplication)getApplication();
        app.getObjectGraph().inject(this);

        // Fullscreen mode
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Create new game or pull existing game from fragment
        handleGameFragment();
        
        // We create our Surfaceview for our OpenGL here.
        glSurfaceView = new GLSurf(this);

        // Set our view.
        setContentView(R.layout.main);

        // Retrieve our Relative layout from our main layout we just set to our view.
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.gamelayout);

        // Attach our surfaceview to our relative layout from our main layout.
        RelativeLayout.LayoutParams glParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        layout.addView(glSurfaceView, glParams);
    }

    @Override
    protected void onPause() {
        super.onPause();
        glSurfaceView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        glSurfaceView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fragment.setGame(game);
    }

    private void handleGameFragment() {
        FragmentManager manager = getFragmentManager();
        fragment = (MainFragment) manager.findFragmentByTag("game");
        if (fragment == null) {
            fragment = new MainFragment();
            manager.beginTransaction().add(fragment, "game").commit();
            game = gameController.createNewGame();
            fragment.setGame(game);
        } else {
            game = fragment.getGame();
        }
    }

    public GLSurfaceView getGlSurfaceView() {
        return glSurfaceView;
    }

    public MainGame getGame() {
        return game;
    }
}
