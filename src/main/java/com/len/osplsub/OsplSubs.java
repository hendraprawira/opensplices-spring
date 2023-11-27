package com.len.osplsub;/*
 * Copyright PT Len Industri (Persero)
 *
 * THIS SOFTWARE SOURCE CODE AND ANY EXECUTABLE DERIVED THEREOF ARE PROPRIETARY
 * TO PT LEN INDUSTRI (PERSERO), AS APPLICABLE, AND SHALL NOT BE USED IN ANY WAY
 * OTHER THAN BEFOREHAND AGREED ON BY PT LEN INDUSTRI (PERSERO), NOR BE REPRODUCED
 * OR DISCLOSED TO THIRD PARTIES WITHOUT PRIOR WRITTEN AUTHORIZATION BY
 * PT LEN INDUSTRI (PERSERO), AS APPLICABLE.
 */

/*
 * @author Hendra
 * */

/*
 * the class for Handling OSPL Subscriber (receive message, and processing to websocket)
 */

import TacticalPoint.TacticPoint;
import com.len.Main;
import org.omg.dds.core.Duration;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.Durability;
import org.omg.dds.core.policy.Partition;
import org.omg.dds.core.policy.PolicyFactory;
import org.omg.dds.core.policy.Reliability;
import org.omg.dds.core.status.Status;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.sub.DataReader;
import org.omg.dds.sub.DataReaderQos;
import org.omg.dds.sub.Sample;
import org.omg.dds.sub.Subscriber;
import org.omg.dds.topic.Topic;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class OsplSubs extends Thread {
    @Override
    public void run() {

        System.setProperty(
                ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY,
                "org.opensplice.dds.core.OsplServiceEnvironment");

        ServiceEnvironment env = ServiceEnvironment
                .createInstance(Main.class.getClassLoader());

        DomainParticipantFactory dpf = DomainParticipantFactory
                .getInstance(env);
        DomainParticipant participant = dpf.createParticipant(0);
        Reliability reliability = PolicyFactory.getPolicyFactory(env).Reliability()
                .withReliable();
        Durability durability = PolicyFactory.getPolicyFactory(env).Durability()
                .withPersistent();

        Collection<Class<? extends Status>> statuses = new HashSet<Class<? extends Status>>();

        Partition partition = PolicyFactory.getPolicyFactory(env).Partition()
                .withName("TacticPoint");

        Topic<TacticPoint> topics = participant.createTopic("TacticPoint", TacticPoint.class,
                participant.getDefaultTopicQos().withPolicies(reliability, durability), null, statuses);

        Subscriber subscriber = participant.createSubscriber(participant.getDefaultSubscriberQos()
                .withPolicy(partition));
        DataReaderQos drQos = subscriber.copyFromTopicQos(subscriber.getDefaultDataReaderQos(), topics.getQos());
        DataReader<TacticPoint> reader = subscriber.createDataReader(topics, drQos);
        Duration waitTimeout = Duration.newDuration(10, TimeUnit.SECONDS, env);
        try {
            reader.waitForHistoricalData(waitTimeout);
        } catch (TimeoutException e) {
            System.out.println("Ended");
        }
        System.out.println("OSPL Subscriber is running");
        boolean closed = false;
        final int BATCH_SIZE = 10; // Adjust the batch size based on your needs

        try {
            reader.waitForHistoricalData(waitTimeout);
            while (!closed) {
                Iterator<Sample<TacticPoint>> samples = reader.take(BATCH_SIZE); // Fetch multiple samples
                int sampleCount = 0;

                while (samples.hasNext()) {
                    Sample<TacticPoint> sample = samples.next();
                    TacticPoint msg = sample.getData();
                    if (msg != null) {
                        System.out.println("| Received Message Point : " + msg.message + "\n");
                    }
                    sampleCount++;
                }

                if (sampleCount == 0) {
                    try {
                        Thread.sleep(100); // Adjust sleep duration based on your needs
                    } catch (InterruptedException ie) {
                        closed = true;
                    }
                }
            }
        } catch (TimeoutException e) {
            System.out.println("Ended");
        } finally {
            // Close resources outside the loop
            topics.close();
            reader.close();
            subscriber.close();
            participant.close();
        }
        }
    }