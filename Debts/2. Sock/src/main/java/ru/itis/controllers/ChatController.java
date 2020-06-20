package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.services.UserService;

import java.util.UUID;

@Controller
public class ChatController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ModelAndView getPage() {
        ModelAndView modelAndView = new ModelAndView("chat");
        modelAndView.addObject("pageId", UUID.randomUUID().toString());
        return modelAndView;
    }
}
