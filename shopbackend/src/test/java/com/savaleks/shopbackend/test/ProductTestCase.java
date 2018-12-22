package com.savaleks.shopbackend.test;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.savaleks.shopbackend.dao.ProductDAO;
import com.savaleks.shopbackend.dto.Product;

public class ProductTestCase {

	private static AnnotationConfigApplicationContext context;

	private static ProductDAO productDAO;

	private Product product;

	@BeforeClass
	public static void init() {

		context = new AnnotationConfigApplicationContext();
		context.scan("com.savaleks.shopbackend");
		context.refresh();

		productDAO = (ProductDAO) context.getBean("productDAO");

	}

	/*
	 * @Test public void testCRUDProduct() {
	 * 
	 * // add operation product = new Product();
	 * 
	 * product.setName("book"); product.setBrand("F.Black");
	 * product.setDescription("some description for books");
	 * product.setUnitPrice(236); product.setActive(true); product.setCategoryId(2);
	 * product.setSupplierId(3);
	 * 
	 * assertEquals("something went wrong while inserting a new product", true,
	 * productDAO.add(product));
	 * 
	 * // reading and updating the category product = productDAO.get(2);
	 * product.setName("Java EE");
	 * 
	 * assertEquals("something went wrong while inserting a new product", true,
	 * productDAO.update(product));
	 * 
	 * assertEquals("something went wrong while inserting a new product", true,
	 * productDAO.delete(product));
	 * 
	 * assertEquals("something went wrong while inserting a new product", 4,
	 * productDAO.list().size()); }
	 */

	@Test
	public void testListActiveProduct() {
		assertEquals("something went wrong while inserting a new product", 4, productDAO.listActiveProducts().size());
	}

	@Test
	public void testListActiveProductByCategory() {
		assertEquals("something went wrong while inserting a new product", 1,
				productDAO.listActiveProductsByCategories(3).size());
		assertEquals("something went wrong while inserting a new product", 2,
				productDAO.listActiveProductsByCategories(2).size());
	}

	@Test
	public void testGetLatestActiveProduct() {
		assertEquals("something went wrong while inserting a new product", 3,
				productDAO.getLatestActiveProducts(3).size());
	}

}
