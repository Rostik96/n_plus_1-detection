package dev.rost.datasource.book;

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

import javax.sql.DataSource;
import java.util.List;

import static io.hypersistence.utils.jdbc.validator.SQLStatementCountValidator.assertSelectCount;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
class BookRepositoryTest0 {

	@Autowired DataSource dataSource;
	@Autowired EntityManager em;
	@Autowired BookRepository repository;

	@BeforeEach
	void beforeEach() {
		SQLStatementCountValidator.reset();
	}


	@Test
	void test() {
		System.out.println("dataSource = " + dataSource);
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
