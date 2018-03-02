
public class ProblemListener implements NotificationListener {
  
  private Engine engine;
  
  public ProblemListener(Engine engine) {
    this.engine = engine;
  }
  
  public void notificationReceived(Notification notification) {
    if (notification.getAppliance().equalsIgnoreCase("firealarm") && engine.getFireAlarmNotifications()) {
        engine.addNotificationToQueue(notification);
        engine.incrementNewNotificationCounter();
    } else if (notification.getAppliance().equalsIgnoreCase("window") && engine.getWindowNotifications()) {
        engine.addNotificationToQueue(notification);
        engine.incrementNewNotificationCounter();
    } else if (notification.getAppliance().equalsIgnoreCase("lightbulb") && engine.getLightbulbNotifications()) {
        engine.addNotificationToQueue(notification);
        engine.incrementNewNotificationCounter();
    } else if (notification.getAppliance().equalsIgnoreCase("smart tv") && engine.getSmartTvNotifications()) {
        engine.addNotificationToQueue(notification);
        engine.incrementNewNotificationCounter();
    }
    println(engine.getNotificationQueue());
  }
}