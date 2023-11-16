package ar.unrn.tp.config;

import ar.unrn.tp.service.index.PostTextIndex;
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
        /*try {
            DatabaseRecord databaseRecord = new DatabaseRecord();
            databaseRecord.setDatabaseName("blog");
            store.maintenance().server().send(new CreateDatabaseOperation(databaseRecord));
        } catch (ConcurrencyException ce) {
            System.out.println("La bd ya existe.");
        }*/

        store.getConventions().getEntityMapper().registerModule(new JavaTimeModule());
        new PostTextIndex().execute(store);

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
