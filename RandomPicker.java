import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyleConstants;
import javax.swing.text.SimpleAttributeSet;

/**
 * The RandomPicker class implements a GUI application that enables 
 * the user to input as many items for selection, and the application 
 * then selects one of these items randomly.
 *
 * @author Pathum Danthanarayana
 * @version 1.0 May 18, 2022
 */
public class RandomPicker implements ActionListener
{
    // Custom colours
    private Color bgColour = new Color(31, 34, 41);
    private Color bgColour2 = new Color(46, 50, 59);
    private Color accentColour = new Color(4, 122, 253);
    private Color fieldColour = new Color(128, 128, 128);
    
    // GUI components
    private JButton addItem;
    private JButton removeItem;
    private JButton pickItem;
    private JTextField itemField;
    private JTextField removeItemField;
    private JTextArea selectedItem;
    private JTextPane list;
    private Container contentPane;
    
    // ArrayList of String items
    private ArrayList<String> items;
    
    // Integer to keep track of the number of items
    private int itemNum;
    /**
     * Constructor for objects of class RandomPicker
     */
    public RandomPicker()
    {
        // Item number (start at 1)
        itemNum = 1;
        
        // Set up JFrame
        JFrame frame = new JFrame("RandomPicker");
        frame.setSize(500, 500);
        frame.setVisible(true);
        frame.setResizable(false);
        
        // Set up ContentPane
        contentPane = frame.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
        
        // Initialize ArrayList of items
        items = new ArrayList<String>();
        
        // Set up log
        prepareLog();
        // Set up addition panel
        prepareAddPanel();
        // Set up removal panel
        prepareRemovePanel();
        // Set up selection panel
        prepareSelectionPanel();
        // Set up pick panel
        preparePickPanel();
        
        // Pack components into frame
        frame.pack();
        // Set default close operation for JFrame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /**
     * The prepareLog method initializes and sets up the JPanel which acts as a log 
     * and outputs when an item is added and removed
     */
    private void prepareLog()
    {
        // JPanel for log
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(bgColour);
        headerPanel.setPreferredSize(new Dimension(500, 55));
        
        // JLabel for log (main title)
        JLabel appHeader = new JLabel("RandomPicker");
        appHeader.setFont(new Font("Roboto", Font.PLAIN, 30));
        appHeader.setForeground(new Color(255, 255, 255));
        
        // JTextPane for displaying log
        list = new JTextPane();
        list.setFont(new Font("Roboto", Font.PLAIN, 16));
        list.setForeground(new Color(255, 255, 255));
        list.setBackground(bgColour2);
        // Center text in JTextPane
        StyledDocument doc = list.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        
        // Add JLabel to JPanel
        headerPanel.add(appHeader);
        // Add JPanel to content pane
        contentPane.add(headerPanel);
        contentPane.add(list);
    }
    
    /**
     * The prepareAddPanel method initializes and sets up the JPanel which contains the 
     * JTextField and JButton, where the user can add an item
     */
    private void prepareAddPanel()
    {
        // JPanel for add
        JPanel additionPanel = new JPanel();
        additionPanel.setLayout(new BoxLayout(additionPanel, BoxLayout.PAGE_AXIS));
        additionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
        additionPanel.setBackground(bgColour2);
        
        // JButton to add items
        addItem = new JButton("Add item");
        addItem.addActionListener(this);
        addItem.setAlignmentX(Component.CENTER_ALIGNMENT);
        addItem.setBackground(accentColour);
        addItem.setForeground(new Color(255, 255, 255));
        addItem.setFont(new Font("Roboto", Font.PLAIN, 15));
        
        // JTextField to enter the item name
        itemField = new JTextField("Enter an item to add to the list...");
        itemField.setBackground(bgColour2);
        itemField.setForeground(fieldColour);
        itemField.setFont(new Font("Roboto", Font.PLAIN, 15));
        itemField.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Add JTextField to JPanel
        additionPanel.add(itemField);
        // Add JButton to JPanel
        additionPanel.add(addItem);
        // Add JPanel to content pane
        contentPane.add(additionPanel);
    }
    
    /**
     * The prepareRemovePanel method initializes and sets up the JPanel 
     * which contains the JTextField and JButton, where the user can remove an item
     */
    private void prepareRemovePanel()
    {
        // JPanel for remove
        JPanel removePanel = new JPanel();
        removePanel.setLayout(new BoxLayout(removePanel, BoxLayout.PAGE_AXIS));
        removePanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10)); 
        removePanel.setBackground(bgColour2);
        
        // JButton to remove an item
        removeItem = new JButton("Remove item");
        removeItem.addActionListener(this);
        removeItem.setAlignmentX(Component.CENTER_ALIGNMENT);
        removeItem.setBackground(accentColour);
        removeItem.setForeground(new Color(255, 255, 255));
        removeItem.setFont(new Font("Roboto", Font.PLAIN, 15));
        
        // JTextField to enter the item name
        removeItemField = new JTextField("Enter an item to remove from the list...");
        removeItemField.setBackground(bgColour2);
        removeItemField.setForeground(fieldColour);
        removeItemField.setFont(new Font("Roboto", Font.PLAIN, 15));
        removeItemField.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Add the JTextField to JPanel
        removePanel.add(removeItemField);
        // Add the JButton to JPanel
        removePanel.add(removeItem);
        // Add the JPanel to content pane
        contentPane.add(removePanel);
    }
    
    /**
     * The prepareSelectionPanel method initializes and sets up the JPanel 
     * which outputs and displays the randomly selected item
     */
    private void prepareSelectionPanel()
    {
        // JPanel for selection
        JPanel selectionPanel = new JPanel();
        selectionPanel.setBackground(bgColour2);
        // Add spacing
        selectionPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        
        // JLabel to help clearly indicate selected item
        JLabel selectionLabel = new JLabel("Selected item:");
        selectionLabel.setFont(new Font("Roboto", Font.BOLD, 20));
        selectionLabel.setForeground(new Color(255, 255, 255));
        
        // JTextArea to display the selected item
        selectedItem = new JTextArea();
        selectedItem.setFont(new Font("Roboto", Font.PLAIN, 20));
        selectedItem.setBackground(bgColour2);
        selectedItem.setForeground(new Color(255, 255, 255));
        
        // Add the JLabel to JPanel
        selectionPanel.add(selectionLabel);
        // Add the JTextArea to JPanel
        selectionPanel.add(selectedItem);
        // Add the JPanel to content pane
        contentPane.add(selectionPanel);
    }
    
    /**
     * The preparePickPanel method initializes and sets up the JPanel which 
     * contains a JButton that allows the user to randomly select an item
     */
    private void preparePickPanel()
    {
        // JPanel for picking
        JPanel pickPanel = new JPanel();
        pickPanel.setBackground(bgColour2);
        
        // JButton to randomly select an item
        pickItem = new JButton("Pick item");
        pickItem.addActionListener(this);
        pickItem.setAlignmentX(Component.CENTER_ALIGNMENT);
        pickItem.setBackground(accentColour);
        pickItem.setForeground(new Color(255, 255, 255));
        pickItem.setFont(new Font("Roboto", Font.PLAIN, 15));
        
        // Add JButton to JPanel
        pickPanel.add(pickItem);
        // Add JPanel to content pane
        contentPane.add(pickPanel);
    }
    
    /**
     * The actionPerformed method performs certain instructions based 
     * on the ActionEvent that is listened to
     */
    public void actionPerformed (ActionEvent e)
    {
        Object o = e.getSource();
        
        // Verify that the 'Add item' button is clicked by the user
        if (o == addItem)
        {
            // Clear selected item
            selectedItem.setText(null);
            // Retrieve the text entered into the JTextField
            String getItem = itemField.getText();
            // Add the String to the ArrayList
            items.add(getItem);
            // Inform the user that the item was added
            list.setText("Item # " + itemNum + ": " + getItem + " added");
            // Increment the item count by 1
            itemNum += 1;
        }
        // Verify that the 'Remove item' button is clicked by the user
        else if (o == removeItem)
        {
            // Clear selected item
            selectedItem.setText(null);
            // Retrieve the text entered into the JTextField
            String getItem = removeItemField.getText();
            // Search through ArrayList for the specified item
            for (String itemElem: items)
            {
                // Verify that the specified item is found in the ArrayList
                if (itemElem.equals(getItem))
                {
                    // Remove the item from the ArrayList
                    items.remove(itemElem);
                    // Inform the user that the item was removed
                    list.setText(itemElem + " removed");
                    // Decrement the item count by 1
                    itemNum -= 1;
                    // Exit the loop
                    return;
                }
            }
        }
        // Verify that the 'Pick item' button was clicked by the user
        else if (o == pickItem)
        {
            // Verify that there is at least 1 item added
            if (items.size() <= 0)
            {
                // Inform the user that at least 1 item must be added for random selection
                list.setText("Please add at least one item first");
                // Exit the if statement
                return;
            }
            else
            {
                // Create new instance of Random
                Random randomGen = new Random();
                // Generate a random valid index for the ArrayList
                int index = randomGen.nextInt(items.size());
                // Display the item found at the randomly selected index
                selectedItem.setText(items.get(index));
            }
        }
    }
}