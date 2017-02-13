package ua.sernikov.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import ua.sernikov.domain.Application;
import ua.sernikov.domain.NewApplicationRequest;
import ua.sernikov.service.ApplicationService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/applications",
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ApplicationController {

    private ApplicationService applicationService;

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Application> getAllApplications() {
        return applicationService.getAllApplications();
    }

    @RequestMapping(method = RequestMethod.GET, params = "publisherKey")
    public List<Application> getAllPublisherApplications(@RequestParam String publisherKey) {
        Assert.hasText(publisherKey);
        return applicationService.getAllPublisherApplications(publisherKey);
    }

    @RequestMapping(method = RequestMethod.GET, value = "{key}")
    public Application getApplication(@PathVariable("key") String applicationKey) {
        Assert.hasText(applicationKey);
        return applicationService.getApplicationByKey(applicationKey);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Application createApplication(@RequestBody @Valid NewApplicationRequest applicationRequest) {
        return applicationService.createApplication(applicationRequest);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "{key}")
    public Application updateApplication(@PathVariable("key") String applicationKey, @RequestBody @Valid Application application) {
        Assert.hasText(applicationKey);
        return applicationService.updateApplication(application);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "{key}")
    public Application deleteApplication(@PathVariable("key") String applicationKey) {
        Assert.hasText(applicationKey);
        return applicationService.deleteApplication(applicationKey);
    }
}
