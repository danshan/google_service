package com.shanhh.google.contacts.filter;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.shanhh.google.contacts.action.LoginContorller;
import com.shanhh.google.contacts.config.ServiceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class SecurityInterceptor.
 */
public class AuthorityFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(LoginContorller.class);
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        if (httpRequest.getSession().getAttribute("contactsService") == null) {

            if ("/oauth2callback".equals(httpRequest.getRequestURI())) {
                chain.doFilter(httpRequest, httpResponse);
            } else {
                // do nothing
            }
            
            List<String> scopes = new ArrayList<String>();
            scopes.add("https://www.google.com/m8/feeds");
            
            // Generate the URL to which we will direct users
            String authorizeUrl = new GoogleAuthorizationCodeRequestUrl(
                    ServiceConfig.get("contacts.client.id"),
                    ServiceConfig.get("oauth2.code.callback.url"), scopes).build();

            logger.info("redirect to google login page: " + authorizeUrl);
            httpResponse.sendRedirect(authorizeUrl);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }

 
}
