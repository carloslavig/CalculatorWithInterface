import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Calculator calculadora = new Calculator(); // Instância da classe Calculator
        boolean continuar = true;

        while (continuar) {
            System.out.print("Informe o primeiro número: ");
            float numero1 = scanner.nextFloat();

            System.out.print("Informe a operação (+, -, *, /): ");
            String operacao = scanner.next();

            System.out.print("Informe o segundo número: ");
            float numero2 = scanner.nextFloat();

            float resultado = calculadora.calcular(numero1, numero2, operacao);

            if (!Float.isNaN(resultado)) { // Verifica se o resultado é válido
                System.out.printf("O resultado da operação é: %.2f%n", resultado);
            }

            System.out.print("Deseja realizar outra operação? (S/N): ");
            String resposta = scanner.next();

            continuar = resposta.equalsIgnoreCase("S");
        }

        System.out.println("Calculadora encerrada. Até mais!");
        scanner.close();
    }
}
