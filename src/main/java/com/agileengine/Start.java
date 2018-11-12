package com.agileengine;

import com.agileengine.matcher.ElementMatcher;
import com.agileengine.matcher.JsoupElementMatcher;
import com.agileengine.parser.ElementFinder;
import com.agileengine.parser.JsoupElementFinder;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class Start {

    private static Logger LOGGER = LoggerFactory.getLogger(Start.class);

    public static void main(String[] args) {

        String targetElementId = null;

        if(args.length < 2) {
            LOGGER.error("At least 2 arguments must exist (1 - origin File and 2 - File for finding elements)");
            return;
        }
        if(args.length == 3) {
            targetElementId = args[2];
            LOGGER.info("Searching id element = '{}'", args[2]);
        }

        ElementFinder finder = new JsoupElementFinder();
        ElementMatcher matcher = new JsoupElementMatcher();
        ElementService service = new JsoupElementService(finder,matcher);

        service.findXPathForElement(  args[0], args[1], targetElementId);
    }

}


