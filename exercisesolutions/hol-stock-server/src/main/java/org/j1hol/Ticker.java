/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.j1hol;

import java.util.Random;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.enterprise.event.Event;
import javax.inject.Inject;

/**
 *
 * @author boblarsen
 */
@Singleton
public class Ticker {
    private Double currentPrice = 50.;
    private Random randomGen = new Random();

    @Inject
    private Event<Double> eventBus;

    @Schedule(second = "*", minute = "*", hour = "*")
    public void updatePrice() {
        currentPrice += (randomGen.nextDouble() - 0.4) * 5;
        eventBus.fireAsync(currentPrice).thenAccept((event) -> System.out.println(String.format("$%.2f", event) + " Delivered"));
        System.out.println(String.format("$%.2f", currentPrice) + " Fired");
    }
}
