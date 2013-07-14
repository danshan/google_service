package com.shanhh.google.contacts.service.impl;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.util.ServiceException;
import com.shanhh.google.contacts.config.Constant;
import com.shanhh.google.contacts.service.ContactsListService;
import com.shanhh.google.core.common.Logger;

@Service
public class ContactsListServiceImpl implements ContactsListService {

    private static final Logger logger = Logger.getLogger(ContactsListServiceImpl.class);
    
    @Override
    public List<ContactEntry> listAll(ContactsService contactsService) throws IOException, ServiceException {
        
        Preconditions.checkNotNull(contactsService);
        
        List<ContactEntry> contactsList = new LinkedList<ContactEntry>();
        
        // first page
        ContactFeed resultFeed = list(contactsService, new URL(Constant.CONTACTS_FEED_URL));
        contactsList.addAll(resultFeed.getEntries());
        
        while (resultFeed.getNextLink() != null) {
            resultFeed = list(contactsService, new URL(resultFeed.getNextLink().getHref()));
            contactsList.addAll(resultFeed.getEntries());
        }
        
        return contactsList;
    }

    @Override
    public ContactFeed list(ContactsService contactsService, URL feedUrl) throws IOException, ServiceException {
        
        Preconditions.checkNotNull(contactsService);
        if (feedUrl == null) {
            feedUrl = new URL(Constant.CONTACTS_FEED_URL);
        }
        
        logger.info("list contacts: {0}", feedUrl.getPath() + feedUrl.getQuery());
        ContactFeed resultFeed = contactsService.getFeed(feedUrl, ContactFeed.class);
        return resultFeed;
        
    }
    
}
