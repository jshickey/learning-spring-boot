class Book {
	String author
	String title
}


class BookTests {
	@Test
	void testBooks() {
		Book book = new Book(author: "Tom Clancy",
			title: "Threat Vector")
		assert "Tom Clancy" == book.title
	}
}