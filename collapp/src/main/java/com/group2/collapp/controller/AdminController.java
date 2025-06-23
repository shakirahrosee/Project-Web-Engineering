package com.group2.collapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.group2.collapp.services.TaskService;
import com.group2.collapp.services.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired private UserService userService;
    @Autowired private TaskService taskService;

    @GetMapping("/users")
    public String allUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin-users";
    }

    @GetMapping("/tasks")
    public String allTasks(Model model) {
        model.addAttribute("tasks", taskService.getAllTasks());
        return "admin-tasks";
    }
}
