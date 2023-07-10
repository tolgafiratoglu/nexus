package com.nexus.reports;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.nexus.bucket.BucketDTO;
import com.nexus.bucket.BucketService;
import com.nexus.dynamo.DynamoDTO;
import com.nexus.dynamo.DynamoService;

@Controller
@RequestMapping("/reports")
@ResponseBody
public class ChartController {
    
    @Autowired
    DynamoService dynamoService; 

    @Autowired
    BucketService bucketService;

    List<String> usageMetrics = Arrays.asList("CallCount", "ResourceCount");
    List<String> s3Metrics = Arrays.asList("NumberOfObjects", "BucketSizeBytes");
    List<String> dynamoMetrics = Arrays.asList("ConsumedReadCapacityUnits", "ConsumedWriteCapacityUnits", "ProvisionedReadCapacityUnits", "AccountMaxTableLevelWrites", "AccountMaxWrites", "AccountProvisionedReadCapacityUtilization");

    List<String> services = Arrays.asList("S3", "Cloudwatch", "DynamoDB");

    @GetMapping("/new")
    public ModelAndView newReport() {
        List<DynamoDTO> tables = dynamoService.getList();
        List<BucketDTO> buckets = bucketService.getList();

        ModelAndView mav = new ModelAndView("cloudwatch/reports/new");
        mav.addObject("tables", tables);
        mav.addObject("buckets", buckets);
        mav.addObject("usageMetrics", usageMetrics);
        mav.addObject("s3Metrics", s3Metrics);
        mav.addObject("dynamoMetrics", dynamoMetrics);
        mav.addObject("services", services);
        return mav;
    }
}
