package edu.gsu.steganography.model;

import java.util.Objects;

public class SecretKey {
    private String id;
    private String name;
    private String key;

    public SecretKey(String id, String name, String key) {
        this.id = id;
        this.name = name;
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SecretKey secretKey = (SecretKey) o;
        return Objects.equals(id, secretKey.id) &&
                Objects.equals(name, secretKey.name) &&
                Objects.equals(key, secretKey.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, key);
    }

    @Override
    public String toString() {
        return "SecretKey{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
