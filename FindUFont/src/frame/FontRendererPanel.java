package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public 	class FontRendererPanel extends JPanel {
	private String fontName, viewLayout = "list";
	private JLabel fontLabel;
	private JLabel label;
	private int number;
	private Color stripeBackground = new Color(223, 231, 242);
	private final static int RESERVE_CNT = 5;
	static String panGramma = "AaBbCcDdEe";

	protected ImageIcon createImageIcon(String path, String description) {
		java.net.URL imgURL = getClass().getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	public JPanel createWidget() {
		JPanel widget = new JPanel();
		ImageIcon fav_icon = createImageIcon("images/25291.png",
				"favourite type");
		ImageIcon print_icon = createImageIcon("images/25383.png",
				"favourite type");
		fav_icon = new ImageIcon(fav_icon.getImage().getScaledInstance(30,
				20, java.awt.Image.SCALE_SMOOTH));
		print_icon = new ImageIcon(print_icon.getImage().getScaledInstance(
				30, 20, java.awt.Image.SCALE_SMOOTH));
		JButton fav_btn = new JButton(fav_icon), print_btn = new JButton(
				print_icon);
		fontLabel = new JLabel(fontName);
		fav_btn.setOpaque(false);
		fav_btn.setContentAreaFilled(false);
		fav_btn.setBorderPainted(false);
		print_btn.setOpaque(false);
		print_btn.setContentAreaFilled(false);
		print_btn.setBorderPainted(false);
		print_btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				exportFile();
			}
		});

		widget.setLayout(new FlowLayout(FlowLayout.TRAILING));
		widget.add(fontLabel);
		widget.add(fav_btn);
		widget.add(print_btn);
		widget.setOpaque(false);
		return widget;
	}

	public FontRendererPanel(String fontName) {
		this.fontName = fontName;
		this.setLayout(new BorderLayout());
		this.setBorder(new TitledBorder(""));
		label = new JLabel();
		label.setFont(new Font(fontName, Font.PLAIN, 48));
		label.setText(panGramma);
		label.setOpaque(false);
		label.setMaximumSize(new Dimension(50, 50));
		label.setMinimumSize(new Dimension(50, 50));
		label.setPreferredSize(new Dimension(50, 50));
		this.add(label, BorderLayout.NORTH);
		this.add(createWidget(), BorderLayout.SOUTH);
	}

	public void changeText(String text) {
		if (viewLayout.equalsIgnoreCase("List")) {
			label.setText(text);
		} else if (viewLayout.equalsIgnoreCase("Grid")) {
			label.setText(text.substring(0, Math.min(RESERVE_CNT, text.length())));
		}
	}

	public void changeFontViewLayout(String cmd) {
		if (cmd.equalsIgnoreCase("List")) {
			fontLabel.setVisible(true);
			label.setFont(new Font(fontName, Font.PLAIN, 48));
			label.setHorizontalAlignment(SwingConstants.LEADING);
		} else if (cmd.equalsIgnoreCase("Grid")) {
			label.setText(label.getText().substring(0, Math.min(RESERVE_CNT, label.getText().length())));
			label.setFont(new Font(fontName, Font.PLAIN, 64));
			label.setHorizontalAlignment(SwingConstants.CENTER);
			fontLabel.setVisible(false);
		}
		viewLayout = cmd;
	}

	public void setStripe(int number) {
		this.number = number;
		if (number % 2 == 0)
			this.setBackground(stripeBackground);
	}

	private File exportDirectory = new java.io.File(".");

	private void exportFile() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(exportDirectory);
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"Images", "png");
		chooser.setSelectedFile(new File("test.png"));
		chooser.setFileFilter(filter);
		int returnVal = chooser.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			exportDirectory = chooser.getCurrentDirectory();
			File file = chooser.getSelectedFile();
			try {
				BufferedImage img = new BufferedImage(label.getWidth(),
						label.getHeight(), BufferedImage.TYPE_INT_ARGB);
				Graphics2D g2d = img.createGraphics();
				label.printAll(g2d);
				g2d.dispose();
				ImageIO.write(img, "png", file);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage(),
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
