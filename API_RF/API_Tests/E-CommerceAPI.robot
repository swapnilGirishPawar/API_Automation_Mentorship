*** Settings ***
Library    Collections
Library    RequestsLibrary
Library    OperatingSystem
Library    JSONLibrary
Library    String


*** Variables ***
${base_url}        https://ecommerceservice.herokuapp.com
${Bearer_Token}    Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2MjM5ODQ0OGUzYTYzNzAwMTczYmE3NWUiLCJpYXQiOjE2NDgxMDA5OTcsImV4cCI6MTY0ODE4NzM5N30.Yt7KZQLp_5LGBCmyfCPefOs9Dn65f13N9WfIaWCyiRI
*** Test Cases ***
Login into system
    Create Session    LoginSession    ${base_url}    verify=true
    ${body}=    Create Dictionary    email=swapnil587@gmail.com    password=swapnil@12321
    ${header}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Directory    Authorization=${Bearer_Token}
    ${response}=    Post Request    LoginSession    /user/login    data=${body}    headers=${header}    params=${params}       
    Log To Console    ${response.status_code}
    ${jsonRes}=    Set Variable    ${response.json()}
    Log To Console    ${jsonRes} 
    ${act}=    Get Value From Json    ${jsonRes}    $.accessToken
    Set Suite Variable    ${accesstoken}    ${act} 

Get All Userlist
    Create Session    AllUserSession    ${base_url}    verify=true
    ${header}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Directory    Authorization=${Bearer_Token}
    ${response}=    Post Request    AllUserSession    /user    headers=${header}    params=${params}       
    Log To Console    ${response.status_code}
    Log To Console    ${response.content}



*** Keywords ***
New_User_SignUp
    Log To Console    india
    Create Session    SignupSession    ${base_url}    verify=true
    ${body}=    Create Dictionary    email=swapnil587@gmail.com    password=swapnil@12321
    ${header}=    Create Dictionary    Content-Type=application/json
    ${response}=    Post Request    SignupSession    /user/signup    data=${body}    headers=${header}    
    Log To Console    ${response.status_code}
    Log To Console    ${response.content}
    
    #Assertions
    ${statusCode}=    Convert To String    ${response.status_code}
    Should Be Equal    ${statusCode}    201
    ${res_body}=    Convert To String    ${response.content}
    Should Contain    ${res_body}    User signup successfully
