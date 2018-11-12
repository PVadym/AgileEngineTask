package com.agileengine.matcher;

import java.util.List;
import java.util.Map;

public interface ElementMatcher<T> {

    Map<T, Integer> getMatchResultByAttrs(T originElement, List<T> elementsToMatch);
    T getMaximumMatchElement(Map<T, Integer> matchMap);

}
