package com.shanhh.google.contacts.action;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.util.ServiceException;
import com.shanhh.google.contacts.config.ServiceConfig;
import com.shanhh.google.contacts.service.ContactsOperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginContorller {

    private static final Logger logger = LoggerFactory.getLogger(LoginContorller.class);

    @Autowired
    private ContactsOperService contactsListService;
    
    @RequestMapping("oauth2callback")
    public String callback(Model model, HttpServletRequest request) throws IOException, ServiceException {
        
        String code = request.getParameter("code");
        
        ContactsService contactsService = new ContactsService("google_service");
        
        Credential credential = exchangeCode(code);
        contactsService.setOAuth2Credentials(credential);
        contactsService.useSsl();
        contactsService.setHeader("GData-Version", "3.0");
        
        request.getSession().setAttribute("contactsService", contactsService);
        
        return "redirect:index";
    }
    
    /**
     * Exchange an authorization code for OAuth 2.0 credentials.
     *
     * @param authorizationCode Authorization code to exchange for OAuth 2.0
     *        credentials.
     * @return OAuth 2.0 credentials.
     */
    private Credential exchangeCode(String authorizationCode) {
        try {
            GoogleAuthorizationCodeFlow flow = getFlow();
            GoogleTokenResponse response =
                    flow.newTokenRequest(authorizationCode).setRedirectUri(ServiceConfig.get("oauth2.code.callback.url")).execute();
            return flow.createAndStoreCredential(response, null);
        } catch (IOException e) {
            logger.error("exchange code failed: ", e);
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
    private GoogleAuthorizationCodeFlow getFlow() throws IOException {
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
