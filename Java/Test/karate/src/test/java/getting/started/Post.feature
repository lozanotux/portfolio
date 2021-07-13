Feature: POST API Demo

  Background:
    * url 'https://reqres.in/api'
    * header Accept = 'application/json'
    * def expectedOutput = read("response1.json")

  Scenario: POST Demo 1
    Given url 'https://reqres.in/api/users'
    And request {"name": "Juan", "job": "consultant"}
    When method POST
    Then status 201
    And print response

  Scenario: POST Demo 2
    Given path '/users'
    And request {"name": "Juan", "job": "consultant"}
    When method POST
    Then status 201
    And print response

  Scenario: POST Demo 3
    Given path '/users'
    And request {"name": "Juan", "job": "consultant"}
    When method POST
    Then status 201
    # Some fields are auto generated. Others like timestamps are impossible to
    # anticipate or calculate it to check or assert. So you can use some
    # generic <types> to validate or <ignore> them.
    And match response == {"createdAt": "#ignore","name": "Juan","id": "#string","job": "consultant"}
    And print response

  Scenario: POST Demo 4
    Given path '/users'
    And request {"name": "Juan", "job": "consultant"}
    When method POST
    Then status 201
    # Here we can use <response> variable or $ symbol interchangeably
    And match response == expectedOutput
    And print response

  Scenario: POST Demo 5
    Given path '/users'
    # Here we can use <karate.properties['user.dir']> to get the absolute path
    # of your project and concatenate it with the name of relative path to file
    And def requestBody = read("request1.json")
    And request requestBody
    When method POST
    Then status 201
    And match $ == expectedOutput
    And print response

  Scenario: POST Demo 6
    Given path '/users'
    And def reqBody = read("request1.json")
    # You can modify you request body after use it
    And set reqBody.job = "developer"
    And request reqBody
    When method POST
    Then status 201
    And match response == expectedOutput
    And print response