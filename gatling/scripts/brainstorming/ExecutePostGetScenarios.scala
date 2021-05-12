package brainstorming

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder.toHttpProtocol
import io.gatling.http.request.builder.HttpRequestBuilder.toActionBuilder

/**
 * @author alokmishra
 */
class ExecutePostGetScenarios extends Simulation {
  //Define http protocol
  val httpProtocol = http.disableWarmUp.
    //define base URL and required header
    baseUrl("https://reqres.in/api").
    acceptHeader("application/json")

  //Post scenarios
  val postScenario = scenario("CreateUserScenario")
    .exec(http("CreateUserUsingPost")
      .post("/users")
      .body(RawFileBody("CreateUser.json")).asJson
      .check(bodyString.saveAs("response"))
      .check(status.is(201)))
    .pause(5)
    .exec(session => {
      println(session.scenario)
      println(session("response").as[String])
      session
    })

  //get scenarios
  val getScenario = scenario("GetSingleUserScenario")
    .exec(http("Single User")
      .get("/users/2"))
    .pause(5)


  //Setup
  setUp(
    postScenario.inject(atOnceUsers(2)),
    getScenario.inject(atOnceUsers(3)),
  ).protocols(httpProtocol)

}
