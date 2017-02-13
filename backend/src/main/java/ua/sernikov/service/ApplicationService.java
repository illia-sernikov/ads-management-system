package ua.sernikov.service;

import ua.sernikov.domain.Application;
import ua.sernikov.domain.NewApplicationRequest;

import java.util.List;

public interface ApplicationService {

    List<Application> getAllApplications();

    List<Application> getAllPublisherApplications(String publisherKey);

    Application getApplicationByKey(String applicationKey);

    Application createApplication(NewApplicationRequest newApplication);

    Application updateApplication(Application application);

    Long deleteApplication(String applicationKey);
}
