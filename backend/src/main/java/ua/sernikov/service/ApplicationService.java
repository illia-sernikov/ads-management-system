package ua.sernikov.service;

import ua.sernikov.domain.Application;

import java.util.List;

public interface ApplicationService {

    List<Application> getAllApplications();

    List<Application> getAllPublisherApplications(String publisherKey);

    Application getApplicationByKey(String applicationKey);

    Application createApplication(Application newApplication);

    Application updateApplication(Application application);

    Application deleteApplication(String applicationKey);
}
