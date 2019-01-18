# Http server push with long-polling technique implementation

The project consists of the server and client side code. It is an implementation of server push that can be added to your own project.

## Features
This small project allows you to query the sever for the real-time data, saving connection's bandwidth.
You can use it for having any type of notification or simply to allow Server data at any time he needs to and not only when the Client is precisely asking for it.

---
## Idea  behind the project ~(what is longpolling?)

##### Short-polling (standard http behaviour)
To be up to date with data on the server client can send a request asking for a data, the server side will respond with the data if it has it or with a response containing information that the data is yet not available.
So client sends another request hoping to get the data this time instead of an empty response. And again, and again forever.
This idea is a bit harsh when it comes to the amount of traffic the server will receive.

##### Long-polling
With the long-polling (THIS PROJECT) the client is sending a request for data to the server and then, if the data is not available in that moment, the request is waiting on the server until the data happens to be acquirable or the time out occurs(the usual browser's timeout is no less than 30sec, so we will keep to that).
The server responses, closing the http session with client.
As soon as the client gets a response, it sends another request for the new data. That way, the server always has a request hanging he can put data in and return it to the client any time some data gets obtainable.

---
## Usage

To make a use of this project you will have to install an npm package for the client side and add a jar to the server so that everything would work fine.

* in your client implementation directory run `npm install long-poll-template` 

* in server side just include a jar `com.millosz.longpoll` from this repository
---
### Client
Inject the class (import it first), and set the requested url
```typescript
constructor(@Inject(LongPoll) private longPoll: LongPoll) {
    longPoll.setUri("http://localhost:8080"); 
    longPoll.setCall("/something/subscribe");
  };
``` 
Enable long-polling to continue sending requests
```typescript
    this.longPoll.changeSubscriptionStatus(true); 
```
Trigger the long-polling request function
```typescript
    this.longPoll.makeLongRequest();
```
It is necessary to wait for the result this way, because the *dataEmitter* of type EventEmitter in LongPoll will emit data as soon as it will receive from the server.
```typescript
    this.longPoll.dataEmitter.subscribe( data => this.received = data)
```
---

#### Visible functions
  * changeSubscriptionStatus(val: boolean) : void { }
    - *true* - starts the until-stopped lasting server polling
    - *false* - pass *false* at any time to stop requesting for new data

  * makeLongRequest(): void { }
    - starts hanging on the server for new data
* setCall(call: string): void {}

* setUri(uri: string): void {}

### Server
For everything to work fine server side, we need to do a few things, that is:

* create a rest controller to handle our long-polling request
* make our *Service*(a class which takes care of fetching data)
    - **extend** *ServicePoll* 
    - define when the new data is available for the server to send it back to *client*
* remember that **every** class we want to use in a long-polling request needs to **extend** the *Resolvable*

Considering extending a class is not a problem that needs any coverage, let's take a look at the *rest controller*.

It is mandatory to import *RequestPromise* , *Overseer*  and *Service* classes.
We should now inject our *Service* and ...the *Overseer* class, which will be responsible for storing long-polling requests, and trying to resolve them every 600 miliseconds, that is it will check whether new data has become available, and handle any kind of related situations properly.

You will need to create a special mapping which will handle our clients request.
We had "/something/subscribe" used in our client, and so we will keep it in the following example.
We need the HttpSession as the mapping argument because we will be destroying the session as soon as the response is being produced.
```java
@GetMapping("/something/subscribe")
    public RequestPromise handle(HttpSession session){
    
```
Now we create our response which is of type RequestPromise, and that extends the DefferedResult<Resolvable>.
    We set the request's session, put the request into the Overseer(an observer), and return it.
```java
        RequestPromise output = new RequestPromise(implementedService);
        output.setSession(session);
        overseer.subscribe(output);
        return output;
    }
```
---



