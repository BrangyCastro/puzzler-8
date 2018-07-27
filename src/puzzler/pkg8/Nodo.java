/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzler.pkg8;

import java.util.ArrayList;

/**
 *
 * @author brangycastro
 */
public class Nodo {
    
    int [][] estado;
    ArrayList<Nodo> hijos = new ArrayList<Nodo>();
    Nodo padres;

    public Nodo(int[][] estado) {
        this.estado = estado;
        hijos = null;
        padres = null;
    }

    public int[][] getEstado() {
        return estado;
    }

    public void setEstado(int[][] estado) {
        this.estado = estado;
    }

    public ArrayList<Nodo> getHijos() {
        return hijos;
    }

    public void setHijos(ArrayList<Nodo> hijos) {
        this.hijos = hijos;
        if(hijos != null){
            for(Nodo h: hijos){
                h.padres = this;
            }
        }
    }

    public Nodo getPadres() {
        return padres;
    }

    public void setPadres(Nodo padres) {
        this.padres = padres;
    }
    
    
    
    
    
}
