package pl.rebigo.libs.crawler.Operations;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import pl.rebigo.libs.crawler.Exceptions.CrawlerException;
import pl.rebigo.libs.crawler.Operations.Enums.OperationSelectDropDown;

import java.util.Objects;

/**
 * IDE Editor: IntelliJ IDEA
 * <p>
 * Date: 27.07.2017
 * Time: 09:27
 * Project name: crawler
 *
 * @author Karol Golec <karol.rebigo@gmail.com>
 */
@Slf4j
public class SelectDropDown {

    /* @var web driver for this step */
    private WebDriver webDriver;

    /**
     * Select option in drop down
     *
     * @param locator                 web element
     * @param value                   value
     * @param operationSelectDropDown method select drop down
     * @param webDriver               web driver
     * @throws CrawlerException failed select drop down
     */
    public void start(By locator, String value, OperationSelectDropDown operationSelectDropDown, WebDriver webDriver) throws CrawlerException {
        this.webDriver = webDriver;
        log.debug(String.format("Begin select drop down. Locator: %1$s, value: %2$s, method: %3$s.", locator.toString(), value, operationSelectDropDown.toString()));
        FindElement findElement = new FindElement();
        WebElement element = findElement.start(locator, 1000, 100, webDriver);
        if (element == null) {
            log.error(String.format("Not found element to select drop down. Locator: %1$s.", locator.toString()));
            throw new CrawlerException(String.format("Not found element to select drop down. Locator: %1$s.", locator.toString()));
        }
        try {
            if (selectOption(element, value, operationSelectDropDown)) {
                log.debug(String.format("End select drop down element. Locator: %1$s.", locator.toString()));
                return;
            }
        } catch (Exception e) {
            log.error(String.format("Failed select drop down. Locator: %1$s.", locator.toString()), e);
            throw new CrawlerException(String.format("Failed select drop down. Locator: %1$s.", locator.toString()), e);
        }
        log.error(String.format("Failed select drop down. Locator: %1$s.", locator.toString()));
        throw new CrawlerException(String.format("Failed select drop down. Locator: %1$s.", locator.toString()));
    }

    /**
     * Select option element
     *
     * @param element                 web element
     * @param value                   value
     * @param operationSelectDropDown method select drop down
     * @return true if selected or false for otherwise
     */
    private Boolean selectOption(WebElement element, String value, OperationSelectDropDown operationSelectDropDown) {
        Boolean selected = false;
        switch (operationSelectDropDown) {
            case SELECT_BY_TEXT:
                selected = selectByOptionDisplayText(element, value);
                break;

            case SELECT_BY_VALUE:
                selected = selectByOptionValue(element, value);
                break;

            default:
                selected = selectByOptionDisplayText(element, value);
        }
        return selected;
    }

    /**
     * Select by option display text
     *
     * @param element element
     * @param value   value
     * @return true if selected or false for otherwise
     */
    private Boolean selectByOptionDisplayText(WebElement element, String value) {
        Boolean selected = false;
        Select dropDown = new Select(element);
        try {
            dropDown.selectByVisibleText(value);
        } catch (Exception e) {
            // service of this exception is not require
        }
        String selectedDisplayText = (String) ((JavascriptExecutor) webDriver).executeScript("return arguments[0].options[arguments[0].selectedIndex].text;", element);
        if (Objects.nonNull(selectedDisplayText))
            selected = selectedDisplayText.equals(value);
        return selected;
    }

    /**
     * Select by option value
     *
     * @param element element
     * @param value   value
     * @return true if selected or false for otherwise
     */
    private Boolean selectByOptionValue(WebElement element, String value) {
        Boolean selected = false;
        Select dropDown = new Select(element);
        try {
            dropDown.deselectByValue(value);
        } catch (Exception e) {
            // service of this exception is not require
        }
        String selectedValue = (String) ((JavascriptExecutor) webDriver).executeScript("return arguments[0].options[arguments[0].selectedIndex].value;", element);
        if (Objects.nonNull(selectedValue))
            selected = selectedValue.equals(value);
        return selected;
    }
}