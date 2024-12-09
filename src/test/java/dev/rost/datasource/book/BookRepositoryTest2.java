package dev.rost.datasource.book;

import dev.rost.datasource.AbstractDataJpaTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


class BookRepositoryTest2 extends AbstractDataJpaTest {

    @Autowired BookRepository repository;


    @Test
    void test() {
        repository.findAll().forEach(System.out::println);
    }
}
