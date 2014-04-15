/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rar.networking;

/**
 *
 * @author rvlander
 */
public interface ClientListener {

    public void register(String name);

    public void playerIsAlreadyPresentException();
    
}
