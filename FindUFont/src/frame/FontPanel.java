package frame;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.List;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.io.*;

public class FontPanel extends JPanel {
	JList<String> fontList;
	DefaultListModel<String> listModel;
	JTextField showDemoFont;
	JPanel fontViewPanel;
	JComboBox<String> fontSelector;
	ArrayList<FontRendererPanel> displayList;

	class TextFieldWithPrompt extends JTextField {
		private Color placeholderForeground = new Color(160, 160, 160);
		private String textWrittenIn = "@type you want";

		@Override
		protected void paintComponent(java.awt.Graphics g) {
			super.paintComponent(g);

			if (getText().isEmpty()
					&& !(FocusManager.getCurrentKeyboardFocusManager()
							.getFocusOwner() == this)) {
				Graphics2D g2 = (Graphics2D) g.create();
				setForeground(placeholderForeground);
				g2.setFont(new Font(getFont().getFamily(), Font.ITALIC,
						getFont().getSize()));
				g2.drawString(textWrittenIn, 10, 20);
				g2.dispose();
			} else {
				setForeground(Color.BLACK);
			}
		}
	}

	public FontPanel() {
		this.setLayout(new BorderLayout());

		this.showDemoFont = new TextFieldWithPrompt();
		showDemoFont.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				warn();
			}

			public void removeUpdate(DocumentEvent e) {
				warn();
			}

			public void insertUpdate(DocumentEvent e) {
				warn();
			}

			public void warn() {
				if (!showDemoFont.getText().isEmpty()) {
					String t = showDemoFont.getText();
					for (FontRendererPanel p : displayList) {
						p.changeText(t);
					}
				}
			}
		});
		JPanel leftPanel;
		leftPanel = new JPanel();
		leftPanel.setBorder(new TitledBorder(""));
		leftPanel.setLayout(new GridLayout(1, 1));
		fontViewPanel = this.getAllFontViewer();
		JScrollPane sc = new JScrollPane(fontViewPanel);
		sc.getVerticalScrollBar().setUnitIncrement(32);
		leftPanel.add(sc);
		this.add(leftPanel, BorderLayout.CENTER);
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout());
		southPanel.add(createModeChooser(), BorderLayout.EAST);
		southPanel.add(showDemoFont, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);
		this.setVisible(true);
	}

	protected ImageIcon createImageIcon(String path, String description) {
		java.net.URL imgURL = getClass().getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	public void changeFontViewLayout(String cmd) {
		if (cmd.equalsIgnoreCase("List")) {
			fontViewPanel.setLayout(new GridLayout(displayList.size(), 1));
			this.validate();
		} else if (cmd.equalsIgnoreCase("Grid")) {
			fontViewPanel.setLayout(new GridBagLayout());
			fontViewPanel.removeAll();
			GridBagConstraints gbc = new GridBagConstraints();

			int column = 5, size = 0;
			gbc.anchor = GridBagConstraints.FIRST_LINE_START;
			gbc.fill = GridBagConstraints.BOTH;
			for (FontRendererPanel p : displayList) {
				gbc.gridx = size % column;
				gbc.gridy = size / column;
				gbc.gridheight = 1;
				gbc.weightx = 1.0;
				gbc.weighty = 0;
				fontViewPanel.add(p, gbc);
				size++;
			}
			this.validate();
		}
		for (FontRendererPanel p : displayList) {
			p.changeFontViewLayout(cmd);
		}
	}

	public JPanel createModeChooser() {
		JPanel panel = new JPanel();
		ImageIcon list_icon = createImageIcon("images/25181.png",
				"favourite type");
		ImageIcon grid_icon = createImageIcon("images/25604.png",
				"favourite type");
		list_icon = new ImageIcon(list_icon.getImage().getScaledInstance(30,
				20, java.awt.Image.SCALE_SMOOTH));
		grid_icon = new ImageIcon(grid_icon.getImage().getScaledInstance(30,
				20, java.awt.Image.SCALE_SMOOTH));

		JRadioButton list_btn = new JRadioButton("List"), grid_btn = new JRadioButton(
				"Grid");
		list_btn.setIcon(list_icon);
		list_btn.setActionCommand("List");
		grid_btn.setIcon(grid_icon);
		grid_btn.setActionCommand("Grid");
		ActionListener changeLayoutActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeFontViewLayout(e.getActionCommand());
			}
		};
		list_btn.addActionListener(changeLayoutActionListener);
		grid_btn.addActionListener(changeLayoutActionListener);
		panel.setLayout(new FlowLayout());
		panel.add(list_btn);
		panel.add(grid_btn);

		ButtonGroup group = new ButtonGroup();
		group.add(list_btn);
		group.add(grid_btn);
		list_btn.setSelected(true);
		return panel;
	}

	public JPanel getAllFontViewer() {
		displayList = new ArrayList<FontRendererPanel>();
		GraphicsEnvironment ge;
		ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String[] fontNames = ge.getAvailableFontFamilyNames();

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(fontNames.length, 1));
		for (String fontName : fontNames) {
			FontRendererPanel renderer = new FontRendererPanel(fontName);
			renderer.setStripe(displayList.size());
			panel.add(renderer);
			displayList.add(renderer);
		}
		return panel;
	}
}