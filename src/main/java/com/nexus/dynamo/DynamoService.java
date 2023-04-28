package com.nexus.dynamo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.Topic;

import java.io.InputStream;

@Service
public class DynamoService {
    
    AmazonSNS snsClient;

    DynamoService() {
        AWSCredentials credentials = new ProfileCredentialsProvider().getCredentials();

        snsClient = AmazonSNSClientBuilder
            .standard()
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .withRegion(Regions.US_EAST_1)
            .build();
    }

    protected List<DynamoDTO> convertIntoDTOList(List<Bucket> buckets) {
        List<DynamoDTO> bucketDTOList = new ArrayList<DynamoDTO>();

        for(Bucket bucket : buckets) {
            DynamoDTO bucketDTO = new DynamoDTO();
            bucketDTO.setName(bucket.getName());
            bucketDTO.setDate(bucket.getCreationDate());
            bucketDTOList.add(bucketDTO);
        }

        return bucketDTOList;
    }

    protected List<DynamoDTO> getList() {
        List<Bucket> buckets = s3Client.listBuckets();
        return convertIntoDTOList(buckets);
    }

    protected Topic create(String topicName) {
        CreateTopicRequest request = CreateTopicRequest.builder()
                .name(topicName)
                .build();

        return snsClient.createTopic(CreateTopicRequest request);
    }
}
