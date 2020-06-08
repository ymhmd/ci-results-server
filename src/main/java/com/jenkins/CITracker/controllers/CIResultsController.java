package com.jenkins.CITracker.controllers;


import com.jenkins.CITracker.entities.CIResult;
import com.jenkins.CITracker.repositories.CIResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
public class CIResultsController {

    @Autowired
    CIResultRepository ciResultRepository;

    @RequestMapping("/ci_results")
    public List<CIResult> getAllResults() {
        return ciResultRepository.findAll();
    }

    @PostMapping(value = "/ci_results", consumes = "application/json", produces = "application/json")
    public CIResult addResult(@RequestBody CIResult ciResultToAdd) {

        CIResult ciResult = new CIResult();
        ciResult.setType(ciResultToAdd.getType());
        ciResult.setDetails(ciResultToAdd.getDetails());
        ciResultRepository.save(ciResult);

        return ciResult;
    }

}