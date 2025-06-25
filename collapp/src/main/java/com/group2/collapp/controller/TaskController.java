package com.group2.collapp.controller;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.group2.collapp.model.Task;
import com.group2.collapp.model.User;
import com.group2.collapp.repository.ActivityRepository;
import com.group2.collapp.services.TaskService;
import com.group2.collapp.services.UserService;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    @Autowired private TaskService taskService;
    @Autowired private UserService userService;
    @Autowired private ActivityRepository activityRepository;

    @GetMapping("/my")
    public String userTasks(Model model, Principal principal) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("tasks", taskService.getAllTasks().stream().map(task -> {
            String userIds = task.getAssignedUsers().stream()
                                .map(u -> String.valueOf(u.getId()))
                                .collect(Collectors.joining(","));
            task.setUserIdsString(userIds); // Add a helper field in Task
            return task;
        }).toList());
        return "user-task";
    }

    @PostMapping("/create")
    public String createTask(@ModelAttribute Task task, @RequestParam List<Long> userIds) {
        taskService.createTask(task, userIds);
        return "redirect:/tasks/my";
    }

    @PostMapping("/update")
    public String updateTask(@ModelAttribute Task task, @RequestParam List<Long> userIds) {
        taskService.updateTask(task, userIds);
        return "redirect:/tasks/my";
    }

    @PostMapping("/updateStatus")
    public String updateStatus(@RequestParam Long id, @RequestParam String status) {
        taskService.updateTaskStatus(id, status);
        return "redirect:/tasks/homepage";
    }

    @PostMapping("/delete")
    public String deleteTask(@RequestParam Long id) {
        taskService.deleteTask(id);
        return "redirect:/tasks/my";
    }

    @PostMapping("/deleteFromHome")
    public String deleteTaskFromHome(@RequestParam Long id) {
        taskService.deleteTask(id);
        return "redirect:/tasks/homepage";
    }

    @GetMapping("/homepage")
    public String homepage(Model model, Principal principal) {
        model.addAttribute("tasks", taskService.getAllTasks());
        model.addAttribute("activities", activityRepository.findTop5ByOrderByIdDesc());
        return "homepage";
    }

    private Long getCurrentUserId(Principal principal) {
        String email = principal.getName(); // Returns the 'username' used to log in (email in your case)
        return userService.findByEmail(email)
                .map(User::getId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }
}
