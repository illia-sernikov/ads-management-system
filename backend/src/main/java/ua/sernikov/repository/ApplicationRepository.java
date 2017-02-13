package ua.sernikov.repository;

import org.springframework.data.repository.CrudRepository;
import ua.sernikov.domain.Application;

import java.util.Collection;
import java.util.Optional;

public interface ApplicationRepository extends CrudRepository<Application, Long> {

    @Override
    Collection<Application> findAll();

    Optional<Application> findByKey(String applicationKey);

    Collection<Application> findAllByOwnerKey(String ownerKey);

    Long deleteByKey(String applicationKey);
}
