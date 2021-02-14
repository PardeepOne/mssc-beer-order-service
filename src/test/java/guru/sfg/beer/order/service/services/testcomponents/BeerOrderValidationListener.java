package guru.sfg.beer.order.service.services.testcomponents;

import guru.sfg.beer.brewery.model.events.ValidateOrderRequest;
import guru.sfg.beer.brewery.model.events.ValidateOrderResult;
import guru.sfg.beer.order.service.config.JmsConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class BeerOrderValidationListener {
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_REQUEST_QUEUE)
    public void list(Message message) {

        ValidateOrderRequest validateOrderRequest = (ValidateOrderRequest) message.getPayload();

        jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_RESULT_QUEUE,
                ValidateOrderResult.builder()
                        .isValid(true).orderId(validateOrderRequest.getBeerOrderDto().getId()).build()
        );
    }
}
