package com.shanhh.google.contacts.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping("/")
    public String empty(Model model, HttpServletRequest request) {
        return "index";
    }
    
    @RequestMapping("index")
    public String index(Model model, HttpServletRequest request) {
        return "index";
    }
}
