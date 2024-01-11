import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MengenEingabeUI extends JFrame {
    private JTextField inputField;
    private JLabel savedValueLabel;
    private boolean valueAccepted = false;

    public MengenEingabeUI() {
        setTitle("Mengeneingabe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());

        JPanel numpadPanel = new JPanel(new GridLayout(4, 3));
        inputField = new JTextField();
        savedValueLabel = new JLabel("Eingegebener Wert: ");

        String[] numpadButtons = {"7", "8", "9", "4", "5", "6", "1", "2", "3", "0", "del", ","};
        for (String buttonLabel : numpadButtons) {
            JButton button = new JButton(buttonLabel);
            button.addActionListener(new NumpadActionListener(buttonLabel));
            numpadPanel.add(button);
        }

        JButton clearButton = new JButton("clear");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputField.setText("");
                valueAccepted = false;
                savedValueLabel.setText("Eingegebener Wert: ");
            }
        });

        JButton uebernehmenButton = new JButton("Übernehmen");
        uebernehmenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputField.getText().replace(',', '.');
                try {
                    double value = Double.parseDouble(input);
                    if (value > 0) {
                        savedValueLabel.setText("Eingegebener Wert: " + input);
                        valueAccepted = true;
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Fehler beim Parsen des Wertes: " + input);
                }
            }
        });

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1));
        buttonPanel.add(clearButton);
        buttonPanel.add(uebernehmenButton);

        add(inputField, BorderLayout.NORTH);
        add(numpadPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.EAST);
        add(savedValueLabel, BorderLayout.SOUTH);
    }

    private class NumpadActionListener implements ActionListener {
        private String buttonLabel;

        public NumpadActionListener(String label) {
            this.buttonLabel = label;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (valueAccepted && !buttonLabel.equals(",") && !inputField.getText().contains(",")) {
                inputField.setText("");
                valueAccepted = false;
            }

            if (buttonLabel.equals(",")) {
                if (!inputField.getText().contains(",")) {
                    inputField.setText(inputField.getText() + buttonLabel);
                }
            } else {
                String[] parts = inputField.getText().split(",");
                if (parts.length <= 1) {
                    inputField.setText(inputField.getText() + buttonLabel);
                } else {
                    String decimalPart = parts[1];
                    if (decimalPart.length() < 3) {
                        inputField.setText(inputField.getText() + buttonLabel);
                    }
                }
            }
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MengenEingabeUI().setVisible(true);
            }
        });
    }
}
