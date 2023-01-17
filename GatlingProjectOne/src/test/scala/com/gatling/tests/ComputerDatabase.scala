package com.gatling.tests

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class ComputerDatabase extends Simulation {
// Protocol Setup
	val httpProtocol = http
		.baseUrl("https://computer-database.gatling.io")
		.inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.woff2""", """.*\.(t|o)tf""", """.*\.png""", """.*detectportal\.firefox\.com.*"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("en-US,en;q=0.9")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36")



// Scenario Definition

	val scn = scenario("ComputerDatabase")
		.exec(http("Add_Computer_Page")
			.get("/computers/new")
			)
		.pause(5)
		.exec(http("Create_New_Computer")
			.post("/computers")

			.formParam("name", "MyComputer1")
			.formParam("introduced", "2023-01-01")
			.formParam("discontinued", "2023-01-30")
			.formParam("company", "1"))
		.pause(5)
		.exec(http("Filter_Computer")
			.get("/computers?f=mycomputer1")
			)

	// Load Simulation
	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}