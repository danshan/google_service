package com.shanhh.google.contacts.action;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.shanhh.google.contacts.config.ServiceConfig;

@Controller
@RequestMapping("api")
public class IndexController {

    @RequestMapping("index")
    public String index(HttpServletRequest request) throws ParseException {
        List<String> scopes = new ArrayList<String>();
        scopes.add("https://www.google.com/m8/feeds");
        
        // Generate the URL to which we will direct users
        String authorizeUrl = new GoogleAuthorizationCodeRequestUrl(
                ServiceConfig.get("contacts.client.id"),
                ServiceConfig.get("oauth2.code.callback.url"), scopes).build();

        
        return "redirect:" + authorizeUrl;
    }

}
