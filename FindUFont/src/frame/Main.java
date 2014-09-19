package frame;

import java.awt.BorderLayout;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

public class Main extends JFrame {
	public Main() {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
			UIManager.put("Tree.rendererFillBackground", false); 
		} catch (Exception e) {
			// If Nimbus is not available, you can set the GUI to another look
			// and feel.
		}
		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenuItem item = new JMenuItem("");
		file.add(item);
		menuBar.add(file);
		JMenu edit = new JMenu("Edit");
		menuBar.add(edit);
		JMenu setting = new JMenu("Setting");
		menuBar.add(setting);
		JMenu about = new JMenu("About");
		menuBar.add(about);
		this.setJMenuBar(menuBar);
		this.setTitle("Find U Font");
		this.setSize(920, 720);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				new DynamicTreeDemo(), new FontPanel());
		splitPane.setDividerLocation(150);
		this.add(splitPane, BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		try {
			System.setProperty("apple.laf.useScreenMenuBar", "true");
			System.setProperty(
					"com.apple.mrj.application.apple.menu.about.name", "Test");
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: " + e.getMessage());
		} catch (InstantiationException e) {
			System.out.println("InstantiationException: " + e.getMessage());
		} catch (IllegalAccessException e) {
			System.out.println("IllegalAccessException: " + e.getMessage());
		} catch (UnsupportedLookAndFeelException e) {
			System.out.println("UnsupportedLookAndFeelException: "
					+ e.getMessage());
		}
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Main f = new Main();
				f.setVisible(true);
			}
		});
	}
}
