@sanity
Feature: Landing to home page

  Scenario: Login with valid username and password
    Given Open era homepage and accept terms and conditions
    When Enter "tchaure" "Trsndhc@5" and click on submit button
    Then Verify title of ERA homepage

  Scenario Outline: Select multiple point-to-point offers
    Given Search journey from <origin> <departure> for <carrier>

    Examples: 
      | origin    | departure | carrier                 |
      | wien hbf  | munich    | obb                     |
      | berlin    | munich    | dbahn_intercity_express |
      | nantes    | clisson   | ter                     |
      | london    | Paris     | eurostar                |
      | edinburgh | london    | rdg_avanti_west_coast   |
      | amsterdam | rotterdam | sncb                    |
      | barcelona | madrid    | renfe                   |
      | zermatt   | chur      | sbb_regioexpress        |
      | wien hbf  | gyor      | regiojet                |
      | rome      | milan     | italo                   |

  Scenario Outline: Select pass offers
    Given Search <pass>, <passName> pass journey

    Examples: 
      | pass | passName                             |
      | EU   | Eurail Global Mobile Pass Continuous |
      | CH	 | Swiss Half Fare Card									|

  Scenario: Add travelers billing info details and proceed to payment page
    Given Click on proceed to add travelers details button
    When Enter traveler details as "Mr", "Tushar", "Chaure", "1990-09-09", "tchaure@raileurope.com", "+919090909090", "T3473647364", "2030-09-09"
    And Enter card holder and agency details as "Mr", "Tushar", "Chaure", "tchaure@raileurope.com", "+919090909090", "Kamala Executive Park 2nd Floor", "Andheri", "400701", "Mumbai", "Maharashtra"
    Then Collect generated booking reference number
