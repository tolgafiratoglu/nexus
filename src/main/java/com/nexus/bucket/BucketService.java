package com.nexus.bucket;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.ObjectMetadata;

import java.io.InputStream;

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

    public List<BucketDTO> getList() {
        List<Bucket> buckets = s3Client.listBuckets();
        return convertIntoDTOList(buckets);
    }

    public Bucket create(String bucketName) {
        return s3Client.createBucket(bucketName);
    }    

    public PutObjectResult upload(String bucketName, InputStream inputStream, String fileName, ObjectMetadata metadata) {
        PutObjectRequest request = new PutObjectRequest(bucketName, fileName, inputStream, metadata);
        return s3Client.putObject(request);
    }

    public Boolean bucketExists(String bucketName) {
        return s3Client.doesBucketExist(bucketName);
    }
}
