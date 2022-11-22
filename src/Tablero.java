/**
 * Tablero del buscaminas.
 *
 * @author (Cristian Arando)
 */
public class Tablero
{
    private final int f = 10;//fila
    private final int c = 10;//columna
    private boolean[][] visible;
    private int[][] oculto;
    private int[][] minas;
    private final int MINAS = 15;
    //las posicionX y posicionY son una forma de representar el contorno de una casilla
    private int[] posicionX = {-1,-1,-1, 0, 0, 1, 1, 1};
    private int[] posicionY = {-1, 0, 1,-1, 1,-1, 0, 1};
    private int numCasillas = 85;
    public Tablero(){
        visible = new boolean[f][c];
        oculto = new int[f+2][c+2];
        minas = new int[f+2][c+2];
        for(int x=0; x<c+2; x++){
            minas[0][x] = -1;
            oculto[0][x] = -1;
            minas[f+1][x] = -1;
            oculto[f+1][x] = -1;
        }
        for(int y=0; y<c+2; y++){
            minas[y][0] = -1;
            oculto[y][0] = -1;
            minas[y][c+1] = -1;
            oculto[y][c+1] = -1;
        }
        for(int i=0;i<f;i++){
            for(int j=0;j<c;j++)
                visible[i][j] = false;
        }
    }

    public void generarTablero(int filaInicio, int columnaInicio){
        llenarTableros(filaInicio,columnaInicio);
        for (int i=0;i<f;i++){
            for (int j=0;j<c;j++){
                if (minas[i+1][j+1] == 0)
                    oculto[i+1][j+1] = contarMinas(i,j);
                else if(minas[i+1][j+1] == 1)
                    oculto[i+1][j+1] = 9;
            }
        }
    }

    private void llenarTableros(int fila,int columna){
        for (int i = 1;i<f+1;i++){
            for (int j = 1;j<c+1;j++){
                minas[i][j] = 0;
                oculto[i][j] = 0;
            }
        }
        colocarMinas(fila+1,columna+1);
    }

    private void colocarMinas(int fila,int columna){
        int contador=0;
        while(contador<=MINAS){
            int x = (int)(Math.random()*f+1);
            int y = (int)(Math.random()*c+1);
            if(!((x==fila && y==columna) || (minas[x][y]==1))){
                minas[x][y] = 1;
                contador++;
            }
        }
    }

    private int contarMinas(int fila,int columna){
        int contMinas = 0;
        for (int var = 0;var<posicionX.length;var++){
            if (minas[fila+posicionX[var]+1][columna+posicionY[var]+1] == 1)
                contMinas++;
        }
        return contMinas;
    }

    public void printTablero(){
        System.out.println("   | 0  1  2  3  4  5  6  7  8  9 |");
        System.out.println(" ----------------------------------");
        for(int i=0;i<f;i++){
            System.out.print(" "+ i + " |");
            for(int j=0;j<c;j++){
                if(visible[i][j]==true)
                    System.out.print(" "+ oculto[i+1][j+1] + " ");
                else
                    System.out.print(" " + "*" + " ");
            }
            System.out.println("|");
        }
        System.out.println("-----------------------------------");
    }

    public void hacerVisible(int fila, int columna){
        numCasillas--;
        visible[fila][columna] = true;
    }

    public boolean esMina(int fila, int columna){
        return minas[fila+1][columna+1]==1;
    }

    public void todoVisible(){
        for (int i=0;i<f;i++){
            for (int j=0;j<c;j++)
                visible[i][j] = true;
        }
    }

    public void printTableroCompleto(){
        System.out.println("   | 0  1  2  3  4  5  6  7  8  9 |");
        System.out.println(" ----------------------------------");
        for(int i=0;i<f;i++){
            System.out.print(" "+ i + " |");
            for(int j=0;j<c;j++){
                if(minas[i+1][j+1]==1)
                    System.out.print(" "+ "M" + " ");
                else
                    System.out.print(" "+ oculto[i+1][j+1] + " ");
            }
            System.out.println("|");
        }
        System.out.println("-----------------------------------");
    }

    public void scanear(int fila, int columna){
        if(oculto[fila+1][columna+1]!=0 && oculto[fila+1][columna+1]!=9)
            hacerVisible(fila,columna);
        else{
            for(int var = 0;var<8;var++){
                int x = fila+posicionX[var]+1;
                int y = columna+posicionY[var]+1;
                switch(oculto[x][y]){
                    case 0:
                        if(visible[x-1][y-1]==false){
                            hacerVisible(x-1,y-1);
                            scanear(x-1,y-1);
                        }
                        break;
                    case 9:
                        break;
                    case -1:
                        break;
                    default:
                        if(visible[x-1][y-1]==false)
                            hacerVisible(x-1,y-1);
                }
            }
        }
    }

    public int getNumCasillas(){
        return numCasillas;
    }
}