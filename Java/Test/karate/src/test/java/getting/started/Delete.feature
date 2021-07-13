Feature: DELETE API Demo

  Scenario: DELETE Demo 1
    Given url 'https://reqres.in/api/users/2'
    When method DELETE
    Then status 204
    And print response
    And print responseStatus