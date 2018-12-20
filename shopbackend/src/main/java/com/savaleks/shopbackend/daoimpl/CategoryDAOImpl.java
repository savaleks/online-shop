package com.savaleks.shopbackend.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.savaleks.shopbackend.dao.CategoryDAO;
import com.savaleks.shopbackend.dto.Category;

@Repository("categoryDAO")
public class CategoryDAOImpl implements CategoryDAO {

	private static List<Category> categories = new ArrayList<>();
	static {
		Category category = new Category();
		category.setId(1);
		category.setName("book");
		category.setDescription("some description for book");
		category.setImageUrl("CAT1.png");

		categories.add(category);

		category = new Category();
		category.setId(2);
		category.setName("accessories");
		category.setDescription("some description for accessories");
		category.setImageUrl("CAT2.png");

		categories.add(category);

		category = new Category();
		category.setId(3);
		category.setName("readers");
		category.setDescription("some description for readers");
		category.setImageUrl("CAT3.png");

		categories.add(category);

	}

	@Override
	public List<Category> list() {
		return categories;
	}

	@Override
	public Category get(int id) {

		// enchnaced for loop
		for (Category category : categories) {
			if (category.getId() == id) {
				return category;
			}
		}
		return null;
	}

}
