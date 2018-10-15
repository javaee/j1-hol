# JAX-RS / CDI exercise

## Initial setup

In this exercise, we will use [CDI 2.0](http://cdi-spec.org/) Asynchronous Events to publish "stock ticker" values. We will then use [JAX-RS 2.1](https://projects.eclipse.org/projects/ee4j.jaxrs) to broadcast those events as Server Sent Events and create a client to consume them.

Start a new Maven project and select *"Web Application"* and give it a meaningful name, ex. *"hol-stock-server"*, click *"Next"*.

Select *"GlassFish"* as the application server and *"Java EE7 Web"*, we will update the Java EE API version later. Validate and you should now have an empty project.

Select the project's *pom.xml*, under *"Project Files"*. Now you can update the project to use Java EE 8 APIs but just updating the version number of the *javaee-web-api* dependency.

```xml
 <dependency>
    <groupId>javax</groupId>
    <artifactId>javaee-web-api</artifactId>
    <version>8.0</version>
    <scope>provided</scope>
 </dependency>
```

Additionally, set up the project to use Java 8

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <version>3.1</version>
    <configuration>
        <source>1.8</source>
        <target>1.8</target>
        <compilerArguments>
            <endorseddirs>${endorsed.dir}</endorseddirs>
        </compilerArguments>
    </configuration>
</plugin>
```

:bulb: This Lab is only using APIs from the Jakarta EE 8 Web Profile which is a subset of the Full platform.

## Create the Ticker Singleton

Right click on the project, select *"New"* and *"Java Class"*, enter an appropriate class name, eg. *"Ticker"*. Make sure to specify a package where you code will reside, eg. *"org.j1hol"* then *"finish"*.  

Annotate your class with `@Singleton`.

Create an Double instance variable to represent the share price and initialize it to 50.00.
```java
private Double currentPrice = 50.;
```

Create an instance of `java.util.Random` which can be used to generate "random" changes to the stock price.
```java
private Random randomGen = new Random();
```

:bulb: Remember to add all missing imports by hitting `CTRL+SHIFT+I`.

Inject as Event bus which will accept Double Events.

```java
@Inject
private Event<Double> eventBus;
```

:bulb: Remember to add all missing imports by hitting `CTRL+SHIFT+I` and select `javax.enterprise.event.Event`.

Now we'll need to create a method to update the price.  

```java
@Schedule(second = "*", minute = "*", hour = "*")
public void updatePrice() {
    currentPrice += (randomGen.nextDouble() - 0.4) * 5;
    eventBus.fireAsync(currentPrice).thenAccept((event) -> System.out.println(String.format("$%.2f", event) + " Delivered"));
    System.out.println(String.format("$%.2f", currentPrice) + " Fired");
}
```

This method will update the price, then fire an event containing the new price.  

:bulb: This `@Schedule` annotation will call this method once every second

:bulb: Notice that when the application is run, the "fired" line is printed before the "delivered" line. 

## Create a JAX-RS Web service

Right click on the project, select *"New"* and *"Java Class"*, enter an appropriate class name, eg. *"SseService"*. Make sure to specify a package where you code will reside, eg. *"org.j1hol"* then *"finish"*.  

Annotate your class with the `@Path` annotation, specifying an appropriate path for our web service, eg. `@Path("sse")`. 

and make it a stateless session bean by adding an `@Stateless` annotation.

:bulb: Remember to add all missing imports by hitting `CTRL+SHIFT+I`.

NetBeans will warn that REST is not configured, click on the class name and hit `Alt+Enter`, then select "Configure REST using Java EE 6 Specification".  

Create an SseBroadcaster instance variable.
```java
private SseBroadcaster broadcaster;
```
Inject the Sse context into the instance.
 
```java
@Context
private Sse sse;
```

Add a method to set up the Ssebroadcaster.
```java
public void setup() {
    broadcaster = sse.newBroadcaster();
}
```

Annotate this method with `@PostConstruct` so that it is called after the instance has been instantiated.

And one to tear it down.
```java
public void tearDown() {
    broadcaster.close();
}
```

Annotate this method with `@PreDestroy` so it will be called before Glassfish destroys the instance.

Add a public method named `register()` which should accept an SseEventSink and return nothing.

Annotate your method with the `@GET` annotation to have the method respond to HTTP GET requests.

Annotate your method with the `@Produces` annotation, specifying `MediaType.SERVER_SENT_EVENTS` as the value attribute of the annotation.

Annotate the argument with `@Context` so it will be injected by Glassfish.

register the sseEventSink with your broadcaster.

```java
@GET
@Produces(MediaType.SERVER_SENT_EVENTS)
public void register(@Context SseEventSink sseEventSink) {
    broadcaster.register(sseEventSink);
}
```

Finally add a `listener()` method which accepts a Double and returns nothing.

Annotate the argument with `@ObservesAsync` so that it will consume the events published by our `Ticker`
construct an `OutboundSseEvent` from the double
```java
OutboundSseEvent sseEvent = sse.newEvent("ticker value", String.format("$%.2f", event));
```

now broadcast the sseEvent top all registered clients
```java
broadcaster.broadcast(sseEvent);
```

At this point your class should look something like this:
```java
@Path("sse")
@Stateless
public class SseResource {

    private SseBroadcaster broadcaster;

    @Context
    private Sse sse;

    @PostConstruct
    public void setup() {
        broadcaster = sse.newBroadcaster();
    }

    @PreDestroy
    public void tearDown() {
        broadcaster.close();
    }

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void register(@Context SseEventSink sseEventSink) {
        broadcaster.register(sseEventSink);
    }

    public void listener(@ObservesAsync Double event) {
        OutboundSseEvent sseEvent = sse.newEvent("ticker value", String.format("$%.2f", event));
        broadcaster.broadcast(sseEvent);
    }
}
```

Deploy your project by right clicking on it and selecting *"Run"*

:bulb: A "Hello World" page will pop up in the browser, this page is automatically generated by NetBeans and can be safely ignored.

Test your web service by navigating to `http://localhost:8080/hol-stock-server/webresources/sse` 

Did you get the result you were expecting? Why or why not?

## Creating a simple JAX-RS client

Start a new Maven project and select *"Java Application"* and give it a meaningful name, ex. *"hol-stock-client"*, click *"Next"*.

Select the project's *pom.xml*, under *"Project Files"* and set up the project to use Java 8

```xml
 <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>1.8</maven.compiler.source>
    <maven.compiler.target>1.8</maven.compiler.target>
</properties>
```

The add the jersey dependencies

```xml
<dependencies>
    <dependency>
        <groupId>org.glassfish.jersey.media</groupId>
        <artifactId>jersey-media-sse</artifactId>
        <version>2.27</version>
    </dependency>
    <dependency>
        <groupId>org.glassfish.jersey.inject</groupId>
        <artifactId>jersey-hk2</artifactId>
        <version>2.27</version>
    </dependency>
</dependencies>
```

:bulb: Jersey is the Reference Implementation for JAX-RS 2.1

Right click on the project, select "New" and "Java Main Class", enter an appropriate class name, eg. "StockClient". Make sure to specify a package where you code will reside, eg. "org.j1hol" then "finish".

Add an instance variable to store the `javax.ws.rs.client.WebTarget` representing the EventStream endpoint 

Initialize this variable in a null constructor.

```java
public StockClient() {
    Client client = ClientBuilder.newClient();
    target = client.target("http://localhost:8080/hol-stock-server/webresources/sse");
}
```

Add a public method called `listen()` that accepts no arguments and returns nothing.

Build an SseEventSource from the target

```java
SseEventSource sse = SseEventSource.target(target).build();
```

Register an event listener which will print a timestamp and the event data and open the event stream

```java
sse.register((event) -> System.out.println(LocalDateTime.now() + ": " + event.readData()));
sse.open();
```

Finally, within the `main()` method, instantiate an instance and call the `listen()` method.

```java
StockClient stockClient = new StockClient();
stockClient.listen();
```

Make sure your server is running and the run your client by right clicking on it and selecting *"Run"*

You should see the prices printed on the console.

### Back to the [Exercises list](https://github.com/dheffelfinger/j1-hol#java-ee-8-hands-on-lab).