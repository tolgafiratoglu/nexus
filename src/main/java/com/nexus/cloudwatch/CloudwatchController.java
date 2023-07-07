package com.nexus.cloudwatch;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.nexus.cloudwatch.MetricDTO;

@Controller
@RequestMapping("/cloudwatch")
@ResponseBody
public class CloudwatchController {
    @Autowired
    CloudwatchService cloudwatchService;

    @GetMapping("/metrics/list")
    public ModelAndView listMetrics() {
       
        List<MetricDTO> metrics = cloudwatchService.listMetrics();

        ModelAndView mav = new ModelAndView("cloudwatch/metrics/list");
        mav.addObject("metrics", metrics);
        return mav;
    }
}
