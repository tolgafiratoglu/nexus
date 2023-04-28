package com.nexus.bucket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.amazonaws.services.s3.model.ObjectMetadata;

import java.io.InputStream;

import javax.validation.Valid;

import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/bucket")
public class BucketRestController {
    
    @Autowired
    BucketService bucketService;

    @ExceptionHandler({ ResponseStatusException.class })
    public ResponseEntity handleException(Exception exception) {
        ResponseStatusException responseEx = (ResponseStatusException) exception;
        String message = responseEx.getReason();
        return ResponseEntity.status(responseEx.getStatusCode()).body(message);
    }

    @PutMapping("/create")
    public ResponseEntity create(@Valid @RequestBody BucketRequest bucketRequest) {
        String bucketName = bucketRequest.getName();
        
        try {
            if (bucketService.bucketExists(bucketName)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Bucket with this name already exists");
            }    
    
            bucketService.create(bucketRequest.getName());
            return ResponseEntity.ok().body("Bucket was created");
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request while creating the bucket", exception);
        }    
    }

    @PutMapping("/upload/{bucket_name}")
    public ResponseEntity upload(@PathVariable("bucket_name") String bucketName, @RequestParam("file") MultipartFile multipartFile) {
        try {
            String contentType = multipartFile.getContentType();
            long contentLength = multipartFile.getSize();
            String fileName = multipartFile.getOriginalFilename();
            InputStream inputStream = multipartFile.getInputStream();
    
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(contentType);
            metadata.setContentLength(contentLength);

            bucketService.upload(bucketName, inputStream, fileName, metadata);

        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, exception.getMessage(), exception);
        }

        return ResponseEntity.ok().body("Object was uploaded to the bucket.");
    }    

}
