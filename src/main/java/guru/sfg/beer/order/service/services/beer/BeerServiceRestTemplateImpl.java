package guru.sfg.beer.order.service.services.beer;

import guru.sfg.beer.order.service.web.model.BeerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@ConfigurationProperties(prefix = "sfg.brewery",ignoreUnknownFields = false)
@Service
public class BeerServiceRestTemplateImpl implements BeerService {

    private final String BEER_PATH_V1 = "/api/v1/beer/";
    private final String BEER_UPC_PATH_V1 = "/api/v1/beerUpc/";
    private final RestTemplate restTemplate;
    private String beerServiceHost;

    public BeerServiceRestTemplateImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public void setBeerServiceHost(String beerServiceHost) {
        this.beerServiceHost = beerServiceHost;
    }

    @Override
    public Optional<BeerDto> getBeerByUpc(String upc) {
        log.debug("Calling guru.sfg.beer.order.service.services.beer.BeerServiceRestTemplateImpl.getBeerByUpc * BEGIN");

        Optional<BeerDto> beerDto = Optional.ofNullable(
                restTemplate.getForObject(beerServiceHost+BEER_UPC_PATH_V1+upc,BeerDto.class)
        );
        log.debug("Calling guru.sfg.beer.order.service.services.beer.BeerServiceRestTemplateImpl.getBeerByUpc * END");
        return beerDto;
    }

    @Override
    public Optional<BeerDto> getBeerById(UUID beerId) {
        log.debug("Calling guru.sfg.beer.order.service.services.beer.BeerServiceRestTemplateImpl.getBeerById * BEGIN");

        Optional<BeerDto> beerDto = Optional.ofNullable(
            restTemplate.getForObject(beerServiceHost+BEER_PATH_V1+beerId.toString(),BeerDto.class)
        );
        log.debug("Calling guru.sfg.beer.order.service.services.beer.BeerServiceRestTemplateImpl.getBeerById * END");
        return beerDto;
    }
}
