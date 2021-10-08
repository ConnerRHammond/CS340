package edu.byu.cs.tweeter.client.model.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.model.service.Tasks.BackgroundTask;

public abstract class ServiceBase <T extends BackgroundTask> {
     public void Execute (T task){
         ExecutorService executor = Executors.newSingleThreadExecutor();
         executor.execute(task);
     }
}
