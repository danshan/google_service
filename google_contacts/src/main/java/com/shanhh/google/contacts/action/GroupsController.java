package com.shanhh.google.contacts.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.contacts.ContactGroupEntry;
import com.google.gdata.util.ServiceException;
import com.shanhh.google.contacts.service.GroupsOperService;

@Controller
@RequestMapping("groups")
public class GroupsController {

    @Autowired
    private GroupsOperService groupsOpenService;
    
    @RequestMapping("list")
    public String list(Model model, HttpServletRequest request) throws IOException, ServiceException {
        ContactsService contactsService = (ContactsService) request.getSession().getAttribute("contactsService");
        
        List<ContactGroupEntry> list = groupsOpenService.list(contactsService, null).getEntries();
        model.addAttribute("grouplist", list);
        return "grouplist";
        
    }
    
}
