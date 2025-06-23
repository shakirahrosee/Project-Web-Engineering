package com.group2.collapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group2.collapp.model.FileUpload;
import com.group2.collapp.repository.FileUploadRepository;
import com.group2.collapp.repository.TaskRepository;

@Service
public class FileUploadService {
    @Autowired private FileUploadRepository fileUploadRepository;
    @Autowired private TaskRepository taskRepository;

    public FileUpload storeFile(Long taskId, String fileName, String contentType, byte[] data) {
        FileUpload file = new FileUpload();
        file.setFileName(fileName);
        file.setFileType(contentType);
        file.setData(data);
        file.setTask(taskRepository.findById(taskId).orElseThrow());
        return fileUploadRepository.save(file);
    }
}
