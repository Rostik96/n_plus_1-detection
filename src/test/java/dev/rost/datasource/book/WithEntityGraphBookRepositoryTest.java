package dev.rost.datasource.book;

import dev.rost.datasource.AbstractPersistTest;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import static io.hypersistence.utils.jdbc.validator.SQLStatementCountValidator.assertSelectCount;

class WithEntityGraphBookRepositoryTest extends AbstractPersistTest {

    @Autowired WithEntityGraphBookRepositoryTest.BookRepository repository;


    @Test
    void test() {
        repository.findAll();
        assertSelectCount(1);
    }


    interface BookRepository extends JpaRepository<Book, Integer> {
        @EntityGraph(attributePaths = "author")
        @NotNull List<Book> findAll();
    }
}
