package dev.rost.datasource.book;

import dev.rost.datasource.AbstractPersistTest;
import io.hypersistence.utils.jdbc.validator.SQLStatementCountValidator;
import jakarta.persistence.EntityManager;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import static io.hypersistence.utils.jdbc.validator.SQLStatementCountValidator.assertSelectCount;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
class BookRepositoryTest extends AbstractPersistTest {

	@Autowired EntityManager em;
	@Autowired BookRepository repository;

	@BeforeEach
	void beforeEach() {
		SQLStatementCountValidator.reset();
	}


	@Test
	void test() {
		var books = repository.findAll();
		retrieveAuthors(books);
		assertSelectCount(1 + books.size());
	}


	@Nested
	class WithJoinFetch {
		@Autowired BookRepository repository;

		@Test
		void test() {
			retrieveAuthors(repository.findAll());
			assertSelectCount(1);
		}

		interface BookRepository extends JpaRepository<Book, Integer> {
			@Query("select b from Book b join fetch b.author")
			@NotNull List<Book> findAll();
		}
	}


    @Nested
	class WithEntityGraph {
		@Autowired BookRepository repository;

		@Test
		void test() {
			retrieveAuthors(repository.findAll());
			assertSelectCount(1);
		}

		interface BookRepository extends JpaRepository<Book, Integer> {
			@EntityGraph(attributePaths = "author")
			@NotNull List<Book> findAll();
		}
	}


	void retrieveAuthors(List<Book> books) {
		books.forEach(b -> {
			b.getAuthor();
			em.clear();
		});
	}
}
