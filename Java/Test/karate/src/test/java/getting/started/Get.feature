Feature: GET API Demo

  Background:
    * url 'https://reqres.in/api'
    * header Accept = 'application/json'

  Scenario: GET Demo 1
    Given url 'https://reqres.in/api/users?page=2'
    When method GET
    Then status 200
    # Some prints
    And print response
    And print responseStatus
    And print responseTime
    And print responseHeaders
    And print responseCookies

  Scenario: GET Demo 2
    # Using <Background> part to apply settings in all scenarios
    Given path '/users?page=2'
    When method GET
    Then status 200
    And print response

  Scenario: GET Demo 3
    Given path '/users'
    # Now with params separately
    And param page = 2
    When method GET
    Then status 200
    And print response

  Scenario: GET Demo 4
    Given path '/users'
    And param page = 2
    When method GET
    Then status 200
    And print response
    # Now verify some asserts and match, you can use https://jsonpathfinder.com/
    # to validate and make path finder.
    And match response.data[0].first_name != null
    And assert response.data.length == 6
    And match response.data[3].id == 10