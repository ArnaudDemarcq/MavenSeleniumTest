/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.krohm.test.selenium;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Arnaud
 */
public class GoogleTest {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(GoogleTest.class);
    private static final String TMP_TEST_URL = "http://www.google.com";

    @Test
    public void helloWorldTest() {
        LOGGER.debug("Hello World.");
    }

    @Test
    public void openBrowser() {
        LOGGER.info("Getting base URL ...");
        String baseUrl = TMP_TEST_URL; //System.getProperty("test.google.url");
        LOGGER.info("base URL is: <" + TMP_TEST_URL + ">");
        WebDriver driver = new HtmlUnitDriver();
        driver.get(baseUrl);
        LOGGER.info("Page loaded, title is: " + driver.getTitle());
    }
}
