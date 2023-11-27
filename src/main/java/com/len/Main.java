package com.len;

import com.len.osplsub.OsplSubs;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		System.out.println("AWURURURURUS");
		OsplSubs osplSubs = new OsplSubs();
		osplSubs.setDaemon(true);
		osplSubs.start();
		SpringApplication.run(Main.class, args);
	}
}
