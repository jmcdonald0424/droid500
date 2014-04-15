package com.fivehundred.droid500.modules;

import android.content.Context;
import com.fivehundred.droid500.activity.MainActivity;
import com.fivehundred.droid500.application.MainApplication;
import com.fivehundred.droid500.game.MainGame;
import com.fivehundred.droid500.game.controllers.GameController;
import com.fivehundred.droid500.game.controllers.GameControllerImpl;
import com.fivehundred.droid500.view.GLRenderer;
import com.fivehundred.droid500.view.animations.DealerAnimation;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(
    injects = {MainActivity.class, MainGame.class, GLRenderer.class, 
                DealerAnimation.class}, library = true
)

public class MainModule{
    private final MainApplication app;
    private final Context context;
    
    public MainModule(MainApplication app){
        this.app = app;
        this.context = this.app.getApplicationContext();
    }
    
    @Singleton
    @Provides 
    public Context provideApplicationContext(){
        return this.context;
    }
    
    @Singleton
    @Provides
    public GameController provideGameController(){
        return new GameControllerImpl();
    }
}