import java.util.Scanner;
import java.util.InputMismatchException;
/**
 * El BuscaMinas es un juego clasico que en este caso
 * se intento hacer una copia del juego original
 * usando y operando el scanner y todo bajo consola.
 *
 * @author (Cristian Arando)
 * codSIS = 201902469
 * @version (1.0)
 */
public class BuscaMinas {
    private Tablero tablero;
    private Scanner sc;
    private boolean enJuego = false;
    boolean primeraVez = false;
    public BuscaMinas(){
        tablero = new Tablero();
        sc = new Scanner(System.in);
    }

    public static void main(String[] args){
        BuscaMinas juego = new BuscaMinas();
        juego.menu();
    }

    private void menu(){
        boolean salir = false;
        do{
            printMenu();
            try{
                int opcion = sc.nextInt();
                switch(opcion){
                    case 1:
                        limpiarConsola();
                        jugar();
                        break;
                    case 2:
                        limpiarConsola();
                        System.out.println("Saliste del juego");
                        System.exit(0);
                        salir = true;
                        break;
                    default:
                        limpiarConsola();
                        System.out.println("ESCOJA UNA OPCION CORRECTA");
                }
            }catch(InputMismatchException e){
                sc.next(); //Se coloca para que no se haga un bucle infinito
                limpiarConsola();
                System.out.println("LA OPCION DEBE SER UN NUMERO");
            }
        }while(!salir);
    }

    private void jugar(){
        enJuego = true;
        do{
            try{
                if(tablero.getNumCasillas()<=0){
                    printWin();
                    primeraVez = false;
                    tablero = new Tablero();
                    enJuego=false;
                }
                System.out.println("Elige una posicion : ");
                System.out.println("Ingresando coordenadas");
                System.out.println("FILA: ");
                int fila = sc.nextInt();
                if(fila>=0 && fila<10){
                    System.out.println("Columna:");
                    int columna = sc.nextInt();
                    if(columna>=0 && columna<10){
                        limpiarConsola();
                        System.out.println("La posicion escogida es: pos[" + fila + "][" + columna + "]");
                        if(primeraVez == false){
                            tablero.generarTablero(fila,columna);
                            seleccionarCasilla(fila,columna);
                            primeraVez = true;
                        }else
                            seleccionarCasilla(fila,columna);
                    }else{
                        limpiarConsola();
                        System.out.println("La columna debe ser un numero valido");
                        System.out.println("EMPIECE DE NUEVO");
                    }
                }else{
                    limpiarConsola();
                    System.out.println("La fila debe ser un numero valido del tablero");
                    System.out.println("EMPIECE DE NUEVO");
                }
            }catch(InputMismatchException e){
                limpiarConsola();
                System.out.println("Se debe ingresar un numero");
                System.out.println("EMPIECE DE NUEVO");
                sc.next();
            }
        }while(enJuego==true);
    }

    private void seleccionarCasilla(int fila,int columna){
        if(tablero.esMina(fila,columna)==false){
            tablero.hacerVisible(fila,columna);
            tablero.scanear(fila,columna);
            tablero.printTablero();
        }else{
            tablero.todoVisible();
            printPerdiste();
            enJuego=false;
            primeraVez = false;
            tablero = new Tablero();
        }
    }

    private void printPerdiste(){
        limpiarConsola();
        System.out.println("=================================");
        System.out.println("           GAME OVER             ");
        System.out.println("=================================");
        tablero.printTableroCompleto();
    }

    private void printMenu(){
        System.out.println("=================================");
        System.out.println("           BUSCA MINAS           ");
        System.out.println("=================================");
        System.out.println("PARA JUGAR_____________Escriba(1)");
        System.out.println("PARA SALIR_____________Escriba(2)");
        System.out.println("=================================");
        System.out.println("Elige una opcion:");
        System.out.println("=================================");
    }

    private void limpiarConsola(){
        System.out.println('\f');
    }

    private void printWin(){
        limpiarConsola();
        System.out.println("=================================");
        System.out.println("           GANASTE             ");
        System.out.println("=================================");
        tablero.printTableroCompleto();
    }
}