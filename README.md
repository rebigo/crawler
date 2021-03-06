# Crawler - library Java
Crawler required minimum Chrome Browser in version 60. If computer hasn't installed chrome browser,
library will download portable Chrome Browser and will download Chrome driver. Crawler integrated with
Selenium.

## Use with Gradle
Url repository in `build.gradle`
```
repositories {
    maven {
        url  "http://dl.bintray.com/rebigo/libs"
    }
}
````

Dependency
```
compile group: 'pl.rebigo.libs', name: 'crawler', version: '1.+'
```

## Example
Check exist chrome browser before run crawler. If not exist, library will download portable chrome browser
from url declared in `CrawlerSetting.chromePortableUrlResourceZip;`. If installed chrome browser is
old version, library crawler will download portable chrome browser.

```
ChromeBrowser.validateAllOrDownload();
```

Example code for screenshot. Save screen to `temp` directory
```
WebRobot webRobot = new WebRobot();
webRobot.startNavigateTo("http://google.pl/");
webRobot.screenshot("temp");
webRobot.close();
```

Default user and profile of chrome browser, save to `resources/chrome/users`.

Change profile by setting:
```
CrwlerSetting.pathProfileDefault;
```

Change user name by constructor of web robot:
```
WebRobot webRobot = new WebRobot("MyUser");
```


## Functions

#### Navigate to web site
```
webRobot.startNavigateTo("http://google.pl/");
```

#### Screenshots
```
// scrennshot full site
webRobot.screenshot("temp");

// screnshot element in site
webRobot.screenshot(By.className("class"), "temp");
```

#### Resize browser window
```
webRobot.startResize(1024, 600);
```

#### Click to web element
```
// second argument:
//      false - by webElement.click()
//      true - by java script
webRobot.startClick(By.cssSelector("button"), true);
```

#### Find first web element
```
// second argument: timeout for ajax load element
// third argument: interval check exist element (for ajax element)
webRobot.startFindElement(By.cssSelector(".element"), 2000, 100);
```

#### Find web elements
```
// second argument: timeout for ajax load element
// third argument: interval check exist element (for ajax element)
webRobot.startFindElements(By.cssSelector(".element"), 2000, 100);
```

#### Click to element and wait for reload
```
// second argument:
//      false - by webElement.click()
//      true - by java script
// third argument: timeout for reload web site
// fourth argument: interval check reloaded web site
 webRobot.startClickReload(By.xpath("//div"), false, 40000, 100, webRobot.getWebDriver());
```

#### Fill text to web element
```
// second argument: text to fill
// third argument: Method fill
//      - by java script
//      - by send keys to element
//      - by send keys to textarea (long text)
// fourth argument: attempts fill
// fifth argument: interval attempt fill
webRobot.startFill(By.cssSelector(".gsfi"), "Example text", OperationFill.JAVASCRIPT, 2, 100);
```

#### Select drop down
```
// second argument: value option
// third argument: type value
//      - select by value attribute of optional
//      - select by text of optional
webRobot.startSelectDropDown(By.xpath("//select[@class='js-states form-control']"), "Nevada", OperationSelectDropDown.SELECT_BY_TEXT);
```


## Settings
All settings of crawler exist in `CrawlerSettings` class.

Default browser is hide mode. Set visible mode:
```
CrawlerSettings.chromeHideBrowser = false;
```

Portable mode of chrome browser.
```
CrawlerSettings.chromePortableMode = true;
```

## DEBUG
Library has debug option with integrated SLF4J. Debug mode in console:
```
System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "trace");
```