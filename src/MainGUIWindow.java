import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class MainGUIWindow extends JFrame {
    private JFrame mainFrame;
    private JFrame notesFrame;
    private JFrame clearFrame;
    private JTextArea noteTextArea;
    private JLabel title;
    private JLabel clearNotif;
    private Map<String, String> notesMap;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel centerPanel;
    private JPanel openTopPanel;
    private JPanel clearCenter;
    private JList<String> notesList;
    private DefaultListModel<String> notes;

    public MainGUIWindow() {
        notesMap = new HashMap<>();

        // Main GUI window
        mainFrame = new JFrame("Note Taking Application");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout(8, 6));

        topPanel = new JPanel();
        topPanel.setBackground(new Color(0));
        bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(255, 239, 213));
        bottomPanel.setLayout(new FlowLayout());
        mainFrame.add(topPanel, BorderLayout.NORTH);
        mainFrame.add(bottomPanel, BorderLayout.SOUTH);

        centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BorderLayout());
        mainFrame.add(centerPanel, BorderLayout.CENTER);

        title = new JLabel();
        title.setText("Note Taking 101");
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setForeground(new Color(255,255,255));
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 30));

        JButton addButton = new JButton("Add Note");
        addButton.setFont(new Font("Comic Sans MS", Font.BOLD, 19));
        addButton.setBackground(new Color(252, 181, 172));
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openNotesWindow();
            }
        });
        addButton.setBounds(130, 100, 150, 100);
        bottomPanel.add(addButton);
        topPanel.add(title, BorderLayout.NORTH);

        JButton clearButton = new JButton();
        clearButton.setText("Clear Notes");
        clearButton.setFont(new Font("Comic Sans MS", Font.BOLD, 19));
        clearButton.setBackground(new Color(135, 206, 235));
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearNote();
            }
        });
        clearButton.setBounds(260, 220, 150, 100);
        bottomPanel.add(clearButton);

        notes = new DefaultListModel<>();
        notesList = new JList<String>(notes);
        JScrollPane scrollList = new JScrollPane(notesList);
        notesList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedIndex = notesList.getSelectedIndex();
                    if (selectedIndex != -1) {
                        String selectedKey = notesList.getSelectedValue();
                        String selectedNoteContent = notesMap.get(selectedKey);
                        JOptionPane.showMessageDialog(mainFrame, selectedNoteContent, selectedKey, JOptionPane.PLAIN_MESSAGE);
                    }
                }
            }
        });
        centerPanel.add(scrollList, BorderLayout.CENTER);

        mainFrame.setSize(700, 500);
        mainFrame.getContentPane().setBackground(new Color(251, 231, 198));
        mainFrame.setVisible(true);
    }

    private void openNotesWindow() {
        // Notes GUI window
        notesFrame = new JFrame("Notes");
        notesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        notesFrame.setLayout(new BorderLayout());

        noteTextArea = new JTextArea();
        notesFrame.add(noteTextArea, BorderLayout.CENTER);

        openTopPanel = new JPanel();
        openTopPanel.setPreferredSize(new Dimension(400, 50));
        openTopPanel.setBackground(new Color(153, 204, 255));
        openTopPanel.setLayout(new FlowLayout());
        notesFrame.add(openTopPanel, BorderLayout.NORTH);

        JButton colorButton = new JButton("Change Color");
        colorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color selectedColor = JColorChooser.showDialog(notesFrame, "Select Text Color", Color.BLACK);
                noteTextArea.setForeground(selectedColor);
            }
        });
        JButton sizeButton = new JButton("Change Size");
        sizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentSize = noteTextArea.getFont().getSize();
                int newSize = currentSize + 2;
                Font currentFont = noteTextArea.getFont();
                Font newFont = currentFont.deriveFont((float) newSize);
                noteTextArea.setFont(newFont);
            }
        });
        openTopPanel.add(colorButton);
        openTopPanel.add(sizeButton);
        JButton saveButton = new JButton("Save Note");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveNote();
            }
        });
        notesFrame.add(saveButton, BorderLayout.SOUTH);

        notesFrame.pack();
        notesFrame.setSize(400, 400);
        notesFrame.setVisible(true);
    }

    private void saveNote() {
        String noteTitle = JOptionPane.showInputDialog(mainFrame, "Enter a title for the note:");
        String noteContent = noteTextArea.getText();
        if ((noteTitle != null && !noteTitle.trim().isEmpty()) && (noteContent != null && !noteContent.trim().isEmpty())) {
            notesMap.put(noteTitle, noteContent);
            notes.addElement(noteTitle);
        }
        notesFrame.dispose();
    }

    private void clearNote() {
        clearFrame = new JFrame();
        clearFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        clearFrame.setLayout(new BorderLayout());

        clearNotif = new JLabel();
        clearNotif.setText("Are you sure that you want to clear?");
        clearNotif.setHorizontalAlignment(JLabel.CENTER);
        clearNotif.setFont(new Font("Comic Sans MS", Font.BOLD, 19));

        clearCenter = new JPanel();
        clearCenter.setOpaque(false);
        clearCenter.setLayout(null);

        JButton confirmButton = new JButton("Clear");
        confirmButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
        confirmButton.setBackground(new Color(200, 179, 144));
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                notesMap.clear();
                notes.clear();
                clearFrame.dispose();
            }
        });
        confirmButton.setBounds(40, 105, 120, 80);

        JButton denyButton = new JButton("Don't Clear");
        denyButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
        denyButton.setBackground(new Color(1, 109, 109));
        denyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearFrame.dispose();
            }
        });
        denyButton.setBounds(220, 105, 120, 80);

        clearFrame.add(clearNotif, BorderLayout.NORTH);
        clearFrame.add(clearCenter, BorderLayout.CENTER);
        clearCenter.add(confirmButton);
        clearCenter.add(denyButton);

        clearFrame.pack();
        clearFrame.setSize(400, 400);
        clearFrame.getContentPane().setBackground(new Color(227, 221, 211));
        clearFrame.setVisible(true);
    }
}