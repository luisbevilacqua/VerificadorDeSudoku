package verificadordesudoku;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.*;

public class VerificadorDeSudoku {

    public static void main(String[] args) {
        int[][] matrizJogo = new int[9][9];
        boolean[][] matrizErros = new boolean[9][9];
        
        matrizJogo = carregarArquivo();
        matrizErros = verificarJogo(matrizJogo);
        /*Debbuging!!!
        for (int lin = 0; lin < 9; lin++) {
            for (int col = 0; col < 9; col++) {
                System.out.printf(" " + matrizJogo[lin][col] + " ");
            }
            System.out.printf("\n");
        }
        
        System.out.printf("\n\n\n");
        
        for (int lin = 0; lin < 9; lin++) {
            for (int col = 0; col < 9; col++) {
                System.out.printf(" " + matrizErros[lin][col] + " ");
            }
            System.out.printf("\n");
        }*/
    }

    /**
     * Carrega o jogo de sudoku a partir de um arquivo txt chamado
     * respostaSudoku.txt e atribui os valores a uma matriz de inteiros
     *
     * @example sudoku = carregarArquivo()
     *
     * @return {Matriz de inteiros 9x9}
     *
     */
    public static int[][] carregarArquivo() {
        boolean matrizCompleta = true;
        int[][] matrizJogo = new int[9][9];
        
        try {
            File arquivo = new File("respostaSudoku.txt");
            Scanner sc = new Scanner(arquivo);
            
            for (int lin = 0; lin < 9; lin++) {
                if (!matrizCompleta) {
                    break;
                }
                for (int col = 0; col < 9; col++) {
                    if (sc.hasNextInt()) {
                        matrizJogo[lin][col] = sc.nextInt();
                    } else {
                        matrizCompleta = false;
                        break;
                    }
                }
            }
            if (!matrizCompleta) {
                JOptionPane.showMessageDialog(null, "O jogo de Sudoku esta "
                        + "incompleto");
            }
        } 
        catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Coloque um arquivo com o nome "
                    + "\"respostaSudoku.txt\"");
            
            /*Impementação:
            *
            * Pedir para o usuário digitar uma matriz ou inserir um
            * arquivo txt contendo uma
            */
        }
        return matrizJogo;
    }
    
    /**
     * Verifica se há repetições de números ao longo das colunas, linhas ou
     * blocos de uma matriz 9x9, retorna uma matriz de tamanho correspondente
     * com "true" onde há erros e "false" onde não há
     *
     * @param jogo {Array tipo int[9][9]}
     * @example erros = verificarJogo(sudoku)
     * 
     * @return erroNaPosicao {Array tipo boolean[9][9]}
     *
     */
    public static boolean[][] verificarJogo(int jogo[][]){
        //A função funciona mas precisa ser otimizada
        boolean[][] erroNaPosicao = new boolean[9][9];
        
        for(int col = 0; col < 9; col++){
            for(int lin = 0; lin < 9; lin++){
                erroNaPosicao[lin][col]=false;
            }
        }
        
        //Verifica as repetições nas linhas
        for(int col = 0; col < 9; col++){
            for(int linA = 0; linA < 9; linA++){
                for(int linB = (linA+1); linB < 9; linB++){
                    if(jogo[linA][col]==jogo[linB][col])
                    {
                        erroNaPosicao[linA][col]=true;
                        erroNaPosicao[linB][col]=true;
                    }
                }
            }
        }
        
        //Verifica as repetições nas linhas
        for(int lin = 0; lin < 9; lin++){
            for(int colA = 0; colA < 9; colA++){
                for(int colB = (colA+1); colB < 9; colB++){
                    if(jogo[lin][colA]==jogo[lin][colB])
                    {
                        erroNaPosicao[lin][colA]=true;
                        erroNaPosicao[lin][colB]=true;
                    }
                }
            }
        }
        
        //Verifica as repetições nos blocos
        for(int col = 0; col < 9; col=col+3){
            for(int lin = 0; lin < 9; lin=lin+3){
                for(int linA = lin; linA < (lin+3); linA++){
                    for(int colA = col; colA < (col+3); colA++){
                        for(int linB = lin; linB < (lin+3); linB++){
                            for(int colB = col; colB < (col+3); colB++){
                                if(jogo[colA][linA]==jogo[colB][linB]&&!(linA==linB&&colA==colB))
                                {
                                    erroNaPosicao[linA][colA]=true;
                                    erroNaPosicao[linB][colB]=true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return erroNaPosicao;
    }
}