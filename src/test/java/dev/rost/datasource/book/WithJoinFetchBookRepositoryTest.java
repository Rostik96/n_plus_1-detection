package dev.rost.datasource.book;

import dev.rost.datasource.AbstractPersistTest;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import static io.hypersistence.utils.jdbc.validator.SQLStatementCountValidator.assertSelectCount;

class WithJoinFetchBookRepositoryTest extends AbstractPersistTest {

    @Autowired WithJoinFetchBookRepositoryTest.BookRepository repository;


    @Test
    void test() {
        repository.findAll();
        assertSelectCount(1);
    }


    interface BookRepository extends JpaRepository<Book, Integer> {
        @Query("select b from Book b join fetch b.author")
        @NotNull List<Book> findAll();
    }
}
