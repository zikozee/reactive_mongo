package com.zikozee.services;

import com.zikozee.commands.UnitOfMeasureCommand;
import com.zikozee.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.zikozee.repositories.reactive.UnitOfMeasureReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
/**
 * Created by jt on 6/28/17.
 */
@Service
@RequiredArgsConstructor
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    @Override
    public Flux<UnitOfMeasureCommand> listAllUoms() {

//        return StreamSupport.stream(unitOfMeasureRepository.findAll()
//                .spliterator(), false)
//                .map(unitOfMeasureToUnitOfMeasureCommand::convert)
//                .collect(Collectors.toSet());

        return unitOfMeasureReactiveRepository
                .findAll()
                .map(unitOfMeasureToUnitOfMeasureCommand::convert);

    }
}
