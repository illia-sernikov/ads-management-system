package ua.sernikov.domain;

public class UpdateApplicationRequest {

    private String key;
    private String name;
    private AppType type;

    public UpdateApplicationRequest() {
    }

    public UpdateApplicationRequest(String name, AppType type) {
        this.name = name;
        this.type = type;
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

    public AppType getType() {
        return type;
    }

    public void setType(AppType type) {
        this.type = type;
    }
}
