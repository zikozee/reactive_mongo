package com.zikozee.repositories.reactive;

import com.zikozee.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@DataMongoTest
public class UnitOfMeasureRepositoryTest {

    public static final String SOME_TEST = "some_test";
    @Autowired
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    @Before
    public void setup() throws Exception {
        unitOfMeasureReactiveRepository.deleteAll().block();
    }

    @Test
    public void testUomSave() throws Exception{
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setDescription(SOME_TEST);

        Mono<UnitOfMeasure> unit = unitOfMeasureReactiveRepository.save(uom1);

        assertEquals(SOME_TEST, unit.block().getDescription());
        assertTrue(unitOfMeasureReactiveRepository.findAll().collectList().block().size() > 0);
    }

    @Test
    public void testUomFindByDescription() throws Exception{
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setDescription(SOME_TEST);

        UnitOfMeasure unit = unitOfMeasureReactiveRepository.save(uom1).block();

        UnitOfMeasure fetchedUom = unitOfMeasureReactiveRepository.findByDescription(SOME_TEST).block();

        assertNotNull(unit.getId());
        assertEquals(SOME_TEST, fetchedUom.getDescription());
    }

}
