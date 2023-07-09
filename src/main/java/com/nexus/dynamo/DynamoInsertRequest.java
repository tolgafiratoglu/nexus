package com.nexus.dynamo;

import lombok.Data;
import javax.validation.constraints.NotBlank;
  
@Data
public class DynamoInsertRequest {
    @NotBlank(message="Key cannot be blank")
    String table;

    @NotBlank(message="ID cannot be blank")
    String id;

    @NotBlank(message="Key cannot be blank")
    String key;
    
    @NotBlank(message="Value cannot be blank")
    String value;
}
