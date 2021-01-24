package guru.springframework.beer.order.service.services.beer;

import guru.springframework.beer.order.service.web.model.BeerDto;

import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    Optional<BeerDto> getBeerByUpc(String upc);
    Optional<BeerDto> getBeerById(UUID uuid);
}
