enum ProblemType { POWER, INSECURE }

class Notification {
   
  int timestamp;
  String category;
  String appliance;
  String location;
  String problem; // power, opened
  int priority;
  
  public Notification(JSONObject json) {
    this.timestamp = json.getInt("timestamp");
    //time in milliseconds for playback from sketch start
    
    if (json.isNull("category")) {
      this.category = "";
    }
    else {
      this.category = json.getString("category");      
    }
    
    if (json.isNull("appliance")) {
      this.appliance = "";
    }
    else {
      this.appliance = json.getString("appliance");
    }
    
    if (json.isNull("location")) {
      this.location = "";
    }
    else {
      this.location = json.getString("location");
    }
    
    if (json.isNull("problem")) {
      this.problem = "";
    }
    else {
      this.problem = json.getString("problem");      
    }
    
    
    this.priority = json.getInt("priority");
    //1-4 levels (1 is lowest, 4 is highest)
    
                             
  }
  
  public int getTimestamp() { return timestamp; }
  public String getCategory() { return category; }
  public String getAppliance() { return appliance; }
  public String getLocation() { return location; }
  public String getProblem() { return problem; }
  public int getPriorityLevel() { return priority; }
  
  public String toString() {
      String output = getAppliance().toString() + ": ";
      output += "(" + getLocation() + ")";
      output += " " + getProblem();
      return output;
    }
}