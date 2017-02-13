package ua.sernikov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.sernikov.domain.User;
import ua.sernikov.domain.UserRole;

import java.util.List;

@Service
public class PublisherServiceImpl implements PublisherService {

    private UserService userService;

    @Autowired
    public PublisherServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User createPublisher(String name, String email) {
        return userService.createUser(name, email, UserRole.PUBLISHER);
    }

    @Override
    public List<User> getAllPublishers() {
        return userService.getAllUsersByRole(UserRole.PUBLISHER);
    }

    @Override
    public User getPublisherByKey(String publisherKey) {
        return userService.getUserByKey(publisherKey);
    }

    @Override
    public void deletePublisherByKey(String publisherKey) {
        userService.deleteUserByKey(publisherKey);
    }

    @Override
    public User updatePublisher(User publisher) {
        return userService.updateUser(publisher);
    }
}
