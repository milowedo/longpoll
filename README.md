# Http server push with long-polling technique implementation
---

The project consists of the server and client side code to properly demonstrate the idea of server pushing something to the user. Well not exacly pushing, but let us start with a little explanation.

## Idea  behind the project ~(what is longpolling?)
-----
##### Short-polling (standard http behaviour)
To be up to date with data on the server client can send a request asking for a data, the server side will respond with the data if it has it or with a response containing information that the data is yet not available.
So client sends another request hoping to get the data this time instead of an empty response. And again, and again forever.
This idea is a bit harsh when it comes to the amount of traffic the server will receive.

##### Long-polling

With the long-polling (THIS PROJECT) the client is sending a request for data to the server and then, if the data is not available in that moment, the request is waiting on the server until the data happens to be acquirable or the time out occurs(the usual browser's timeout is no less than 30sec, so we will keep to that).
The server responses, closing the http session with client.
As soon as the client gets a response, it sends another request for the new data. That way, the server always has a request hanging he can put data in and return it to the client any time some data gets obtainable.

---
Usage
-----

To make a use of this project you will have to install an npm package for the client side and add a jar to the server so that everything would work fine.

: in your client implementation directory run `npm install long-poll-template` 

: in server side just include a jar `com.millosz.longpoll` from this repository

#### Client
Inject the class (import it first), and set the requested url
```typescript
constructor(@Inject(LongPoll) private longPoll: LongPoll) {
    longPoll.setUri("http://localhost:8080"); 
    longPoll.setCall("/special/endpoint");
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
## Visible functions
-------------------------

  * changeSubscriptionStatus(val: boolean) : void { }
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; *true* - starts the until-stopped lasting server polling
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; *false* - pass *false* at any time to stop requesting for new data

  * makeLongRequest(): void { }
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; starts hanging on the server for new data
* setCall(call: string): void {}

* setUri(uri: string): void {}

