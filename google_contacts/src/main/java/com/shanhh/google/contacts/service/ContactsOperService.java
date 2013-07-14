package com.shanhh.google.contacts.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.util.ServiceException;

public interface ContactsOperService {

    /**
     * 获取全部联系人列表
     * @author dan.shan
     * @since Jul 14, 2013 10:17:41 AM
     *
     * @param contactsService
     * @return 
     * @throws MalformedURLException 
     * @throws ServiceException 
     * @throws IOException 
     */
    public List<ContactEntry> listAll(ContactsService contactsService, URL feedUrl) throws MalformedURLException, IOException, ServiceException;
    
    /**
     * 获取指定页的联系人列表
     * @author dan.shan
     * @since Jul 14, 2013 10:47:10 AM
     *
     * @param contactsService
     * @param feedUrl
     * @return
     * @throws IOException
     * @throws ServiceException
     */
    public ContactFeed list(ContactsService contactsService, URL feedUrl) throws IOException, ServiceException;
    
    public List<ContactEntry> query(ContactsService contactsService, String query) throws MalformedURLException, IOException, ServiceException;
}
