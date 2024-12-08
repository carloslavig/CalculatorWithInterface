public class Calculator {
    public float calcular(float numero1, float numero2, String operacao) {
        switch (operacao) {
            case "+":
                return numero1 + numero2;
            case "-":
                return numero1 - numero2;
            case "*":
                return numero1 * numero2;
            case "/":
                if (numero2 != 0) {
                    return numero1 / numero2;
                } else {
                    System.out.println("Erro: divisão por zero!");
                    return Float.NaN; // Resultado inválido
                }
            default:
                System.out.println("Erro: operação inválida!");
                return Float.NaN; // Resultado inválido
        }
    }
}
