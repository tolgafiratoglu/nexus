package com.nexus.dynamo;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
  
@Data
public class DynamoRequest {

    @Pattern(regexp="(?!(^xn--|.+-s3alias$))^[a-z0-9][a-z0-9-]{1,61}[a-z0-9]$", message="Please enter a proper bucket name")
    @NotBlank(message="Bucket name cannot be blank")
    String name;    
}
