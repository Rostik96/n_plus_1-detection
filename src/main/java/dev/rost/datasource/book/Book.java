package dev.rost.datasource.book;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
public class Book {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;
    private String name;
    @ManyToOne
    private Author author;
}
