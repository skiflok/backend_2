package com.edu.backend.entity;

import com.edu.backend.enums.Gender;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "client", schema = "s21")
public class Client {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "clientsIdSeq", sequenceName = "s21.client_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clientsIdSeq")
    private Long id;

    @Column(name = "client_name")
    private String name;

    @Column(name = "client_surname")
    private String surname;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Enumerated(EnumType.STRING) // Используйте EnumType.ORDINAL, если хотите хранить индекс
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(name, client.name) &&
                Objects.equals(surname, client.surname) &&
                Objects.equals(birthday, client.birthday) &&
                gender == client.gender &&
                Objects.equals(registrationDate, client.registrationDate) &&
                Objects.equals(address, client.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, birthday, gender, registrationDate, address);
    }

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

}
