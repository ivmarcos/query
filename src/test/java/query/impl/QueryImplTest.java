package query.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import query.mock.MockEntity;

public class QueryImplTest {
	
	@Test
	public void shouldCreateInJPASyntax() {
		final String result = "Select m from MockEntity m where m.id = :id";
		String query  = new Query(null, MockEntity.class).where("id").is(1L).getQueryString();
		assertEquals(result, query);
	}
}
