package ua.sernikov.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import ua.sernikov.domain.UpdateUserRequest;
import ua.sernikov.domain.User;
import ua.sernikov.service.PublisherService;

import java.util.List;

@RestController
@RequestMapping(value = "/publishers",
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PublisherController {

    private PublisherService publisherService;

    @Autowired
    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<User> getAllPublishers() {
        return publisherService.getAllPublishers();
    }

    @RequestMapping(method = RequestMethod.GET, value = "{key}")
    public User getPublisher(@PathVariable("key") String publisherKey) {
        Assert.hasText(publisherKey);
        return publisherService.getPublisherByKey(publisherKey);
    }

    @RequestMapping(method = RequestMethod.POST)
    public User createPublisher(@RequestBody User publisher) {
        return publisherService.createPublisher(publisher.getName(), publisher.getEmail());
    }

    @RequestMapping(method = RequestMethod.PUT, value = "{key}")
    public User updatePublisher(@PathVariable("key") String publisherKey, @RequestBody UpdateUserRequest updatePublisherRequest) {
        Assert.hasText(publisherKey);
        Assert.notNull(updatePublisherRequest);

        updatePublisherRequest.setKey(publisherKey);
        return publisherService.updatePublisher(updatePublisherRequest);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "{key}")
    public void deletePublisher(@PathVariable("key") String publisherKey) {
        Assert.hasText(publisherKey);
        publisherService.deletePublisherByKey(publisherKey);
    }
}
