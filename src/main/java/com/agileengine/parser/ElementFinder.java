package com.agileengine.parser;



import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ElementFinder<T> {

    Optional<T> getElementById(String elementId);

    Optional<List<T>> getElementsByCssSelector(String cssQuery);

    Document parseDocument(File htmlFile) throws IOException;

}
