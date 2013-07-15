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
import com.google.gdata.data.extensions.FamilyName;
import com.google.gdata.data.extensions.GivenName;
import com.google.gdata.data.extensions.Name;
import com.google.gdata.util.ServiceException;
import com.shanhh.google.contacts.config.Constant;
import com.shanhh.google.contacts.service.ContactsOperService;
import com.shanhh.google.core.common.Logger;
import com.shanhh.google.core.common.SuperString;

@Service
public class ContactsOperServiceImpl implements ContactsOperService {

    private static final Logger logger = Logger.getLogger(ContactsOperServiceImpl.class);
    
    @Override
    public List<ContactEntry> listAll(ContactsService contactsService, URL feedUrl) throws IOException, ServiceException {
        
        Preconditions.checkNotNull(contactsService);
        if (feedUrl == null) {
            feedUrl = new URL(Constant.CONTACTS_FEED_URL);
        }
        
        List<ContactEntry> contactsList = new LinkedList<ContactEntry>();
        
        // first page
        ContactFeed resultFeed = list(contactsService, feedUrl);
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

    @Override
    public List<ContactEntry> query(ContactsService contactsService, String query) throws IOException, ServiceException {
        
        Preconditions.checkNotNull(contactsService);
        if (SuperString.isBlank(query)) {
            return listAll(contactsService, null);
        }
        
        URL feedUrl = new URL(Constant.CONTACTS_FEED_URL + "?q=" + query);
        
        logger.info("query contacts: {0}", query);
        
        List<ContactEntry> contactsList = new LinkedList<ContactEntry>();
        
        // first page
        ContactFeed resultFeed = list(contactsService, feedUrl);
        contactsList.addAll(resultFeed.getEntries());
        
        while (resultFeed.getNextLink() != null) {
            resultFeed = list(contactsService, new URL(resultFeed.getNextLink().getHref()));
            contactsList.addAll(resultFeed.getEntries());
        }
        return contactsList;
    }

    @Override
    public ContactEntry update(ContactsService contactsService, String contactId, String field, String value) throws IOException, ServiceException {
        
        Preconditions.checkNotNull(contactsService);
        Preconditions.checkNotNull(contactId);
        Preconditions.checkNotNull(field);
        
        System.out.println(Constant.CONTACTS_FEED_URL + "/" + contactId);
        URL feedUrl = new URL(Constant.CONTACTS_FEED_URL + "/" + contactId);
        
        logger.info("update contact, contactId={0}, {1} = {2}", contactId, field, value);
        
        ContactEntry contactToBeUpdate = contactsService.getEntry(feedUrl, ContactEntry.class);
        if ("familyname".equals(field)) {
            Name name = contactToBeUpdate.getName();
            if (name == null) {
                name = new Name();
                contactToBeUpdate.setName(name);
            }
            FamilyName fname = contactToBeUpdate.getName().getFamilyName();
            if (fname == null) {
                fname = new FamilyName();
                contactToBeUpdate.getName().setFamilyName(fname);
            }
            
            contactToBeUpdate.getName().getFamilyName().setValue(value);
        } else if ("givenname".equals(field)) {
            Name name = contactToBeUpdate.getName();
            if (name == null) {
                name = new Name();
                contactToBeUpdate.setName(name);
            }
            GivenName gname = contactToBeUpdate.getName().getGivenName();
            if (gname == null) {
                gname = new GivenName();
                contactToBeUpdate.getName().setGivenName(gname);
            }
            
            contactToBeUpdate.getName().getGivenName().setValue(value);
        } else {
            return contactToBeUpdate;
        }
        
        URL editUrl = new URL(contactToBeUpdate.getEditLink().getHref());
        ContactEntry updated = contactsService.update(editUrl, contactToBeUpdate);
        
        return updated;
    }

}
