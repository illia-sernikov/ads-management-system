package ua.sernikov.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import ua.sernikov.domain.User;
import ua.sernikov.domain.UserRole;
import ua.sernikov.exception.UserAlreadyExistException;

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
}
