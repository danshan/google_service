package com.shanhh.google.contacts.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.shanhh.google.contacts.action.LoginContorller;
import com.shanhh.google.contacts.config.ServiceConfig;
import com.shanhh.google.core.common.Logger;

/**
 * The Class SecurityInterceptor.
 */
public class AuthorityFilter implements Filter {

    private static final Logger logger = Logger.getLogger(LoginContorller.class);
    
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
