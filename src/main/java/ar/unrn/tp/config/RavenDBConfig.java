package ar.unrn.tp.config;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import net.ravendb.client.documents.DocumentStore;
import net.ravendb.client.documents.IDocumentStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RavenDBConfig {
    private IDocumentStore store;

    @PostConstruct
    public void initialize() {
        store = new DocumentStore("http://127.0.0.1:8081", "blog");
        store.initialize();
        store.getConventions().getEntityMapper().registerModule(new JavaTimeModule());
    }

    @PreDestroy
    public void close() {
        if (store != null) {
            store.close();
        }
    }

    @Bean
    public IDocumentStore documentStore() {
        return store;
    }
}
