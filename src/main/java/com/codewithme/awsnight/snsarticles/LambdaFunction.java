package com.codewithme.awsnight.snsarticles;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.services.lambda.runtime.Context;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.log4j.Logger;

public class LambdaFunction {

    private static AWSSNSUtil snsUtil;
    private static AWSS3Util s3Util;
    private static AtomicBoolean instantiated = new AtomicBoolean(false);
    private static final Lock LOCK = new ReentrantLock(true);
    private static final Logger log = Logger.getLogger(LambdaFunction.class);

    public LambdaFunction() {
        LOCK.lock();
        try {
            if (instantiated.get() == false) {
                log.info("Instantiating");
                AWSCredentialsProvider credentials = new EnvironmentVariableCredentialsProvider();
                snsUtil = new AWSSNSUtil(credentials);
                s3Util = new AWSS3Util(credentials);
                instantiated.set(true);
            }
        } finally {
            LOCK.unlock();
        }
    }

    public void lambdaHandler(Object event, Context context) {
        System.out.println(event.getClass().getCanonicalName());
        System.out.println(event.getClass().getSimpleName());
    }
}
