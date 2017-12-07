package com.codewithme.awsnight.snsarticles;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSAsync;
import com.amazonaws.services.sns.AmazonSNSAsyncClientBuilder;

public class AWSSNSUtil {

    private AmazonSNSAsync snsClient;
    public String ERROR_SNS_TOPIC_ARN = "arn:aws:sns:us-east-1:256842276575:JungleApplicationError";

    public AWSSNSUtil(AWSCredentialsProvider credentials) {
        snsClient = AmazonSNSAsyncClientBuilder.standard()
                .withCredentials(credentials)
                .withRegion(Regions.US_EAST_1)
                .withClientConfiguration(new ClientConfiguration()
                        .withCacheResponseMetadata(false))
                .build();
    }
    
    public AmazonSNSAsync getSNSClient() {
        return snsClient;
    }

}
