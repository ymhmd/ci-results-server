package com.jenkins.CITracker.controllers;


import com.jenkins.CITracker.entities.CIResult;
import com.jenkins.CITracker.helpers.PushNotificationHelpers;
import com.jenkins.CITracker.repositories.CIResultRepository;
import com.jenkins.CITracker.services.AndroidPushNotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CIResultsController {

    @Autowired
    CIResultRepository ciResultRepository;

    @Autowired
    AndroidPushNotificationsService androidPushNotificationsService;

    @RequestMapping("/ci_results")
    public List<CIResult> getAllResults() {
        return ciResultRepository.findAllByOrderByDateDesc();
    }

    @PostMapping(value = "/ci_results", consumes = "application/json", produces = "application/json")
    public CIResult addResult(@RequestBody CIResult ciResultToAdd) {

        CIResult ciResult = new CIResult();
        ciResult.setType(ciResultToAdd.getType());
        ciResult.setDetails(ciResultToAdd.getDetails());
        if (ciResultToAdd.getStatus() == null) {
            ciResult.setStatus("PASS");
        } else {
            ciResult.setStatus(ciResultToAdd.getStatus());
        }
        ciResultRepository.save(ciResult);

        //Send notification to Android App
        String notificationTitle = "Notification from CITracker!";
        String notificationBody = "Task finished on: " + ciResultToAdd.getType() + ", " + ciResultToAdd.getDetails();

        PushNotificationHelpers pushNotificationHelpers = new PushNotificationHelpers(androidPushNotificationsService);
        pushNotificationHelpers.pushNotificationToAndroid(notificationTitle, notificationBody);

        return ciResult;
    }

    @DeleteMapping("/ci_results")
    public void deleteAll() {
        ciResultRepository.deleteAll();
    }

}