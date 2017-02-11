package ua.sernikov.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotBlank;

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
}
