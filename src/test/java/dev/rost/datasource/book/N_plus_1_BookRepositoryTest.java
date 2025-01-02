package dev.rost.datasource.book;

import dev.rost.datasource.AbstractPersistTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.hypersistence.utils.jdbc.validator.SQLStatementCountValidator.assertSelectCount;


@SuppressWarnings("NewClassNamingConvention")
class N_plus_1_BookRepositoryTest extends AbstractPersistTest {

	@Autowired BookRepository repository;


	@Test
	void test() {
		var books = repository.findAll();
		var n = books.size();
		assertSelectCount(1 + n);
	}
}
