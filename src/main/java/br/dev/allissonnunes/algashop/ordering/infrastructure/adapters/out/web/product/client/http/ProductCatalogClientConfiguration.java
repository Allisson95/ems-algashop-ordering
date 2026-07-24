package br.dev.allissonnunes.algashop.ordering.infrastructure.adapters.out.web.product.client.http;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.client.OAuth2ClientHttpRequestInterceptor;
import org.springframework.web.client.support.RestClientHttpServiceGroupConfigurer;
import org.springframework.web.service.registry.HttpServiceGroup;
import org.springframework.web.service.registry.ImportHttpServices;

@ImportHttpServices(group = "product-catalog", types = ProductCatalogClient.class, clientType = HttpServiceGroup.ClientType.REST_CLIENT)
@Configuration
class ProductCatalogClientConfiguration {

    @Bean
    RestClientHttpServiceGroupConfigurer restClientHttpServiceGroupConfigurer(
            final OAuth2AuthorizedClientManager authorizedClientManager
    ) {
        final OAuth2ClientHttpRequestInterceptor interceptor = new OAuth2ClientHttpRequestInterceptor(authorizedClientManager);
        interceptor.setClientRegistrationIdResolver(_ -> "algashop-ordering-service-client");
        interceptor.setPrincipalResolver(_ -> new UsernamePasswordAuthenticationToken("algashop-ordering-service-client", null));

        return groups -> {
            groups.filterByName("product-catalog")
                    .forEachClient((_, client) ->
                            client.requestInterceptor(interceptor));
        };
    }

}
