# canevim-integration-service

The integration service is a microservice that provides the integration between the ZGD and the third party platform. It
is responsible for the following tasks:

> API ROOT PATHS : ***/api/zgd-integration/v1***
>
>DEV ENDPOINT   : ***<not_implement>***
>
>PROD ENDPOINT  : ***[Swagger](https://zorgundostu.com/api/zgd-integration/v1/swagger-ui/index.html)***

| Method       | Method | Path                          | Description                                                                                                                                                                                                                                                                                                                                                                 |
|--------------|--------|-------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| notification | POST   | /                             | This method send sms or email and store it to notifications table, type is enum `SMS` or `EMAIL`, content is enum `WELCOME` or `CONFIRMATION` or `PASSWORD_RESET` or `PASSWORD_CHANGED` or `OTHER`, if you select `OTHER` you can put your own message to parameterList with `KEY`:`content`. Also you can put more than one phone number to `gsmList` to send multiple sms |
| notification | GET    | /:page:size                   | This method list all the notifications that have been send.     <br/>                                                                                                                                                                                                                                                                                                       |
| identities   | POST   | /identity-number-verification | This method validates tc number and store the results     <br/>                                                                                                                                                                                                                                                                                                             |
| identities   | GET    | /:page:size                   | This method list all the identities that have been send.     <br/>                                                                                                                                                                                                                                                                                                          |

## Sample Request / Response

```
Request: /api/zgd-integration/v1/notifications
Body: 
{
  "type": "SMS",
  "content": "OTHER",
  "gsmList": [
    "05526243464"
  ],
  "parameterList": [
    {
      "key": "code",
      "value": "OHK1675932503774"
    },
     {
      "key": "content",
      "value": "merhaba []l.;'56,7l6456l3456"
    }
  ]
}
```

```
Sucess Response: 
{
    "type": "SMS",
    "content": "OTHER",
    "gsmList": [
        "05526243464"
    ],
    "parameterList": [
        {
            "key": "code",
            "value": "OHK1675932503774"
        },
        {
            "key": "content",
            "value": "merhaba []l.;'56,7l6456l3456"
        },
        {
            "key": "responseId",
            "value": "121631481"
        }
    ]
}
```

```http request
GET http://localhost:8082/api/zgd-integration/v1/identities
Accept: application/json
```

```http request
POST http://localhost:8082/api/zgd-integration/v1/identities/identity-number-verification
Content-Type: application/json

{
  "identityNumber": "xxxxxxxxxxx",
  "name": "name",
  "surname": "surname",
  "birthYear": "year"
}
```

## Installation

```bash
mvn spring-boot:run
```

[tc](https://tckimlik.nvi.gov.tr/Service/KPSPublic.asmx?op=TCKimlikNoDogrula)

[sms](http://g.ajanswebsms.com/sms_soap/sms.asmx)

