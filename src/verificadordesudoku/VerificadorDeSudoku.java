package verificadordesudoku;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.*;

public class VerificadorDeSudoku {

    public static void main(String[] args) {
        int[][] matrizJogo = new int[9][9];
        boolean[][] matrizErros = new boolean[9][9];

        for (int col = 0; col < 9; col++) {
            for (int lin = 0; lin < 9; lin++) {
                matrizErros[lin][col] = false;
            }
        }

        matrizJogo = carregarArquivo();
        
        for (int x = 0; x < 9; x++) {
            matrizErros = mesclarMatrizes(matrizErros, verificarJogo(matrizJogo, x));
        }
        
        for (int col = 0; col < 9; col = col + 3) {
            for (int lin = 0; lin < 9; lin = lin + 3) {
                matrizErros = mesclarMatrizes(matrizErros, verificarJogo(matrizJogo, col, lin));
            }
        }
        
        mostrarMatriz(matrizJogo, matrizErros);
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
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Coloque um arquivo com o nome "
                    + "\"respostaSudoku.txt\"");

            /* TODO
             * Impementação:
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
     * @param x
     * 
     * @example erros = verificarJogo(sudoku)
     *
     * @return erroNaPosicao {Array tipo boolean[9][9]}
     *
     */
    public static boolean[][] verificarJogo(int jogo[][], int x) {
        //A função funciona mas precisa ser otimizada
        boolean[][] erroNaPosicao = new boolean[9][9];

        for (int col = 0; col < 9; col++) {
            for (int lin = 0; lin < 9; lin++) {
                erroNaPosicao[lin][col] = false;
            }
        }

        for (int y = 0; y < 9; y++) {
            for (int z = (y + 1); z < 9; z++) {
                if (jogo[x][y] == jogo[x][z]) {
                    erroNaPosicao[x][y] = true;
                    erroNaPosicao[x][z] = true;
                }
                if (jogo[y][x] == jogo[z][x]) {
                    erroNaPosicao[y][x] = true;
                    erroNaPosicao[z][x] = true;
                }
            }
        }

        return erroNaPosicao;
    }

    public static boolean[][] verificarJogo(int jogo[][], int linhaInicial, int colunaInicial) {
        boolean[][] erroNaPosicao = new boolean[9][9];

        for (int col = 0; col < 9; col++) {
            for (int lin = 0; lin < 9; lin++) {
                erroNaPosicao[lin][col] = false;
            }
        }
        for (int linA = linhaInicial; linA < (linhaInicial + 3); linA++) {
            for (int colA = colunaInicial; colA < (colunaInicial + 3); colA++) {
                for (int linB = linhaInicial; linB < (linhaInicial + 3); linB++) {
                    for (int colB = colunaInicial; colB < (colunaInicial + 3); colB++) {
                        if (jogo[colA][linA] == jogo[colB][linB] && !(linA == linB && colA == colB)) {
                            erroNaPosicao[colA][linA] = true;
                            erroNaPosicao[colB][linB] = true;
                        }
                    }
                }
            }
        }
        return erroNaPosicao;
    }

    /**
     * Imprime a matriz que representa o jogo de sudoku com seus erros
     * representados na cor vermelha Quando há "true" na matrizErros, a posição
     * correspondente na matrizJogo é pintada de vermelho, caso contrário é
     * pintada de azul
     *
     * @param matrizJogo
     * @param matrizErros
     */
    public static void mostrarMatriz(int[][] matrizJogo, boolean[][] matrizErros) {
        //Código provisório, alterar na eventual implementação de uma interface 

        String jogoFormatado = "<html>";
            
        jogoFormatado += "┌";
        for(int x = 0; x < 8; x++){
            jogoFormatado += "  &nbsp;&nbsp;&nbsp;┬";
        }
        jogoFormatado += " &nbsp;&nbsp;&nbsp;┐<br>";
        
        for (int lin = 0; lin < 9; lin++) {
            for (int col = 0; col < 9; col++) {
                jogoFormatado += "│";
                jogoFormatado += " <font color=#" + ((matrizErros[lin][col]) ? "EB1010>" : "0E32C4>") + matrizJogo[lin][col] + "</font>  ";
            }
            if(lin!=8){
                jogoFormatado += "│";
                jogoFormatado += "<br>";
                jogoFormatado += "├";
                for(int x = 0; x < 8; x++){
                    jogoFormatado += "  &nbsp;&nbsp;&nbsp;┼";    
                }
                jogoFormatado += " &nbsp;&nbsp;&nbsp;┤<br>";
            }
        }
        
        jogoFormatado += "│<br>└";
        for(int x = 0; x < 8; x++){
            jogoFormatado += " &nbsp;&nbsp;&nbsp;┴";
        }
        jogoFormatado += " &nbsp;&nbsp;&nbsp;┘";

        jogoFormatado += "</html>";
        JOptionPane.showMessageDialog(null, jogoFormatado, "FIM DO PROGRAMA", 0);
    }

    /**
     * Mescla duas matrizes booleanas usando lógica OU entre todos seus
     * componentes
     *
     * @param matrizA {boolean 9x9}
     * @param matrizB {boolean 9x9}
     *
     * @return matrizMesclada {boolean 9x9}
     */
    public static boolean[][] mesclarMatrizes(boolean[][] matrizA, boolean[][] matrizB) {
        boolean[][] matrizMesclada = new boolean[9][9];

        for (int lin = 0; lin < 9; lin++) {
            for (int col = 0; col < 9; col++) {
                matrizMesclada[lin][col] = matrizA[lin][col] || matrizB[lin][col];
            }
        }

        return matrizMesclada;
    }
}