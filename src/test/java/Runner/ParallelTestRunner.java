package Runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        tags = "@Regression",
        features = "src/test/resources/featureFiles",
        glue = "StepDefinitions",
        plugin = {
                "html:target/cucumber-report",
                "json:target/cucumber.json",
                "pretty:target/cucumber-pretty.txt",
                "usage:target/cucumber-usage.json",
                "junit:target/cucumber-results.xml"
        }
)
public class ParallelTestRunner extends AbstractTestNGCucumberTests {
} 