import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.prefs.Preferences;

public class NumberGame extends JFrame {

    // =========================================================
    // GAME DATA
    // =========================================================

    private int secretNumber;
    private int attempts;
    private int lives;
    private int timeLeft;

    private int maxNumber = 100;
    private String difficulty = "MEDIUM";

    private final Random random = new Random();

    private Timer gameTimer;
    private Timer particleTimer;

    // =========================================================
    // PLAYER DATA
    // =========================================================

    private int gamesPlayed = 0;
    private int gamesWon = 0;

    private int currentStreak = 0;
    private int bestStreak = 0;
    private int bestScore = 0;

    // =========================================================
    // STORAGE
    // =========================================================

    private final Preferences preferences =
            Preferences.userNodeForPackage(NumberGame.class);

    // =========================================================
    // UI
    // =========================================================

    private JTextField guessField;

    private JLabel messageLabel;
    private JLabel timerLabel;
    private JLabel livesLabel;
    private JLabel attemptsLabel;

    private JLabel streakLabel;
    private JLabel bestLabel;
    private JLabel difficultyLabel;
    private JLabel rangeLabel;

    private JTextArea historyArea;

    private JButton guessButton;
    private JButton hintButton;
    private JButton newGameButton;

    // =========================================================
    // COLORS
    // =========================================================

    private Color accent = new Color(124, 77, 255);
    private Color accentLight = new Color(0, 220, 255);

    private final Color background =
            new Color(5, 7, 20);

    private final Color panelColor =
            new Color(16, 20, 42);

    private final Color panelLight =
            new Color(25, 30, 58);

    private final Color white =
            new Color(245, 247, 255);

    private final Color muted =
            new Color(150, 157, 185);

    private final Color success =
            new Color(55, 225, 125);

    private final Color danger =
            new Color(255, 70, 100);

    private final Color gold =
            new Color(255, 205, 70);

    // =========================================================
    // PARTICLES
    // =========================================================

    private final List<Particle> particles =
            new ArrayList<>();

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public NumberGame() {

        setTitle("NUMBER QUEST - Stage 3");

        setSize(700, 850);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);

        setResizable(false);

        loadProgress();

        createParticles();

        createInterface();

        startNewGame();

        startParticleAnimation();
    }

    // =========================================================
    // PARTICLES
    // =========================================================

    private void createParticles() {

        for (int i = 0; i < 50; i++) {

            particles.add(
                    new Particle(
                            random.nextInt(700),
                            random.nextInt(850),
                            1 + random.nextInt(3),
                            1 + random.nextInt(2)
                    )
            );
        }
    }

    private void startParticleAnimation() {

        particleTimer = new Timer(
                40,
                e -> {

                    for (Particle p : particles) {

                        p.y -= p.speed;

                        if (p.y < 0) {

                            p.y = getHeight();

                            p.x = random.nextInt(
                                    Math.max(
                                            getWidth(),
                                            1
                                    )
                            );
                        }
                    }

                    repaint();
                }
        );

        particleTimer.start();
    }

    // =========================================================
    // INTERFACE
    // =========================================================

    private void createInterface() {

        NeonBackground backgroundPanel =
                new NeonBackground();

        backgroundPanel.setLayout(
                new BorderLayout()
        );

        JPanel content = new JPanel();

        content.setOpaque(false);

        content.setLayout(
                new BoxLayout(
                        content,
                        BoxLayout.Y_AXIS
                )
        );

        content.setBorder(
                new EmptyBorder(
                        20,
                        35,
                        15,
                        35
                )
        );

        // =====================================================
        // HEADER
        // =====================================================

        JPanel header =
                new JPanel(
                        new BorderLayout()
                );

        header.setOpaque(false);

        JLabel logo =
                createLabel(
                        "◈ NUMBER QUEST",
                        white,
                        21,
                        Font.BOLD
                );

        JLabel stage =
                createLabel(
                        "STAGE 03",
                        accentLight,
                        10,
                        Font.BOLD
                );

        header.add(
                logo,
                BorderLayout.WEST
        );

        header.add(
                stage,
                BorderLayout.EAST
        );

        content.add(header);

        content.add(
                Box.createVerticalStrut(12)
        );

        // =====================================================
        // HERO
        // =====================================================

        JLabel icon =
                createLabel(
                        "🎯",
                        white,
                        40,
                        Font.PLAIN
                );

        icon.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel title =
                createLabel(
                        "CRACK THE CODE",
                        white,
                        30,
                        Font.BOLD
                );

        title.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel subtitle =
                createLabel(
                        "Think fast. Guess smart. Become the champion.",
                        muted,
                        11,
                        Font.PLAIN
                );

        subtitle.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        content.add(icon);

        content.add(
                Box.createVerticalStrut(3)
        );

        content.add(title);

        content.add(
                Box.createVerticalStrut(3)
        );

        content.add(subtitle);

        content.add(
                Box.createVerticalStrut(15)
        );

        // =====================================================
        // PLAYER STATS
        // =====================================================

        JPanel playerPanel =
                new GlassPanel();

        playerPanel.setLayout(
                new GridLayout(
                        1,
                        3,
                        8,
                        0
                )
        );

        JLabel level =
                createLabel(
                        "⭐ LEVEL 1",
                        accent,
                        14,
                        Font.BOLD
                );

        JLabel streak =
                createLabel(
                        "🔥 STREAK 0",
                        gold,
                        14,
                        Font.BOLD
                );

        JLabel best =
                createLabel(
                        "🏆 BEST --",
                        success,
                        14,
                        Font.BOLD
                );

        streakLabel = streak;
        bestLabel = best;

        playerPanel.add(centerPanel(level));
        playerPanel.add(centerPanel(streak));
        playerPanel.add(centerPanel(best));

        content.add(playerPanel);

        content.add(
                Box.createVerticalStrut(10)
        );

        // =====================================================
        // STATUS CARDS
        // =====================================================

        JPanel statusPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                3,
                                8,
                                0
                        )
                );

        statusPanel.setOpaque(false);

        timerLabel =
                createValueLabel("45");

        livesLabel =
                createValueLabel(
                        "❤️ ❤️ ❤️ ❤️ ❤️"
                );

        attemptsLabel =
                createValueLabel("0");

        statusPanel.add(
                createStatusCard(
                        "⏱",
                        "TIME",
                        timerLabel
                )
        );

        statusPanel.add(
                createStatusCard(
                        "❤️",
                        "LIVES",
                        livesLabel
                )
        );

        statusPanel.add(
                createStatusCard(
                        "🎯",
                        "ATTEMPTS",
                        attemptsLabel
                )
        );

        content.add(statusPanel);

        content.add(
                Box.createVerticalStrut(10)
        );

        // =====================================================
        // GAME CARD
        // =====================================================

        GlassPanel gameCard =
                new GlassPanel();

        gameCard.setLayout(
                new BoxLayout(
                        gameCard,
                        BoxLayout.Y_AXIS
                )
        );

        // Range row

        JPanel rangePanel =
                new JPanel(
                        new BorderLayout()
                );

        rangePanel.setOpaque(false);

        JLabel target =
                createLabel(
                        "TARGET RANGE",
                        muted,
                        9,
                        Font.BOLD
                );

        rangeLabel =
                createLabel(
                        "1 — 100",
                        accentLight,
                        16,
                        Font.BOLD
                );

        difficultyLabel =
                createLabel(
                        "● MEDIUM",
                        success,
                        9,
                        Font.BOLD
                );

        rangePanel.add(
                target,
                BorderLayout.WEST
        );

        rangePanel.add(
                rangeLabel,
                BorderLayout.CENTER
        );

        rangePanel.add(
                difficultyLabel,
                BorderLayout.EAST
        );

        gameCard.add(rangePanel);

        gameCard.add(
                Box.createVerticalStrut(12)
        );

        // Input label

        JLabel inputLabel =
                createLabel(
                        "ENTER YOUR GUESS",
                        muted,
                        9,
                        Font.BOLD
                );

        inputLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        gameCard.add(inputLabel);

        gameCard.add(
                Box.createVerticalStrut(6)
        );

        // Input

        guessField =
                new JTextField();

        guessField.setPreferredSize(
                new Dimension(
                        500,
                        60
                )
        );

        guessField.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        60
                )
        );

        guessField.setHorizontalAlignment(
                JTextField.CENTER
        );

        guessField.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        guessField.setForeground(white);

        guessField.setCaretColor(
                accentLight
        );

        guessField.setBackground(
                new Color(
                        7,
                        9,
                        23
                )
        );

        guessField.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        90,
                                        80,
                                        150
                                ),
                                2
                        ),
                        new EmptyBorder(
                                5,
                                10,
                                5,
                                10
                        )
                )
        );

        gameCard.add(guessField);

        gameCard.add(
                Box.createVerticalStrut(8)
        );

        // Guess button

        guessButton =
                createNeonButton(
                        "🎲  MAKE YOUR GUESS"
                );

        gameCard.add(guessButton);

        gameCard.add(
                Box.createVerticalStrut(8)
        );

        // Action buttons

        JPanel actions =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                8,
                                0
                        )
                );

        actions.setOpaque(false);

        hintButton =
                createDarkButton(
                        "💡 HINT"
                );

        newGameButton =
                createDarkButton(
                        "🔄 NEW GAME"
                );

        actions.add(hintButton);
        actions.add(newGameButton);

        gameCard.add(actions);

        gameCard.add(
                Box.createVerticalStrut(8)
        );

        // Message

        messageLabel =
                new JLabel(
                        "💡 Ready? Find the hidden number!",
                        SwingConstants.CENTER
                );

        messageLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        11
                )
        );

        messageLabel.setForeground(white);

        messageLabel.setOpaque(true);

        messageLabel.setBackground(
                new Color(
                        8,
                        10,
                        25
                )
        );

        messageLabel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        55,
                                        60,
                                        100
                                )
                        ),
                        new EmptyBorder(
                                9,
                                5,
                                9,
                                5
                        )
                )
        );

        messageLabel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        45
                )
        );

        gameCard.add(messageLabel);

        content.add(gameCard);

        content.add(
                Box.createVerticalStrut(10)
        );

        // =====================================================
        // LOWER AREA
        // =====================================================

        JPanel lower =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                8,
                                0
                        )
                );

        lower.setOpaque(false);

        // History

        GlassPanel history =
                new GlassPanel();

        history.setLayout(
                new BorderLayout()
        );

        JLabel historyTitle =
                createLabel(
                        "📜 GUESS HISTORY",
                        muted,
                        9,
                        Font.BOLD
                );

        historyArea =
                new JTextArea();

        historyArea.setEditable(false);

        historyArea.setFont(
                new Font(
                        "Consolas",
                        Font.PLAIN,
                        10
                )
        );

        historyArea.setForeground(
                new Color(
                        200,
                        205,
                        230
                )
        );

        historyArea.setBackground(
                new Color(
                        7,
                        9,
                        22
                )
        );

        historyArea.setBorder(
                new EmptyBorder(
                        5,
                        5,
                        5,
                        5
                )
        );

        JScrollPane scroll =
                new JScrollPane(
                        historyArea
                );

        scroll.setBorder(null);

        history.add(
                historyTitle,
                BorderLayout.NORTH
        );

        history.add(
                scroll,
                BorderLayout.CENTER
        );

        lower.add(history);

        // Instructions

        GlassPanel info =
                new GlassPanel();

        info.setLayout(
                new BorderLayout()
        );

        JLabel infoTitle =
                createLabel(
                        "💡 HOW TO PLAY",
                        muted,
                        9,
                        Font.BOLD
                );

        JLabel infoText =
                new JLabel(
                        "<html><center>"
                                + "Guess the hidden number.<br><br>"
                                + "⬆ LOW → Guess higher<br><br>"
                                + "⬇ HIGH → Guess lower<br><br>"
                                + "❤️ You have 5 lives<br><br>"
                                + "⏱ Beat the timer!"
                                + "</center></html>",
                        SwingConstants.CENTER
                );

        infoText.setForeground(white);

        infoText.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        10
                )
        );

        info.add(
                infoTitle,
                BorderLayout.NORTH
        );

        info.add(
                infoText,
                BorderLayout.CENTER
        );

        lower.add(info);

        content.add(lower);

        content.add(
                Box.createVerticalStrut(10)
        );

        // =====================================================
        // MENU
        // =====================================================

        JPanel menu =
                new JPanel(
                        new GridLayout(
                                1,
                                4,
                                6,
                                0
                        )
                );

        menu.setOpaque(false);

        JButton difficultyButton =
                createDarkButton(
                        "🎮 DIFFICULTY"
                );

        JButton themeButton =
                createDarkButton(
                        "🎨 THEME"
                );

        JButton statsButton =
                createDarkButton(
                        "📊 STATS"
                );

        JButton resetButton =
                createDarkButton(
                        "🗑 RESET"
                );

        menu.add(difficultyButton);
        menu.add(themeButton);
        menu.add(statsButton);
        menu.add(resetButton);

        content.add(menu);

        content.add(
                Box.createVerticalStrut(8)
        );

        JLabel footer =
                createLabel(
                        "NUMBER QUEST • TRAIN YOUR BRAIN • HAVE FUN",
                        muted,
                        8,
                        Font.BOLD
                );

        footer.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        content.add(footer);

        // =====================================================
        // EVENTS
        // =====================================================

        guessButton.addActionListener(
                e -> checkGuess()
        );

        guessField.addActionListener(
                e -> checkGuess()
        );

        hintButton.addActionListener(
                e -> giveHint()
        );

        newGameButton.addActionListener(
                e -> startNewGame()
        );

        difficultyButton.addActionListener(
                e -> changeDifficulty()
        );

        themeButton.addActionListener(
                e -> changeTheme()
        );

        statsButton.addActionListener(
                e -> showStatistics()
        );

        resetButton.addActionListener(
                e -> resetProgress()
        );

        backgroundPanel.add(
                content,
                BorderLayout.CENTER
        );

        add(backgroundPanel);
    }

    // =========================================================
    // CENTER PANEL
    // =========================================================

    private JPanel centerPanel(
            JLabel label
    ) {

        JPanel panel = new JPanel();

        panel.setOpaque(false);

        panel.setLayout(
                new GridBagLayout()
        );

        panel.add(label);

        return panel;
    }

    // =========================================================
    // LABEL
    // =========================================================

    private JLabel createLabel(
            String text,
            Color color,
            int size,
            int style
    ) {

        JLabel label =
                new JLabel(text);

        label.setForeground(color);

        label.setFont(
                new Font(
                        "Arial",
                        style,
                        size
                )
        );

        return label;
    }

    // =========================================================
    // VALUE LABEL
    // =========================================================

    private JLabel createValueLabel(
            String text
    ) {

        JLabel label =
                new JLabel(
                        text,
                        SwingConstants.CENTER
                );

        label.setForeground(white);

        label.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        12
                )
        );

        return label;
    }

    // =========================================================
    // STATUS CARD
    // =========================================================

    private JPanel createStatusCard(
            String icon,
            String title,
            JLabel value
    ) {

        GlassPanel panel =
                new GlassPanel();

        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel iconLabel =
                createLabel(
                        icon,
                        white,
                        15,
                        Font.PLAIN
                );

        iconLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel titleLabel =
                createLabel(
                        title,
                        muted,
                        7,
                        Font.BOLD
                );

        titleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        value.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        panel.add(iconLabel);
        panel.add(titleLabel);
        panel.add(value);

        return panel;
    }

    // =========================================================
    // NEON BUTTON
    // =========================================================

    private JButton createNeonButton(
            String text
    ) {

        JButton button =
                new JButton(text);

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        12
                )
        );

        button.setForeground(Color.WHITE);

        button.setBackground(accent);

        button.setFocusPainted(false);

        button.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        190,
                                        160,
                                        255
                                )
                        ),
                        new EmptyBorder(
                                11,
                                8,
                                11,
                                8
                        )
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        addHover(
                button,
                accent,
                new Color(
                        155,
                        100,
                        255
                )
        );

        return button;
    }

    // =========================================================
    // DARK BUTTON
    // =========================================================

    private JButton createDarkButton(
            String text
    ) {

        JButton button =
                new JButton(text);

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        8
                )
        );

        button.setForeground(
                new Color(
                        220,
                        223,
                        240
                )
        );

        button.setBackground(panelLight);

        button.setFocusPainted(false);

        button.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        60,
                                        65,
                                        105
                                )
                        ),
                        new EmptyBorder(
                                8,
                                4,
                                8,
                                4
                        )
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        addHover(
                button,
                panelLight,
                new Color(
                        45,
                        50,
                        90
                )
        );

        return button;
    }

    // =========================================================
    // HOVER
    // =========================================================

    private void addHover(
            JButton button,
            Color normal,
            Color hover
    ) {

        button.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            MouseEvent e
                    ) {

                        button.setBackground(
                                hover
                        );
                    }

                    @Override
                    public void mouseExited(
                            MouseEvent e
                    ) {

                        button.setBackground(
                                normal
                        );
                    }
                }
        );
    }

    // =========================================================
    // GLASS PANEL
    // =========================================================

    private class GlassPanel
            extends JPanel {

        GlassPanel() {

            setOpaque(false);

            setBorder(
                    BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(
                                    new Color(
                                            75,
                                            80,
                                            125
                                    )
                            ),
                            new EmptyBorder(
                                    9,
                                    10,
                                    9,
                                    10
                            )
                    )
            );
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

            g2.setColor(panelColor);

            g2.fillRoundRect(
                    0,
                    0,
                    getWidth(),
                    getHeight(),
                    18,
                    18
            );

            g2.setColor(
                    new Color(
                            255,
                            255,
                            255,
                            8
                    )
            );

            g2.fillRoundRect(
                    1,
                    1,
                    getWidth() - 2,
                    Math.max(
                            1,
                            getHeight() / 3
                    ),
                    18,
                    18
            );

            g2.dispose();

            super.paintComponent(g);
        }
    }

    // =========================================================
    // BACKGROUND
    // =========================================================

    private class NeonBackground
            extends JPanel {

        @Override
        protected void paintComponent(
                Graphics g
        ) {

            super.paintComponent(g);

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(background);

            g2.fillRect(
                    0,
                    0,
                    getWidth(),
                    getHeight()
            );

            int centerX =
                    getWidth() / 2;

            int radius =
                    Math.max(
                            150,
                            getWidth() / 2
                    );

            RadialGradientPaint glow =
                    new RadialGradientPaint(
                            centerX,
                            100,
                            radius,
                            new float[]{
                                    0f,
                                    1f
                            },
                            new Color[]{
                                    new Color(
                                            accent.getRed(),
                                            accent.getGreen(),
                                            accent.getBlue(),
                                            55
                                    ),
                                    new Color(
                                            0,
                                            0,
                                            0,
                                            0
                                    )
                            }
                    );

            g2.setPaint(glow);

            g2.fillRect(
                    0,
                    0,
                    getWidth(),
                    getHeight()
            );

            g2.setColor(
                    new Color(
                            150,
                            180,
                            255,
                            80
                    )
            );

            for (Particle p : particles) {

                g2.fillOval(
                        p.x,
                        p.y,
                        p.size,
                        p.size
                );
            }

            g2.dispose();
        }
    }

    // =========================================================
    // PARTICLE
    // =========================================================

    private static class Particle {

        int x;
        int y;
        int size;
        int speed;

        Particle(
                int x,
                int y,
                int size,
                int speed
        ) {

            this.x = x;
            this.y = y;
            this.size = size;
            this.speed = speed;
        }
    }

    // =========================================================
    // START NEW GAME
    // =========================================================

    private void startNewGame() {

        if (gameTimer != null) {
            gameTimer.stop();
        }

        secretNumber =
                random.nextInt(maxNumber) + 1;

        attempts = 0;

        lives = 5;

        if (difficulty.equals("EASY")) {

            timeLeft = 60;

        } else if (difficulty.equals("HARD")) {

            timeLeft = 30;

        } else {

            timeLeft = 45;
        }

        attemptsLabel.setText("0");

        livesLabel.setText(
                "❤️ ❤️ ❤️ ❤️ ❤️"
        );

        timerLabel.setText(
                String.valueOf(timeLeft)
        );

        timerLabel.setForeground(white);

        rangeLabel.setText(
                "1 — " + maxNumber
        );

        difficultyLabel.setText(
                "● " + difficulty
        );

        guessField.setText("");

        historyArea.setText("");

        messageLabel.setText(
                "💡 Ready? Find the hidden number!"
        );

        messageLabel.setForeground(white);

        guessButton.setEnabled(true);

        hintButton.setEnabled(true);

        startTimer();

        guessField.requestFocusInWindow();
    }

    // =========================================================
    // TIMER
    // =========================================================

    private void startTimer() {

        gameTimer =
                new Timer(
                        1000,
                        e -> {

                            timeLeft--;

                            timerLabel.setText(
                                    String.valueOf(
                                            timeLeft
                                    )
                            );

                            if (timeLeft <= 10) {

                                timerLabel.setForeground(
                                        danger
                                );
                            }

                            if (timeLeft <= 0) {

                                gameTimer.stop();

                                gamesPlayed++;

                                currentStreak = 0;

                                saveProgress();

                                updatePlayerUI();

                                gameOver(
                                        "⏰ TIME'S UP!"
                                );
                            }
                        }
                );

        gameTimer.start();
    }

    // =========================================================
    // CHECK GUESS
    // =========================================================

    private void checkGuess() {

        if (!guessButton.isEnabled()) {
            return;
        }

        String input =
                guessField
                        .getText()
                        .trim();

        if (input.isEmpty()) {

            showMessage(
                    "⚠️ Enter a number first.",
                    danger
            );

            return;
        }

        int guess;

        try {

            guess =
                    Integer.parseInt(input);

        } catch (NumberFormatException ex) {

            showMessage(
                    "⚠️ Please enter a valid number.",
                    danger
            );

            return;
        }

        if (
                guess < 1 ||
                guess > maxNumber
        ) {

            showMessage(
                    "⚠️ Enter a number from 1 to "
                            + maxNumber
                            + ".",
                    danger
            );

            return;
        }

        attempts++;

        attemptsLabel.setText(
                String.valueOf(attempts)
        );

        addHistory(guess);

        // Correct

        if (guess == secretNumber) {

            handleWin();

            return;
        }

        // Wrong

        lives--;

        updateLives();

        if (guess < secretNumber) {

            showMessage(
                    "⬆️ TOO LOW — GO HIGHER",
                    accentLight
            );

        } else {

            showMessage(
                    "⬇️ TOO HIGH — GO LOWER",
                    danger
            );
        }

        if (lives <= 0) {

            if (gameTimer != null) {
                gameTimer.stop();
            }

            gamesPlayed++;

            currentStreak = 0;

            saveProgress();

            updatePlayerUI();

            gameOver(
                    "💔 ALL LIVES LOST!"
            );
        }

        guessField.selectAll();
    }

    // =========================================================
    // WIN
    // =========================================================

    private void handleWin() {

        if (gameTimer != null) {
            gameTimer.stop();
        }

        gamesPlayed++;

        gamesWon++;

        currentStreak++;

        if (currentStreak > bestStreak) {

            bestStreak =
                    currentStreak;
        }

        if (
                bestScore == 0 ||
                attempts < bestScore
        ) {

            bestScore = attempts;
        }

        saveProgress();

        updatePlayerUI();

        guessButton.setEnabled(false);

        hintButton.setEnabled(false);

        messageLabel.setText(
                "🏆 CORRECT! YOU WIN!"
        );

        messageLabel.setForeground(
                success
        );

        JOptionPane.showMessageDialog(
                this,
                "<html><center>"
                        + "<h1>🏆 VICTORY!</h1>"
                        + "<p>The hidden number was:</p>"
                        + "<h1>"
                        + secretNumber
                        + "</h1>"
                        + "<p>Attempts: "
                        + attempts
                        + "</p>"
                        + "<p>Streak: "
                        + currentStreak
                        + "</p>"
                        + "</center></html>",
                "MISSION COMPLETE",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // =========================================================
    // LIVES
    // =========================================================

    private void updateLives() {

        StringBuilder text =
                new StringBuilder();

        for (int i = 0; i < 5; i++) {

            if (i < lives) {

                text.append("❤️ ");

            } else {

                text.append("🖤 ");
            }
        }

        livesLabel.setText(
                text.toString()
        );
    }

    // =========================================================
    // HISTORY
    // =========================================================

    private void addHistory(
            int guess
    ) {

        String result;

        if (guess == secretNumber) {

            result =
                    "🏆 "
                            + guess
                            + " → CORRECT";

        } else if (guess < secretNumber) {

            result =
                    "⬆ "
                            + guess
                            + " → LOW";

        } else {

            result =
                    "⬇ "
                            + guess
                            + " → HIGH";
        }

        historyArea.append(
                result + "\n"
        );
    }

    // =========================================================
    // HINT
    // =========================================================

    private void giveHint() {

        if (!hintButton.isEnabled()) {
            return;
        }

        String hint;

        if (secretNumber % 2 == 0) {

            hint =
                    "💡 HINT: The number is EVEN.";

        } else {

            hint =
                    "💡 HINT: The number is ODD.";
        }

        showMessage(
                hint,
                gold
        );

        hintButton.setEnabled(false);
    }

    // =========================================================
    // GAME OVER
    // =========================================================

    private void gameOver(
            String reason
    ) {

        guessButton.setEnabled(false);

        hintButton.setEnabled(false);

        messageLabel.setText(
                reason
                        + " Number: "
                        + secretNumber
        );

        messageLabel.setForeground(
                danger
        );

        JOptionPane.showMessageDialog(
                this,
                "<html><center>"
                        + "<h2>"
                        + reason
                        + "</h2>"
                        + "<p>The hidden number was:</p>"
                        + "<h1>"
                        + secretNumber
                        + "</h1>"
                        + "<p>Press NEW GAME to try again.</p>"
                        + "</center></html>",
                "GAME OVER",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // =========================================================
    // MESSAGE
    // =========================================================

    private void showMessage(
            String text,
            Color color
    ) {

        messageLabel.setText(text);

        messageLabel.setForeground(color);
    }

    // =========================================================
    // DIFFICULTY
    // =========================================================

    private void changeDifficulty() {

        String[] options = {

                "EASY  •  1 — 50",
                "MEDIUM  •  1 — 100",
                "HARD  •  1 — 500"
        };

        String selected =
                (String) JOptionPane.showInputDialog(
                        this,
                        "Choose difficulty:",
                        "DIFFICULTY",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        options,
                        options[1]
                );

        if (selected == null) {
            return;
        }

        if (selected.startsWith("EASY")) {

            difficulty = "EASY";

            maxNumber = 50;

        } else if (
                selected.startsWith("HARD")
        ) {

            difficulty = "HARD";

            maxNumber = 500;

        } else {

            difficulty = "MEDIUM";

            maxNumber = 100;
        }

        startNewGame();
    }

    // =========================================================
    // THEME
    // =========================================================

    private void changeTheme() {

        String[] themes = {

                "PURPLE NEON",
                "CYAN CYBER",
                "GREEN MATRIX",
                "RED LASER"
        };

        String selected =
                (String) JOptionPane.showInputDialog(
                        this,
                        "Select theme:",
                        "THEME",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        themes,
                        themes[0]
                );

        if (selected == null) {
            return;
        }

        if (
                selected.equals(
                        "PURPLE NEON"
                )
        ) {

            accent =
                    new Color(
                            124,
                            77,
                            255
                    );

            accentLight =
                    new Color(
                            0,
                            220,
                            255
                    );

        } else if (
                selected.equals(
                        "CYAN CYBER"
                )
        ) {

            accent =
                    new Color(
                            0,
                            190,
                            255
                    );

            accentLight =
                    new Color(
                            80,
                            255,
                            230
                    );

        } else if (
                selected.equals(
                        "GREEN MATRIX"
                )
        ) {

            accent =
                    new Color(
                            30,
                            210,
                            100
                    );

            accentLight =
                    new Color(
                            100,
                            255,
                            160
                    );

        } else {

            accent =
                    new Color(
                            255,
                            55,
                            90
                    );

            accentLight =
                    new Color(
                            255,
                            150,
                            70
                    );
        }

        guessButton.setBackground(
                accent
        );

        repaint();
    }

    // =========================================================
    // STATISTICS
    // =========================================================

    private void showStatistics() {

        double winRate = 0;

        if (gamesPlayed > 0) {

            winRate =
                    ((double) gamesWon
                            / gamesPlayed)
                            * 100;
        }

        String best =
                bestScore == 0
                        ? "No score"
                        : bestScore
                        + " attempts";

        JOptionPane.showMessageDialog(
                this,
                "<html><center>"
                        + "<h2>📊 PLAYER STATISTICS</h2>"
                        + "<hr>"
                        + "<b>GAMES PLAYED:</b> "
                        + gamesPlayed
                        + "<br><br>"
                        + "<b>GAMES WON:</b> "
                        + gamesWon
                        + "<br><br>"
                        + "<b>WIN RATE:</b> "
                        + String.format(
                                "%.1f",
                                winRate
                        )
                        + "%"
                        + "<br><br>"
                        + "<b>CURRENT STREAK:</b> "
                        + currentStreak
                        + "<br><br>"
                        + "<b>BEST STREAK:</b> "
                        + bestStreak
                        + "<br><br>"
                        + "<b>BEST SCORE:</b> "
                        + best
                        + "</center></html>",
                "STATISTICS",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // =========================================================
    // PLAYER UI
    // =========================================================

    private void updatePlayerUI() {

        streakLabel.setText(
                "🔥 STREAK "
                        + currentStreak
        );

        if (bestScore > 0) {

            bestLabel.setText(
                    "🏆 BEST "
                            + bestScore
            );

        } else {

            bestLabel.setText(
                    "🏆 BEST --"
            );
        }
    }

    // =========================================================
    // SAVE
    // =========================================================

    private void saveProgress() {

        preferences.putInt(
                "gamesPlayed",
                gamesPlayed
        );

        preferences.putInt(
                "gamesWon",
                gamesWon
        );

        preferences.putInt(
                "currentStreak",
                currentStreak
        );

        preferences.putInt(
                "bestStreak",
                bestStreak
        );

        preferences.putInt(
                "bestScore",
                bestScore
        );
    }

    // =========================================================
    // LOAD
    // =========================================================

    private void loadProgress() {

        gamesPlayed =
                preferences.getInt(
                        "gamesPlayed",
                        0
                );

        gamesWon =
                preferences.getInt(
                        "gamesWon",
                        0
                );

        currentStreak =
                preferences.getInt(
                        "currentStreak",
                        0
                );

        bestStreak =
                preferences.getInt(
                        "bestStreak",
                        0
                );

        bestScore =
                preferences.getInt(
                        "bestScore",
                        0
                );
    }

    // =========================================================
    // RESET
    // =========================================================

    private void resetProgress() {

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Erase all player progress?",
                        "RESET PROGRESS",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (
                result !=
                        JOptionPane.YES_OPTION
        ) {

            return;
        }

        gamesPlayed = 0;

        gamesWon = 0;

        currentStreak = 0;

        bestStreak = 0;

        bestScore = 0;

        saveProgress();

        updatePlayerUI();

        JOptionPane.showMessageDialog(
                this,
                "🧹 Progress reset successfully!",
                "RESET COMPLETE",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // =========================================================
    // MAIN
    // =========================================================

    public static void main(
            String[] args
    ) {

        SwingUtilities.invokeLater(
                () -> {

                    NumberGame game =
                            new NumberGame();

                    game.setVisible(true);
                }
        );
    }
}