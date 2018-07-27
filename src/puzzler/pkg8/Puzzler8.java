/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzler.pkg8;

import java.util.*;

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
           
       int matriz[][]=new int [3][3];
        
        int[] numeros = generarNumerosAleatoriosSinRepetir(0, 8, matriz.length*matriz[0].length); 
        for(int i=0;i<matriz.length;i++){
            for(int j=0;j<matriz[0].length;j++){
                matriz[i][j]=numeros[(matriz.length*i) + j ];
                System.out.print(matriz[i][j]+" ");
            }
            System.out.println("");
        }
         
        //int[][] inicio = {{6, 0, 1}, {2, 7, 3}, {4, 5, 8}};
        int[][] solucion = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        

        Nodo inicial = new Nodo(matriz);
        Nodo sol = buscarSolucion(inicial, solucion);
        boolean llave = true;
        int contador = 0;

        while (sol.padres!=null & llave==true) {
            imprimirEstado(sol.getEstado());
            System.out.println("-----------------------------------" + (++contador));
            sol = sol.padres;
            if(contador==30000){
                  llave=false;    
              }
        }
        
        System.out.println(" Inicio ");
           imprimirEstado(matriz);
           
       System.out.println("-----------------"); 

    }

    public static Nodo buscarSolucion(Nodo inicio, int[][] solucion) {
        ArrayList<Nodo> expandidos = new ArrayList<Nodo>();
        //*********************************************************
        ArrayList<Nodo> visitado = new ArrayList<Nodo>();
        expandidos.add(inicio);
        
        int cont = 0;
        
        boolean llave = true;//Se utiliza para salir del while


        try {
            while (!expandidos.isEmpty() && llave == true) {
                Nodo revisar = expandidos.remove(0);
                //imprimirEstado(revisar.getEstado());
                int[] pcero = ubicarPosicionCero(revisar.getEstado());

                //System.out.println("# VUELTAS ES:" + (++contador));
                if (Arrays.deepEquals(revisar.getEstado(), solucion)) {
                    System.out.println("SOLUCION ENCONTRADA");
                    return revisar;
                }
                //Cuando llega a 20000 intentos sale del while      
                if (cont == 30000) {//Cuando cont==Intentos llegue a los 30000, vuelve la llave falsa y sale del while
                    llave = false;
                }

                ArrayList<Nodo> hijos = new ArrayList<Nodo>();
                visitado.add(revisar);
                //*********************************************************
                // MOVIMINETO HACIA ARRIBA
                if (pcero[0] != 0) {
                    Nodo hijo = new Nodo(clonar(revisar.getEstado()));
                    int arriba = hijo.getEstado()[pcero[0] - 1][pcero[1]];
                    hijo.getEstado()[pcero[0]][pcero[1]] = arriba;
                    hijo.getEstado()[pcero[0] - 1][pcero[1]] = 0;
                    if (!estaVisitado(visitado, hijo)) {
                        expandidos.add(hijo);
                    }
                    hijos.add(hijo);
                }
                //*********************************************************
                // MOVIMINETO HACIA ABAJO
                if (pcero[0] != 2) {
                    Nodo hijo = new Nodo(clonar(revisar.getEstado()));
                    int abajo = hijo.getEstado()[pcero[0] + 1][pcero[1]];
                    hijo.getEstado()[pcero[0]][pcero[1]] = abajo;
                    hijo.getEstado()[pcero[0] + 1][pcero[1]] = 0;
                    if (!estaVisitado(visitado, hijo)) {
                        expandidos.add(hijo);
                    }
                    hijos.add(hijo);
                }
                //*********************************************************
                // MOVIMINETO HACIA IZQUIERDA
                if (pcero[1] != 0) {
                    Nodo hijo = new Nodo(clonar(revisar.getEstado()));
                    int izquierda = hijo.getEstado()[pcero[0]][pcero[1] - 1];
                    hijo.getEstado()[pcero[0]][pcero[1]] = izquierda;
                    hijo.getEstado()[pcero[0]][pcero[1] - 1] = 0;
                    if (!estaVisitado(visitado, hijo)) {
                        expandidos.add(hijo);
                    }
                    hijos.add(hijo);
                }
                //*********************************************************
                // MOVIMINETO HACIA DERECHA
                if (pcero[1] != 2) {
                    Nodo hijo = new Nodo(clonar(revisar.getEstado()));
                    int derecha = hijo.getEstado()[pcero[0]][pcero[1] + 1];
                    hijo.getEstado()[pcero[0]][pcero[1]] = derecha;
                    hijo.getEstado()[pcero[0]][pcero[1] + 1] = 0;
                    if (!estaVisitado(visitado, hijo)) {
                        expandidos.add(hijo);
                    }
                    hijos.add(hijo);
                }

                revisar.setHijos(hijos);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public static void imprimirEstado(int[][] estado) {
        for (int i = 0; i < estado.length; i++) {
            for (int j = 0; j < estado.length; j++) {
                System.out.print("[" + estado[i][j] + "]");
            }
            System.out.println("");
        }
    }

    private static int[] ubicarPosicionCero(int[][] estado) {
        //Para guardar la posicion i y j
        int[] posicion = new int[2];
        for (int i = 0; i < estado.length; i++) {
            for (int j = 0; j < estado.length; j++) {
                if (estado[i][j] == 0) {
                    posicion[0] = i;
                    posicion[1] = j;
                }
            }
        }
        //System.out.println("La posicion del espacio es: "+posicion[0]+","+posicion[1]); 
        return posicion;
    }

    private static int[][] clonar(int[][] estado) {
        int[][] clon = new int[estado.length][estado.length];

        for (int i = 0; i < estado.length; i++) {
            for (int j = 0; j < estado.length; j++) {
                clon[i][j] = estado[i][j];
            }
        }
        return clon;
    }

    private static boolean estaVisitado(ArrayList<Nodo> visitado, Nodo hijo) {
        for (Nodo v : visitado) {
            if (Arrays.deepEquals(v.getEstado(), hijo.getEstado())) {
                return true;
            }
        }
        return false;
    }

    //Funcion para generar numeroa aleatorios que no se repitan
    public static int[] generarNumerosAleatoriosSinRepetir(int minimo, int maximo, int longitud) {

        //En caso de que uno sea mayotr que otro
        //Lo intercambiamos
        if (maximo < minimo) {
            int aux = maximo;
            maximo = minimo;
            minimo = aux;
        }

        //Si caben los numeros del rango
        //Generamos el array
        if ((maximo - minimo) >= (longitud - 1)) {

            int numero_elementos = 0;
            int numeros[] = new int[longitud];
            //RECOMENDADO: rellena el arreglo con un numero que nunca se va a generar
            Arrays.fill(numeros, minimo - 1);
            boolean encontrado;
            int aleatorio;

            //Hasta que el numero de elementos no sea como el de la longitud del array no salimos
            while (numero_elementos < longitud) {

                aleatorio = generaNumeroAleatorio(minimo, maximo);
                encontrado = false;

                //Buscamos si el numero existe
                for (int i = 0; i < numeros.length && !encontrado; i++) {
                    if (aleatorio == numeros[i]) {
                        encontrado = true;
                    }
                }

                //Sino lo agregamos
                if (!encontrado) {
                    numeros[numero_elementos++] = aleatorio;
                }

            }

            return numeros;

        } else {
            System.out.println("No se puede generar el arreglo, revusa los parametros");
            return null;
        }

    }

    //Genera un numero aleatorio entre el minimo y el maximo, includo el maximo y el minimo
    public static int generaNumeroAleatorio(int minimo, int maximo) {

        int num = (int) Math.floor(Math.random() * (maximo - minimo + 1) + (minimo));
        return num;
    }
    
}
