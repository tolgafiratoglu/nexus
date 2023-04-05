package com.nexus.bucket;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;

@Service
public class BucketService {
    
    AmazonS3 s3Client;

    BucketService() {
        AWSCredentials credentials = new ProfileCredentialsProvider().getCredentials();

        s3Client = AmazonS3ClientBuilder
            .standard()
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .withRegion(Regions.US_EAST_1)
            .build();
    }

    protected List<BucketDTO> convertIntoDTOList(List<Bucket> buckets) {
        List<BucketDTO> bucketDTOList = new ArrayList<BucketDTO>();

        for(Bucket bucket : buckets) {
            BucketDTO bucketDTO = new BucketDTO();
            bucketDTO.setName(bucket.getName());
            bucketDTO.setDate(bucket.getCreationDate());
            bucketDTOList.add(bucketDTO);
        }

        return bucketDTOList;
    }

    protected List<BucketDTO> getList() {
        List<Bucket> buckets = s3Client.listBuckets();
        return convertIntoDTOList(buckets);
    }

    protected Bucket create(String bucketName) {
        return s3Client.createBucket(bucketName);
    }    

    protected Bucket upload(String bucketName) {
        return s3Client.createBucket(bucketName);
    }

    protected Boolean bucketExists(String bucketName) {
        return s3Client.doesBucketExist(bucketName);
    }
}
