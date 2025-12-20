package com.github.allisson95.algashop.ordering.domain.model.order.shipping;

import com.github.allisson95.algashop.ordering.domain.model.commons.Money;
import com.github.allisson95.algashop.ordering.domain.model.commons.ZipCode;
import com.github.allisson95.algashop.ordering.domain.model.order.shipping.ShippingCostService.CalculationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;

import java.time.LocalDate;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnableWireMock(
        @ConfigureWireMock(
                baseUrlProperties = "spring.http.serviceclient.rapidex.base-url"
        )
)
class ShippingCostServiceIT {

    @Autowired
    private ShippingCostService shippingCostService;

    @Autowired
    private OriginAddressService originAddressService;

    @Test
    void shouldCalculate() {
        final Money expectedCost = new Money("35.00");
        final LocalDate expectedDeliveryDate = LocalDate.now().plusDays(7);
        final ZipCode origin = originAddressService.originAddress().zipCode();
        final ZipCode destination = new ZipCode("12345");

        stubFor(post(urlPathEqualTo("/api/delivery-cost"))
                .withRequestBody(matchingJsonPath("$.originZipCode"))
                .withRequestBody(matchingJsonPath("$.destinationZipCode"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {
                                  "deliveryCost": "35.00",
                                  "estimatedDaysToDeliver": "7"
                                }
                                """)));

        final var calculate = shippingCostService
                .calculate(new CalculationRequest(origin, destination));

        assertThat(calculate.cost()).isEqualTo(expectedCost);
        assertThat(calculate.estimatedDeliveryDate()).isEqualTo(expectedDeliveryDate);
    }

}