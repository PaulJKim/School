// P5
ControlP5 p5;

// View elements and toggles
CheckBox typeCheckBox;
List<Toggle> eventTypeList;
Toggle firealarm;
Toggle window;
Toggle smarttv;
Toggle lightbulb;

color back = color(61, 65, 75);
color fore = color(218, 218, 218);

int btn_w = 80;
int btn_h = 25;
int title_start = 30;

// Separate module for Managing view elements

public void addLabel(String name, String str, int pos) {
  p5.addTextlabel(name)
    .setText(str)
    .setPosition(title_start, pos) // w, h
    .setColorValue(fore)
    .setFont(createFont("Lucida Console", 16));
}

public void setUpCheckBoxes() {
  firealarm = new Toggle(p5, "Fire Alarm").setSize(20, 20);
  window = new Toggle(p5, "Window").setSize(20, 20);
  smarttv = new Toggle(p5, "Smart TV").setSize(20, 20);
  lightbulb = new Toggle(p5, "Lightbulb").setSize(20, 20);

  eventTypeList = new ArrayList();
  eventTypeList.add(firealarm);
  eventTypeList.add(window);
  eventTypeList.add(smarttv);
  eventTypeList.add(lightbulb);

  engine.setEventTypes(eventTypeList);

  firealarm.addListener(new ControlListener() {
    public void controlEvent(ControlEvent theEvent) {
      engine.setEventTypes(eventTypeList);
    }
  }
  );

  window.addListener(new ControlListener() {
    public void controlEvent(ControlEvent theEvent) {
      engine.setEventTypes(eventTypeList);
      //print(engine.getEventTypes());
    }
  }
  );
  
  smarttv.addListener(new ControlListener() {
    public void controlEvent(ControlEvent theEvent) {
      engine.setEventTypes(eventTypeList);
      print(engine.getEventTypes());
    }
  }
  );
  
  lightbulb.addListener(new ControlListener() {
    public void controlEvent(ControlEvent theEvent) {
      engine.setEventTypes(eventTypeList);
      print(engine.getEventTypes());
    }
  }
  );

  addTypeCheckBoxes("Event Types");
}

public CheckBox addTypeCheckBoxes(String label) {
  return p5.addCheckBox(label)
    .setPosition(40, 55)
    .setSize(20, 20)
    .setItemsPerRow(2)
    .setSpacingColumn(120)
    .setSpacingRow(30)
    .setColorBackground(color(40, 40, 40))
    .setColorForeground(color(92, 92, 92))
    .setColorActive(color(250, 250, 250))
    .addItem(firealarm, 1)
    .addItem(window, 2)
    .addItem(smarttv, 3)
    .addItem(lightbulb, 4);
}

public void setUpActionButton() {
  p5.addButton("action")
    .setPosition(34, 255)
    .setSize(btn_w, btn_h)
    .setColorBackground(color(232, 232, 232))
    .setColorForeground(color(202, 202, 202))
    .setColorActive(color(172, 172, 172))
    .setColorLabel(color(0, 0, 0))
    .setLabel("Complete Task")
    .onPress(new CallbackListener() {
    public void controlEvent(CallbackEvent theEvent) {
         engine.setCurrentTaskComplete(true);
         engine.playSuccessSound();
    }});
}

public void setUpStartButton() {
  p5.addButton("start")
    .setPosition(34, 160)
    .setSize(btn_w, btn_h)
    .setColorBackground(color(232, 232, 232))
    .setColorForeground(color(202, 202, 202))
    .setColorActive(color(172, 172, 172))
    .setColorLabel(color(0, 0, 0))
    .setLabel("Start")
    .onPress(new CallbackListener() {
    public void controlEvent(CallbackEvent theEvent) {
      
      engine.loadDataStream1();
    }
  }
  );
}

public void setUpGiveMoreInfoButton() {
  p5.addButton("infobutton")
    .setPosition(125, 160)
    .setSize(btn_w, btn_h)
    .setColorBackground(color(232, 232, 232))
    .setColorForeground(color(202, 202, 202))
    .setColorActive(color(172, 172, 172))
    .setColorLabel(color(0, 0, 0))
    .setLabel("Tell Me More")
    .onPress(new CallbackListener() {
    public void controlEvent(CallbackEvent theEvent) {
      engine.setGiveMoreInfo(true);
    }
  }
  );
}