package ua.sernikov.domain;

import org.hibernate.validator.constraints.NotBlank;
import ua.sernikov.annotation.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class NewApplicationRequest {

    @NotBlank
    @Size(min = 2, max = 255)
    private String name;

    @NotNull
    private AppType type;

    @UUID
    private String publisherKey;

    private List<ContentType> contentTypes;

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

    public String getPublisherKey() {
        return publisherKey;
    }

    public void setPublisherKey(String publisherKey) {
        this.publisherKey = publisherKey;
    }

    public List<ContentType> getContentTypes() {
        return contentTypes;
    }

    public void setContentTypes(List<ContentType> contentTypes) {
        this.contentTypes = contentTypes;
    }
}
