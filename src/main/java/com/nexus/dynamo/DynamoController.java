package com.nexus.dynamo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/dynamo")
@ResponseBody
public class DynamoController {

    @Autowired
    DynamoService dynamoService; 

    @GetMapping("/list")
    public ModelAndView list() {
       
        List<DynamoDTO> tables = dynamoService.getList();

        ModelAndView mav = new ModelAndView("dynamo/list");
        mav.addObject("tables", tables);
        return mav;
    }
    
    @GetMapping("/new")
    public ModelAndView newTable() {
        ModelAndView mav = new ModelAndView("dynamo/new");
        return mav;
    }

    @GetMapping("/table/insert")
    public ModelAndView newKeyValue() {
        List<DynamoDTO> tables = dynamoService.getList();
        ModelAndView mav = new ModelAndView("dynamo/insert");
        mav.addObject("tables", tables);
        return mav;
    }

}