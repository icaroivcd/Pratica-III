

package br.edu.ifnmg.passeioheuristica;


import java.util.ArrayList;
import java.util.Arrays;

public class PasseioHeuristica {
    private static final int TAMANHO = 8;
    private static final int[][] MOVIMENTOS = { {2, 1}, {1, 2}, {-1, 2}, {-2, 1}, {-2, -1}, {-1, -2}, {1, -2}, {2, -1} };
    private static int[][] tabuleiro;
    private static int numeroMovimentos = 1;

    public static void main(String[] args) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tabuleiro = new int[TAMANHO][TAMANHO];
                if (resolverPasseioCavalo(i, j)) {
                    imprimirTabuleiro();
                    System.out.println("Passeio completo iniciando em: (" + i + ", " + j + ")");
                    System.out.println("=================================");
                } else {
                    System.out.println("Nenhuma solução encontrada para o início em: (" + i + ", " + j + ")");
                }
            }
        }
    }

    private static boolean resolverPasseioCavalo(int linha, int coluna) {
        tabuleiro[linha][coluna] = numeroMovimentos;

        if (numeroMovimentos == TAMANHO * TAMANHO) {
            return true;
        }

        int[][] proximosMovimentos = calcularProximosMovimentos(linha, coluna);

        for (int i = 0; i < 8; i++) {
            int proximaLinha = proximosMovimentos[i][0];
            int proximaColuna = proximosMovimentos[i][1];

            if (movimentoValido(proximaLinha, proximaColuna) && tabuleiro[proximaLinha][proximaColuna] == 0) {
                numeroMovimentos++;
                if (resolverPasseioCavalo(proximaLinha, proximaColuna)) {
                    return true;
                }
                numeroMovimentos--;
                tabuleiro[proximaLinha][proximaColuna] = 0;
            }
        }

        return false;
    }

    private static boolean movimentoValido(int linha, int coluna) {
        return (linha >= 0 && linha < TAMANHO && coluna >= 0 && coluna < TAMANHO);
    }

    private static int[][] calcularProximosMovimentos(int linha, int coluna) {
        int[][] acessibilidade = new int[8][2];
        int[][] proximosMovimentos = new int[8][2];

        for (int i = 0; i < 8; i++) {
            int proximaLinha = linha + MOVIMENTOS[i][0];
            int proximaColuna = coluna + MOVIMENTOS[i][1];

            if (movimentoValido(proximaLinha, proximaColuna) && tabuleiro[proximaLinha][proximaColuna] == 0) {
                int acessibilidadeCount = calcularAcessibilidade(proximaLinha, proximaColuna);
                acessibilidade[i][0] = acessibilidadeCount;
                acessibilidade[i][1] = i;
            } else {
                acessibilidade[i][0] = 9; // Definir alta acessibilidade para movimentos inválidos
                acessibilidade[i][1] = i;
            }
        }

        Arrays.sort(acessibilidade, (a, b) -> Integer.compare(a[0], b[0]));

        for (int i = 0; i < 8; i++) {
            proximosMovimentos[i] = MOVIMENTOS[acessibilidade[i][1]];
        }

        return proximosMovimentos;
    }

    private static int calcularAcessibilidade(int linha, int coluna) {
        int acessibilidade = 0;
        for (int i = 0; i < 8; i++) {
            int proximaLinha = linha + MOVIMENTOS[i][0];
            int proximaColuna = coluna + MOVIMENTOS[i][1];
            if (movimentoValido(proximaLinha, proximaColuna) && tabuleiro[proximaLinha][proximaColuna] == 0) {
                acessibilidade++;
            }
        }
        return acessibilidade;
    }

    private static void imprimirTabuleiro() {
        for (int[] linha : tabuleiro) {
            for (int celula : linha) {
                System.out.printf("%2d ", celula);
            }
            System.out.println();
        }
    }
}

