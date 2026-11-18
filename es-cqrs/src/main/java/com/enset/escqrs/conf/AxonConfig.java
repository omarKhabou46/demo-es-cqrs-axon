package com.enset.escqrs.conf;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.jpa.JpaEventStorageEngine;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.axonframework.spring.messaging.unitofwork.SpringTransactionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class AxonConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public EntityManagerProvider entityManagerProvider() {
        return () -> entityManager;
    }

    @Bean
    public TransactionManager axonTransactionManager(PlatformTransactionManager platformTransactionManager) {
        return new SpringTransactionManager(platformTransactionManager);
    }

    @Bean
    @Primary
    public Serializer serializer() {
        return JacksonSerializer.defaultSerializer();
    }

    @Bean
    public EventStorageEngine eventStorageEngine(
            EntityManagerProvider entityManagerProvider,
            TransactionManager transactionManager,
            Serializer serializer) {

        return JpaEventStorageEngine.builder()
                .entityManagerProvider(entityManagerProvider)
                .transactionManager(transactionManager)
                .snapshotSerializer(serializer)
                .eventSerializer(serializer)
                .build();
    }
}
