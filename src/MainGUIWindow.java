import javax.swing.*;
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
    private JPanel centerPanel;
    private JPanel openTopPanel;
    private JPanel clearCenter;

    public MainGUIWindow() {
        notesMap = new HashMap<>();

        // Main GUI window
        mainFrame = new JFrame("Note Taking Application");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout(8, 6));

        topPanel = new JPanel();
        topPanel.setBackground(new Color(0));
        mainFrame.add(topPanel, BorderLayout.NORTH);

        centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(null);
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
        centerPanel.add(addButton, BorderLayout.WEST);
        topPanel.add(title, BorderLayout.NORTH);

        JButton viewButton = new JButton();
        viewButton.setText("View Notes");
        viewButton.setFont(new Font("Comic Sans MS", Font.BOLD, 19));
        viewButton.setBackground(new Color(177, 216, 183));
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewNotes();
            }
        });
        viewButton.setBounds(390, 100, 150, 100);
        centerPanel.add(viewButton, BorderLayout.EAST);

        JButton clearButton = new JButton();
        clearButton.setText("Clear Notes");
        clearButton.setFont(new Font("Comic Sans MS", Font.BOLD, 19));
        clearButton.setBackground(Color.BLUE);
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearNote();
            }
        });
        clearButton.setBounds(260, 220, 150, 100);
        centerPanel.add(clearButton, BorderLayout.SOUTH);

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

        JButton colorButton = new JButton("Color");
        colorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Buttons.setColor();
            }
        });
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

    private void viewNotes() {
        String[] noteTitles = notesMap.keySet().toArray(new String[0]);
        if (noteTitles.length == 0) {
            JOptionPane.showMessageDialog(mainFrame, "No notes available.", "View Notes", JOptionPane.INFORMATION_MESSAGE);
        } else {
            String selectedNoteTitle = (String) JOptionPane.showInputDialog(mainFrame, "Select a note to view:", "View Notes",
                    JOptionPane.QUESTION_MESSAGE, null, noteTitles, noteTitles[0]);

            if (selectedNoteTitle != null) {
                String selectedNoteContent = notesMap.get(selectedNoteTitle);
                JOptionPane.showMessageDialog(mainFrame, selectedNoteContent, selectedNoteTitle, JOptionPane.PLAIN_MESSAGE);
            }
        }
    }

    private void saveNote() {
        String noteTitle = JOptionPane.showInputDialog(mainFrame, "Enter a title for the note:");
        String noteContent = noteTextArea.getText();
        notesMap.put(noteTitle, noteContent);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainGUIWindow();
            }
        });
    }
}