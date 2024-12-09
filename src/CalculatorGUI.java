import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CalculatorGUI extends JFrame {
    private JTextField display;
    private Calculator calculadora;
    private String operador = "";
    private float numero1 = 0;
    private boolean novaOperacao = true;
    private boolean isDarkMode = false;
    private boolean pontoUsado = false; // Flag para verificar se o ponto já foi usado

    public CalculatorGUI() {
        calculadora = new Calculator();
        configurarJanela();
        configurarComponentes();
    }

    private void configurarJanela() {
        setTitle("Calculadora");
        setSize(350, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
    }

    private void configurarComponentes() {
        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        add(display, BorderLayout.NORTH);

        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridLayout(6, 4, 5, 5));

        String[] botoes = {
                "7", "8", "9", "+",
                "4", "5", "6", "-",
                "1", "2", "3", "*",
                "C", "0", "=", "/",
                "CE", "√", "x²", "Dark Mode",
                "." // O ponto será ao lado do zero
        };

        for (String texto : botoes) {
            JButton botao = new JButton(texto);
            botao.setFont(new Font("Arial", Font.BOLD, 18));
            painelBotoes.add(botao);
            botao.addActionListener(new BotaoClickListener());
        }

        add(painelBotoes, BorderLayout.CENTER);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                char tecla = e.getKeyChar();

                // Reconhecer números e operadores
                if (Character.isDigit(tecla)) {
                    adicionarAoVisor(String.valueOf(tecla));
                } else if (tecla == '+' || tecla == '-' || tecla == '*' || tecla == '/') {
                    operador = String.valueOf(tecla);
                    numero1 = Float.parseFloat(display.getText());
                    novaOperacao = true;
                    pontoUsado = false; // Reset ponto ao começar uma nova operação
                } else if (tecla == '=' || tecla == KeyEvent.VK_ENTER) {
                    calcularResultado();
                } else if (tecla == 'c' || tecla == 'C') {
                    limparTudo();
                } else if (tecla == 'e' || tecla == 'E') {
                    limparTudo(); // Alterando para limpar tudo ao pressionar CE
                } else if (tecla == 'r' || tecla == 'R') {
                    raizQuadrada();
                } else if (tecla == 's' || tecla == 'S') {
                    elevarAoQuadrado();
                } else if (tecla == 'd' || tecla == 'D') {
                    alternarTema();
                } else if (tecla == '.') {
                    adicionarPonto();
                }
            }
        });

        setFocusable(true);
    }

    private void adicionarAoVisor(String texto) {
        if (novaOperacao) {
            display.setText(texto);
            novaOperacao = false;
        } else {
            display.setText(display.getText() + texto);
        }
    }

    private void calcularResultado() {
        float numero2 = Float.parseFloat(display.getText());
        float resultado = calculadora.calcular(numero1, numero2, operador);
        display.setText(Float.isNaN(resultado) ? "Erro" : String.valueOf(resultado));
        novaOperacao = true;
        pontoUsado = false;
    }

    private void limparTudo() {
        display.setText("");
        numero1 = 0;
        operador = "";
        novaOperacao = true;
        pontoUsado = false;
    }

    private void limparUltimaEntrada() {
        String textoDisplay = display.getText();
        if (textoDisplay.length() > 0) {
            char ultimoChar = textoDisplay.charAt(textoDisplay.length() - 1);
            if (ultimoChar == '.') {
                pontoUsado = false; // Reset ponto
            }
            display.setText(textoDisplay.substring(0, textoDisplay.length() - 1));
        }
    }

    private void raizQuadrada() {
        float numero = Float.parseFloat(display.getText());
        float resultado = calculadora.raizQuadrada(numero);
        display.setText(String.valueOf(resultado));
        novaOperacao = true;
        pontoUsado = false;
    }

    private void elevarAoQuadrado() {
        float numero = Float.parseFloat(display.getText());
        float resultado = calculadora.elevarAoQuadrado(numero);
        display.setText(String.valueOf(resultado));
        novaOperacao = true;
        pontoUsado = false;
    }

    private void alternarTema() {
        isDarkMode = !isDarkMode;
        Color fundo, texto, fundoBotoes, textoBotoes;

        if (isDarkMode) {
            fundo = new Color(18, 32, 47); // Azul escuro
            texto = Color.WHITE;
            fundoBotoes = new Color(45, 45, 45);
            textoBotoes = Color.WHITE;
        } else {
            fundo = Color.WHITE;
            texto = Color.BLACK;
            fundoBotoes = new Color(220, 220, 220);
            textoBotoes = Color.BLACK;
        }

        getContentPane().setBackground(fundo);
        display.setBackground(fundo);
        display.setForeground(texto);

        JPanel painelBotoes = (JPanel) getContentPane().getComponent(1);
        for (Component componente : painelBotoes.getComponents()) {
            JButton botao = (JButton) componente;
            botao.setBackground(fundoBotoes);
            botao.setForeground(textoBotoes);
        }

        repaint();
    }

    private void adicionarPonto() {
        // Verificar se o ponto já foi inserido
        if (!pontoUsado) {
            adicionarAoVisor(".");
            pontoUsado = true; // Marcar que o ponto foi usado
        }
    }

    private class BotaoClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String texto = ((JButton) e.getSource()).getText();

            if ("0123456789".contains(texto)) {
                adicionarAoVisor(texto);
            } else if ("+-*/".contains(texto)) {
                operador = texto;
                numero1 = Float.parseFloat(display.getText());
                novaOperacao = true;
                pontoUsado = false;
            } else if (texto.equals("=")) {
                calcularResultado();
            } else if (texto.equals("C")) {
                limparTudo();
            } else if (texto.equals("CE")) {
                limparTudo(); // Alterando para limpar tudo ao pressionar CE
            } else if (texto.equals("√")) {
                raizQuadrada();
            } else if (texto.equals("x²")) {
                elevarAoQuadrado();
            } else if (texto.equals("Dark Mode")) {
                alternarTema();
            } else if (texto.equals(".")) {
                adicionarPonto();
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
