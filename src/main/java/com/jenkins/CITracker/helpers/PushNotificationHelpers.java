package com.jenkins.CITracker.helpers;

import com.jenkins.CITracker.services.AndroidPushNotificationsService;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class PushNotificationHelpers {

    AndroidPushNotificationsService androidPushNotificationsService;


    public PushNotificationHelpers(AndroidPushNotificationsService androidPushNotificationsService) {
        this.androidPushNotificationsService = androidPushNotificationsService;
    }

    public boolean pushNotificationToAndroid(String notificationTitle, String notificationBody) {
        JSONObject body = new JSONObject();
        body.put("to", "/topics/CITracker");
        body.put("priority", "high");

        JSONObject notification = new JSONObject();
        notification.put("title", notificationTitle);
        notification.put("body", notificationBody);

        body.put("notification", notification);
        body.put("data", new JSONObject());

        HttpEntity<String> request = new HttpEntity<>(body.toString());
        CompletableFuture<String> pushNotification = androidPushNotificationsService.send(request);
        CompletableFuture.allOf(pushNotification).join();
        try {
            pushNotification.get();
            return true;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

}
