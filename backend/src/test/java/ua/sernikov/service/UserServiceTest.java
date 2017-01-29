package ua.sernikov.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import ua.sernikov.domain.User;
import ua.sernikov.domain.UserRole;
import ua.sernikov.exception.UserAlreadyExistException;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class UserServiceTest {

    private UserService userService;

    private final String TEST_NAME = "test";
    private final String TEST_EMAIL = "test@mail.com";

    @Before
    public void setUp() throws Exception {
        userService = new UserServiceImpl();
    }

    @Test
    public void shouldCreateNewOperator() throws Exception {
        User operator = userService.createOperator(TEST_NAME, TEST_EMAIL);

        assertThat(operator).isNotNull();
        assertThat(operator.getRole()).isEqualTo(UserRole.OPERATOR);
    }

    @Test
    public void shouldCreateNewPublisher() throws Exception {
        User publisher = userService.createPublisher(TEST_NAME, TEST_EMAIL);

        assertThat(publisher).isNotNull();
        assertThat(publisher.getRole()).isEqualTo(UserRole.PUBLISHER);
    }

    @Test
    public void shouldCreateOperatorWithKey() throws Exception {
        User actualOperator = userService.createOperator(TEST_NAME, TEST_EMAIL);

        assertThat(actualOperator.getKey()).isNotEmpty();
    }

    @Test
    public void shouldCreateOperatorWithUniqueKey() throws Exception {
        String name1 = "test1";
        String email1 = "test1@mail.com";
        User operator1 = userService.createOperator(name1, email1);

        String name2 = "test2";
        String email2 = "test2@mail.com";
        User operator2 = userService.createOperator(name2, email2);

        assertThat(operator1.getKey()).isNotEqualTo(operator2.getKey());
    }

    @Test
    public void shouldCreateOperatorForGivenNameAndEmail() throws Exception {
        User actualOperator = userService.createOperator(TEST_NAME, TEST_EMAIL);

        assertThat(actualOperator.getName()).isEqualTo(TEST_NAME);
        assertThat(actualOperator.getEmail()).isEqualTo(TEST_EMAIL);
    }

    @Test(expected = UserAlreadyExistException.class)
    public void shouldThrowUserAlreadyExistsException() throws Exception {
        String name1 = "test1";
        String name2 = "test2";

        userService.createOperator(name1, TEST_EMAIL);
        userService.createOperator(name2, TEST_EMAIL);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenEmailNotPresented() throws Exception {
        userService.createPublisher(TEST_NAME, null);
        userService.createOperator(TEST_NAME, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenEmailIsEmptyString() throws Exception {
        userService.createPublisher(TEST_NAME, "");
        userService.createOperator(TEST_NAME, "");
    }

    @Test
    public void shouldGiveEmptyListWhenHasNoOperators() throws Exception {
        userService.createPublisher(TEST_NAME, TEST_EMAIL);

        List<User> operators = userService.getAllOperators();

        assertThat(operators).isEmpty();
    }

    @Test
    public void shouldGiveEmptyListWhenHasNoPublishers() throws Exception {
        userService.createOperator(TEST_NAME, TEST_EMAIL);

        List<User> publishers = userService.getAllPublishers();

        assertThat(publishers).isEmpty();
    }

    @Test
    public void shouldGiveAllOperators() throws Exception {
        User operator1 = userService.createOperator(TEST_NAME, "test1@mail.com");
        User operator2 = userService.createOperator(TEST_NAME, "test2@mail.com");
        User publisher = userService.createPublisher(TEST_NAME, "test3@mail.com");

        List<User> operators = userService.getAllOperators();

        assertThat(operators).hasSize(2)
                             .contains(operator1, operator2)
                             .doesNotContain(publisher);
    }

    @Test
    public void shouldGiveOperatorByKey() throws Exception {
        User operator1 = userService.createOperator(TEST_NAME, "test1@mail.com");
        User operator2 = userService.createOperator(TEST_NAME, "test2@mail.com");

        User actualOperator = userService.getOperatorByKey(operator2.getKey());

        assertThat(actualOperator).isNotNull()
                                  .isEqualTo(operator2)
                                  .isNotEqualTo(operator1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenGiveOperatorByEmptyKey() throws Exception {
        User operator = userService.createOperator(TEST_NAME, TEST_EMAIL);

        userService.getOperatorByKey(null);
        userService.getOperatorByKey("");
    }

    @Test
    public void shouldGiveNullWhenOperatorDoesNotExistWithGivenKey() throws Exception {
        User operator = userService.getOperatorByKey(UUID.randomUUID().toString());

        assertThat(operator).isNull();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenGiveOperatorByNotUUIDKey() throws Exception {
        userService.getOperatorByKey("test string");
    }

    @Test
    public void shouldGivePublisherByKey() throws Exception {
        User expectedPublisher = userService.createPublisher(TEST_NAME, "test1@mail.com");
        User anotherPublisher = userService.createPublisher(TEST_NAME, "test2@mail.com");
        User operator = userService.createOperator(TEST_NAME, "test3@mail.com");

        User actualPublisher = userService.getPublisherByKey(expectedPublisher.getKey());

        assertThat(actualPublisher).isNotNull()
                                   .isEqualTo(expectedPublisher)
                                   .isNotEqualTo(anotherPublisher)
                                   .isNotEqualTo(operator);
    }

    @Test
    public void shouldGiveNullWhenPublisherDoesNotExistWithGivenKey() throws Exception {
        User publisher = userService.getPublisherByKey(UUID.randomUUID().toString());

        assertThat(publisher).isNull();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenGivePublisherByEmptyKey() throws Exception {
        userService.getPublisherByKey(null);
        userService.getPublisherByKey("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenGivePublisherByNotUUIDKey() throws Exception {
        userService.getPublisherByKey("test string");
    }

    @Test
    public void shouldRemoveOperatorByKey() throws Exception {
        User operator = userService.createOperator(TEST_NAME, TEST_EMAIL);

        User removedOperator = userService.removeOperatorByKey(operator.getKey());
        List<User> operators = userService.getAllOperators();

        assertThat(removedOperator).isNotNull()
                                   .isEqualTo(operator);
        assertThat(operators).isEmpty();
    }

    @Test
    public void shouldGiveNullWhenRemoveNotExistingOperator() throws Exception {
        User removeOperator = userService.removeOperatorByKey(UUID.randomUUID().toString());
        List<User> operators = userService.getAllOperators();

        assertThat(removeOperator).isNull();
        assertThat(operators).isEmpty();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenRemoveOperatorByNotUUIDKey() throws Exception {
        userService.removeOperatorByKey("test string");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenRemoveOperatorByEmptyKey() throws Exception {
        userService.removeOperatorByKey(null);
        userService.removeOperatorByKey("");
    }

    @Test
    public void shouldRemovePublisherByKey() throws Exception {
        User publisher = userService.createPublisher(TEST_NAME, TEST_EMAIL);

        User removedPublisher = userService.removePublisherByKey(publisher.getKey());
        List<User> publishers = userService.getAllPublishers();

        assertThat(removedPublisher).isNotNull()
                                    .isEqualTo(publisher);
        assertThat(publishers).isEmpty();
    }

    @Test
    public void shouldGiveNullWhenRemoveNotExistingPublisher() throws Exception {
        User removedPublisher = userService.removePublisherByKey(UUID.randomUUID().toString());
        List<User> publishers = userService.getAllPublishers();

        assertThat(removedPublisher).isNull();
        assertThat(publishers).isEmpty();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenRemovePublisherByNotUUIDKey() throws Exception {
        userService.removePublisherByKey("test string");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenRemovePublisherByEmptyKey() throws Exception {
        userService.removePublisherByKey(null);
        userService.removePublisherByKey("");
    }
}
