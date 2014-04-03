package com.fivehundred.droid500.application;

import android.app.Application;
import com.fivehundred.droid500.modules.MainModule;
import dagger.ObjectGraph;
import java.util.Arrays;
import java.util.List;

public class MainApplication extends Application{
    
    // Dagger ObjectGraph used for dependency injection
    private ObjectGraph objectGraph;
    
    @Override
    public void onCreate(){
        super.onCreate();
        Object[] modules = getModules().toArray();
        objectGraph = ObjectGraph.create(modules);
    }
    
    protected List<Object> getModules(){
        return Arrays.<Object>asList(new MainModule(this));
    }
    
    public ObjectGraph getObjectGraph(){
        return this.objectGraph;
    }
}