import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorGUI extends JFrame {
    private JTextField display; // Campo de texto para mostrar os números e resultados
    private Calculator calculadora; // Instância da classe Calculator
    private String operador = ""; // Operador atual
    private float numero1 = 0; // Primeiro número
    private boolean novaOperacao = true; // Define se a próxima entrada é uma nova operação

    public CalculatorGUI() {
        calculadora = new Calculator(); // Instância da classe de cálculo
        configurarJanela();
        configurarComponentes();
    }

    private void configurarJanela() {
        setTitle("Calculadora");
        setSize(300, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    private void configurarComponentes() {
        // Campo de texto para exibir os números e resultados
        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        add(display, BorderLayout.NORTH);

        // Painel de botões
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridLayout(5, 4, 5, 5)); // Mudando para 5 linhas para adicionar o botão "CE"

        // Botões numéricos e de operação
        String[] botoes = {
                "7", "8", "9", "+",
                "4", "5", "6", "-",
                "1", "2", "3", "*",
                "C", "0", "=", "/",
                "CE" // Botão para apagar a última entrada
        };

        for (String texto : botoes) {
            JButton botao = new JButton(texto);
            botao.setFont(new Font("Arial", Font.BOLD, 18));
            painelBotoes.add(botao);

            // Adiciona a funcionalidade de clique
            botao.addActionListener(new BotaoClickListener());
        }

        add(painelBotoes, BorderLayout.CENTER);
    }

    private class BotaoClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String texto = ((JButton) e.getSource()).getText();

            if ("0123456789".contains(texto)) {
                if (novaOperacao) {
                    display.setText(texto);
                    novaOperacao = false;
                } else {
                    display.setText(display.getText() + texto);
                }
            } else if ("+-*/".contains(texto)) {
                numero1 = Float.parseFloat(display.getText());
                operador = texto;
                novaOperacao = true;
            } else if (texto.equals("=")) {
                float numero2 = Float.parseFloat(display.getText());
                float resultado = calculadora.calcular(numero1, numero2, operador);
                display.setText(Float.isNaN(resultado) ? "Erro" : String.valueOf(resultado));
                novaOperacao = true;
            } else if (texto.equals("C")) {
                // Limpa todas as operações e reseta a calculadora
                display.setText("");
                numero1 = 0;
                operador = "";
                novaOperacao = true;
            } else if (texto.equals("CE")) {
                // Limpa a última entrada no display, mas não altera as variáveis principais
                String textoDisplay = display.getText();
                if (textoDisplay.length() > 0) {
                    display.setText(textoDisplay.substring(0, textoDisplay.length() - 1));
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculatorGUI gui = new CalculatorGUI();
            gui.setVisible(true);
        });
    }
}
