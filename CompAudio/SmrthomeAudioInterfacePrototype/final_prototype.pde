/*
*  Written by Paul Kim
* pkim49
* 902986200
*/
import beads.*;
import controlP5.*;
import java.util.List;

// Event Types
//boolean twitter;
//boolean email;
//boolean missedCall;
//boolean textMessage;
//boolean voiceMail;

// Context Samples
SamplePlayer workout;
SamplePlayer walking;
SamplePlayer socializing;
SamplePlayer presenting;

// Heartbeat
SamplePlayer heartbeatSample;

// Ugens
Gain masterGain;
Gain workoutGain;
Gain walkingGain;
Gain socializingGain;
Gain presentingGain;
Gain heartbeatGain;
Reverb reverb;

// Engine
Engine engine;

// Global Variable
int programTime = 0;
WavePlayer heartbeatSound;

void setup() {
  size(400, 320);
  // Initialize controlp5 and audio context
  ac = new AudioContext();
  p5 = new ControlP5(this);
    
  // Initialize engine
  engine = new Engine();
    
  ProblemListener problemListener = new ProblemListener(engine);
  engine.addListenerToServer(problemListener);
  
  initializeGains();
  
  // Set up View
  addLabel("eventTitle", "Events", 20);
  setUpCheckBoxes();
  setUpStartButton();
  addLabel("buttonTitle", "Action", 220);
  setUpActionButton();
  setUpGiveMoreInfoButton();
  
  ac.out.addInput(masterGain);
  ac.start();
}

public void initializeGains() {
  masterGain = new Gain(ac, 1, 1);
}

void draw() {
  if ((millis() - programTime) > 10000) {
    print(engine.getSamplePlayerQueue());
    print(engine.getSecondSamplePlayerQueue());
    engine.processNextNotification();
    programTime = millis();
  }
  
  if (engine.getGiveMoreInfo() && !engine.isPlaying()) {
    engine.setGiveMoreInfo(false);
    engine.getCurrentSample().setToLoopStart();
    engine.getCurrentSample().pause(true);
    engine.setIsPlaying(false);
    Notification currentHighestPriority = engine.getHighestPriorityNotification();
    if (currentHighestPriority.getAppliance().equalsIgnoreCase("firealarm")) {
      engine.addFireAlarmSamples();
    } else if (currentHighestPriority.getAppliance().equalsIgnoreCase("window")) {
      engine.addWindowSamples();
    } else if (currentHighestPriority.getAppliance().equalsIgnoreCase("lightbulb")) {
      engine.addLightBulbSamples();
    } else if (currentHighestPriority.getAppliance().equalsIgnoreCase("smart tv")) {
      engine.addSmartTvSamples();
    }
  }
  
  if (!engine.isPlaying()) {
      engine.playNextSample();
   }
  background(back);
}