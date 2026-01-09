/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prog.cipfpbatoi;


/**
 *
 * @author batoi
 */
public class Data {
    
    private int dia;
    private int mes;
    private int any;

    public Data() {
    }

    public Data(int dia, int mes, int any) {
        this.dia = dia;
        this.mes = mes;
        this.any = any;
    }

    public Data(String fecha) {
    }

    public void set(int dia, int mes, int any) {
        
    }
  
    public Data copy() {
        return null;
    }

    public int getDia() {
        return this.dia;
    }

    public int getMes(){
        return this.mes;
    }

    public int getAny(){
        return this.any;
    }

    public void mostrarEnFormatES()  {
        
    }

    public void mostrarEnFormatGB() {
        
    }

    public void mostrarEnFormatText() {
        
    }

    public boolean isIgual(Data fecha) {
        return false;
    }

    public String getDiaSetmana() {
        return null;
    }
    
    public boolean isFestiu() {
        return false;
    }

    public int getNumeroSetmana() {
        return -1;

    }

    public Data afegir(long numDias) {

        return null;
    }

    public Data restar(long numDias){
        return null;
    }

    public boolean isCorrecta(){
        return false;
    }

    public long getDiesDeDiferencia(Data data) {
        return -1L;
    }
   
    public boolean esPosteriorA(Data data) {
        return false;
    }
    
    public static boolean isBisiesto(int any){
        return false;
    }
    
    public static int getDiesMes(int mes, int any) {

        return -1;
    }

    public static int getDiesAny(int any){
        return -1;
    }
}
