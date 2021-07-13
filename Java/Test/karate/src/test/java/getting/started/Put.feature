Feature: PUT API Demo

  Scenario: PUT Demo 1
    Given url 'https://reqres.in/api/users/2'
    And request {"name": "Morpheus","job": "Zion President"}
    When method PUT
    Then status 200
    And print response