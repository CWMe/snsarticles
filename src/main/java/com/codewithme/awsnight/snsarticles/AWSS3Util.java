package com.codewithme.awsnight.snsarticles;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.ObjectTagging;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.SetObjectTaggingRequest;
import com.amazonaws.services.s3.model.Tag;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class AWSS3Util {

    private AmazonS3Client s3Client;

    public AWSS3Util(AWSCredentialsProvider credentials) {
        s3Client = new AmazonS3Client(credentials);
    }

    public boolean doesFileExist(String bucket, String s3Path) {
        return (s3Client.doesObjectExist(bucket, s3Path));
    }

    public Properties getPropertiesFile(String bucket, String s3Path) throws AmazonClientException, IOException {
        Properties properties = new Properties();
        try (S3ObjectInputStream propsStream = s3Client.getObject(new GetObjectRequest(bucket, s3Path)).getObjectContent()) {
            properties.load(propsStream);
        }
        
        return properties;
    }
    
    public AmazonS3Client getS3Client() {
        return s3Client;
    }
    
    public String getS3ObjectAsString(String bucket, String key) throws AmazonClientException, IOException {
        return s3Client.getObjectAsString(bucket, key);
    }
    
    public void putS3Object(String bucket, String key, String data) throws AmazonClientException, IOException {
        s3Client.putObject(bucket, key, data);
    }
    
    public void putS3Object(String bucket, String key, String data, Map<String, String> metadata) throws AmazonClientException, IOException {
        s3Client.putObject(bucket, key, data);
        List<Tag> tagSet = new ArrayList<>(metadata.size());
        for (String metakey : metadata.keySet()) {
            tagSet.add(new Tag(metakey, metadata.get(metakey)));
        }
        
        ObjectTagging tagging = new ObjectTagging(tagSet);
        SetObjectTaggingRequest taggingRequest = new SetObjectTaggingRequest(bucket, key, tagging);
        s3Client.setObjectTagging(taggingRequest);
    }
    
}
