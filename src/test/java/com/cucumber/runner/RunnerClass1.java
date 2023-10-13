package com.cucumber.runner
;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = { "src/test/resources/features" }, glue = { "com.cucumber.steps",
"com.cucumber.hooks" }, plugin = { "pretty", "html:target/cucumber/report.html",
		"json:target/cucumber/report.json" }, monochrome = true, dryRun = !true, tags = "@all_ds")
public class RunnerClass1 extends AbstractTestNGCucumberTests {

}
