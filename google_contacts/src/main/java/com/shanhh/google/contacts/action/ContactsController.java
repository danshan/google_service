package com.shanhh.google.contacts.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.util.ServiceException;
import com.shanhh.google.contacts.service.ContactsOperService;
import com.shanhh.google.core.common.Logger;

@Controller
public class ContactsController {
    
    private static final Logger logger = Logger.getLogger(ContactsController.class);

    @Autowired
    private ContactsOperService contactsListService;
    
    @RequestMapping("list")
    public String list(Model model, HttpServletRequest request) throws IOException, ServiceException {
        ContactsService contactsService = (ContactsService) request.getSession().getAttribute("contactsService");
        
        List<ContactEntry> list = contactsListService.list(contactsService, null).getEntries();
        model.addAttribute("contactlist", list);
        System.out.println(list.get(0).getContactPhotoLink());
        return "contactlist";
    }
    
    @RequestMapping("query")
    public String query(String query, Model model, HttpServletRequest request) throws IOException, ServiceException {

        ContactsService contactsService = (ContactsService) request.getSession().getAttribute("contactsService");
        
        List<ContactEntry> list = contactsListService.query(contactsService, query);
        model.addAttribute("contactlist", list);
        System.out.println(list.get(0).getContactPhotoLink());
        return "contactlist";
    }
    
    @RequestMapping("update")
    public @ResponseBody ContactEntry update(String contactId, String field, String value, Model model, HttpServletRequest request) throws IOException, ServiceException {
        ContactsService contactsService = (ContactsService) request.getSession().getAttribute("contactsService");
        
        ContactEntry contact = contactsListService.update(contactsService, contactId, field, value);
        model.addAttribute("contact", contact);
        return contact;
    }
}
