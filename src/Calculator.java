public class Calculator {

    // Métodos para operações básicas
    public float calcular(float numero1, float numero2, String operador) {
        switch (operador) {
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
                    return Float.NaN; // Evita divisão por zero
                }
            default:
                return Float.NaN; // Caso de erro
        }
    }

    // Método para raiz quadrada
    public float raizQuadrada(float numero) {
        return (float) Math.sqrt(numero);
    }

    // Método para elevar ao quadrado
    public float elevarAoQuadrado(float numero) {
        return (float) Math.pow(numero, 2);
    }
}
