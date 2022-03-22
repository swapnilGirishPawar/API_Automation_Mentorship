*** Settings ***
Library    Collections
Library    RequestsLibrary

*** Variables ***
${base_url}        https://ecommerceservice.herokuapp.com/user
${signup}          

*** Test Cases ***
testcase
    Log    india
    Create Session    mysession    ${base_url}    verify=true
    ${body}=    Create Dictionary    email=swapnil
    ${header}=    Create Dictionary    Content-Type=application/json
    ${response}=    POST On Session    mysession    /signup    data=${body}    headers=${header}        
    
    

*** Keywords ***

