package pl.rebigo.libs.crawler.Operations;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pl.itgolo.plugin.helper.Thread.SleepHelper;
import pl.rebigo.libs.crawler.Exceptions.CrawlerException;
import pl.rebigo.libs.crawler.Operations.Enums.OperationFill;

import java.util.Arrays;
import java.util.List;
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
public class Fill {

    /* @var web driver */
    private WebDriver webDriver;

    /**
     * Fill element
     *
     * @param locator         web element
     * @param text            text
     * @param operationFill   method fill
     * @param attempts        attempts fill element
     * @param intervalAttempt interval attempt
     * @param webDriver       web driver
     * @throws CrawlerException failed fill
     */
    public void start(By locator, String text, OperationFill operationFill, Integer attempts, Integer intervalAttempt, WebDriver webDriver) throws CrawlerException {
        this.webDriver = webDriver;
        log.debug(String.format("Begin fill element. Locator: %1$s, method fill: %2$s, attempts: %3$s.", locator.toString(), operationFill.toString(), attempts));
        FindElement findElement = new FindElement();
        WebElement element = findElement.start(locator, 1000, 100, webDriver);
        if (element == null) {
            log.error(String.format("Not found element to fill. Locator: %1$s.", locator.toString()));
            throw new CrawlerException(String.format("Not found element to fill. Locator: %1$s.", locator.toString()));
        }
        if (text.equals("")) {
            log.debug(String.format("End fill element. Locator: %1$s.", locator.toString()));
            return;
        }
        try {
            for (Integer count = 0; count < attempts; count++) {
                if (fillElement(element, text, operationFill)) {
                    log.debug(String.format("End fill element. Locator: %1$s.", locator.toString()));
                    return;
                }
                SleepHelper.sleep(intervalAttempt);
            }
        } catch (Exception e) {
            log.error(String.format("Failed fill element. Locator: %1$s, method fill: %2$s, attempts: %3$s.", locator.toString(), operationFill.toString(), attempts), e);
            throw new CrawlerException(String.format("Failed fill element. Locator: %1$s, method fill: %2$s, attempts: %3$s.", locator.toString(), operationFill.toString(), attempts));
        }
        log.error(String.format("Failed fill element. Locator: %1$s, method fill: %2$s, attempts: %3$s.", locator.toString(), operationFill.toString(), attempts));
        throw new CrawlerException(String.format("Failed fill element. Locator: %1$s, method fill: %2$s, attempts: %3$s.", locator.toString(), operationFill.toString(), attempts));
    }

    /**
     * Fill element
     *
     * @param element       web element
     * @param text          text
     * @param operationFill method fill
     * @return true if filled or false for otherwise
     */
    private Boolean fillElement(WebElement element, String text, OperationFill operationFill) {
        Boolean filled = false;
        switch (operationFill) {
            case SEND_KEYS_INPUT:
                filled = inputFill(text, element);
                break;

            case SEND_KEYS_TEXTAREA:
                filled = textareaFill(text, element);
                break;

            case JAVASCRIPT:
                filled = fillJavascript(text, element);
                break;

            default:
                filled = fillJavascript(text, element);
        }
        return filled;
    }

    /**
     * Fill input
     *
     * @param text    text
     * @param element web element
     * @return true if filled or false for otherwise
     */
    private Boolean inputFill(String text, WebElement element) {
        String lastChar = text.substring(text.length() - 1);
        List<String> words = Arrays.asList(text.split("\\s"));
        for (Integer i = 0; i < words.size(); i++) {
            if (i + 1 == words.size() && lastChar.equals(" ")) {
                element.sendKeys(words.get(i) + " ");
            } else if (i + 1 == words.size() && !lastChar.equals(" ")) {
                element.sendKeys(words.get(i));
            } else {
                element.sendKeys(words.get(i) + " ");
            }
        }
        String valueFilled = element.getAttribute("value");
        if (Objects.nonNull(valueFilled)) {
            return text.equals(valueFilled);
        }
        return false;
    }

    /**
     * Fill by java script
     *
     * @param text    text
     * @param element web element
     * @return true if filled or false for otherwise
     */
    private Boolean fillJavascript(String text, WebElement element) {
        Boolean filled = false;
        ((JavascriptExecutor) webDriver).executeScript(String.format("arguments[0].value=\"%1$s\";", text), element);
        String valueFilled = (String) ((JavascriptExecutor) webDriver).executeScript("return arguments[0].value;", element);
        if (Objects.nonNull(valueFilled))
            filled = valueFilled.equals(text);
        return filled;
    }

    /**
     * Fill textarea
     *
     * @param text    text to fill element
     * @param element web element for fill text
     * @return true if filled or false if not filled
     */
    private Boolean textareaFill(String text, WebElement element) {
        String lastChar = text.substring(text.length() - 1);
        List<String> words = Arrays.asList(text.split("\\."));
        for (Integer i = 0; i < words.size(); i++) {
            if (i + 1 == words.size() && lastChar.equals(".")) {
                element.sendKeys(words.get(i) + ".");
            } else if (i + 1 == words.size() && !lastChar.equals(".")) {
                element.sendKeys(words.get(i));
            } else {
                element.sendKeys(words.get(i) + ".");
            }
        }
        String valueFilled = element.getAttribute("value");
        if (Objects.nonNull(valueFilled)) {
            return text.equals(valueFilled);
        }
        return false;
    }
}