package ru.avalon.javapp.devj120;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;

public class MainFrame extends JFrame {
    long startTimeCounter = System.currentTimeMillis();
    private ArrayList<String> ar = new ArrayList<>();
    ArrayList<JLabel> listLabel = new ArrayList<>();

    public MainFrame() {
        // заголовок
        super("15 game");
        setBounds(300,200,600,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel p = new JPanel(new GridLayout(4, 4));
        JButton button = new JButton("Reset");
        Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
        button.setFont(button.getFont().deriveFont(24.f));
        button.setBorder(border);
        button.addActionListener(e -> pressedButton());

        // заполним массив и перемешаем
        for (int i = 0; i <= 15;i++ ) {
            if (i == 0) {
                continue;
            }
            ar.add(String.valueOf(i));
        }
        // вы можете закомментировать перемешивание массива, чтобы проверить победу в игре
        Collections.shuffle(ar);
        ar.add("");

        // создадим кнопки
        for (String curStr: ar) {
            if (curStr.equals("0")) continue;
            p.add(createLabel(curStr));
        }
        add(p, BorderLayout.CENTER);
        add(button, BorderLayout.SOUTH);
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text, SwingConstants.CENTER);

        listLabel.add(lbl);
        Border border = BorderFactory.createLineBorder(Color.BLACK, 3);
        lbl.setFont(lbl.getFont().deriveFont(24.f));
        lbl.setBorder(border);
        lbl.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

                // начинаем бежать только с индекса нажатого поля
                int countStart = 0;
                for (int i=0; i < listLabel.size();i++ ) {
                    if (ar.get(i) == lbl.getText()) {
                        countStart = i;
                    }
                }


                for (int i=countStart; i < listLabel.size();i++ ) {

                    if (ar.get(i).equals("")) break;

                    if (i == 0) {
                        if (ar.get(i+1).equals("") || ar.get(i+4).equals("")) {
                            pressed(lbl.getText());
                            lbl.setText("");
                            break;
                        }
                        break;
                    }

                    if (i == 1 || i == 2 || i == 3) {
                        if (ar.get(i-1).equals("") || ar.get(i+1).equals("") || ar.get(i+4).equals("")) {
                            pressed(lbl.getText());
                            lbl.setText("");
                            break;
                        }
                        break;
                    }

                    if (i >= 4 && i <= 11) {
                        if (ar.get(i-1).equals("") || ar.get(i-4).equals("") || ar.get(i+1).equals("") || ar.get(i+4).equals("")) {
                            pressed(lbl.getText());
                            lbl.setText("");
                            break;
                        }
                        break;
                    }

                    if (i == 12 || i == 13 || i == 14) {
                        if (ar.get(i-1).equals("") || ar.get(i+1).equals("") || ar.get(i-4).equals("")) {
                            pressed(lbl.getText());
                            lbl.setText("");
                            break;
                        }
                        break;
                    }

                    if (i == 15) {
                        if (ar.get(i-1).equals("") || ar.get(i-4).equals("")) {
                            pressed(lbl.getText());
                            lbl.setText("");
                            break;
                        }
                        break;
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        return lbl;
    }


    private void pressed(String digit) {
        ArrayList<String> arNew = new ArrayList<>();
        //System.out.println(Arrays.toString(ar.toArray()));
        for (int i = 0; i <ar.size(); i++) {
            if (ar.get(i).equals("")) {
                arNew.add(digit);
                continue;
            }

            if (ar.get(i).equals(digit)) {
                arNew.add("");
                continue;
            }
            arNew.add(ar.get(i));
        }
        ar = arNew;

        //System.out.println(Arrays.toString(arNew.toArray()));

        int i = 0;
        for (JLabel lbl: listLabel) {
            lbl.setText(ar.get(i++));
        }

        checkWin();
    }

    private void checkWin() {
        int count = 0;
        for (int i = 1; i < ar.size();i++ ) {
            if (ar.get(i-1).equals("")) continue;
            if (Integer.parseInt(ar.get(i-1)) == i )
                count++;
        }

        // если победа, то выводим результат
        if (count == ar.size()-1) {
            long endTimeCounter = System.currentTimeMillis();
            long rezCounter = endTimeCounter - startTimeCounter;

            int seconds = (int) (rezCounter / 1000) % 60 ;
            int minutes = (int) ((rezCounter / (1000*60)) % 60);
            int hours   = (int) ((rezCounter / (1000*60*60)) % 24);

            String res = String.format("%02d:%02d:%02d", hours, minutes, seconds);


            JOptionPane.showMessageDialog(null,"You're win!  Your play time is " + res);
            System.exit(0);
        }
    }

    private void pressedButton() {

        int res = JOptionPane.showConfirmDialog(null, "Are you sure?");

        if (res == 0) {
            // перемешаем
            Collections.shuffle(ar);

             // переместим пустоту в конец
               for (int i = 0; i < ar.size(); i++) {
                 if (ar.get(i).equals("")) {
                   ar.set(i, ar.get(ar.size() - 1));
                   ar.set(ar.size() - 1, "");
              }
            }

           // заполним поля
           int i=0;
            for (JLabel lbl: listLabel) {
                lbl.setText(ar.get(i));
                i++;
            }
        } else if (res == 2)
            System.exit(0);
    }

    public static void main(String[] args) {
        new MainFrame().setVisible(true);
    }
}
