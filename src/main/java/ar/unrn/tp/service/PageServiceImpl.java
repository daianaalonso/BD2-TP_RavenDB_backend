package ar.unrn.tp.service;

import ar.unrn.tp.api.PageService;
import ar.unrn.tp.domain.Page;
import lombok.AllArgsConstructor;
import net.ravendb.client.documents.IDocumentStore;
import net.ravendb.client.documents.session.IDocumentSession;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@AllArgsConstructor
@Service
public class PageServiceImpl implements PageService {
    private IDocumentStore store;

    @Override
    public Page insertPage(Page page) {
        return inTx(session ->
        {
            session.store(page);
            session.saveChanges();
            return page;
        });
    }

    @Override
    public List<Page> findPage(String id) {
        return inTx(session ->
                Arrays.asList(session.load(Page.class, id))
        );
    }

    private <T> T inTx(Function<IDocumentSession, T> toExecute) {
        try (IDocumentSession session = store.openSession()) {
            return toExecute.apply(session);
        } catch (Exception e) {
            throw e;
        }
    }
}
