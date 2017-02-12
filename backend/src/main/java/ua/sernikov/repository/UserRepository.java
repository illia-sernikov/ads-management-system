package ua.sernikov.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ua.sernikov.domain.User;
import ua.sernikov.domain.UserRole;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    @Override
    Collection<User> findAll();

    Optional<User> findByKey(String key);

    Optional<User> findByEmail(String email);

    Collection<User> findAllByRole(UserRole role);

    Optional<User> deleteByKey(String key);

    @Modifying(clearAutomatically = true)
    @Query("update User u set u.name=?1, u.email=?2 where u.key=?3")
    void updateByKey(String name, String email, String key);
}
