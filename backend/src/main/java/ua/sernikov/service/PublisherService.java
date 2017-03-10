package ua.sernikov.service;

import ua.sernikov.domain.UpdateUserRequest;
import ua.sernikov.domain.User;

import java.util.List;

public interface PublisherService {

    User createPublisher(String name, String email);

    List<User> getAllPublishers();

    User getPublisherByKey(String publisherKey);

    void deletePublisherByKey(String publisherKey);

    User updatePublisher(UpdateUserRequest updatePublisherRequest);
}
