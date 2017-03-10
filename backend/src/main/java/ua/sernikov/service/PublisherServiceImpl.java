package ua.sernikov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ua.sernikov.domain.UpdateUserRequest;
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
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERATOR')")
    public User createPublisher(String name, String email) {
        return userService.createUser(name, email, UserRole.PUBLISHER);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERATOR')")
    public List<User> getAllPublishers() {
        return userService.getAllUsersByRole(UserRole.PUBLISHER);
    }

    @Override
    @PreAuthorize("hasAnyRole('OPERATOR', 'PUBLISHER')")
    public User getPublisherByKey(String publisherKey) {
        return userService.getUserByKey(publisherKey);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERATOR')")
    public void deletePublisherByKey(String publisherKey) {
        userService.deleteUserByKey(publisherKey);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERATOR')")
    public User updatePublisher(UpdateUserRequest updatePublisherRequest) {
        return userService.updateUser(updatePublisherRequest);
    }
}
