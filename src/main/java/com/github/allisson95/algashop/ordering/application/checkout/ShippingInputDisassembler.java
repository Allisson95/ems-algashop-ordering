package com.github.allisson95.algashop.ordering.application.checkout;

import com.github.allisson95.algashop.ordering.application.utility.Mapper;
import com.github.allisson95.algashop.ordering.domain.model.commons.Address;
import com.github.allisson95.algashop.ordering.domain.model.order.Recipient;
import com.github.allisson95.algashop.ordering.domain.model.order.Shipping;
import com.github.allisson95.algashop.ordering.domain.model.order.shipping.ShippingCostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class ShippingInputDisassembler {

    private final Mapper mapper;

    public Shipping toDomainModel(final ShippingInput shipping, final ShippingCostService.CalculationResponse shippingCostDetails) {
        return Shipping.builder()
                .cost(shippingCostDetails.cost())
                .expectedDeliveryDate(shippingCostDetails.estimatedDeliveryDate())
                .recipient(mapper.convert(shipping.recipient(), Recipient.class))
                .address(mapper.convert(shipping.address(), Address.class))
                .build();
    }

}
