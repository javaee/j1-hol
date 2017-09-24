# Java EE 8 Hands On Lab

## Table of contents

* [Introduction](#introduction)
* [Initial setup](#initial-setup)
* [Exercise 1 : JSON-B 1.0 and Bean Validation 2.0]()
* [Exercise 2 : Java EE Security API](ex-security.md) 
* [Exercise 3 : Servlet 4 and HTTP/2 Support](ex-servlet.md)
* [Exercise 4 : JAX-RS 2.1 and CDI 2.0]()
* [Conclusions](#conclusions)

## Introduction

*TODO!* 

## Initial setup

During the following Lab, you will use NetBeans 8.2 and GlassFish 5, the open source Java EE reference implementation. But you should be able to use any Java EE 8 compatible application server and your preferred IDE.

:bulb: Java EE 8 requires Java SE 8 or above. For JavaOne, we will use the latest JDK 8 release.

In the interest of time, NetBeans 8.2 and GlassFish have been installed and configured.


### Start NetBeans and GlassFish

In NetBeans, select *"Services"* tab, *"Servers"*, right click on the *"GlassFish 5"* instance and *"Start"* it. After a few seconds you should see that GF5 is started in the NB Output window at the bottom. You can also connect to the GF Admin console (http://localhost:4848) to confirm that GF is up and running.

:bulb: If the *"Start"* option is greyed, just wait a few seconds or select 
*"Refresh"* first.

!["Team", "Git", "Clone..."](pic/pic0-1.jpg)

### Clone the Lab GitHub repository

The only thing you have to do is to clone the Lab GitHub repository on your machine.

In NetBeans, choose *"Team", "Git", "Clone..." *

!["Team", "Git", "Clone..."](pic/pic0-2.jpg)

... and enter the URL (https://github.com/javaee/j1-hol.git) of the Lab GitHub repository to clone as below, keep the other default values.

!["Team", "Git", "Clone..."](pic/pic0-3.jpg)

Select *"Open Sources in Favourites"* and *"Finish"*. You can check the clone operation in the "Output" windows, the cloned repository should also appear under the *"Favourites"* tab.

!["Open Sources in Favourites"](pic/pic0-4.jpg)

:bulb: Sometime when you will add code, NetBeans will complain (e.g. some "imports" are missing). Simply click on the yellow bulb on the left side of the code and NetBeans will, most of the time, provides you with suggestions on how to fix the issue.

!["NetBeans trick"](pic/pic0-5.jpg)

You are now all set to do the exercises!


## Conclusions

*TODO!* 



