package com.shanhh.google.contacts.action;

import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.util.ServiceException;
import com.shanhh.google.contacts.service.ContactsOperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("contacts")
public class ContactsController {
    
    private static final Logger logger = LoggerFactory.getLogger(ContactsController.class);

    @Autowired
    private ContactsOperService contactsListService;
    
    @RequestMapping("list")
    public String list(Model model, HttpServletRequest request) throws IOException, ServiceException {
        ContactsService contactsService = (ContactsService) request.getSession().getAttribute("contactsService");
        
        List<ContactEntry> list = contactsListService.list(contactsService, null).getEntries();
        model.addAttribute("contactlist", list);
        return "contactlist";
    }
    
    @RequestMapping("query")
    public String query(String query, Model model, HttpServletRequest request) throws IOException, ServiceException {

        ContactsService contactsService = (ContactsService) request.getSession().getAttribute("contactsService");
        
        List<ContactEntry> list = contactsListService.query(contactsService, query);
        model.addAttribute("contactlist", list);
        return "contactlist";
    }
    
    @RequestMapping("update")
    public @ResponseBody ContactEntry update(String contactId, String field, String value, Model model, HttpServletRequest request) throws IOException, ServiceException {
        ContactsService contactsService = (ContactsService) request.getSession().getAttribute("contactsService");
        
        ContactEntry contact = contactsListService.update(contactsService, contactId, field, value);
        model.addAttribute("contact", contact);
        return contact;
    }
    
    @RequestMapping("get")
    public String get(String contactId, Model model, HttpServletRequest request) throws IOException, ServiceException {
        ContactsService contactsService = (ContactsService) request.getSession().getAttribute("contactsService");
        
        ContactEntry contact = contactsListService.get(contactsService, contactId);
        model.addAttribute("contact", contact);
        return "contactprofile";
    }
}
