package ua.sernikov.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotBlank;
import ua.sernikov.annotation.UUID;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    @Column(unique = true, nullable = false)
    @UUID
    private String key;

    @Column(unique = true, nullable = false)
    @NotBlank
    @Size(min = 2, max = 255)
    private String name;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    @NotNull
    private AppType type;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    @ElementCollection(targetClass = ContentType.class)
    private List<ContentType> contentTypes;

    @OneToOne
    private User owner;

    public void setId(Long id) {
        this.id = id;
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

    public List<ContentType> getContentTypes() {
        return contentTypes;
    }

    public void setContentTypes(List<ContentType> contentTypes) {
        this.contentTypes = contentTypes;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                       .append("key", key)
                       .append("name", name)
                       .append("type", type)
                       .append("contentTypes", contentTypes)
                       .append("owner", owner)
                       .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }

        if (!(o instanceof Application)) { return false; }

        Application that = (Application) o;

        return new EqualsBuilder()
                       .append(key, that.key)
                       .append(name, that.name)
                       .append(type, that.type)
                       .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                       .append(key)
                       .append(name)
                       .append(type)
                       .toHashCode();
    }

    public static final class ApplicationBuilder {

        private String key;
        private String name;
        private AppType type;
        private List<ContentType> contentTypes;
        private User owner;

        private ApplicationBuilder() {}

        public static ApplicationBuilder anApplication() { return new ApplicationBuilder();}

        public ApplicationBuilder withKey(String key) {
            this.key = key;
            return this;
        }

        public ApplicationBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public ApplicationBuilder withType(AppType type) {
            this.type = type;
            return this;
        }

        public ApplicationBuilder withContentTypes(List<ContentType> contentTypes) {
            this.contentTypes = contentTypes;
            return this;
        }

        public ApplicationBuilder withPublisher(User publisher) {
            this.owner = publisher;
            return this;
        }

        public Application build() {
            Application application = new Application();
            application.setKey(key);
            application.setName(name);
            application.setType(type);
            application.setContentTypes(contentTypes);
            application.setOwner(owner);
            return application;
        }
    }
}
