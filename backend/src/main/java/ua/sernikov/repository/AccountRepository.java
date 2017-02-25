package ua.sernikov.repository;

import org.springframework.data.repository.CrudRepository;
import ua.sernikov.domain.Account;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {

    Optional<Account> findByEmail(String email);

}
