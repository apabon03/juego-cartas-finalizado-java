/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package juegocartas;

import java.util.List;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
  * @Andres Eduardo Pabón Roldan
 * CC 1017236099
 * @Juan Jose Jaramillo Marin
 * CC 1035973625
 */
public class Jugador {
    private int TOTAL_CARTAS = 10;
    private int MARGEN= 20;
    private int DISTANCIA = 50;

    private Carta[] cartas = new Carta[TOTAL_CARTAS];
    
    private ArrayList<int[]> escaleras= new ArrayList();
    
    private Random r = new Random();
    
    public void repartir(){
        for(int i = 0; i < TOTAL_CARTAS; i++){
            cartas[i] = new Carta(r);
        }
    }
    
    public void mostrar(JPanel pnl){
        pnl.removeAll();
        int x = MARGEN;
        for(Carta c: cartas){
            c.mostrar(pnl, x, MARGEN);
            
            x += DISTANCIA;
        }
        pnl.repaint();
    }
    
    public String getGrupos(){
        String mensaje = "No se encontraron grupos";
        int[] contadores = new int[NombreCarta.values().length];
        for(Carta c: cartas){
            contadores[c.getNombre().ordinal()]++; //devuelve un enumerado, pregunta por la posición
        }
        int totalGrupos=0;
        for(int c:contadores){
            if(c>=2){
                totalGrupos++;
            }        
        }
        if(totalGrupos>0){
            mensaje="Los grupos encontrados fueron:\n";
            for(int i = 0; i<contadores.length; i++){
                if(contadores[i]>=2){
                    mensaje += Grupo.values()[contadores[i]]+" de "+ NombreCarta.values()[i] + "\n";
                }
            }
        }
        return mensaje;        
    }
    
    public void ordenarCartas(){
        Arrays.sort(cartas, Comparator.comparingInt(Carta::getIndice));
        
    }
    
    
    public String obtenerEscaleras() {
    String mensaje = "";
    
    //se ordena el arreglo cartas repartido
    Arrays.sort(cartas, Comparator.comparingInt(Carta::getIndice));

    for (Pinta pinta : Pinta.values()) {
        
        //por cada pinta especifica se crea un array de pintas con las cartas correspondientes
        ArrayList<Carta> arrayPinta = new ArrayList<>();
        
        
        for (Carta c : cartas) {
            if (c.getPinta().equals(pinta)) {
                //se añade cada carta respectiva del arreglo cartas
                arrayPinta.add(c);
            }
        }

        // Crear un LinkedHashSet a partir del ArrayList para eliminar duplicados y mantener el orden
        Set<Carta> set = new LinkedHashSet<>(arrayPinta);

        // Limpiar el ArrayList original
        arrayPinta.clear();

        // Agregar los elementos del LinkedHashSet de vuelta al ArrayList para mantener el orden y eliminar duplicados
        arrayPinta.addAll(set);

        int inicio = 0;
        ArrayList<int[]> matrizEscaleras = new ArrayList<>();
        
        //recorremos los arrays respectivos a su pinta: trebol, pica, corazon, diamante
        for (int j = 1; j < arrayPinta.size(); j++) {
            
            int segundo_numb = arrayPinta.get(j).getIndice();
            int primer_numb = arrayPinta.get(j - 1).getIndice() + 1;
            if (segundo_numb != primer_numb) {
                
                if (j - inicio > 1) {  // Si hay más de un elemento consecutivo
                    
                    int longitud = j - inicio;
                    int[] conjunto = new int[longitud];

                    // Copiar los elementos del arreglo original al nuevo arreglo 'conjunto'
                    for (int k = 0; k < longitud; k++) {
                        conjunto[k] = arrayPinta.get(k + inicio).getIndice();
                    }
                    matrizEscaleras.add(conjunto);
                    escaleras.add(conjunto);
                }
                inicio = j;
            }
        }
        if (arrayPinta.size() - inicio > 1) {
            int longitud = arrayPinta.size() - inicio;
            int[] conjunto = new int[longitud];

            for (int k = 0; k < longitud; k++) {
                conjunto[k] = arrayPinta.get(k + inicio).getIndice();
            }
            matrizEscaleras.add(conjunto);
            escaleras.add(conjunto);
        }

        for (int i = 0; i < matrizEscaleras.size(); i++) {
            mensaje += "\nEscalera " + pinta.name() + ":";
            for (int j = 0; j < matrizEscaleras.get(i).length; j++) {
                Carta c = new Carta(r);
                c.setIndice(matrizEscaleras.get(i)[j]);
                mensaje += " " + c.getNombre().toString() + " ";
            }
        }
        matrizEscaleras.clear();
        arrayPinta.clear();
    }
    
    return mensaje;
    }
    
    public String getSuma(){
    
        int suma = 0;
        
   
        int[] contador = new int[13];
        ArrayList<Carta> betados = new ArrayList();
        ArrayList<Carta> copiaCartas = new ArrayList();
        
        for(int i = 0; i < cartas.length; i++){
            copiaCartas.add(cartas[i]); 
        }
        
        
        for(int i=0; i < copiaCartas.size(); i++){
            for(int j=0; j< escaleras.size(); j++){
                for(int k=0; k< escaleras.get(j).length; k++){
                    if(copiaCartas.get(i).getIndice() == escaleras.get(j)[k]){
                        copiaCartas.remove(copiaCartas.get(i));
                    }
                }                
            }
        }
        
        for(int i = 0; i < cartas.length; i++){
            int condicion = cartas[i].getIndice() % 13;
            contador[condicion]++;
        }
        
        
        for(int i =0; i < copiaCartas.size(); i++){
            int moduloCarta = copiaCartas.get(i).getIndice() % 13;
            
            if(contador[moduloCarta] < 2){
                if(moduloCarta<10 && moduloCarta >1){
                    suma += moduloCarta;
                    
                }else{
                    suma += 10;
                }
            }
        }
        String mensaje = "Tu baraja tiene un valor de: "+ suma +" puntos descontando las figuras.";
        
        return mensaje;
    }

    public ArrayList<int[]> getEscaleras() {
        return escaleras;
    }
    
}






