import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class MainGUIWindow extends JFrame {
    private JFrame mainFrame;
    private JFrame notesFrame;
    private JTextArea noteTextArea;
    private JLabel title;
    private Map<String, String> notesMap;
    private JPanel topPanel;
    private JPanel centerPanel;
    private JPanel openTopPanel;

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
        addButton.setBounds(130, 150, 150, 100);
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
        viewButton.setBounds(390, 150, 150, 100);
        centerPanel.add(viewButton, BorderLayout.EAST);

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainGUIWindow();
            }
        });
    }
}