package com.agileengine.parser;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class JsoupElementFinder implements ElementFinder<Element> {

    private static String CHARSET_NAME = "utf8";
    private  Document document;

    @Override
    public Document parseDocument(File htmlFile) throws IOException {
        Document doc = Jsoup.parse(
                htmlFile,
                CHARSET_NAME,
                htmlFile.getAbsolutePath());
        this.document = doc;

        return doc;
    }

    @Override
    public Optional<Element> getElementById(String elementId) {
        return Optional.of(document.getElementById(elementId));

    }

    @Override
    public Optional<List<Element>> getElementsByCssSelector(String cssQuery) {
        return Optional.of(document.select(cssQuery));
    }

}
