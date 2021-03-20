package com.zikozee.repositories.reactive;

import com.zikozee.domain.Category;
import com.zikozee.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@DataMongoTest
public class CategoryRepositoryTest {

    @Autowired
    CategoryReactiveRepository categoryReactiveRepository;

    @Before
    public void setup() throws Exception{
        categoryReactiveRepository.deleteAll().block();
    }

    @Test
    public void persistData() throws Exception{
        Category category =new Category();
        category.setDescription("FOO-BAR");

        Category persistedCategory =  categoryReactiveRepository.save(category).block();

        Long count = categoryReactiveRepository.count().block();

        assertEquals( "FOO-BAR", persistedCategory.getDescription());
        assertEquals(Long.valueOf(1L) , count);
    }

    @Test
    public void testFindByDescription() throws Exception{
        Category category =new Category();
        category.setDescription("FOO-BAR");

        Category persistedCategory =  categoryReactiveRepository.save(category).block();

        Category fetchedCat = categoryReactiveRepository.findByDescription("FOO-BAR").block();

        assertNotNull(fetchedCat.getId());
        assertEquals("FOO-BAR", persistedCategory.getDescription());
    }
}
