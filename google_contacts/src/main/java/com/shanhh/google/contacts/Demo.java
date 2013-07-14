package com.shanhh.google.contacts;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gdata.client.authn.oauth.GoogleOAuthParameters;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.util.ServiceException;


public class Demo {
    private String username;
    private String password;
    
    private static ContactsService contactsService;
    private static final String BASE_URL = "http://www.google.com/m8/feeds/"; 

    public Demo(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    public static void main(String[] args) throws MalformedURLException, IOException, ServiceException {
        
        Demo demo = new Demo("yuer@shanhh.com", "9902426tiancai");

        demo.demo();
    }
    
    public void demo() throws MalformedURLException, IOException, ServiceException {
        
        String url = BASE_URL + "contacts/" + username + "/full";
        System.out.println(url);
        contactsService = new ContactsService("google_service");
        
        contactsService.setUserCredentials(username, password);

        ContactFeed resultFeed = contactsService.getFeed(new URL(url), ContactFeed.class);
        System.err.println("Total: " + resultFeed.getEntries().size()
                + " entries found");
    }
   
}
