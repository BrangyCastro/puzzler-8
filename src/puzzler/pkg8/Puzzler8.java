/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzler.pkg8;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author brangycastro
 */
public class Puzzler8 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        int [][] inicio = {{4,1,3},{2,6,8},{7,5,0}};
        int [][] solucion = {{1,2,3},{4,5,6},{7,8,0}};
        
        Nodo inicial = new Nodo(inicio);
        Nodo sol = buscarSolucion(inicial, solucion);
        
        while(sol.padres != null){
            imprimirEstado(sol.getEstado());
            System.out.println("-----------------------------------");
            sol = sol.padres;
        }
         
    }
    
    public static Nodo buscarSolucion(Nodo inicio, int[][] solucion){
        ArrayList<Nodo> expandidos = new ArrayList<Nodo>();
        //*********************************************************
        ArrayList<Nodo> visitado = new ArrayList<Nodo>();
        expandidos.add(inicio);
        int contador = 0;
        while(expandidos.size() != 0){
           Nodo revisar = expandidos.remove(0);
           imprimirEstado(revisar.getEstado());
           int[] pcero = ubicarPosicionCero(revisar.getEstado());
            System.out.println("# VUELTAS ES:" + (++contador));
           if(Arrays.deepEquals(revisar.getEstado(), solucion)){
               System.out.println("SOLUCION ENCONTRADA");
               return revisar;
           }
           ArrayList<Nodo> hijos = new ArrayList<Nodo>();
           visitado.add(revisar);
           //*********************************************************
           if(pcero[0] != 0){
               Nodo hijo = new Nodo(clonar(revisar.getEstado()));
               int arriba = hijo.getEstado()[pcero[0]-1][pcero[1]];
               hijo.getEstado()[pcero[0]][pcero[1]] = arriba;
               hijo.getEstado()[pcero[0]-1][pcero[1]] = 0;
               if(!estaVisitado(visitado,hijo))
                   expandidos.add(hijo); 
               hijos.add(hijo);
           }
           //*********************************************************
           if(pcero[0] != 2){
               Nodo hijo = new Nodo(clonar(revisar.getEstado()));
               int abajo = hijo.getEstado()[pcero[0]+1][pcero[1]];
               hijo.getEstado()[pcero[0]][pcero[1]] = abajo;
               hijo.getEstado()[pcero[0]+1][pcero[1]] = 0;
               if(!estaVisitado(visitado,hijo))
                   expandidos.add(hijo); 
               hijos.add(hijo);
           }
           //*********************************************************
           if(pcero[1] != 0){
               Nodo hijo = new Nodo(clonar(revisar.getEstado()));
               int izquierda = hijo.getEstado()[pcero[0]][pcero[1]-1];
               hijo.getEstado()[pcero[0]][pcero[1]] = izquierda;
               hijo.getEstado()[pcero[0]][pcero[1]-1] = 0;
               if(!estaVisitado(visitado,hijo))
                   expandidos.add(hijo);  
               hijos.add(hijo);
           }
           //*********************************************************
           if(pcero[1] != 2){
               Nodo hijo = new Nodo(clonar(revisar.getEstado()));
               int derecha = hijo.getEstado()[pcero[0]][pcero[1]+1];
               hijo.getEstado()[pcero[0]][pcero[1]] = derecha;
               hijo.getEstado()[pcero[0]][pcero[1]+1] = 0;
               if(!estaVisitado(visitado,hijo))
                   expandidos.add(hijo);  
               hijos.add(hijo);
           }
           
           revisar.setHijos(hijos);
        }
        
        return null;
    }
    
    public static void imprimirEstado(int [][] estado){
        for(int i = 0; i < estado.length; i++){
            for(int j = 0; j < estado.length; j++){
                System.out.print("["+ estado[i][j] + "]");
            }
            System.out.println("");
        }
    }

    private static int[] ubicarPosicionCero(int[][] estado) {
        //Para guardar la posicion i y j
        int[] posicion = new int[2]; 
         for(int i = 0; i < estado.length; i++){
            for(int j = 0; j < estado.length; j++){
                if(estado[i][j] == 0){
                    posicion[0] = i;
                    posicion[1] = j;
                }
            }
        }
        System.out.println("La posicion del espacio es: "+posicion[0]+","+posicion[1]); 
        return posicion;
    }

    private static int[][] clonar(int[][] estado) {
        int[][] clon = new int[estado.length][estado.length];
        
        for(int i = 0; i < estado.length; i++){
            for(int j = 0; j < estado.length; j++){
                clon[i][j] = estado[i][j];
            }
        }
        return clon;
    }

    private static boolean estaVisitado(ArrayList<Nodo> visitado, Nodo hijo) {
        for(Nodo v:visitado){
            if(Arrays.deepEquals(v.getEstado(), hijo.getEstado())){
                return true;
            }
        }
        return false;
    }

}
