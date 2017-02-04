package ua.sernikov.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ua.sernikov.domain.User;
import ua.sernikov.domain.UserRole;
import ua.sernikov.exception.UserAlreadyExistException;
import ua.sernikov.exception.UserNotFoundException;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class UserServiceTest {

    private UserService userService;

    private final String TEST_NAME = "test";
    private final String TEST_EMAIL = "test@mail.com";

    @Before
    public void setUp() throws Exception {
        userService = new UserServiceImpl();
    }

    @Test
    public void createUser_ShouldCreateNewUser() throws Exception {
        User actualUser = userService.createUser(TEST_NAME, TEST_EMAIL, UserRole.OPERATOR);

        assertThat(actualUser).isNotNull();
        assertThat(actualUser.getKey()).isNotEmpty();
        assertThat(actualUser.getName()).isEqualTo(TEST_NAME);
        assertThat(actualUser.getEmail()).isEqualTo(TEST_EMAIL);
        assertThat(actualUser.getRole()).isEqualTo(UserRole.OPERATOR);
    }

    @Test
    public void createUser_ShouldCreateUsersWithUniqueKeys() throws Exception {
        String name1 = "test1";
        String email1 = name1 + "@mail.com";
        User user1 = userService.createUser(name1, email1, UserRole.OPERATOR);

        String name2 = "test2";
        String email2 = name2 + "@mail.com";
        User user2 = userService.createUser(name2, email2, UserRole.OPERATOR);

        assertThat(user1.getKey()).isNotEqualTo(user2.getKey());
    }

    @Test(expected = UserAlreadyExistException.class)
    public void createUser_ShouldThrowUserAlreadyExistException_WhenUserExistsWithGivenEmail() throws Exception {
        String name1 = "test1";
        String name2 = "test2";

        userService.createUser(name1, TEST_EMAIL, UserRole.OPERATOR);
        userService.createUser(name2, TEST_EMAIL, UserRole.PUBLISHER);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createUser_ShouldThrowIllegalArgumentException_WhenEmailIsNotPresentedOrEmpty() throws Exception {
        userService.createUser(TEST_NAME, null, UserRole.OPERATOR);
        userService.createUser(TEST_NAME, "", UserRole.PUBLISHER);
    }

    @Test
    public void getUserByKey_ShouldGiveUserByKey() throws Exception {
        User operator1 = userService.createUser(TEST_NAME, "test1@mail.com", UserRole.OPERATOR);
        User operator2 = userService.createUser(TEST_NAME, "test2@mail.com", UserRole.OPERATOR);

        User actualOperator = userService.getUserByKey(operator2.getKey());

        assertThat(actualOperator).isNotNull()
                                  .isEqualTo(operator2)
                                  .isNotEqualTo(operator1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getUserByKey_ShouldThrowIllegalArgumentException_WhenUserKeyNotPresentedOrEmpty() throws Exception {
        User operator = userService.createUser(TEST_NAME, TEST_EMAIL, UserRole.OPERATOR);

        userService.getUserByKey(null);
        userService.getUserByKey("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void getUserByKey_ShouldThrowIllegalArgumentException_WhenUserKeyIsNotUUID() throws Exception {
        userService.getUserByKey("test string");
    }

    @Test
    public void getUserByKey_ShouldGiveNull_WhenUserDoesNotExistWithGivenKey() throws Exception {
        User operator = userService.getUserByKey(UUID.randomUUID().toString());

        assertThat(operator).isNull();
    }

    @Test
    public void removeUserByKey_ShouldRemoveUserByKey() throws Exception {
        User operator = userService.createUser(TEST_NAME, TEST_EMAIL, UserRole.OPERATOR);

        User removedOperator = userService.removeUserByKey(operator.getKey());
        List<User> operators = userService.getAllUsers(UserRole.OPERATOR);

        assertThat(removedOperator).isNotNull()
                                   .isEqualTo(operator);
        assertThat(operators).isEmpty();
    }

    @Test
    public void removeUserByKey_ShouldGiveNull_WhenUserDoesNotExist() throws Exception {
        User removedUser = userService.removeUserByKey(UUID.randomUUID().toString());

        assertThat(removedUser).isNull();
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeUserByKey_ShouldThrowIllegalArgumentException_WhenUserKeyIsNotUUID() throws Exception {
        userService.removeUserByKey("test string");
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeUserByKey_ShouldThrowIllegalArgumentException_WhenUserKeyIsNotPresentedOrEmpty() throws Exception {
        userService.removeUserByKey(null);
        userService.removeUserByKey("");
    }

    @Test
    public void updateUser_ShouldUpdateOnlyNameAndEmail() throws Exception {
        String expectedName = "new_test";
        String expectedEmail = expectedName + "@mail.com";
        User operator = userService.createUser(TEST_NAME, TEST_EMAIL, UserRole.OPERATOR);
        operator.setName(expectedName);
        operator.setEmail(expectedEmail);

        User actualOperator = userService.updateUser(operator);

        assertThat(actualOperator).isNotNull()
                                  .isEqualTo(operator);

        assertThat(actualOperator.getName()).isEqualTo(expectedName)
                                            .isNotEqualTo(TEST_NAME);

        assertThat(actualOperator.getEmail()).isEqualTo(expectedEmail)
                                             .isNotEqualTo(TEST_EMAIL);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateUser_ShouldThrowIllegalArgumentException_WhenUserIsNull() throws Exception {
        userService.updateUser(null);
    }

    @Test(expected = UserNotFoundException.class)
    public void updateUser_ShouldThrowUserNotFoundException_WhenUserDoesNotExist() throws Exception {
        User user = new User(TEST_NAME, TEST_EMAIL, UserRole.OPERATOR);

        userService.updateUser(user);
    }
}
