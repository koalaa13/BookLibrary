package com.example.entity;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "clients")
public class ClientEntity {
    @Id
    @Column(name = "client_id")
    private String clientId;
    private String hash;
    @Enumerated(EnumType.STRING)
    private Role role;

    public ClientEntity() {
    }

    public ClientEntity(String clientId, String hash, Role role) {
        this.clientId = clientId;
        this.hash = hash;
        this.role = role;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ClientEntity that = (ClientEntity) o;
        return Objects.equals(clientId, that.clientId) && Objects.equals(hash, that.hash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, hash);
    }

    @Override
    public String toString() {
        return "ClientEntity{" +
                "clientId='" + clientId + '\'' +
                ", hash='" + hash + '\'' +
                '}';
    }
}
