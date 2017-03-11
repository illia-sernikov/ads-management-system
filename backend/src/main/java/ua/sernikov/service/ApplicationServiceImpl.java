package ua.sernikov.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ua.sernikov.domain.Application;
import ua.sernikov.domain.Application.ApplicationBuilder;
import ua.sernikov.domain.NewApplicationRequest;
import ua.sernikov.domain.UpdateApplicationRequest;
import ua.sernikov.domain.User;
import ua.sernikov.exception.ApplicationNotFoundException;
import ua.sernikov.repository.ApplicationRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private ApplicationRepository applicationRepository;
    private PublisherService publisherService;

    @Autowired
    public ApplicationServiceImpl(ApplicationRepository applicationRepository, PublisherService publisherService) {
        this.applicationRepository = applicationRepository;
        this.publisherService = publisherService;
    }

    @Override
    @PreAuthorize("hasRole('OPERATOR')")
    public List<Application> getAllApplications() {
        return applicationRepository.findAll().stream()
                                    .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasAnyRole('OPERATOR', 'PUBLISHER')")
    public List<Application> getAllPublisherApplications(String publisherKey) {
        Assert.hasText(publisherKey);
        return applicationRepository.findAllByOwnerKey(publisherKey).stream()
                                    .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasAnyRole('OPERATOR', 'PUBLISHER')")
    public Application getApplicationByKey(String applicationKey) {
        Assert.hasText(applicationKey);
        return applicationRepository.findByKey(applicationKey).orElse(null);
    }

    @Override
    @PreAuthorize("hasAnyRole('OPERATOR', 'PUBLISHER')")
    public Application createApplication(NewApplicationRequest applicationRequest) {
        Assert.notNull(applicationRequest);

        User publisher = publisherService.getPublisherByKey(applicationRequest.getPublisherKey());

        Application newApplication = ApplicationBuilder.anApplication()
                                                       .withKey(UUID.randomUUID().toString())
                                                       .withName(applicationRequest.getName())
                                                       .withType(applicationRequest.getType())
                                                       .withPublisher(publisher)
                                                       .withContentTypes(applicationRequest.getContentTypes())
                                                       .build();

        return applicationRepository.save(newApplication);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('OPERATOR', 'PUBLISHER')")
    public Application updateApplication(UpdateApplicationRequest updateRequest) {
        Application existingApplication = getApplicationByKey(updateRequest.getKey());

        if (existingApplication == null) {
            throw new ApplicationNotFoundException("Application with key " + updateRequest.getKey() + " not found");
        }

        if (StringUtils.isNoneBlank(updateRequest.getName())) {
            existingApplication.setName(updateRequest.getName());
        }

        if (updateRequest.getType() != null) {
            existingApplication.setType(updateRequest.getType());
        }

        return applicationRepository.save(existingApplication);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('OPERATOR', 'PUBLISHER')")
    public Long deleteApplication(String applicationKey) {
        Assert.hasText(applicationKey);
        return this.applicationRepository.deleteByKey(applicationKey);
    }
}
