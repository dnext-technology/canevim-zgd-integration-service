# zgd-integration-service



The integration service is a microservice that provides the integration between the ZGD and the third party platform. It
is responsible for the following tasks:

> API ROOT PATHS : ***/api/zgd-integration/v1***
>
>DEV ENDPOINT   : ***<not_implement>***
>
>PROD ENDPOINT  : ***[Swagger](https://zorgundostu.com/api/zgd-integration/v1/swagger-ui/index.html)***

| Method       | Method | Path        | Description                                                                                                                                                                                                                                                                                                                                                                 |
|--------------|--------|-------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| notification | POST   | /           | This method send sms or email and store it to notifications table, type is enum `SMS` or `EMAIL`, content is enum `WELCOME` or `CONFIRMATION` or `PASSWORD_RESET` or `PASSWORD_CHANGED` or `OTHER`, if you select `OTHER` you can put your own message to parameterList with `KEY`:`content`. Also you can put more than one phone number to `gsmList` to send multiple sms |
| notification | GET    | /:page:size | This method list all the notifications that have been send.                                                                                                                                                                                                                                                                                                                 |

## Sample Request / Response

```
Request: /api/zgd-integration/v1/notifications
Body: 
{
  "type": "SMS",
  "content": "OTHER",
  "gsmList": [
    "05399461708"
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
        "05399461708"
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

## Installation

```bash
mvn spring-boot:run
```

