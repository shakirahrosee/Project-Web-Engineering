package com.group2.collapp.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.group2.collapp.model.Task;
import com.group2.collapp.services.TaskService;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    @Autowired private TaskService taskService;

    @GetMapping("/my")
    public String userTasks(Model model, Principal principal) {
        Long userId = getCurrentUserId(principal);
        model.addAttribute("tasks", taskService.getTasksForUser(userId));
        return "user-task";
    }

    @PostMapping("/create")
    public String createTask(@ModelAttribute Task task, @RequestParam List<Long> userIds) {
        taskService.createTask(task, userIds);
        return "redirect:/tasks/my";
    }

    @PostMapping("/delete")
    public String deleteTask(@RequestParam Long taskId) {
        taskService.deleteTask(taskId);
        return "redirect:/tasks/my";
    }

    private Long getCurrentUserId(Principal principal) {
        // Dummy implementation, replace with proper logic
        return 1L;
    }
}
