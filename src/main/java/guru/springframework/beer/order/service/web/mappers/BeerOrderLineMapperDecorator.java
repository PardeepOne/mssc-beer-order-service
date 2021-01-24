package guru.springframework.beer.order.service.web.mappers;

import guru.springframework.beer.order.service.domain.BeerOrderLine;
import guru.springframework.beer.order.service.services.beer.BeerService;
import guru.springframework.beer.order.service.web.model.BeerDto;
import guru.springframework.beer.order.service.web.model.BeerOrderLineDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public abstract class BeerOrderLineMapperDecorator implements BeerOrderLineMapper {

    private BeerService beerService;
    private BeerOrderLineMapper beerOrderLineMapper;

    @Autowired
    public void setBeerService(BeerService beerService) {
        this.beerService = beerService;
    }

    @Autowired
    public void setBeerOrderLineMapper(BeerOrderLineMapper beerOrderLineMapper) {
        this.beerOrderLineMapper = beerOrderLineMapper;
    }

    @Override
    public BeerOrderLineDto beerOrderLineToDto(BeerOrderLine line) {
        BeerOrderLineDto lineDto = beerOrderLineMapper.beerOrderLineToDto(line);
        Optional<BeerDto> optionalBeerDto = beerService.getBeerByUpc(line.getUpc());

        optionalBeerDto.ifPresent(beerDto -> {
            lineDto.setBeerName(beerDto.getBeerName());
            lineDto.setBeerStyle(beerDto.getBeerStyle());
            lineDto.setPrice(beerDto.getPrice());
            lineDto.setBeerId(beerDto.getId());
        });

        return lineDto;
    }

    @Override
    public BeerOrderLine dtoToBeerOrderLine(BeerOrderLineDto dto) {
        return beerOrderLineMapper.dtoToBeerOrderLine(dto);
    }
}
