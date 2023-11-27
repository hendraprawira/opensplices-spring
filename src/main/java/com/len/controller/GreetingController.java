package com.len.controller;


import TacticalPoint.TacticPoint;
import com.len.record.Greeting;
import com.len.osplpub.OsplPubs;
import org.omg.dds.core.InstanceHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private final OsplPubs osplPubs;

    public GreetingController(OsplPubs osplPubs) {
        this.osplPubs = osplPubs;
    }

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
//        System.out.println(osplPubs.participants);
        TacticPoint tacticMsg = new TacticPoint();
        tacticMsg.message = "pointTactic.toJson()sss";
        tacticMsg.mockID = 12;
        tacticMsg.method = "Post";
        tacticMsg.tacticType = "Point";
        tacticMsg.saveDB =false;
        try {
            osplPubs.writers.write(tacticMsg, InstanceHandle.nilHandle(osplPubs.envs));
            System.out.println("| Publish Message : " + tacticMsg.message + "\n");
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
}