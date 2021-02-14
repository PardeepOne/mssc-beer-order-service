package guru.sfg.beer.order.service.services.testcomponents;

import guru.sfg.beer.brewery.model.events.AllocateOrderRequest;
import guru.sfg.beer.brewery.model.events.AllocateOrderResult;
import guru.sfg.beer.order.service.config.JmsConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class BeerOrderAllocaitonListener {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.ALLOCATE_ORDER_REQUEST_QUEUE)
    public void listen(Message message){
        AllocateOrderRequest allocateOrderRequest = (AllocateOrderRequest) message.getPayload();

        allocateOrderRequest.getBeerOrderDto().getBeerOrderLines().forEach(beerOrderLineDto -> {
            beerOrderLineDto.setQuantityAllocated(beerOrderLineDto.getOrderQuantity());
        });

        jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_ORDER_RESULT_QUEUE,
                AllocateOrderResult.builder()
        .beerOrderDto(allocateOrderRequest.getBeerOrderDto())
        .pendingInventory(false)
        .allocationError(false).build());
    }
}
