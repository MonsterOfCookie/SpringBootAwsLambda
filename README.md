# Spring With AWS Lambda Example

Hey you!

You may have noticed the AWS-Labs awesome project, https://github.com/awslabs/aws-serverless-java-container, which
allows for applications inside a Spring/Jersey/Spark container to be easily used on AWS Lambda.

Yes, I know you might be saying - Cold starts, timing etc. This has to be taken on case per case basis
and what works for me, might not work for you. But for now, perhaps you have an existing microservice
that you wish to port over.

This project is to show how to use a bare Spring WebMVC project, in a Lambda - But! - also have Spring Boot
available for local testing. I.e. starting up your server locally for debugging, testing and proving your code.
Pretty handy for integration testing too!

##Usage

`./gradlew build`

Will give you a jar you can deploy to AWS

`./gradlew bootRun`

Will start a local jetty instance and you can go to

`http://127.0.0.1:8080/`

And see a nice "hello world" message.

##Deploy on Lambda

I am going to assume you know a bit about Lambda already, so you can take this project and deploy it as a function, then create
a test event from API Gateway Proxy template and change the path to a "/" and the method to a GET.

For example:

```
{
  "body": "{\"test\":\"body\"}",
  "resource": "/{proxy+}",
  "requestContext": {
    "resourceId": "123456",
    "apiId": "1234567890",
    "resourcePath": "/{proxy+}",
    "httpMethod": "GET",
    "requestId": "c6af9ac6-7b61-11e6-9a41-93e8deadbeef",
    "accountId": "123456789012",
    "identity": {
      "apiKey": null,
      "userArn": null,
      "cognitoAuthenticationType": null,
      "caller": null,
      "userAgent": "Custom User Agent String",
      "user": null,
      "cognitoIdentityPoolId": null,
      "cognitoIdentityId": null,
      "cognitoAuthenticationProvider": null,
      "sourceIp": "127.0.0.1",
      "accountId": null
    },
    "stage": "prod"
  },
  "queryStringParameters": {
    "foo": "bar"
  },
  "headers": {
    "Via": "1.1 08f323deadbeefa7af34d5feb414ce27.cloudfront.net (CloudFront)",
    "Accept-Language": "en-US,en;q=0.8",
    "CloudFront-Is-Desktop-Viewer": "true",
    "CloudFront-Is-SmartTV-Viewer": "false",
    "CloudFront-Is-Mobile-Viewer": "false",
    "X-Forwarded-For": "127.0.0.1, 127.0.0.2",
    "CloudFront-Viewer-Country": "US",
    "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8",
    "Upgrade-Insecure-Requests": "1",
    "X-Forwarded-Port": "443",
    "Host": "1234567890.execute-api.us-east-1.amazonaws.com",
    "X-Forwarded-Proto": "https",
    "X-Amz-Cf-Id": "cDehVQoZnx43VYQb9j2-nvCh-9z396Uhbp027Y2JvkCPNLmGJHqlaA==",
    "CloudFront-Is-Tablet-Viewer": "false",
    "Cache-Control": "max-age=0",
    "User-Agent": "Custom User Agent String",
    "CloudFront-Forwarded-Proto": "https",
    "Accept-Encoding": "gzip, deflate, sdch"
  },
  "pathParameters": {
    "proxy": "path/to/resource"
  },
  "httpMethod": "GET",
  "stageVariables": {
    "baz": "qux"
  },
  "path": "/"
}
```

Then run the test and you should get an output like so:

```
{
  "statusCode": 200,
  "headers": {
    "Content-Encoding": "ISO-8859-1",
    "Content-Length": "12",
    "Content-Type": "text/html;charset=ISO-8859-1"
  },
  "body": "Hello World!",
  "base64Encoded": false
}
```


##Cold Starts

One of the downsides of Lambda is the slowness in JVM start time vs say the Node.js option they provide. This
can be also be extended when it bootstraps an application container, like Spring. However with the right tuning
and settings you can get your cold start time right down to the 2-3seconds range. Depending on your usecase this might
be fine.

In the case of this demo project, I used 1536Mb, and on average the cold start was 2100ms.

##Properties

Unfortunately AWS doesn't like "." in their property names, if you are specifying external properties, so I use underscores. This
can mess with Springs opinionated view on what the properties should look like - so be wary!