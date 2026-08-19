import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class StudentGradeCalculator extends JFrame {

    // =========================================================
    // COLORS
    // =========================================================

    private static final Color BG = new Color(245, 247, 251);
    private static final Color WHITE = Color.WHITE;
    private static final Color PRIMARY = new Color(79, 70, 229);
    private static final Color PRIMARY_DARK = new Color(67, 56, 202);
    private static final Color TEXT = new Color(31, 41, 55);
    private static final Color SECONDARY = new Color(107, 114, 128);
    private static final Color BORDER = new Color(229, 231, 235);
    private static final Color SUCCESS = new Color(16, 185, 129);
    private static final Color WARNING = new Color(245, 158, 11);
    private static final Color DANGER = new Color(239, 68, 68);
    private static final Color LIGHT_PURPLE = new Color(238, 242, 255);

    // =========================================================
    // COMPONENTS
    // =========================================================

    private JTextField studentNameField;
    private JPanel subjectsPanel;
    private JPanel resultContent;

    private final List<SubjectRow> subjectRows = new ArrayList<>();

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public StudentGradeCalculator() {

        setTitle("Student Grade Calculator");

        setSize(1200, 760);
        setMinimumSize(new Dimension(1050, 680));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createUI();

        // Default subjects
        addSubject("Mathematics");
        addSubject("Physics");
        addSubject("Programming");

        setVisible(true);
    }

    // =========================================================
    // CREATE MAIN UI
    // =========================================================

    private void createUI() {

        JPanel mainPanel = new JPanel(new BorderLayout());

        mainPanel.setBackground(BG);

        mainPanel.setBorder(
                new EmptyBorder(25, 30, 25, 30)
        );

        mainPanel.add(
                createHeader(),
                BorderLayout.NORTH
        );

        JPanel content = new JPanel(
                new GridLayout(1, 2, 22, 0)
        );

        content.setOpaque(false);

        content.setBorder(
                new EmptyBorder(20, 0, 0, 0)
        );

        content.add(createInputCard());
        content.add(createResultCard());

        mainPanel.add(
                content,
                BorderLayout.CENTER
        );

        setContentPane(mainPanel);
    }

    // =========================================================
    // HEADER
    // =========================================================

    private JPanel createHeader() {

        JPanel header = new JPanel(
                new BorderLayout()
        );

        header.setOpaque(false);

        JPanel titlePanel = new JPanel();

        titlePanel.setOpaque(false);

        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel title = new JLabel(
                "Student Grade Calculator"
        );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        30
                )
        );

        title.setForeground(TEXT);

        JLabel subtitle = new JLabel(
                "Analyze your academic performance instantly"
        );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        subtitle.setForeground(SECONDARY);

        titlePanel.add(title);

        titlePanel.add(
                Box.createVerticalStrut(5)
        );

        titlePanel.add(subtitle);

        header.add(
                titlePanel,
                BorderLayout.WEST
        );

        return header;
    }

    // =========================================================
    // INPUT CARD
    // =========================================================

    private JPanel createInputCard() {

        RoundedPanel card = new RoundedPanel(
                22,
                WHITE
        );

        card.setLayout(
                new BorderLayout(0, 15)
        );

        card.setBorder(
                new EmptyBorder(
                        22,
                        22,
                        22,
                        22
                )
        );

        // Student Name

        JPanel namePanel = new JPanel(
                new BorderLayout(0, 8)
        );

        namePanel.setOpaque(false);

        JLabel nameLabel = new JLabel(
                "Student Name"
        );

        nameLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        nameLabel.setForeground(TEXT);

        studentNameField = createTextField();

        studentNameField.setToolTipText(
                "Enter student name"
        );

        namePanel.add(
                nameLabel,
                BorderLayout.NORTH
        );

        namePanel.add(
                studentNameField,
                BorderLayout.CENTER
        );

        // Subject Header

        JPanel subjectHeader = new JPanel(
                new BorderLayout()
        );

        subjectHeader.setOpaque(false);

        JLabel subjectTitle = new JLabel(
                "Subjects & Marks"
        );

        subjectTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        17
                )
        );

        subjectTitle.setForeground(TEXT);

        JLabel markInfo = new JLabel(
                "Marks: 0 - 100"
        );

        markInfo.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        markInfo.setForeground(SECONDARY);

        subjectHeader.add(
                subjectTitle,
                BorderLayout.WEST
        );

        subjectHeader.add(
                markInfo,
                BorderLayout.EAST
        );

        // Subjects Panel

        subjectsPanel = new JPanel();

        subjectsPanel.setOpaque(false);

        subjectsPanel.setLayout(
                new BoxLayout(
                        subjectsPanel,
                        BoxLayout.Y_AXIS
                )
        );

        JScrollPane scrollPane = new JScrollPane(
                subjectsPanel
        );

        scrollPane.setBorder(
                BorderFactory.createEmptyBorder()
        );

        scrollPane.setOpaque(false);

        scrollPane.getViewport().setOpaque(false);

        scrollPane.getVerticalScrollBar()
                .setUnitIncrement(12);

        // Buttons

        JButton addButton = createPrimaryButton(
                "+ Add Subject"
        );

        JButton resetButton = createSecondaryButton(
                "Reset"
        );

        JButton calculateButton = createPrimaryButton(
                "Calculate Result"
        );

        addButton.addActionListener(
                e -> addSubject("")
        );

        resetButton.addActionListener(
                e -> resetAll()
        );

        calculateButton.addActionListener(
                e -> calculateResult()
        );

        JPanel bottomPanel = new JPanel(
                new BorderLayout()
        );

        bottomPanel.setOpaque(false);

        bottomPanel.add(
                addButton,
                BorderLayout.WEST
        );

        JPanel actionPanel = new JPanel(
                new GridLayout(
                        1,
                        2,
                        10,
                        0
                )
        );

        actionPanel.setOpaque(false);

        actionPanel.add(resetButton);
        actionPanel.add(calculateButton);

        bottomPanel.add(
                actionPanel,
                BorderLayout.EAST
        );

        // Top Section

        JPanel topSection = new JPanel(
                new BorderLayout(0, 15)
        );

        topSection.setOpaque(false);

        topSection.add(
                namePanel,
                BorderLayout.NORTH
        );

        topSection.add(
                subjectHeader,
                BorderLayout.CENTER
        );

        card.add(
                topSection,
                BorderLayout.NORTH
        );

        card.add(
                scrollPane,
                BorderLayout.CENTER
        );

        card.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        return card;
    }

    // =========================================================
    // RESULT CARD
    // =========================================================

    private JPanel createResultCard() {

        RoundedPanel card = new RoundedPanel(
                22,
                WHITE
        );

        card.setLayout(
                new BorderLayout()
        );

        card.setBorder(
                new EmptyBorder(
                        22,
                        22,
                        22,
                        22
                )
        );

        JPanel header = new JPanel();

        header.setOpaque(false);

        header.setLayout(
                new BoxLayout(
                        header,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel title = new JLabel(
                "Result Dashboard"
        );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        22
                )
        );

        title.setForeground(TEXT);

        title.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel subtitle = new JLabel(
                "Detailed performance analysis"
        );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        subtitle.setForeground(SECONDARY);

        subtitle.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        header.add(title);

        header.add(
                Box.createVerticalStrut(5)
        );

        header.add(subtitle);

        card.add(
                header,
                BorderLayout.NORTH
        );

        resultContent = new JPanel();

        resultContent.setOpaque(false);

        resultContent.setLayout(
                new BoxLayout(
                        resultContent,
                        BoxLayout.Y_AXIS
                )
        );

        resultContent.add(
                createInitialResult()
        );

        JScrollPane resultScroll = new JScrollPane(
                resultContent
        );

        resultScroll.setBorder(
                BorderFactory.createEmptyBorder()
        );

        resultScroll.setOpaque(false);

        resultScroll.getViewport().setOpaque(false);

        resultScroll.getVerticalScrollBar()
                .setUnitIncrement(10);

        card.add(
                resultScroll,
                BorderLayout.CENTER
        );

        return card;
    }

    // =========================================================
    // INITIAL RESULT
    // =========================================================

    private JPanel createInitialResult() {

        JPanel panel = new JPanel();

        panel.setOpaque(false);

        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.Y_AXIS
                )
        );

        panel.add(
                Box.createVerticalStrut(50)
        );

        JLabel icon = new JLabel("📊");

        icon.setFont(
                new Font(
                        "Segoe UI Emoji",
                        Font.PLAIN,
                        60
                )
        );

        icon.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        panel.add(icon);

        panel.add(
                Box.createVerticalStrut(15)
        );

        JLabel message = new JLabel(
                "<html><center>" +
                        "Enter your marks<br>" +
                        "and click Calculate Result" +
                        "</center></html>"
        );

        message.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15
                )
        );

        message.setForeground(SECONDARY);

        message.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        panel.add(message);

        return panel;
    }

    // =========================================================
    // CALCULATE RESULT
    // =========================================================

    private void calculateResult() {

        String studentName =
                studentNameField
                        .getText()
                        .trim();

        if (studentName.isEmpty()) {

            showWarning(
                    "Please enter the student's name."
            );

            studentNameField.requestFocus();

            return;
        }

        if (subjectRows.isEmpty()) {

            showWarning(
                    "Please add at least one subject."
            );

            return;
        }

        int totalMarks = 0;

        SubjectRow bestSubject = null;
        SubjectRow lowestSubject = null;

        for (SubjectRow row : subjectRows) {

            String subjectName =
                    row.nameField
                            .getText()
                            .trim();

            String marksText =
                    row.marksField
                            .getText()
                            .trim();

            if (subjectName.isEmpty()) {

                showWarning(
                        "Please enter all subject names."
                );

                row.nameField.requestFocus();

                return;
            }

            if (marksText.isEmpty()) {

                showWarning(
                        "Please enter marks for "
                                + subjectName
                                + "."
                );

                row.marksField.requestFocus();

                return;
            }

            int marks;

            try {

                marks =
                        Integer.parseInt(
                                marksText
                        );

            } catch (NumberFormatException ex) {

                showError(
                        "Marks for "
                                + subjectName
                                + " must be a number between 0 and 100."
                );

                row.marksField.requestFocus();

                return;
            }

            if (marks < 0 || marks > 100) {

                showError(
                        "Marks for "
                                + subjectName
                                + " must be between 0 and 100."
                );

                row.marksField.requestFocus();

                return;
            }

            row.marks = marks;

            totalMarks += marks;

            if (bestSubject == null ||
                    marks > bestSubject.marks) {

                bestSubject = row;
            }

            if (lowestSubject == null ||
                    marks < lowestSubject.marks) {

                lowestSubject = row;
            }
        }

        int maximumMarks =
                subjectRows.size() * 100;

        double percentage =
                (totalMarks * 100.0)
                        / maximumMarks;

        String overallGrade =
                getGrade(percentage);

        boolean passed =
                percentage >= 50;

        displayResults(
                studentName,
                totalMarks,
                maximumMarks,
                percentage,
                overallGrade,
                passed,
                bestSubject,
                lowestSubject
        );
    }

    // =========================================================
    // DISPLAY RESULTS
    // =========================================================

    private void displayResults(
            String studentName,
            int totalMarks,
            int maximumMarks,
            double percentage,
            String overallGrade,
            boolean passed,
            SubjectRow bestSubject,
            SubjectRow lowestSubject
    ) {

        resultContent.removeAll();

        JLabel welcomeLabel =
                new JLabel(
                        "Hello, "
                                + studentName
                                + " 👋"
                );

        welcomeLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18
                )
        );

        welcomeLabel.setForeground(TEXT);

        welcomeLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        resultContent.add(welcomeLabel);

        resultContent.add(
                Box.createVerticalStrut(15)
        );

        // Overall Grade

        RoundedPanel gradeBox =
                new RoundedPanel(
                        25,
                        LIGHT_PURPLE
                );

        gradeBox.setLayout(
                new BoxLayout(
                        gradeBox,
                        BoxLayout.Y_AXIS
                )
        );

        gradeBox.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        145
                )
        );

        JLabel gradeLabel =
                new JLabel(
                        overallGrade
                );

        gradeLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        55
                )
        );

        gradeLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        gradeLabel.setForeground(
                getGradeColor(percentage)
        );

        JLabel gradeDescription =
                new JLabel(
                        "Overall Grade"
                );

        gradeDescription.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        gradeDescription.setForeground(
                SECONDARY
        );

        gradeDescription.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        gradeBox.add(
                Box.createVerticalGlue()
        );

        gradeBox.add(gradeLabel);

        gradeBox.add(
                Box.createVerticalStrut(2)
        );

        gradeBox.add(gradeDescription);

        gradeBox.add(
                Box.createVerticalGlue()
        );

        resultContent.add(gradeBox);

        resultContent.add(
                Box.createVerticalStrut(15)
        );

        // Statistics

        JPanel statsPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                3,
                                10,
                                0
                        )
                );

        statsPanel.setOpaque(false);

        statsPanel.add(
                createInfoCard(
                        totalMarks
                                + " / "
                                + maximumMarks,
                        "Total Marks"
                )
        );

        statsPanel.add(
                createInfoCard(
                        String.format(
                                "%.2f%%",
                                percentage
                        ),
                        "Average"
                )
        );

        statsPanel.add(
                createInfoCard(
                        passed
                                ? "PASS"
                                : "FAIL",
                        "Status"
                )
        );

        statsPanel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        90
                )
        );

        resultContent.add(statsPanel);

        resultContent.add(
                Box.createVerticalStrut(18)
        );

        // Best and Lowest Subjects

        JPanel highlightPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                10,
                                0
                        )
                );

        highlightPanel.setOpaque(false);

        highlightPanel.add(
                createHighlightCard(
                        "🏆 Best Subject",
                        bestSubject.nameField
                                .getText()
                                .trim(),
                        bestSubject.marks,
                        SUCCESS
                )
        );

        highlightPanel.add(
                createHighlightCard(
                        "⚠ Needs Attention",
                        lowestSubject.nameField
                                .getText()
                                .trim(),
                        lowestSubject.marks,
                        WARNING
                )
        );

        highlightPanel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        100
                )
        );

        resultContent.add(
                highlightPanel
        );

        resultContent.add(
                Box.createVerticalStrut(18)
        );

        // Subject Performance

        JLabel performanceTitle =
                new JLabel(
                        "Subject Performance"
                );

        performanceTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        16
                )
        );

        performanceTitle.setForeground(TEXT);

        performanceTitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        resultContent.add(
                performanceTitle
        );

        resultContent.add(
                Box.createVerticalStrut(10)
        );

        for (SubjectRow row : subjectRows) {

            resultContent.add(
                    createSubjectPerformance(row)
            );

            resultContent.add(
                    Box.createVerticalStrut(8)
            );
        }

        // Grade Distribution

        resultContent.add(
                Box.createVerticalStrut(10)
        );

        JLabel distributionTitle =
                new JLabel(
                        "Grade Distribution"
                );

        distributionTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        16
                )
        );

        distributionTitle.setForeground(TEXT);

        distributionTitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        resultContent.add(
                distributionTitle
        );

        resultContent.add(
                Box.createVerticalStrut(8)
        );

        resultContent.add(
                createGradeDistribution()
        );

        resultContent.add(
                Box.createVerticalStrut(15)
        );

        // Smart Analysis

        JLabel smartAnalysis =
                new JLabel();

        smartAnalysis.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        smartAnalysis.setForeground(TEXT);

        smartAnalysis.setOpaque(true);

        smartAnalysis.setBackground(
                new Color(
                        248,
                        250,
                        252
                )
        );

        smartAnalysis.setBorder(
                new EmptyBorder(
                        15,
                        15,
                        15,
                        15
                )
        );

        smartAnalysis.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        smartAnalysis.setText(
                createSmartMessage(
                        percentage,
                        bestSubject,
                        lowestSubject
                )
        );

        resultContent.add(smartAnalysis);

        resultContent.revalidate();
        resultContent.repaint();

        animateDashboard();
    }

    // =========================================================
    // SUBJECT PERFORMANCE
    // =========================================================

    private JPanel createSubjectPerformance(
            SubjectRow row
    ) {

        JPanel panel =
                new JPanel(
                        new BorderLayout(
                                10,
                                5
                        )
                );

        panel.setOpaque(false);

        JLabel subjectName =
                new JLabel(
                        row.nameField
                                .getText()
                                .trim()
                );

        subjectName.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        subjectName.setForeground(TEXT);

        subjectName.setPreferredSize(
                new Dimension(
                        100,
                        25
                )
        );

        JProgressBar progressBar =
                new JProgressBar(
                        0,
                        100
                );

        progressBar.setValue(
                row.marks
        );

        progressBar.setBorderPainted(false);

        progressBar.setStringPainted(false);

        progressBar.setForeground(
                getGradeColor(row.marks)
        );

        progressBar.setBackground(
                new Color(
                        229,
                        231,
                        235
                )
        );

        progressBar.setPreferredSize(
                new Dimension(
                        180,
                        10
                )
        );

        JLabel marksLabel =
                new JLabel(
                        row.marks
                                + "  "
                                + getGrade(row.marks)
                );

        marksLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        marksLabel.setForeground(
                getGradeColor(row.marks)
        );

        marksLabel.setPreferredSize(
                new Dimension(
                        55,
                        25
                )
        );

        panel.add(
                subjectName,
                BorderLayout.WEST
        );

        panel.add(
                progressBar,
                BorderLayout.CENTER
        );

        panel.add(
                marksLabel,
                BorderLayout.EAST
        );

        return panel;
    }

    // =========================================================
    // GRADE DISTRIBUTION
    // =========================================================

    private JPanel createGradeDistribution() {

        int[] gradeCounts = new int[6];

        for (SubjectRow row : subjectRows) {

            String grade =
                    getGrade(row.marks);

            switch (grade) {

                case "A+":
                    gradeCounts[0]++;
                    break;

                case "A":
                    gradeCounts[1]++;
                    break;

                case "B":
                    gradeCounts[2]++;
                    break;

                case "C":
                    gradeCounts[3]++;
                    break;

                case "D":
                    gradeCounts[4]++;
                    break;

                default:
                    gradeCounts[5]++;
                    break;
            }
        }

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                2,
                                3,
                                7,
                                7
                        )
                );

        panel.setOpaque(false);

        String[] grades = {
                "A+",
                "A",
                "B",
                "C",
                "D",
                "F"
        };

        for (int i = 0;
             i < grades.length;
             i++) {

            RoundedPanel item =
                    new RoundedPanel(
                            12,
                            new Color(
                                    248,
                                    250,
                                    252
                            )
                    );

            item.setLayout(
                    new BorderLayout()
            );

            JLabel label =
                    new JLabel(
                            grades[i]
                                    + "  × "
                                    + gradeCounts[i]
                    );

            label.setHorizontalAlignment(
                    SwingConstants.CENTER
            );

            label.setFont(
                    new Font(
                            "Segoe UI",
                            Font.BOLD,
                            12
                    )
            );

            label.setForeground(
                    getGradeColorForLetter(
                            grades[i]
                    )
            );

            item.add(label);

            panel.add(item);
        }

        panel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        80
                )
        );

        return panel;
    }

    // =========================================================
    // INFO CARD
    // =========================================================

    private JPanel createInfoCard(
            String value,
            String title
    ) {

        RoundedPanel card =
                new RoundedPanel(
                        14,
                        new Color(
                                248,
                                250,
                                252
                        )
                );

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel valueLabel =
                new JLabel(value);

        valueLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        20
                )
        );

        valueLabel.setForeground(PRIMARY);

        valueLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        11
                )
        );

        titleLabel.setForeground(SECONDARY);

        titleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        card.add(
                Box.createVerticalGlue()
        );

        card.add(valueLabel);

        card.add(
                Box.createVerticalStrut(3)
        );

        card.add(titleLabel);

        card.add(
                Box.createVerticalGlue()
        );

        return card;
    }

    // =========================================================
    // HIGHLIGHT CARD
    // =========================================================

    private JPanel createHighlightCard(
            String title,
            String subject,
            int marks,
            Color color
    ) {

        RoundedPanel card =
                new RoundedPanel(
                        14,
                        new Color(
                                248,
                                250,
                                252
                        )
                );

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        11
                )
        );

        titleLabel.setForeground(color);

        titleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel subjectLabel =
                new JLabel(subject);

        subjectLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        subjectLabel.setForeground(TEXT);

        subjectLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel marksLabel =
                new JLabel(
                        marks + " / 100"
                );

        marksLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        11
                )
        );

        marksLabel.setForeground(
                SECONDARY
        );

        marksLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        card.add(
                Box.createVerticalGlue()
        );

        card.add(titleLabel);

        card.add(
                Box.createVerticalStrut(4)
        );

        card.add(subjectLabel);

        card.add(
                Box.createVerticalStrut(2)
        );

        card.add(marksLabel);

        card.add(
                Box.createVerticalGlue()
        );

        return card;
    }

    // =========================================================
    // SMART PERFORMANCE MESSAGE
    // =========================================================

    private String createSmartMessage(
            double percentage,
            SubjectRow bestSubject,
            SubjectRow lowestSubject
    ) {

        String message;

        if (percentage >= 90) {

            message =
                    "🌟 Outstanding performance! "
                            + "You have demonstrated excellent "
                            + "understanding across your subjects.";

        } else if (percentage >= 80) {

            message =
                    "👏 Excellent performance! "
                            + "Keep maintaining this consistency "
                            + "to reach the top grade.";

        } else if (percentage >= 70) {

            message =
                    "👍 Very good performance! "
                            + "With a little more practice, "
                            + "you can reach the next level.";

        } else if (percentage >= 60) {

            message =
                    "📚 Good performance. "
                            + "Focus on your weaker subjects "
                            + "to improve your overall percentage.";

        } else if (percentage >= 50) {

            message =
                    "💪 You passed, but there is room "
                            + "for improvement. Create a study "
                            + "plan and practice regularly.";

        } else {

            message =
                    "⚠ Your current result needs improvement. "
                            + "Focus on fundamentals and "
                            + "practice consistently.";
        }

        message +=
                "<br><br>🏆 Strongest: "
                        + bestSubject.nameField
                                .getText()
                                .trim()
                        + " ("
                        + bestSubject.marks
                        + ")";

        message +=
                "<br>📌 Focus more on: "
                        + lowestSubject.nameField
                                .getText()
                                .trim()
                        + " ("
                        + lowestSubject.marks
                        + ")";

        return "<html>" + message + "</html>";
    }

    // =========================================================
    // GRADE CALCULATION
    // =========================================================

    private String getGrade(
            double percentage
    ) {

        if (percentage >= 90)
            return "A+";

        if (percentage >= 80)
            return "A";

        if (percentage >= 70)
            return "B";

        if (percentage >= 60)
            return "C";

        if (percentage >= 50)
            return "D";

        return "F";
    }

    // =========================================================
    // GRADE COLOR
    // =========================================================

    private Color getGradeColor(
            double percentage
    ) {

        if (percentage >= 80)
            return SUCCESS;

        if (percentage >= 50)
            return WARNING;

        return DANGER;
    }

    private Color getGradeColorForLetter(
            String grade
    ) {

        if (grade.equals("A+") ||
                grade.equals("A")) {

            return SUCCESS;
        }

        if (grade.equals("B") ||
                grade.equals("C") ||
                grade.equals("D")) {

            return WARNING;
        }

        return DANGER;
    }

    // =========================================================
    // RESULT ANIMATION
    // =========================================================

    private void animateDashboard() {

        resultContent.setVisible(false);

        Timer timer =
                new Timer(
                        80,
                        null
                );

        timer.addActionListener(
                new ActionListener() {

                    int counter = 0;

                    @Override
                    public void actionPerformed(
                            ActionEvent e
                    ) {

                        counter++;

                        resultContent.setVisible(true);
                        resultContent.repaint();

                        if (counter >= 4) {
                            timer.stop();
                        }
                    }
                }
        );

        timer.setRepeats(true);
        timer.start();
    }

    // =========================================================
    // ADD SUBJECT
    // =========================================================

    private void addSubject(
            String subjectName
    ) {

        SubjectRow row =
                new SubjectRow(
                        subjectName
                );

        subjectRows.add(row);

        subjectsPanel.add(
                row.panel
        );

        subjectsPanel.revalidate();
        subjectsPanel.repaint();
    }

    // =========================================================
    // REMOVE SUBJECT
    // =========================================================

    private void removeSubject(
            SubjectRow row
    ) {

        if (subjectRows.size() <= 1) {

            showWarning(
                    "At least one subject is required."
            );

            return;
        }

        subjectRows.remove(row);

        subjectsPanel.remove(
                row.panel
        );

        subjectsPanel.revalidate();
        subjectsPanel.repaint();
    }

    // =========================================================
    // RESET
    // =========================================================

    private void resetAll() {

        int answer =
                JOptionPane.showConfirmDialog(
                        this,
                        "Reset all subjects and results?",
                        "Reset",
                        JOptionPane.YES_NO_OPTION
                );

        if (answer != JOptionPane.YES_OPTION) {
            return;
        }

        studentNameField.setText("");

        subjectRows.clear();

        subjectsPanel.removeAll();

        addSubject("Mathematics");
        addSubject("Physics");
        addSubject("Programming");

        resultContent.removeAll();

        resultContent.add(
                createInitialResult()
        );

        resultContent.revalidate();
        resultContent.repaint();
    }

    // =========================================================
    // TEXT FIELD
    // =========================================================

    private JTextField createTextField() {

        JTextField field =
                new JTextField();

        field.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        field.setForeground(TEXT);

        field.setBackground(
                new Color(
                        249,
                        250,
                        251
                )
        );

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                9,
                                11,
                                9,
                                11
                        )
                )
        );

        return field;
    }

    // =========================================================
    // PRIMARY BUTTON
    // =========================================================

    private JButton createPrimaryButton(
            String text
    ) {

        JButton button =
                new JButton(text);

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        button.setForeground(
                Color.WHITE
        );

        button.setBackground(
                PRIMARY
        );

        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setBorder(
                new EmptyBorder(
                        10,
                        15,
                        10,
                        15
                )
        );

        button.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            MouseEvent e
                    ) {

                        button.setBackground(
                                PRIMARY_DARK
                        );
                    }

                    @Override
                    public void mouseExited(
                            MouseEvent e
                    ) {

                        button.setBackground(
                                PRIMARY
                        );
                    }
                }
        );

        return button;
    }

    // =========================================================
    // SECONDARY BUTTON
    // =========================================================

    private JButton createSecondaryButton(
            String text
    ) {

        JButton button =
                new JButton(text);

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        button.setForeground(TEXT);

        button.setBackground(
                new Color(
                        243,
                        244,
                        246
                )
        );

        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setBorder(
                new EmptyBorder(
                        10,
                        15,
                        10,
                        15
                )
        );

        return button;
    }

    // =========================================================
    // WARNING
    // =========================================================

    private void showWarning(
            String message
    ) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Warning",
                JOptionPane.WARNING_MESSAGE
        );
    }

    // =========================================================
    // ERROR
    // =========================================================

    private void showError(
            String message
    ) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Invalid Input",
                JOptionPane.ERROR_MESSAGE
        );
    }

    // =========================================================
    // SUBJECT ROW
    // =========================================================

    private class SubjectRow {

        JPanel panel;

        JTextField nameField;
        JTextField marksField;

        JButton removeButton;

        int marks = 0;

        SubjectRow(
                String defaultName
        ) {

            panel =
                    new RoundedPanel(
                            15,
                            new Color(
                                    249,
                                    250,
                                    251
                            )
                    );

            panel.setLayout(
                    new BorderLayout(
                            10,
                            0
                    )
            );

            panel.setBorder(
                    new EmptyBorder(
                            8,
                            8,
                            8,
                            8
                    )
            );

            // Subject name

            nameField =
                    createTextField();

            nameField.setText(
                    defaultName
            );

            // Marks

            marksField =
                    createTextField();

            marksField.setHorizontalAlignment(
                    SwingConstants.CENTER
            );

            marksField.setPreferredSize(
                    new Dimension(
                            70,
                            40
                    )
            );

            marksField.setToolTipText(
                    "Enter marks from 0 to 100"
            );

            // Marks validation

            marksField.getDocument()
                    .addDocumentListener(
                            new DocumentListener() {

                                @Override
                                public void insertUpdate(
                                        DocumentEvent e
                                ) {
                                    validateMarksInput();
                                }

                                @Override
                                public void removeUpdate(
                                        DocumentEvent e
                                ) {
                                    validateMarksInput();
                                }

                                @Override
                                public void changedUpdate(
                                        DocumentEvent e
                                ) {
                                    validateMarksInput();
                                }

                                private void validateMarksInput() {

                                    String text =
                                            marksField
                                                    .getText()
                                                    .trim();

                                    if (text.isEmpty()) {

                                        marksField.setBackground(
                                                new Color(
                                                        249,
                                                        250,
                                                        251
                                                )
                                        );

                                        return;
                                    }

                                    try {

                                        int value =
                                                Integer.parseInt(
                                                        text
                                                );

                                        if (value >= 0 &&
                                                value <= 100) {

                                            marksField.setBackground(
                                                    new Color(
                                                            240,
                                                            253,
                                                            244
                                                    )
                                            );

                                        } else {

                                            marksField.setBackground(
                                                    new Color(
                                                            254,
                                                            242,
                                                            242
                                                    )
                                            );
                                        }

                                    } catch (
                                            NumberFormatException ex
                                    ) {

                                        marksField.setBackground(
                                                new Color(
                                                        254,
                                                        242,
                                                        242
                                                )
                                        );
                                    }
                                }
                            }
                    );

            // Remove button

            removeButton =
                    new JButton("×");

            removeButton.setFont(
                    new Font(
                            "Segoe UI",
                            Font.BOLD,
                            20
                    )
            );

            removeButton.setForeground(
                    DANGER
            );

            removeButton.setBackground(
                    new Color(
                            254,
                            242,
                            242
                    )
            );

            removeButton.setFocusPainted(false);
            removeButton.setBorderPainted(false);

            removeButton.setCursor(
                    new Cursor(
                            Cursor.HAND_CURSOR
                    )
            );

            removeButton.setPreferredSize(
                    new Dimension(
                            38,
                            38
                    )
            );

            removeButton.addActionListener(
                    e -> removeSubject(this)
            );

            // Fields

            JPanel fields =
                    new JPanel(
                            new GridLayout(
                                    1,
                                    2,
                                    10,
                                    0
                            )
                    );

            fields.setOpaque(false);

            fields.add(nameField);
            fields.add(marksField);

            panel.add(
                    fields,
                    BorderLayout.CENTER
            );

            panel.add(
                    removeButton,
                    BorderLayout.EAST
            );
        }
    }

    // =========================================================
    // ROUNDED PANEL
    // =========================================================

    private static class RoundedPanel
            extends JPanel {

        private final int radius;
        private final Color backgroundColor;

        RoundedPanel(
                int radius,
                Color backgroundColor
        ) {

            this.radius = radius;
            this.backgroundColor =
                    backgroundColor;

            setOpaque(false);
        }

        @Override
        protected void paintComponent(
                Graphics g
        ) {

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(
                    backgroundColor
            );

            g2.fillRoundRect(
                    0,
                    0,
                    getWidth(),
                    getHeight(),
                    radius,
                    radius
            );

            g2.dispose();

            super.paintComponent(g);
        }
    }

    // =========================================================
    // MAIN
    // =========================================================

    public static void main(
            String[] args
    ) {

        try {

            UIManager.setLookAndFeel(
                    UIManager
                            .getSystemLookAndFeelClassName()
            );

        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(
                () -> new StudentGradeCalculator()
        );
    }
}