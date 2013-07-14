package com.shanhh.google.contacts.service;

import org.springframework.stereotype.Service;

import com.google.gdata.client.contacts.ContactsService;

@Service
public interface ContactsListService {

    /**
     * 获取全部联系人列表
     * @author dan.shan
     * @since Jul 14, 2013 10:17:41 AM
     *
     * @param contactsService
     */
    public void listAll(ContactsService contactsService);
    
    public void list(ContactsService contactsService);
}
