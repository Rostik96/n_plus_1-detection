package dev.rost.datasource.book;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
class Author {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    int id;
    String name;
    @OneToMany
    List<Book> books;
}
