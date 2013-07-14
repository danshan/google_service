package com.shanhh.google.contacts.action;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.util.ServiceException;
import com.shanhh.google.contacts.config.ServiceConfig;

@Controller
public class LoginContorller {

    @RequestMapping("oauth2callback")
    public String callback(HttpServletRequest request) throws IOException, ServiceException {
        
        String code = request.getParameter("code");
        
        ContactsService contactsService = new ContactsService("google_service");
        
        Credential credential = exchangeCode(code);
        contactsService.setOAuth2Credentials(credential);
        contactsService.useSsl();
        contactsService.setHeader("GData-Version", "3.0");
        
        
        ContactFeed resultFeed = contactsService.getFeed(new URL("http://www.google.com/m8/feeds/contacts/default/full"), ContactFeed.class);
        

        for (ContactEntry feed : resultFeed.getEntries()) {
            System.err.println(feed.getTitle().getPlainText());
        }
        
        return "";
    }
    /**
     * Exchange an authorization code for OAuth 2.0 credentials.
     *
     * @param authorizationCode Authorization code to exchange for OAuth 2.0
     *        credentials.
     * @return OAuth 2.0 credentials.
     * @throws CodeExchangeException An error occurred.
     */
    private Credential exchangeCode(String authorizationCode) {
        try {
            GoogleAuthorizationCodeFlow flow = getFlow();
            GoogleTokenResponse response =
                    flow.newTokenRequest(authorizationCode).setRedirectUri(ServiceConfig.get("oauth2.code.callback.url")).execute();
            return flow.createAndStoreCredential(response, null);
        } catch (IOException e) {
            System.err.println("An error occurred: " + e);
            return null;
        }
    }


    /**
     * Build an authorization flow and store it as a static class attribute.
     * 
     * @return GoogleAuthorizationCodeFlow instance.
     * @throws IOException
     *         Unable to load client_secrets.json.
     */
    static GoogleAuthorizationCodeFlow getFlow() throws IOException {
        List<String> scopes = new ArrayList<String>();
        scopes.add("https://www.google.com/m8/feeds");
        
        HttpTransport httpTransport = new NetHttpTransport();
        JacksonFactory jsonFactory = new JacksonFactory();
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(jsonFactory,
                        LoginContorller.class.getResourceAsStream("/client_secrets.json"));
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(httpTransport, jsonFactory, clientSecrets, scopes)
                        .setAccessType("online").setApprovalPrompt("force").build();
        return flow;
    }

}
