package com.shanhh.google.contacts.service.impl;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactGroupEntry;
import com.google.gdata.data.contacts.ContactGroupFeed;
import com.google.gdata.util.ServiceException;
import com.shanhh.google.contacts.config.Constant;
import com.shanhh.google.contacts.service.GroupsOperService;
import com.shanhh.google.core.common.Logger;

@Service
public class GroupsOperServiceImpl implements GroupsOperService {

    private static final Logger logger = Logger.getLogger(GroupsOperServiceImpl.class);
    
    @Override
    public List<ContactGroupEntry> listAll(ContactsService contactsService, URL feedUrl) throws IOException, ServiceException {
        
        Preconditions.checkNotNull(contactsService);
        if (feedUrl == null) {
            feedUrl = new URL(Constant.GROUPS_FEED_URL);
        }
        
        List<ContactGroupEntry> groupList = new LinkedList<ContactGroupEntry>();
        
        // first page
        ContactGroupFeed resultFeed = list(contactsService, feedUrl);
        groupList.addAll(resultFeed.getEntries());
        
        while (resultFeed.getNextLink() != null) {
            resultFeed = list(contactsService, new URL(resultFeed.getNextLink().getHref()));
            groupList.addAll(resultFeed.getEntries());
        }
        
        return groupList;
    }

    @Override
    public ContactGroupFeed list(ContactsService contactsService, URL feedUrl) throws IOException, ServiceException {
        
        Preconditions.checkNotNull(contactsService);
        if (feedUrl == null) {
            feedUrl = new URL(Constant.GROUPS_FEED_URL);
        }
        
        logger.info("list groups: {0}", feedUrl.getPath() + feedUrl.getQuery());
        ContactGroupFeed resultFeed = contactsService.getFeed(feedUrl, ContactGroupFeed.class);
        return resultFeed;
        
    }

}
