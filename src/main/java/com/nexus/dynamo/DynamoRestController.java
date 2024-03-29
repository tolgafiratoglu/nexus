package com.nexus.dynamo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;



import javax.validation.Valid;

@RestController
@RequestMapping("/dynamo")
public class DynamoRestController {
    
    @Autowired
    DynamoService dynamoService;

    @ExceptionHandler({ ResponseStatusException.class })
    public ResponseEntity handleException(Exception exception) {
        ResponseStatusException responseEx = (ResponseStatusException) exception;
        String message = responseEx.getReason();
        return ResponseEntity.status(responseEx.getStatusCode()).body(message);
    }

    @PutMapping("/table/create")
    public ResponseEntity create(@Valid @RequestBody DynamoRequest dynamoRequest) {
        try {
            dynamoService.create(dynamoRequest.getName());
            return ResponseEntity.ok().body("Bucket was created");
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, dynamoRequest.getName() + exception.getMessage(), exception);
        }    
    }

    @PutMapping("/table/insert")
    public ResponseEntity insert(@Valid @RequestBody DynamoInsertRequest dynamoInsertRequest) {
        try {
            dynamoService.insert(dynamoInsertRequest.getTable(), dynamoInsertRequest.getId(), dynamoInsertRequest.getKey(), dynamoInsertRequest.getValue());    
            return ResponseEntity.ok().body("Value inserted to table.");
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage(), exception);
        }    
    }
}
