package com.ilyasov.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ilyasov.damir
 * @version 1.0
 */

@Controller
public class MainController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final String URL_REGEX = "(?=(\\[url=(.*?)](.*?)\\[/url]))";
    private final String B_REGEX = "(?=(\\[b](.*?)\\[/b]))";
    private final String I_REGEX = "(?=(\\[i](.*?)\\[/i]))";

    /**
     * <p>Get index page</p>
     *
     * @return index page
     */
    @RequestMapping(value = "/")
    public String getStartPage(Model model) throws IOException {
        return "index";
    }


    /**
     * <p>Build html code from inputted String</p>
     *
     * @param text - inputted String
     * @return html code by inputted text
     */
    @ResponseBody
    @RequestMapping(value = "/build_html")
    public String buildHtmlCode(@RequestParam("text") String text) {
        LOGGER.info("New request, processing...");
        StringBuilder stringBuilder = new StringBuilder(text);
        LOGGER.info("Processing [b].*[/b]");
        Pattern pattern = Pattern.compile(B_REGEX);
        Matcher matcher = pattern.matcher(stringBuilder);
        while (matcher.find()) {
            stringBuilder.replace(matcher.start(),
                    matcher.start() + matcher.group(1).length(),
                    "<p style=\"font-weight:bold; display: inline-block;\">" + matcher.group(2) + "</p>");
            matcher = pattern.matcher(stringBuilder);
        }
        LOGGER.info("Processing [i].*[/i]");
        pattern = Pattern.compile(I_REGEX);
        matcher = pattern.matcher(stringBuilder);
        while (matcher.find()) {
            stringBuilder.replace(matcher.start(),
                    matcher.start() + matcher.group(1).length(),
                    "<p style=\"font-style:italic; display: inline-block;\">" + matcher.group(2) + "</p>");
            matcher = pattern.matcher(stringBuilder);
        }
        LOGGER.info("Processing [url=.*].*[/url]");
        pattern = Pattern.compile(URL_REGEX);
        matcher = pattern.matcher(stringBuilder);
        while (matcher.find()) {
            stringBuilder.replace(matcher.start(),
                    matcher.start() + matcher.group(1).length(),
                    "<a href = \"" + matcher.group(2) + "\"> " + matcher.group(3) + "</a>");
            matcher = pattern.matcher(stringBuilder);
        }

        LOGGER.info("Processing completed, returning html code...");
        return String.valueOf(stringBuilder);
    }
}
