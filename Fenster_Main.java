import java.awt.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Fenster_Main {
	public static void main(String[] args) {
		Taschenrechner_GUI frame = new Taschenrechner_GUI();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}

class Taschenrechner_GUI extends JFrame {
	public Taschenrechner_GUI() {
		setTitle("Taschenrechner");
		TaschenrechnerPanel panel = new TaschenrechnerPanel();
		add(panel);
		pack();
	}
}

class TaschenrechnerPanel extends JPanel {
	public TaschenrechnerPanel() {
		setLayout(new BorderLayout());

		result = 0;
		lastCommand = "=";
		start = true;

		display = new JButton("0");
		display.setEnabled(false);
		add(display, BorderLayout.NORTH);

		ActionListener insert = new InsertAction();
		ActionListener command = new CommandAction();

		panel = new JPanel();
		panel.setLayout(new GridLayout(4, 4));

		Map<String, JButton> buttons = new HashMap<>();

		JButton b1 = addButton("1", insert);
		b1.setBackground(Color.GREEN);
		JButton b2 = addButton("2", insert);
		b2.setBackground(Color.CYAN);
		JButton b3 = addButton("3", insert);
		b3.setBackground(Color.lightGray);
		JButton bGeteilt = addButton("/", command);
		bGeteilt.setBackground(Color.PINK);
		JButton b4 = addButton("4", insert);
		b4.setBackground(Color.YELLOW);
		JButton b5 = addButton("5", insert);
		b5.setBackground(Color.red);
		JButton b6 = addButton("6", insert);
		b6.setBackground(Color.BLUE);
		JButton bMal = addButton("*", command);
		bMal.setBackground(Color.LIGHT_GRAY);
		JButton b7 = addButton("7", insert);
		b7.setBackground(Color.magenta);
		JButton b8 = addButton("8", insert);
		b8.setBackground(Color.ORANGE);
		JButton b9 = addButton("9", insert);
		b9.setBackground(Color.RED);
		JButton bMinus = addButton("-", command);
		bMinus.setBackground(Color.PINK);
		JButton b0 = addButton("0", insert);
		b0.setBackground(Color.ORANGE);
		JButton bKomma = addButton(".", insert);
		bKomma.setBackground(Color.YELLOW);
		JButton bGleich = addButton("=", command);
		bGleich.setBackground(Color.GREEN);
		JButton bPlus = addButton("+", command);
		bPlus.setBackground(Color.MAGENTA);

		buttons.put("1", b1);
		buttons.put("2", b2);
		buttons.put("3", b3);
		buttons.put("/", bGeteilt);
		buttons.put("4", b4);
		buttons.put("5", b5);
		buttons.put("6", b6);
		buttons.put("*", bMal);
		buttons.put("7", b7);
		buttons.put("8", b8);
		buttons.put("9", b9);
		buttons.put("-", bMinus);
		buttons.put("0", b0);
		buttons.put(",", bKomma);
		buttons.put("=", bGleich);
		buttons.put("+", bPlus);

		KeyListener keyListener = new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// System.out.println(e.getSource());
	
				if (e.getSource() instanceof JButton) {

					// JButton button = (JButton)e.getSource();
					// System.out.println(button.getText());

					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						buttons.get("=").doClick();
					} else {
						char keyChar = e.getKeyChar();
						String str = "" + keyChar;

						if (buttons.containsKey(str)) {
							JButton jButton = buttons.get(str);

							jButton.doClick();
						}
					}

					// System.out.println(str);
					// if (str.equals("*")) {
					//
					// } else if (str.equals("=")) {
					// } else if (str.equals("+")) {
					//
					// System.err.println("++++");
					// bPlus.doClick();
					// return;
					//
					// } else if (str.equals("/")) {
					// } else if (str.equals("-")) {
					// } else {
					// try {
					// Integer valueOf = Integer.valueOf(str);
					//
					// } catch (Exception e1) {
					// // TODO Auto-generated catch block
					// e1.printStackTrace();
					// }
					// }
				}

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
	            if(e.getKeyCode() == KeyEvent.VK_ENTER)
	            {
					JButton jButton = buttons.get("=");

					jButton.doClick();
	            }

			}
		};

		b1.addKeyListener(keyListener);
		b2.addKeyListener(keyListener);

		b3.addKeyListener(keyListener);
		bGeteilt.addKeyListener(keyListener);

		b4.addKeyListener(keyListener);
		b5.addKeyListener(keyListener);
		b6.addKeyListener(keyListener);
		bMal.addKeyListener(keyListener);

		b7.addKeyListener(keyListener);
		b8.addKeyListener(keyListener);
		b9.addKeyListener(keyListener);
		bMinus.addKeyListener(keyListener);

		b0.addKeyListener(keyListener);
		bKomma.addKeyListener(keyListener);
		bGleich.addKeyListener(keyListener);
		bPlus.addKeyListener(keyListener);

		add(panel, BorderLayout.CENTER);
	}

	private JButton addButton(String label, ActionListener listener) {
		JButton button = new JButton(label);
		button.addActionListener(listener);
		panel.add(button);

		return button;
	}

	private class InsertAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			String input = event.getActionCommand();
			if (start) {
				display.setText("");
				start = false;
			}
			display.setText(display.getText() + input);
		}
	}

	private class CommandAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			String command = event.getActionCommand();

			rechne(command);
		}

		private void rechne(String command) {
			if (start) {
				if (command.equals("-")) {
					display.setText(command);
					start = false;
				} else
					lastCommand = command;
			} else {
				calculate(Double.parseDouble(display.getText()));
				lastCommand = command;
				start = true;
			}
		}

	}

	public void calculate(double x) {
		if (lastCommand.equals("+"))
			result += x;
		else if (lastCommand.equals("-"))
			result -= x;
		else if (lastCommand.equals("*"))
			result *= x;
		else if (lastCommand.equals("/"))
			result /= x;
		else if (lastCommand.equals("="))
			result = x;
		display.setText("" + result);
	}

	private JButton display;
	private JPanel panel;
	private double result;
	private String lastCommand;
	private boolean start;

}
