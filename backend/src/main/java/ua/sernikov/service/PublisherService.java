package ua.sernikov.service;

import ua.sernikov.domain.User;

import java.util.List;

public interface PublisherService {

    User createPublisher(String name, String email);

    List<User> getAllPublishers();

    User getPublisherByKey(String publisherKey);

    User deletePublisherByKey(String publisherKey);

    User updatePublisher(User publisher);
}
