package backend.com.parcelsystem.Service;

import backend.com.parcelsystem.Models.Parcel;

public interface NotificationService {
    
    public void sendNotification(Parcel parcel);
    public void setNotificationAsRead();
    public void switchNotificationStatus();
}
