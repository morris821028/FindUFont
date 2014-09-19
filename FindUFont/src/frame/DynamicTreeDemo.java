package frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;
 
public class DynamicTreeDemo extends JPanel {
     
    private DynamicTree treePanel;
 
    public DynamicTreeDemo() {
        super(new BorderLayout());
         
        //Create the components.
        treePanel = new DynamicTree();
        populateTree(treePanel);
 
 
        //Lay everything out.
        treePanel.setPreferredSize(new Dimension(300, 150));
        add(treePanel, BorderLayout.CENTER);
    }
 
    public void populateTree(DynamicTree treePanel) {
        String p1Name = new String("Art");
        String p2Name = new String("Doc");
        String c1Name = new String("Child 1");
        String c2Name = new String("Child 2");
 
        DefaultMutableTreeNode p1, p2;
 
        p1 = treePanel.addObject(null, p1Name);
        p2 = treePanel.addObject(null, p2Name);
 
        treePanel.addObject(p1, c1Name);
        treePanel.addObject(p1, c2Name);
 
        treePanel.addObject(p2, c1Name);
        treePanel.addObject(p2, c2Name);
        treePanel.unfold();
    }
}