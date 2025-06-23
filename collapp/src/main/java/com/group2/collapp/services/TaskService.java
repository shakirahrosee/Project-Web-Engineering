package com.group2.collapp.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group2.collapp.model.Task;
import com.group2.collapp.model.User;
import com.group2.collapp.repository.TaskRepository;
import com.group2.collapp.repository.UserRepository;

@Service
public class TaskService {
    @Autowired private TaskRepository taskRepository;
    @Autowired private UserRepository userRepository;

    public Task createTask(Task task, List<Long> userIds) {
        Set<User> users = new HashSet<>(userRepository.findAllById(userIds));
        task.setAssignedUsers(users);
        task.setStatus("Pending");
        return taskRepository.save(task);
    }

    public List<Task> getTasksForUser(Long userId) {
        return taskRepository.findByAssignedUsers_Id(userId);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task updateTask(Task task) {
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public void updateTaskStatus(Long id, String status) {
        Task task = taskRepository.findById(id).orElseThrow();
        task.setStatus(status);
        taskRepository.save(task);
        // send notification logic here
    }
}