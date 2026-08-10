package br.dev.allissonnunes.algashop.ordering.infrastructure.config.persistence;

import br.dev.allissonnunes.algashop.ordering.core.application.security.SecurityCheckApplicationService;
import org.jspecify.annotations.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware", dateTimeProviderRef = "dateTimeProvider")
@EnableJpaRepositories(
        basePackages = { "br.dev.allissonnunes.algashop.ordering.infrastructure.adapters.out.persistence" }
)
public class SpringDataJpaConfiguration {

    @Bean
    AuditorAware<@NonNull UUID> auditorAware(final SecurityCheckApplicationService securityCheck) {
        return () -> {
            if (!securityCheck.isAuthenticated() || securityCheck.isMachineAuthentication()) {
                return Optional.empty();
            }
            return Optional.of(securityCheck.getAuthenticatedUserId());
        };
    }

    @Bean
    DateTimeProvider dateTimeProvider() {
        return () -> Optional.of(Instant.now());
    }

}
