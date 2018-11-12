package com.agileengine;

import com.agileengine.matcher.ElementMatcher;
import com.agileengine.parser.ElementFinder;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class JsoupElementService implements ElementService {

    private static Logger LOGGER = LoggerFactory.getLogger(JsoupElementService.class);

    private static String DEFAULT_TARGET_ID = "make-everything-ok-button";
    private ElementFinder finder;
    private ElementMatcher matcher;
    private String targetElementId;
    private String tag = "a";
    private String classToFind = "btn";

    public JsoupElementService(ElementFinder finder, ElementMatcher matcher ) {
        this.finder = finder;
        this.matcher = matcher;
    }

    public  String findXPathForElement(String originFilePath, String fileToFindPath, String targetElementId) {
        String pathToElement = null;

        File fileOrigin = new File(originFilePath);
        File file = new File(fileToFindPath);
        if(!fileOrigin.exists() || !file.exists()){
            LOGGER.error("File is not exist!");
            return pathToElement;
        }

        this.targetElementId = Objects.isNull(targetElementId) ? DEFAULT_TARGET_ID : targetElementId;

        Optional<Element> elementToFind = getOriginElement(fileOrigin, finder);

        String cssQuery = createCssQuery();

        Optional<List<Element>> elementsByCssSelector = getElementsByCss(file, finder, cssQuery);


        if(elementsByCssSelector.isPresent()){

            Map <Element, Integer> matchMap = matcher.getMatchResultByAttrs(elementToFind.get(), elementsByCssSelector.get());

            if (!matchMap.isEmpty()) {
                Element foundElement = (Element) matcher.getMaximumMatchElement(matchMap);
                LOGGER.info("Found element is {}", foundElement);

                pathToElement = getPathToElement(foundElement);
                LOGGER.info("XPath to Element: {}", pathToElement);

            } else {
                LOGGER.info("Match did not found");
            }

        } else {
            LOGGER.info("Elements not found by cssQuery {}", cssQuery);
        }

        return pathToElement;

    }

    private String getPathToElement(Element foundElement) {
        List<Element> parents = foundElement.parents();
        ListIterator<Element> iterator = parents.listIterator(parents.size());
        StringBuilder sb = new StringBuilder();
        while (iterator.hasPrevious()) {
            Element element = iterator.previous();
            sb.append(element.tagName());
            if (element.elementSiblingIndex() != 0  && !element.tagName().equals("body")) {
                sb.append("[")
                        .append(element.elementSiblingIndex())
                        .append("]");

            }
            sb.append("/");

        }
        sb.append(foundElement.tagName());
        if (foundElement.elementSiblingIndex() != 0) {
            sb.append("[")
                    .append(foundElement.elementSiblingIndex())
                    .append("]");

        }
        return sb.toString();

    }


    private String createCssQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append(tag).append("[class^=").append(classToFind).append("]");
        return sb.toString();
    }


    private Optional<List<Element>> getElementsByCss(File file, ElementFinder finder, String cssQuery) {
        Optional<List<Element>> elementsByCssSelector = Optional.empty();
        try {
            finder.parseDocument(file);
            elementsByCssSelector = finder.getElementsByCssSelector(cssQuery);
        } catch (IOException e) {
            LOGGER.error("Error during parsing file {}", file);
        }
        return elementsByCssSelector;
    }


    private Optional<Element> getOriginElement(File fileOrigin, ElementFinder finder) {
        Optional<Element> elementToFind = Optional.empty();
        try {
            finder.parseDocument(fileOrigin);
            elementToFind = finder.getElementById(targetElementId);
            LOGGER.info("Origin element is {}", elementToFind.orElseGet(null));
        } catch (IOException e) {
            LOGGER.error("Error during parsing file {}", fileOrigin);
        }

        if(elementToFind.isPresent()){
            tag = elementToFind.get().tagName();
            classToFind = elementToFind.get().className().substring(0, 3);
        }
        return elementToFind;
    }

}
