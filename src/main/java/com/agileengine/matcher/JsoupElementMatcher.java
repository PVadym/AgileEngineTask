package com.agileengine.matcher;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JsoupElementMatcher implements ElementMatcher<Element> {

    @Override
    public Map<Element, Integer> getMatchResultByAttrs(Element originElement, List<Element> elementsToMatch) {

        List<Attribute> attributes = originElement.attributes().asList();
        Map<Element, Integer> result= new HashMap<>();

        for(Element element: elementsToMatch) {
            List<Attribute> list = element.attributes().asList();
            List<Attribute> common = attributes.stream().filter(list::contains).collect(Collectors.toList());
            result.put(element, common.size());
        }
        return result;
    }

    @Override
    public Element getMaximumMatchElement(Map<Element, Integer> matchMap) throws MaxMatchElementNotFound {
        Element maxMatchElement = matchMap.entrySet()
                .stream()
                .reduce((curr, nxt) -> curr.getValue() > nxt.getValue() ? curr : nxt)
                .orElseThrow(() -> new MaxMatchElementNotFound("Few elements have been found!")).getKey();

        return maxMatchElement;
    }
}
