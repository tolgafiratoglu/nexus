package com.nexus.dynamo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/bucket")
@ResponseBody
public class DynamoController {

    @Autowired
    DynamoService bucketService; 

    @GetMapping("/list")
    public ModelAndView list() {
       
        List<DynamoDTO> buckets = bucketService.getList();

        ModelAndView mav = new ModelAndView("bucket/list");
        mav.addObject("buckets", buckets);
        return mav;
    }
    
    @GetMapping("/new")
    public ModelAndView newBucket() {
        ModelAndView mav = new ModelAndView("bucket/new");
        mav.addObject("buckets", "test");
        return mav;
    }

    @GetMapping("/object")
    public ModelAndView bucketObject() {
        List<DynamoDTO> buckets = bucketService.getList();
        ModelAndView mav = new ModelAndView("bucket/object");
        mav.addObject("buckets", buckets);
        return mav;
    }

}