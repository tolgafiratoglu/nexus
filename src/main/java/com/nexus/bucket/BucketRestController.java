package com.nexus.bucket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.amazonaws.services.apigateway.model.BadRequestException;

import javax.validation.Valid;

@RestController
@RequestMapping("/bucket")
public class BucketRestController {
    
    @Autowired
    BucketService bucketService;

    @ExceptionHandler({ ResponseStatusException.class })
    public ResponseEntity handleException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to save bucket, it's possible that this bucket name may exist in global scope.");
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
}
