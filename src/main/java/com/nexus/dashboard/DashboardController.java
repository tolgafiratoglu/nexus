package com.nexus.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.nexus.reports.Chart;
import com.nexus.reports.ChartData;
import com.nexus.reports.ChartService;

import java.util.List;

@Controller
@ResponseBody
public class DashboardController {
    @Autowired
    ChartService chartService;

    @GetMapping("/")
    public ModelAndView dashboard() {
       
        List<ChartData> charts = chartService.getChartData();

        ModelAndView mav = new ModelAndView("dashboard/charts");
        mav.addObject("charts", charts);
        return mav;
    }
}
