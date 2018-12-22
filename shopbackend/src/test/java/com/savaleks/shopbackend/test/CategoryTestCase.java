package com.savaleks.shopbackend.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.savaleks.shopbackend.dao.CategoryDAO;
import com.savaleks.shopbackend.dto.Category;

public class CategoryTestCase {

	private static AnnotationConfigApplicationContext context;

	private static CategoryDAO categoryDAO;

	private Category category;

	@Before
	public void init() {

		context = new AnnotationConfigApplicationContext();
		context.scan("com.savaleks.shopbackend");
		context.refresh();

		categoryDAO = (CategoryDAO) context.getBean("categoryDAO");

	}

	/*
	 * @Test public void testAddCategory() {
	 * 
	 * category = new Category();
	 * 
	 * category.setName("readers");
	 * category.setDescription("some description for readers");
	 * category.setImageUrl("CAT3.png");
	 * 
	 * assertEquals("successfully added a category to the table", true,
	 * categoryDAO.add(category)); }
	 */

	/*
	 * @Test public void testGetCategory() { category = categoryDAO.get(3);
	 * 
	 * assertEquals("successfully fetched a single category from the table",
	 * "readers", category.getName()); }
	 */

	/*
	 * @Test public void testUpdateCategory() { category = categoryDAO.get(3);
	 * category.setName("magazines");
	 * 
	 * assertEquals("successfully updated a single category in the table", true,
	 * categoryDAO.update(category)); }
	 */

	/*
	 * @Test public void testDeleteCategory() { category = categoryDAO.get(3);
	 * 
	 * assertEquals("successfully deleted a single category from the table", true,
	 * categoryDAO.delete(category)); }
	 */

	/*
	 * @Test public void testListCategory() {
	 * 
	 * assertEquals("successfully fetched the list of category from the table", 2,
	 * categoryDAO.list().size()); }
	 */

	@Test
	public void testCRUDCategory() {

		// add operation
		category = new Category();

		category.setName("book");
		category.setDescription("some description for books");
		category.setImageUrl("CAT1.png");

		assertEquals("successfully added a category to the table", true, categoryDAO.add(category));

		category = new Category();

		category.setName("magazines");
		category.setDescription("some description for booagazinesks");
		category.setImageUrl("CAT2.png");

		assertEquals("successfully added a category to the table", true, categoryDAO.add(category));

		// fetching and updating the category
		category = categoryDAO.get(2);

		category.setName("accessories");

		assertEquals("successfully updated a single category in the table", true, categoryDAO.update(category));

		// delete the category
		assertEquals("successfully deleted a single category from the table", true, categoryDAO.delete(category));

		// fetching the list
		assertEquals("successfully fetched the list of category from the table", 1, categoryDAO.list().size());
	}

}
