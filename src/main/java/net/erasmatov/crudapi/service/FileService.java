package net.erasmatov.crudapi.service;

import net.erasmatov.crudapi.model.Event;
import net.erasmatov.crudapi.model.File;
import net.erasmatov.crudapi.model.User;
import net.erasmatov.crudapi.repository.EventRepository;
import net.erasmatov.crudapi.repository.FileRepository;
import net.erasmatov.crudapi.repository.UserRepository;
import net.erasmatov.crudapi.repository.hibernate.HibernateEventRepositoryImpl;
import net.erasmatov.crudapi.repository.hibernate.HibernateFileRepositoryImpl;
import net.erasmatov.crudapi.repository.hibernate.HibernateUserRepositoryImpl;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

public class FileService {
    private final FileRepository fileRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public FileService() {
        this.fileRepository = new HibernateFileRepositoryImpl();
        this.userRepository = new HibernateUserRepositoryImpl();
        this.eventRepository = new HibernateEventRepositoryImpl();
    }

    public FileService(FileRepository fileRepository, UserRepository userRepository, EventRepository eventRepository) {
        this.fileRepository = fileRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    public File saveFile(File file) {
        return fileRepository.save(file);
    }

    public List<File> getAllFiles() {
        return fileRepository.getAll();
    }

    public File getFileById(Integer id) {
        return fileRepository.getById(id);
    }

    public File uploadFile(HttpServletRequest req, String fileUploadPath) {
        File fileEntity;
        User user;
        Event event;

        int userId = Integer.parseInt(req.getHeader("User-ID"));
        int maxFileSize = 50 * 1024;
        int maxMemSize = 4 * 1024;
        java.io.File file;

        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(maxMemSize);
        factory.setRepository(new java.io.File(fileUploadPath));
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setSizeMax(maxFileSize);

        try {
            List<FileItem> fileItems = upload.parseRequest(req);
            Iterator<FileItem> i = fileItems.iterator();
            while (i.hasNext()) {
                FileItem fi = i.next();
                if (!fi.isFormField()) {
                    String fileName = fi.getName();

                    if (fileName.lastIndexOf("//") >= 0) {
                        file = new java.io.File(fileUploadPath + fileName.substring(fileName.lastIndexOf("\\")));
                    } else {
                        file = new java.io.File(fileUploadPath + fileName.substring(fileName.lastIndexOf("\\") + 1));
                    }
                    fi.write(file);

                    fileEntity = new File();
                    fileEntity.setName(fileName);
                    fileEntity.setPath(fileUploadPath + fileName);

                    user = userRepository.getById(userId);

                    event = new Event();
                    event.setTimestamp(LocalDateTime.now());
                    event.setUser(user);
                    event.setFile(fileEntity);

                    eventRepository.save(event);
                    return fileEntity;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new File();
    }

    public File updateFile(File file) {
        return fileRepository.update(file);
    }

    public void deleteFileById(Integer id) {
        fileRepository.deleteById(id);
    }

}
