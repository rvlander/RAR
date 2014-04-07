/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rar.client;

import rar.utils.Options;

/**
 *
 * @author rvlander
 */
public class ConcreteCarFactory {

    public static ConcreteCar makeCar() {

        String samplerType = Options.getCarType();

        if (samplerType.equals("dummy")) {
            return new DummyCar();
        }

        return new SocketCar();
    }

}
