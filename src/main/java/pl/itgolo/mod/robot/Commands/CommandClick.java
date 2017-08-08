package pl.itgolo.mod.robot.Commands;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pl.itgolo.mod.robot.Exceptions.ChromeBrowserException;

import java.util.List;
import java.util.Objects;

/**
 * Created by ITGolo on 03.03.2017.
 */
public class CommandClick {

    /**
     * Click in element
     * @param locator web element
     * @param javascript click by script of javascript
     * @throws ChromeBrowserException error click
     */
    public void run(By locator, Boolean javascript, WebDriver webDriver) throws ChromeBrowserException {
        Boolean clicked = false;
        WebElement element = null;
        List<WebElement> elements = webDriver.findElements(locator);
        if (elements.size() > 0){
             element = elements.get(0);
        }
        try {
            if (Objects.nonNull(element)){
                if (javascript == false){
                    element.click();
                } else {
                    ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", element);
                }
                clicked = true;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        if (clicked == false){
            throw new ChromeBrowserException(String.format("Can not click in chrome browser."));
        } else
            System.out.println("DEBUG: Clicked in chrome browser.");
    }
}