package ar.unrn.tp.service;

import ar.unrn.tp.api.PostService;
import ar.unrn.tp.controller.request.AuthorCount;
import ar.unrn.tp.domain.Post;
import lombok.AllArgsConstructor;
import net.ravendb.client.documents.IDocumentStore;
import net.ravendb.client.documents.session.IDocumentSession;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@AllArgsConstructor
@Service
public class PostServiceImpl implements PostService {
    private IDocumentStore store;

    @Override
    public Post insertPost(Post post) {
        return inTx(session ->
        {
            session.store(post);
            session.saveChanges();
            return post;
        });
    }

    @Override
    public List<Post> findPost(String id) {
        return inTx(session ->
                Arrays.asList(session.load(Post.class, id))
        );
    }

    @Override
    public List<Post> findLatestPosts() {
        return inTx(session -> session.query(Post.class)
                .orderByDescending("date")
                .take(4)
                .selectFields(Post.class, "id", "title", "resume")
                .toList()
        );
    }

    @Override
    public List<Post> findPostsByAuthor(String author) {
        return inTx(session -> session.query(Post.class)
                .whereEquals("author", author)
                .toList()
        );
    }

    @Override
    public List<Post> findPostsByText(String text) {
        return inTx(session -> session.query(Post.class)
                .search("text", text)
                .selectFields(Post.class, "id", "title", "resume", "author")
                .toList()
        );
    }

    @Override
    public List<AuthorCount> countPostsByAuthor() {
        return inTx(session -> session.query(Post.class)
                .groupBy("author")
                .selectKey("author", "id")
                .selectCount()
                .ofType(AuthorCount.class)
                .toList()
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
