package ua.sernikov.domain;

public class UpdateUserRequest {

    private String key;
    private String name;
    private String email;
    private String password;

    public UpdateUserRequest() {
    }

    public UpdateUserRequest(String key, String name, String email) {
        this.key = key;
        this.name = name;
        this.email = email;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
