# Bean Validation / JSON-B exercise

## Initial setup

In this exercise, we will use [Bean Validation 2.0](http://beanvalidation.org/) to validate a collection of objects. We will also use [JSON-B](http://json-b.net/) to generate a JSON string from a collection of Java objects.

Start a new Maven project and select *"Web Application"* and give it a meaningful name, ex. *"hol-jsonb-beanval"*, click *"Next"*.

Select *"GlassFish"* as the application server and *"Java EE7 Web"*, we will update the Java EE API version later. Validate and you should now have an empty project.

Select the project\'s *pom.xml*, under *"Project Files"*. Now you can update the project to use Java EE 8 APIs but just updating the version number of the *<javaee-web-api>* dependency.

```xml
 <dependency>
    <groupId>javax</groupId>
    <artifactId>javaee-api</artifactId>
    <version>8.0</version>
    <scope>provided</scope>
 </dependency>
```

:bulb: This Lab is only using APIs from the Java EE 8 Profile which is a subset of the Full platform.

## Create a JAX-RS Web service

Right click on the project, select *"New"* and *"Java Cless"*, enter an appropriate class name, eg. *"JsonBBeanValPracticeService"*. Make sure to specify a package where you code will reside, eg. *"org.j1hol"* then *"finish"*.  

Annotate your class with the `@Path` annotation, specifying an appropriate path for our web service, eg. `@Path("jsonbbeanval")`.

:bulb: Add all missing imports by hitting `CTRL+SHIFT+I`.

NetBeans will warn that REST is not configured, click on the class name and hit `Alt+Enter`, then select "Configure REST using Java EE 6 Specification".

![Configure REST](pic/pic-configure-rest.jpg)
