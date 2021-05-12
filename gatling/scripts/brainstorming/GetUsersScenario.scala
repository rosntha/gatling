package brainstorming

//Import basic package
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder.toHttpProtocol
import io.gatling.http.request.builder.HttpRequestBuilder.toActionBuilder

//Extend class with Simulation
class GetUsersScenario extends Simulation {

  //Define http protocol
  val httpProtocol = http.disableWarmUp.
    //define base URL and required header
    baseUrl("https://reqres.in/api").
    acceptHeader("application/json")

  //get scenarios
  val scn = scenario("GetSingleUserScenario")
    .exec(http("Single User")
      .get("/users/2"))
    .pause(5)

  //Setup
  setUp(
    scn.inject(atOnceUsers(3))
  ).protocols(httpProtocol)
}
