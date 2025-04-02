package Utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import Pages.Parent;

/**
 * BaseDriver class manages WebDriver instances using ThreadLocal for parallel test execution.
 * This ensures thread safety when running tests in parallel.
 */
public class BaseDriver {
    
    // ThreadLocal WebDriver instance for parallel execution support
    private static ThreadLocal<WebDriver> threadDriver = new ThreadLocal<>();
    
    // ThreadLocal browser name for multi-browser support
    public static ThreadLocal<String> threadBrowserName = new ThreadLocal<>();

    /**
     * Gets the WebDriver instance for the current thread.
     * If no driver exists, creates a new one based on the specified browser.
     * Default browser is Chrome if not specified.
     *
     * @return WebDriver instance for the current thread
     */
    public static WebDriver getDriver() {
        // Set default browser to Chrome if not specified
        if (threadBrowserName.get() == null) {
            threadBrowserName.set("chrome");
        }

        // Create new driver instance if none exists for the current thread
        if (threadDriver.get() == null) {
            switch (threadBrowserName.get().toLowerCase()) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    threadDriver.set(new ChromeDriver());
                    break;

                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    threadDriver.set(new FirefoxDriver());
                    break;

                case "edge":
                    WebDriverManager.edgedriver().setup();
                    threadDriver.set(new EdgeDriver());
                    break;

                case "safari":
                    // Safari doesn't require WebDriverManager setup
                    threadDriver.set(new SafariDriver());
                    break;

                default:
                    throw new IllegalArgumentException("Unsupported browser: " + threadBrowserName.get());
            }
        }

        return threadDriver.get();
    }

    /**
     * Closes the WebDriver instance and cleans up resources.
     * Waits for 5 seconds before closing the driver.
     */
    public static void quitDriver() {
        Parent.delay(5);

        if (threadDriver.get() != null) {
            threadDriver.get().quit();
            WebDriver driver = threadDriver.get();
            driver = null;
            threadDriver.set(driver);
        }
    }

    /**
     * Sets the browser name for the current thread
     * @param browserName Name of the browser (chrome, firefox, edge, safari)
     */
    public static void setBrowser(String browserName) {
        threadBrowserName.set(browserName.toLowerCase());
    }
} 