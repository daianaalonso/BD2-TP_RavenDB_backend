package ar.unrn.tp.service.index;

import net.ravendb.client.documents.indexes.AbstractIndexCreationTask;
import net.ravendb.client.documents.indexes.FieldIndexing;
import net.ravendb.client.documents.indexes.FieldStorage;

public class PostTextIndex extends AbstractIndexCreationTask {
    public PostTextIndex() {
        map = "docs.Posts.Select(post => new { text = post.text })";
        index("text", FieldIndexing.SEARCH);
        store("text", FieldStorage.YES);
    }
}

