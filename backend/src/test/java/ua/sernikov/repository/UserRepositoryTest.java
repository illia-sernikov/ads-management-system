package ua.sernikov.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import ua.sernikov.domain.User;
import ua.sernikov.domain.UserRole;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    private final String TEST_NAME = "test";
    private final String TEST_EMAIL = TEST_NAME + "@mail.com";
    private final String TEST_PASSWORD = "123456";

    @Test
    public void updateByKey_ShouldUpdateNameAndEmail() throws Exception {
        User operator = new User(TEST_NAME, TEST_EMAIL, TEST_PASSWORD, UserRole.OPERATOR);
        operator.setKey(UUID.randomUUID().toString());
        entityManager.persistAndFlush(operator);

        String expectedName = "new_test";
        String expectedEmail = expectedName + "@mail.com";
        userRepository.updateByKey(expectedName, expectedEmail, operator.getKey());

        User actualUser = userRepository.findByKey(operator.getKey()).orElse(null);

        assertThat(actualUser).isNotNull();

        assertThat(actualUser.getName()).isEqualTo(expectedName)
                                        .isNotEqualTo(TEST_NAME);

        assertThat(actualUser.getEmail()).isEqualTo(expectedEmail)
                                         .isNotEqualTo(TEST_EMAIL);

        assertThat(actualUser.getKey()).isEqualTo(operator.getKey());
    }

    @Test
    public void updateByKey_ShouldNotUpdate_WhenUserDoesNotExist() throws Exception {
        User operator = new User(TEST_NAME, TEST_EMAIL, TEST_PASSWORD, UserRole.OPERATOR);
        operator.setKey(UUID.randomUUID().toString());
        entityManager.persistAndFlush(operator);

        userRepository.updateByKey("new_test", "new_test@mail.com", UUID.randomUUID().toString());

        User actualUser = userRepository.findByKey(operator.getKey()).orElse(null);

        assertThat(actualUser).isNotNull();
        assertThat(actualUser.getName()).isEqualTo(TEST_NAME);
        assertThat(actualUser.getEmail()).isEqualTo(TEST_EMAIL);
    }
}