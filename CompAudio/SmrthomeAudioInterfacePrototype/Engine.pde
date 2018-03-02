import java.util.List;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.LinkedList;

public class Engine {

  private String currentContext;
  private boolean window;
  private boolean firealarm;
  private boolean smarttv;
  private boolean lightbulb;
  private boolean currentTaskComplete;
  private boolean giveMoreInfo;
  private boolean sampleIsPlaying;
  private boolean hasLoadedNotifications = false;

  private int heartbeatInterval;
  private int newNotificationCounter;
  
  private Gain mainEngineGain = new Gain(ac, 1, 1);
  private Gain motionDetectorNotificationGain = new Gain(ac, 1, 1);
  private Gain homeNotificationGain = new Gain(ac, 1, 1);
  private Gain deviceNotificationGain = new Gain(ac, 1, 1);
  private Gain insecureNotificationGain = new Gain(ac, 1, 1);
  private Gain lowPowerNotificationGain = new Gain(ac, 1, 1);
  private Gain successNotificationGain = new Gain(ac, 1, 1);
  private Gain smokeDetectorTTSGain = new Gain(ac, 1, 1);
  private Gain windowTTSGain = new Gain(ac, 1, 1);
  private Gain lightbulbTTSGain = new Gain(ac, 1, 1);
  private Gain tvGain = new Gain(ac, 1, 1);
  private Gain networkAlertGain = new Gain(ac, 1, 1);
  private Gain newNotificationsGain = new Gain(ac, 1, 1);

  private NotificationServer server = new NotificationServer();
  // Text to Speech
  private TextToSpeechMaker ttsMaker = new TextToSpeechMaker();

  private List notificationQueue;
  private Queue samplePlayerQueue;
  private Queue secondSamplePlayerQueue;

  // Samples used by Engine (Earcons)
  private SamplePlayer motionDetectorNotification;
  private SamplePlayer homeNotification;
  private SamplePlayer deviceNotification;
  private SamplePlayer insecureNotification;
  private SamplePlayer lowPowerNotification;
  private SamplePlayer successNotification;
  private SamplePlayer smokeDetectorTTS;
  private SamplePlayer windowTTS;
  private SamplePlayer lightbulbTTS;
  private SamplePlayer tv;
  private SamplePlayer networkAlert;
  private SamplePlayer newNotifications;
  
  private SamplePlayer nextSample;
  
  private Notification highestPriorityNotification;

  public Engine() {
    loadEngineEarcons();
    //presentingNotificationSound.pause(true);

    //notificationQueue = new PriorityQueue<Notification>(10, new Comparator<Notification>() {
    //  public int compare(Notification notification1, Notification notification2) {
    //    if (notification1.getPriorityLevel() < notification2.getPriorityLevel()) return -1;
    //    if (notification1.getPriorityLevel() > notification2.getPriorityLevel()) return 1;
    //    return 0;
    //  }
    //}
    //);
    
    notificationQueue = new ArrayList();

    samplePlayerQueue = new LinkedList();
    secondSamplePlayerQueue = new LinkedList();

    motionDetectorNotificationGain.addInput(motionDetectorNotification);
    homeNotificationGain.addInput(homeNotification);
    deviceNotificationGain.addInput(deviceNotification);
    insecureNotificationGain.addInput(insecureNotification);
    lowPowerNotificationGain.addInput(lowPowerNotification);
    successNotificationGain.addInput(successNotification);
    smokeDetectorTTSGain.addInput(smokeDetectorTTS);
    windowTTSGain.addInput(windowTTS);
    lightbulbTTSGain.addInput(lightbulbTTS);
    tvGain.addInput(tv);
    networkAlertGain.addInput(networkAlert);
    newNotificationsGain.addInput(newNotifications);
    mainEngineGain.addInput(motionDetectorNotificationGain);
    mainEngineGain.addInput(homeNotificationGain);
    mainEngineGain.addInput(deviceNotificationGain);
    mainEngineGain.addInput(insecureNotificationGain);
    mainEngineGain.addInput(lowPowerNotificationGain);
    mainEngineGain.addInput(successNotificationGain);
    mainEngineGain.addInput(smokeDetectorTTSGain);
    mainEngineGain.addInput(windowTTSGain);
    mainEngineGain.addInput(lightbulbTTSGain);
    mainEngineGain.addInput(tvGain);
    mainEngineGain.addInput(networkAlertGain);
    mainEngineGain.addInput(newNotificationsGain);
    ac.out.addInput(mainEngineGain);
  }

  public void addListenerToServer(NotificationListener listener) {
    server.addListener(listener);
  }

  public void restartEngine() {
  }

  public void loadDataStream1() {
    server.clearEventStream();
    server.loadEventStream("ExampleData_1.json");
    this.newNotificationCounter = 0;
    this.hasLoadedNotifications = true;
  } 

  public void loadDataStream2() {
    server.loadEventStream("ExampleData_2.json");
  }

  public void loadDataStream3() {
    server.loadEventStream("ExampleData_3.json");
  }

  public void addNotificationToQueue(Notification notification) {
    this.notificationQueue.add(notification);
  }

  public void addSamplePlayerToQueue(SamplePlayer samplePlayer) {
    this.samplePlayerQueue.add(samplePlayer);
  }
  
  public void addSamplePlayerToSecondQueue(SamplePlayer samplePlayer) {
    this.secondSamplePlayerQueue.add(samplePlayer);
  }

  public void loadEngineEarcons() {
    motionDetectorNotification = getSamplePlayer("beep.wav");
    motionDetectorNotification.setEndListener(
      new Bead() {
      public void messageReceived(Bead mess) {
        motionDetectorNotification.pause(true);
        motionDetectorNotification.setToLoopStart();
        setIsPlaying(false);
      }
    }
    );
    motionDetectorNotification.pause(true);
    
    homeNotification = getSamplePlayer("home.mp3");
    homeNotification.setEndListener(
      new Bead() {
      public void messageReceived(Bead mess) {
        homeNotification.pause(true);
        homeNotification.setToLoopStart();
        setIsPlaying(false);
      }
    }
    );
    homeNotification.pause(true);
    
    deviceNotification = getSamplePlayer("device.wav");
    deviceNotification.setEndListener(
      new Bead() {
      public void messageReceived(Bead mess) {
        deviceNotification.pause(true);
        deviceNotification.setToLoopStart();
        setIsPlaying(false);
      }
    }
    );
    deviceNotification.pause(true);
    
    insecureNotification = getSamplePlayer("insecure.wav");
    insecureNotification.setEndListener(
      new Bead() {
      public void messageReceived(Bead mess) {
        insecureNotification.pause(true);
        insecureNotification.setToLoopStart();
        setIsPlaying(false);
      }
    }
    );
    insecureNotification.pause(true);
    
    lowPowerNotification = getSamplePlayer("power.mp3");
    lowPowerNotification.setEndListener(
      new Bead() {
      public void messageReceived(Bead mess) {
        lowPowerNotification.pause(true);
        lowPowerNotification.setToLoopStart();
        setIsPlaying(false);
      }
    }
    );
    lowPowerNotification.pause(true);
    
    successNotification = getSamplePlayer("success.wav");
    successNotification.setEndListener(
      new Bead() {
      public void messageReceived(Bead mess) {
        successNotification.pause(true);
        successNotification.setToLoopStart();
        setIsPlaying(false);
      }
    }
    );
    successNotification.pause(true);
    
    smokeDetectorTTS = getSamplePlayer("smokedetectorTTS.wav");
    smokeDetectorTTS.setEndListener(
      new Bead() {
      public void messageReceived(Bead mess) {
        smokeDetectorTTS.pause(true);
        smokeDetectorTTS.setToLoopStart();
        setIsPlaying(false);
      }
    }
    );
    smokeDetectorTTS.pause(true);
    
    windowTTS = getSamplePlayer("windowTTS.wav");
    windowTTS.setEndListener(
      new Bead() {
      public void messageReceived(Bead mess) {
        windowTTS.pause(true);
        windowTTS.setToLoopStart();
        setIsPlaying(false);
      }
    }
    );
    windowTTS.pause(true);
    
    lightbulbTTS = getSamplePlayer("lightbulbTTS.mp3");
    lightbulbTTS.setEndListener(
      new Bead() {
      public void messageReceived(Bead mess) {
        lightbulbTTS.pause(true);
        lightbulbTTS.setToLoopStart();
        setIsPlaying(false);
      }
    }
    );
    lightbulbTTS.pause(true);
    
    tv = getSamplePlayer("tv.wav");
    tv.setEndListener(
      new Bead() {
      public void messageReceived(Bead mess) {
        tv.pause(true);
        tv.setToLoopStart();
        setIsPlaying(false);
      }
    }
    );
    tv.pause(true);
    
    networkAlert = getSamplePlayer("networkalert.wav");
    networkAlert.setEndListener(
      new Bead() {
      public void messageReceived(Bead mess) {
        networkAlert.pause(true);
        networkAlert.setToLoopStart();
        setIsPlaying(false);
      }
    }
    );
    networkAlert.pause(true);
    
    newNotifications = getSamplePlayer("newnotifications.mp3");
    newNotifications.setEndListener(
      new Bead() {
      public void messageReceived(Bead mess) {
        newNotifications.pause(true);
        newNotifications.setToLoopStart();
        setIsPlaying(false);
      }
    }
    );
    newNotifications.pause(true);
    
  }

  public void stopEvents() {
    server.stopEventStream();
  }
  
  public void addFireAlarmSamples() {
    addSamplePlayerToQueue(smokeDetectorTTS); // home sound sampleplayer
    addSamplePlayerToQueue(lowPowerNotification); // low power sound sampleplayer
  }
  
  public void addWindowSamples() {
    addSamplePlayerToQueue(windowTTS);
    addSamplePlayerToQueue(insecureNotification);
  }
  
  public void addSmartTvSamples() {
    addSamplePlayerToQueue(tv);
    addSamplePlayerToQueue(networkAlert);
  }
  
  public void addLightBulbSamples() {
    addSamplePlayerToQueue(lightbulbTTS);
  }

  public void processNextNotification() { 
    if (hasLoadedNotifications) {
      addSamplePlayerToQueue(generateTextToSpeechSample(newNotificationCounter + "New Notifications"));
      this.hasLoadedNotifications = false;
    }
    
    if (currentTaskComplete) {
      getNotificationQueue().remove(highestPriorityNotification);
      highestPriorityNotification = null;
      currentTaskComplete = false;
    }
    //print(getNotificationQueue());
    if (!getNotificationQueue().isEmpty()) {
      if (highestPriorityNotification == null) {
        highestPriorityNotification = getNotificationQueue().get(0);
        for (int i = 1; i < getNotificationQueue().size(); i++){
          if (getNotificationQueue().get(i).getPriorityLevel() > highestPriorityNotification.getPriorityLevel()) {
            highestPriorityNotification = getNotificationQueue().get(i);
          }
        }
        if (highestPriorityNotification.getPriorityLevel() >=3) {
          addSamplePlayerToQueue(deviceNotification);
        } else {
          addSamplePlayerToQueue(homeNotification);
        }
      }
      //if (highestPriorityNotification.getAppliance().equalsIgnoreCase("firealarm")) {
      //  //addSamplePlayerToSecondQueue(smokeDetectorTTS); // home sound sampleplayer
      //  //addSamplePlayerToSecondQueue(lowPowerNotification); // low power sound sampleplayer
        
      //} else if (highestPriorityNotification.getCategory().equalsIgnoreCase("device")) {
      //  addSamplePlayerToQueue(deviceNotification); // device sound sampleplayer
      //  addSamplePlayerToQueue(generateTextToSpeechSample("Problem with " + highestPriorityNotification.getAppliance() + " in " + highestPriorityNotification.getLocation()));
      //  if (highestPriorityNotification.getProblem().equalsIgnoreCase("power")) {
      //    addSamplePlayerToQueue(lowPowerNotification); // power sound sampleplayer
      //  } else {
      //    addSamplePlayerToQueue(insecureNotification); // insecure sound sampleplayer
      //  }
        
      //} 
 
    }
  }

  public void playNextSample() {
    
    if (!getSamplePlayerQueue().isEmpty()) {
      nextSample = getSamplePlayerQueue().remove();
      nextSample.pause(false);
      setIsPlaying(true);
    }
  }
  
  public void playNextSampleFromSecondQueue() {
    if (!getSamplePlayerQueue().isEmpty()) {
      nextSample = getSecondSamplePlayerQueue().remove();
      nextSample.pause(false);
      setIsPlaying(true);
    }
  }
  
  public void clearNextTwoSamplesFromSecondQueue() {
    getSecondSamplePlayerQueue().remove();
    getSecondSamplePlayerQueue().remove();
  }

  public SamplePlayer generateTextToSpeechSample(String text) {
    String ttsFilePath = ttsMaker.createTTSWavFile(text);

    SamplePlayer sp = getSamplePlayer(ttsFilePath, true);
    sp.pause(true);
    sp.setKillListener(
      new Bead() {
      public void messageReceived(Bead mess) {
        setIsPlaying(false);
      }
    }
    );
    mainEngineGain.addInput(sp);
    return sp;
  }

  /*
  * Getters and Setters
   */
  public void setEventTypes(List<Toggle> eventTypeList) {
    for (Toggle eventType : eventTypeList) {
      if (eventType.getLabel().equalsIgnoreCase("Fire Alarm")) {
        firealarm = eventType.getState();
      }
      if (eventType.getLabel().equalsIgnoreCase("Window")) {
        window = eventType.getState();
      }
      if (eventType.getLabel().equalsIgnoreCase("Smart Tv")) {
        smarttv = eventType.getState();
      }
      if (eventType.getLabel().equalsIgnoreCase("lightbulb")) {
        lightbulb = eventType.getState();
      }
      
    }
  }

  public List getEventTypes() {
    List<Boolean> eventTypeList = new ArrayList();
    eventTypeList.add(firealarm);
    eventTypeList.add(window);
    eventTypeList.add(smarttv);
    eventTypeList.add(lightbulb);

    return eventTypeList;
  }

  public Queue<SamplePlayer> getSamplePlayerQueue() {
    return this.samplePlayerQueue;
  }
  
  public Queue<SamplePlayer> getSecondSamplePlayerQueue() {
    return this.secondSamplePlayerQueue;
  }

  public List<Notification> getNotificationQueue() {
    return this.notificationQueue;
  }

  public Notification getHighestPriorityNotification() {
    return this.highestPriorityNotification;
  }

  public void setContext(String currentContext) {
    this.currentContext = currentContext;
  }

  public String getContext() {
    return this.currentContext;
  }

  public void setIsPlaying(boolean isPlaying) {
    this.sampleIsPlaying = isPlaying;
  }

  public boolean isPlaying() {
    return this.sampleIsPlaying;
  }

  public void setHeartbeatInterval(int millis) {
    this.heartbeatInterval = millis;
  }

  public int getHeartbeatInterval() {
    return this.heartbeatInterval;
  }

  public boolean getFireAlarmNotifications() {
    return this.firealarm;
  }

  public boolean getWindowNotifications() {
    return this.window;
  }
  
  public boolean getSmartTvNotifications() {
    return this.smarttv;
  }

  public boolean getLightbulbNotifications() {
    return this.lightbulb;
  }

  public void setCurrentTaskComplete(boolean state) {
    this.currentTaskComplete = state;
  }

  public boolean getCurrentTaskComplete() {
    return this.currentTaskComplete;
  }
  
  public void setGiveMoreInfo(boolean state) {
    this.giveMoreInfo = state;
  }

  public boolean getGiveMoreInfo() {
    return this.giveMoreInfo;
  }
  
  public void incrementNewNotificationCounter() {
    this.newNotificationCounter++;
  }
  
  public SamplePlayer getCurrentSample() {
    return this.nextSample;
  }


  public void sortByPriority() {
  }
  
  public void playSuccessSound() {
    this.successNotification.pause(false);
  }
}