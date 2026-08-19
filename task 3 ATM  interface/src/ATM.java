import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ATM extends JFrame {

    // ============================================================
    // ACCOUNT INFORMATION
    // ============================================================

    private final String accountName = "Cheerla Radhika";
    private final String accountNumber = "XXXX XXXX 4582";

    private String pin = "1234";
    private double balance = 25000.00;

    private final List<String> transactions = new ArrayList<>();

    private final String DATA_FILE = "atm_data.txt";

    // ============================================================
    // COLORS
    // ============================================================

    private final Color BG = new Color(8, 13, 25);
    private final Color PANEL = new Color(20, 28, 45);
    private final Color PANEL_DARK = new Color(15, 22, 36);

    private final Color PRIMARY = new Color(59, 130, 246);
    private final Color PRIMARY_HOVER = new Color(79, 148, 255);

    private final Color SUCCESS = new Color(34, 197, 94);
    private final Color SUCCESS_HOVER = new Color(55, 215, 115);

    private final Color DANGER = new Color(239, 68, 68);
    private final Color DANGER_HOVER = new Color(250, 85, 85);

    private final Color PURPLE = new Color(139, 92, 246);
    private final Color PURPLE_HOVER = new Color(158, 112, 255);

    private final Color TEXT = new Color(248, 250, 252);
    private final Color MUTED = new Color(156, 163, 175);

    private final Color BORDER = new Color(55, 65, 85);

    // ============================================================
    // CONSTRUCTOR
    // ============================================================

    public ATM() {

        setTitle("ATM Banking System");

        setSize(1100, 720);

        setMinimumSize(
                new Dimension(950, 620)
        );

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);

        setResizable(true);

        loadData();

        showLogin();
    }

    // ============================================================
    // LOAD DATA
    // ============================================================

    private void loadData() {

        File file = new File(DATA_FILE);

        if (!file.exists()) {

            transactions.add(
                    getCurrentTime()
                            + " | Account opened | +₹25,000.00"
            );

            saveData();

            return;
        }

        try {

            BufferedReader reader =
                    new BufferedReader(
                            new FileReader(file)
                    );

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.startsWith("PIN=")) {

                    pin = line.substring(4);

                } else if (line.startsWith("BALANCE=")) {

                    balance =
                            Double.parseDouble(
                                    line.substring(8)
                            );

                } else if (line.startsWith("TRANSACTION=")) {

                    transactions.add(
                            line.substring(12)
                    );
                }
            }

            reader.close();

        } catch (Exception e) {

            pin = "1234";
            balance = 25000.00;
        }
    }

    // ============================================================
    // SAVE DATA
    // ============================================================

    private void saveData() {

        try {

            BufferedWriter writer =
                    new BufferedWriter(
                            new FileWriter(DATA_FILE)
                    );

            writer.write("PIN=" + pin);
            writer.newLine();

            writer.write("BALANCE=" + balance);
            writer.newLine();

            for (String transaction : transactions) {

                writer.write(
                        "TRANSACTION=" + transaction
                );

                writer.newLine();
            }

            writer.close();

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to save ATM data.",
                    "Storage Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ============================================================
    // LOGIN SCREEN
    // ============================================================

    private void showLogin() {

        JPanel background =
                new JPanel(
                        new GridBagLayout()
                );

        background.setBackground(BG);

        RoundedPanel card =
                new RoundedPanel(30);

        card.setBackground(PANEL);

        card.setPreferredSize(
                new Dimension(470, 570)
        );

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );

        card.setBorder(
                new EmptyBorder(
                        40,
                        55,
                        40,
                        55
                )
        );

        // LOGO

        JLabel logo =
                new JLabel("ATM");

        logo.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        logo.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        logo.setForeground(Color.WHITE);

        logo.setBackground(PRIMARY);

        logo.setOpaque(true);

        logo.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        30
                )
        );

        logo.setPreferredSize(
                new Dimension(100, 70)
        );

        logo.setMaximumSize(
                new Dimension(100, 70)
        );

        // TITLE

        JLabel title =
                new JLabel(
                        "Welcome Back"
                );

        title.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        title.setForeground(TEXT);

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        30
                )
        );

        // SUBTITLE

        JLabel subtitle =
                new JLabel(
                        "Secure access to your banking account"
                );

        subtitle.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        subtitle.setForeground(MUTED);

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        // ACCOUNT LABEL

        JLabel accountLabel =
                createLabel(
                        "ACCOUNT NUMBER"
                );

        // ACCOUNT FIELD

        JTextField accountField =
                createTextField();

        accountField.setText(
                accountNumber
        );

        // PIN LABEL

        JLabel pinLabel =
                createLabel(
                        "SECURITY PIN"
                );

        // PIN FIELD

        JPasswordField pinField =
                new JPasswordField();

        styleTextField(
                pinField
        );

        // LOGIN BUTTON

        CustomButton loginButton =
                new CustomButton(
                        "SIGN IN",
                        PRIMARY,
                        PRIMARY_HOVER
                );

        loginButton.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        52
                )
        );

        // SECURITY LABEL

        JLabel security =
                new JLabel(
                        "🔒  Secure banking session"
                );

        security.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        security.setForeground(
                SUCCESS
        );

        security.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        // DEMO PIN

        JLabel demo =
                new JLabel(
                        "Demo PIN: " + pin
                );

        demo.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        demo.setForeground(
                new Color(
                        100,
                        115,
                        140
                )
        );

        demo.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        // ADD

        card.add(logo);

        card.add(
                Box.createVerticalStrut(25)
        );

        card.add(title);

        card.add(
                Box.createVerticalStrut(7)
        );

        card.add(subtitle);

        card.add(
                Box.createVerticalStrut(35)
        );

        card.add(accountLabel);

        card.add(
                Box.createVerticalStrut(8)
        );

        card.add(accountField);

        card.add(
                Box.createVerticalStrut(20)
        );

        card.add(pinLabel);

        card.add(
                Box.createVerticalStrut(8)
        );

        card.add(pinField);

        card.add(
                Box.createVerticalStrut(28)
        );

        card.add(loginButton);

        card.add(
                Box.createVerticalStrut(20)
        );

        card.add(security);

        card.add(
                Box.createVerticalStrut(12)
        );

        card.add(demo);

        // LOGIN ACTION

        loginButton.addActionListener(
                e -> {

                    String enteredAccount =
                            accountField
                                    .getText()
                                    .trim();

                    String enteredPin =
                            new String(
                                    pinField
                                            .getPassword()
                            );

                    if (
                            enteredAccount.equals(
                                    accountNumber
                            )
                                    &&
                            enteredPin.equals(pin)
                    ) {

                        showDashboard();

                    } else {

                        JOptionPane.showMessageDialog(
                                this,
                                "Invalid account number or PIN.",
                                "Login Failed",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
        );

        background.add(card);

        setContentPane(background);

        revalidate();

        repaint();
    }

    // ============================================================
    // DASHBOARD
    // ============================================================

    private void showDashboard() {

        JPanel main =
                new JPanel(
                        new BorderLayout()
                );

        main.setBackground(BG);

        // ========================================================
        // HEADER
        // ========================================================

        JPanel header =
                new JPanel(
                        new BorderLayout()
                );

        header.setBackground(PANEL);

        header.setBorder(
                new EmptyBorder(
                        18,
                        30,
                        18,
                        30
                )
        );

        JLabel brand =
                new JLabel(
                        "ATM BANKING"
                );

        brand.setForeground(TEXT);

        brand.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        24
                )
        );

        JLabel secure =
                new JLabel(
                        "● SECURE SESSION"
                );

        secure.setForeground(SUCCESS);

        secure.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        JPanel brandPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                0,
                                0
                        )
                );

        brandPanel.setOpaque(false);

        brandPanel.add(brand);

        brandPanel.add(
                Box.createHorizontalStrut(20)
        );

        brandPanel.add(secure);

        CustomButton logout =
                new CustomButton(
                        "LOGOUT",
                        DANGER,
                        DANGER_HOVER
                );

        logout.setPreferredSize(
                new Dimension(
                        110,
                        40
                )
        );

        logout.addActionListener(
                e -> showLogin()
        );

        header.add(
                brandPanel,
                BorderLayout.WEST
        );

        header.add(
                logout,
                BorderLayout.EAST
        );

        // ========================================================
        // CONTENT
        // ========================================================

        JPanel content =
                new JPanel(
                        new BorderLayout(
                                25,
                                25
                        )
                );

        content.setBackground(BG);

        content.setBorder(
                new EmptyBorder(
                        30,
                        35,
                        30,
                        35
                )
        );

        // ========================================================
        // LEFT CONTENT
        // ========================================================

        JPanel left =
                new JPanel();

        left.setBackground(BG);

        left.setLayout(
                new BoxLayout(
                        left,
                        BoxLayout.Y_AXIS
                )
        );

        // WELCOME

        JLabel welcome =
                new JLabel(
                        "Hello, " + accountName
                );

        welcome.setForeground(TEXT);

        welcome.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        30
                )
        );

        JLabel number =
                new JLabel(
                        accountNumber
                );

        number.setForeground(MUTED);

        number.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15
                )
        );

        left.add(welcome);

        left.add(
                Box.createVerticalStrut(5)
        );

        left.add(number);

        left.add(
                Box.createVerticalStrut(25)
        );

        // ========================================================
        // BALANCE CARD
        // ========================================================

        RoundedPanel balanceCard =
                new RoundedPanel(25);

        balanceCard.setBackground(
                PRIMARY
        );

        balanceCard.setPreferredSize(
                new Dimension(
                        600,
                        190
                )
        );

        balanceCard.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        190
                )
        );

        balanceCard.setLayout(
                new BoxLayout(
                        balanceCard,
                        BoxLayout.Y_AXIS
                )
        );

        balanceCard.setBorder(
                new EmptyBorder(
                        25,
                        30,
                        25,
                        30
                )
        );

        JLabel balanceTitle =
                new JLabel(
                        "TOTAL AVAILABLE BALANCE"
                );

        balanceTitle.setForeground(
                Color.WHITE
        );

        balanceTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        JLabel balanceValue =
                new JLabel(
                        "₹ " +
                                String.format(
                                        "%,.2f",
                                        balance
                                )
                );

        balanceValue.setForeground(
                Color.WHITE
        );

        balanceValue.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        42
                )
        );

        JLabel balanceInfo =
                new JLabel(
                        "Available for withdrawal and transfer"
                );

        balanceInfo.setForeground(
                new Color(
                        225,
                        235,
                        255
                )
        );

        balanceInfo.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        balanceCard.add(
                balanceTitle
        );

        balanceCard.add(
                Box.createVerticalStrut(8)
        );

        balanceCard.add(
                balanceValue
        );

        balanceCard.add(
                Box.createVerticalStrut(5)
        );

        balanceCard.add(
                balanceInfo
        );

        left.add(balanceCard);

        left.add(
                Box.createVerticalStrut(28)
        );

        // ========================================================
        // QUICK SERVICES
        // ========================================================

        JLabel servicesTitle =
                new JLabel(
                        "Quick Services"
                );

        servicesTitle.setForeground(TEXT);

        servicesTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        21
                )
        );

        left.add(
                servicesTitle
        );

        left.add(
                Box.createVerticalStrut(15)
        );

        JPanel buttons =
                new JPanel(
                        new GridLayout(
                                2,
                                3,
                                15,
                                15
                        )
                );

        buttons.setBackground(BG);

        // CHECK BALANCE

        CustomServiceButton checkBalance =
                new CustomServiceButton(
                        "💰",
                        "Check Balance",
                        PRIMARY,
                        PRIMARY_HOVER
                );

        // WITHDRAW

        CustomServiceButton withdraw =
                new CustomServiceButton(
                        "💸",
                        "Withdraw",
                        DANGER,
                        DANGER_HOVER
                );

        // DEPOSIT

        CustomServiceButton deposit =
                new CustomServiceButton(
                        "💵",
                        "Deposit",
                        SUCCESS,
                        SUCCESS_HOVER
                );

        // TRANSFER

        CustomServiceButton transfer =
                new CustomServiceButton(
                        "↔",
                        "Transfer",
                        PURPLE,
                        PURPLE_HOVER
                );

        // STATEMENT

        CustomServiceButton statement =
                new CustomServiceButton(
                        "📜",
                        "Mini Statement",
                        PANEL,
                        new Color(
                                35,
                                45,
                                65
                        )
                );

        // CHANGE PIN

        CustomServiceButton changePin =
                new CustomServiceButton(
                        "🔑",
                        "Change PIN",
                        PANEL,
                        new Color(
                                35,
                                45,
                                65
                        )
                );

        buttons.add(checkBalance);
        buttons.add(withdraw);
        buttons.add(deposit);
        buttons.add(transfer);
        buttons.add(statement);
        buttons.add(changePin);

        left.add(buttons);

        // ACTIONS

        checkBalance.addActionListener(
                e -> checkBalance()
        );

        withdraw.addActionListener(
                e -> withdrawMoney()
        );

        deposit.addActionListener(
                e -> depositMoney()
        );

        transfer.addActionListener(
                e -> transferMoney()
        );

        statement.addActionListener(
                e -> showStatement()
        );

        changePin.addActionListener(
                e -> changePin()
        );

        // ========================================================
        // RIGHT SUMMARY
        // ========================================================

        RoundedPanel right =
                new RoundedPanel(25);

        right.setBackground(PANEL);

        right.setPreferredSize(
                new Dimension(
                        280,
                        500
                )
        );

        right.setLayout(
                new BoxLayout(
                        right,
                        BoxLayout.Y_AXIS
                )
        );

        right.setBorder(
                new EmptyBorder(
                        30,
                        30,
                        30,
                        30
                )
        );

        JLabel summaryTitle =
                new JLabel(
                        "Account Summary"
                );

        summaryTitle.setForeground(TEXT);

        summaryTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        21
                )
        );

        right.add(summaryTitle);

        right.add(
                Box.createVerticalStrut(30)
        );

        addSummary(
                right,
                "ACCOUNT TYPE",
                "Savings Account"
        );

        addSummary(
                right,
                "ACCOUNT NUMBER",
                accountNumber
        );

        addSummary(
                right,
                "TRANSACTIONS",
                String.valueOf(
                        transactions.size()
                )
        );

        addSummary(
                right,
                "STATUS",
                "Active"
        );

        right.add(
                Box.createVerticalGlue()
        );

        JLabel secureBanking =
                new JLabel(
                        "✓  Secure Banking"
                );

        secureBanking.setForeground(
                SUCCESS
        );

        secureBanking.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        right.add(
                secureBanking
        );

        // ========================================================
        // FINAL LAYOUT
        // ========================================================

        content.add(
                left,
                BorderLayout.CENTER
        );

        content.add(
                right,
                BorderLayout.EAST
        );

        main.add(
                header,
                BorderLayout.NORTH
        );

        main.add(
                content,
                BorderLayout.CENTER
        );

        setContentPane(main);

        revalidate();

        repaint();
    }

    // ============================================================
    // SUMMARY
    // ============================================================

    private void addSummary(
            JPanel panel,
            String title,
            String value
    ) {

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setForeground(
                MUTED
        );

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        11
                )
        );

        JLabel valueLabel =
                new JLabel(value);

        valueLabel.setForeground(
                TEXT
        );

        valueLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15
                )
        );

        panel.add(titleLabel);

        panel.add(
                Box.createVerticalStrut(5)
        );

        panel.add(valueLabel);

        panel.add(
                Box.createVerticalStrut(22)
        );
    }

    // ============================================================
    // CHECK BALANCE
    // ============================================================

    private void checkBalance() {

        JOptionPane.showMessageDialog(
                this,
                "CURRENT AVAILABLE BALANCE\n\n₹ "
                        +
                        String.format(
                                "%,.2f",
                                balance
                        ),
                "Account Balance",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // ============================================================
    // WITHDRAW
    // ============================================================

    private void withdrawMoney() {

        String input =
                JOptionPane.showInputDialog(
                        this,
                        "Enter amount to withdraw:",
                        "Withdraw Money",
                        JOptionPane.PLAIN_MESSAGE
                );

        if (input == null) {
            return;
        }

        try {

            double amount =
                    Double.parseDouble(
                            input.trim()
                    );

            if (amount <= 0) {

                showError(
                        "Enter a valid amount."
                );

            } else if (amount > balance) {

                showError(
                        "Insufficient balance."
                );

            } else if (amount % 100 != 0) {

                showError(
                        "ATM accepts amounts in multiples of ₹100."
                );

            } else {

                balance -= amount;

                transactions.add(
                        getCurrentTime()
                                + " | Withdrawal | -₹"
                                + String.format(
                                "%.2f",
                                amount
                        )
                );

                saveData();

                JOptionPane.showMessageDialog(
                        this,
                        "Withdrawal Successful!\n\n"
                                + "Amount: ₹"
                                + String.format(
                                "%,.2f",
                                amount
                        )
                                + "\nRemaining Balance: ₹"
                                + String.format(
                                "%,.2f",
                                balance
                        ),
                        "Transaction Successful",
                        JOptionPane.INFORMATION_MESSAGE
                );

                showDashboard();
            }

        } catch (NumberFormatException e) {

            showError(
                    "Please enter a valid number."
            );
        }
    }

    // ============================================================
    // DEPOSIT
    // ============================================================

    private void depositMoney() {

        String input =
                JOptionPane.showInputDialog(
                        this,
                        "Enter amount to deposit:",
                        "Deposit Money",
                        JOptionPane.PLAIN_MESSAGE
                );

        if (input == null) {
            return;
        }

        try {

            double amount =
                    Double.parseDouble(
                            input.trim()
                    );

            if (amount <= 0) {

                showError(
                        "Enter a valid amount."
                );

            } else {

                balance += amount;

                transactions.add(
                        getCurrentTime()
                                + " | Deposit | +₹"
                                + String.format(
                                "%.2f",
                                amount
                        )
                );

                saveData();

                JOptionPane.showMessageDialog(
                        this,
                        "Deposit Successful!\n\n"
                                + "Amount: ₹"
                                + String.format(
                                "%,.2f",
                                amount
                        )
                                + "\nNew Balance: ₹"
                                + String.format(
                                "%,.2f",
                                balance
                        ),
                        "Deposit Complete",
                        JOptionPane.INFORMATION_MESSAGE
                );

                showDashboard();
            }

        } catch (NumberFormatException e) {

            showError(
                    "Please enter a valid number."
            );
        }
    }

    // ============================================================
    // TRANSFER
    // ============================================================

    private void transferMoney() {

        JTextField receiverField =
                createTextField();

        JTextField amountField =
                createTextField();

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                4,
                                1,
                                8,
                                8
                        )
                );

        panel.setBorder(
                new EmptyBorder(
                        15,
                        15,
                        15,
                        15
                )
        );

        panel.add(
                new JLabel(
                        "Receiver Account Number:"
                )
        );

        panel.add(receiverField);

        panel.add(
                new JLabel(
                        "Amount:"
                )
        );

        panel.add(amountField);

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Money Transfer",
                        JOptionPane.OK_CANCEL_OPTION
                );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String receiver =
                receiverField
                        .getText()
                        .trim();

        if (receiver.isEmpty()) {

            showError(
                    "Enter receiver account number."
            );

            return;
        }

        try {

            double amount =
                    Double.parseDouble(
                            amountField
                                    .getText()
                                    .trim()
                    );

            if (amount <= 0) {

                showError(
                        "Enter a valid amount."
                );

            } else if (amount > balance) {

                showError(
                        "Insufficient balance."
                );

            } else {

                balance -= amount;

                transactions.add(
                        getCurrentTime()
                                + " | Transfer to "
                                + receiver
                                + " | -₹"
                                + String.format(
                                "%.2f",
                                amount
                        )
                );

                saveData();

                JOptionPane.showMessageDialog(
                        this,
                        "Transfer Successful!\n\n"
                                + "Receiver: "
                                + receiver
                                + "\nAmount: ₹"
                                + String.format(
                                "%,.2f",
                                amount
                        )
                                + "\nRemaining Balance: ₹"
                                + String.format(
                                "%,.2f",
                                balance
                        ),
                        "Transfer Complete",
                        JOptionPane.INFORMATION_MESSAGE
                );

                showDashboard();
            }

        } catch (NumberFormatException e) {

            showError(
                    "Please enter a valid amount."
            );
        }
    }

    // ============================================================
    // MINI STATEMENT
    // ============================================================

    private void showStatement() {

        JTextArea area =
                new JTextArea();

        area.setEditable(false);

        area.setFont(
                new Font(
                        "Monospaced",
                        Font.PLAIN,
                        13
                )
        );

        area.setBackground(BG);

        area.setForeground(TEXT);

        area.setBorder(
                new EmptyBorder(
                        15,
                        15,
                        15,
                        15
                )
        );

        StringBuilder statement =
                new StringBuilder();

        statement.append(
                "========================================\n"
        );

        statement.append(
                "              MINI STATEMENT\n"
        );

        statement.append(
                "========================================\n\n"
        );

        statement.append(
                "Account : "
        );

        statement.append(
                accountNumber
        );

        statement.append("\n");

        statement.append(
                "Holder  : "
        );

        statement.append(
                accountName
        );

        statement.append("\n\n");

        statement.append(
                "----------------------------------------\n"
        );

        for (String transaction :
                transactions) {

            statement.append(
                    transaction
            );

            statement.append("\n");
        }

        statement.append(
                "----------------------------------------\n"
        );

        statement.append(
                "\nCurrent Balance: ₹"
        );

        statement.append(
                String.format(
                        "%,.2f",
                        balance
                )
        );

        area.setText(
                statement.toString()
        );

        JScrollPane scroll =
                new JScrollPane(area);

        scroll.setPreferredSize(
                new Dimension(
                        720,
                        430
                )
        );

        JOptionPane.showMessageDialog(
                this,
                scroll,
                "Mini Statement",
                JOptionPane.PLAIN_MESSAGE
        );
    }

    // ============================================================
    // CHANGE PIN
    // ============================================================

    private void changePin() {

        JPasswordField oldPin =
                new JPasswordField();

        JPasswordField newPin =
                new JPasswordField();

        JPasswordField confirmPin =
                new JPasswordField();

        styleTextField(oldPin);
        styleTextField(newPin);
        styleTextField(confirmPin);

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                6,
                                1,
                                7,
                                7
                        )
                );

        panel.setBorder(
                new EmptyBorder(
                        15,
                        15,
                        15,
                        15
                )
        );

        panel.add(
                new JLabel(
                        "Current PIN:"
                )
        );

        panel.add(oldPin);

        panel.add(
                new JLabel(
                        "New PIN:"
                )
        );

        panel.add(newPin);

        panel.add(
                new JLabel(
                        "Confirm New PIN:"
                )
        );

        panel.add(confirmPin);

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Change PIN",
                        JOptionPane.OK_CANCEL_OPTION
                );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String oldValue =
                new String(
                        oldPin.getPassword()
                );

        String newValue =
                new String(
                        newPin.getPassword()
                );

        String confirmValue =
                new String(
                        confirmPin.getPassword()
                );

        if (!oldValue.equals(pin)) {

            showError(
                    "Current PIN is incorrect."
            );

            return;
        }

        if (!newValue.matches(
                "\\d{4}"
        )) {

            showError(
                    "New PIN must contain exactly 4 digits."
            );

            return;
        }

        if (!newValue.equals(
                confirmValue
        )) {

            showError(
                    "New PINs do not match."
            );

            return;
        }

        pin = newValue;

        transactions.add(
                getCurrentTime()
                        + " | PIN changed"
        );

        saveData();

        JOptionPane.showMessageDialog(
                this,
                "PIN changed successfully!",
                "Security Updated",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // ============================================================
    // TEXT FIELD
    // ============================================================

    private JTextField createTextField() {

        JTextField field =
                new JTextField();

        styleTextField(field);

        return field;
    }

    private void styleTextField(
            JTextField field
    ) {

        field.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        48
                )
        );

        field.setPreferredSize(
                new Dimension(
                        300,
                        48
                )
        );

        field.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        16
                )
        );

        field.setForeground(TEXT);

        field.setBackground(
                new Color(
                        11,
                        18,
                        31
                )
        );

        field.setCaretColor(TEXT);

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER,
                                1
                        ),
                        new EmptyBorder(
                                5,
                                12,
                                5,
                                12
                        )
                )
        );
    }

    // ============================================================
    // LABEL
    // ============================================================

    private JLabel createLabel(
            String text
    ) {

        JLabel label =
                new JLabel(text);

        label.setForeground(
                MUTED
        );

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        return label;
    }

    // ============================================================
    // ERROR
    // ============================================================

    private void showError(
            String message
    ) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Transaction Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    // ============================================================
    // DATE AND TIME
    // ============================================================

    private String getCurrentTime() {

        return new SimpleDateFormat(
                "dd-MM-yyyy HH:mm:ss"
        ).format(
                new Date()
        );
    }

    // ============================================================
    // ROUNDED PANEL
    // ============================================================

    private static class RoundedPanel
            extends JPanel {

        private final int radius;

        public RoundedPanel(
                int radius
        ) {

            this.radius = radius;

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
                    getBackground()
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
        }
    }

    // ============================================================
    // CUSTOM MAIN BUTTON
    // ============================================================

    private static class CustomButton
            extends JButton {

        private Color normalColor;
        private Color hoverColor;
        private boolean hovering = false;

        public CustomButton(
                String text,
                Color normalColor,
                Color hoverColor
        ) {

            super(text);

            this.normalColor =
                    normalColor;

            this.hoverColor =
                    hoverColor;

            setForeground(Color.WHITE);

            setFont(
                    new Font(
                            "Segoe UI",
                            Font.BOLD,
                            14
                    )
            );

            setFocusPainted(false);

            setBorderPainted(false);

            setContentAreaFilled(false);

            setOpaque(false);

            setCursor(
                    new Cursor(
                            Cursor.HAND_CURSOR
                    )
            );

            addMouseListener(
                    new MouseAdapter() {

                        @Override
                        public void mouseEntered(
                                MouseEvent e
                        ) {

                            hovering = true;

                            repaint();
                        }

                        @Override
                        public void mouseExited(
                                MouseEvent e
                        ) {

                            hovering = false;

                            repaint();
                        }
                    }
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

            g2.setColor(
                    hovering
                            ? hoverColor
                            : normalColor
            );

            g2.fillRoundRect(
                    0,
                    0,
                    getWidth(),
                    getHeight(),
                    12,
                    12
            );

            g2.setColor(Color.WHITE);

            FontMetrics fm =
                    g2.getFontMetrics(
                            getFont()
                    );

            int x =
                    (getWidth()
                            - fm.stringWidth(
                            getText()
                    )) / 2;

            int y =
                    (getHeight()
                            - fm.getHeight()) / 2
                            + fm.getAscent();

            g2.setFont(
                    getFont()
            );

            g2.drawString(
                    getText(),
                    x,
                    y
            );

            g2.dispose();
        }
    }

    // ============================================================
    // CUSTOM SERVICE BUTTON
    // ============================================================

    private static class CustomServiceButton
            extends JButton {

        private final String icon;
        private final String title;

        private final Color normalColor;
        private final Color hoverColor;

        private boolean hovering = false;

        public CustomServiceButton(
                String icon,
                String title,
                Color normalColor,
                Color hoverColor
        ) {

            this.icon = icon;

            this.title = title;

            this.normalColor =
                    normalColor;

            this.hoverColor =
                    hoverColor;

            setPreferredSize(
                    new Dimension(
                            180,
                            100
                    )
            );

            setMinimumSize(
                    new Dimension(
                            150,
                            90
                    )
            );

            setFocusPainted(false);

            setBorderPainted(false);

            setContentAreaFilled(false);

            setOpaque(false);

            setCursor(
                    new Cursor(
                            Cursor.HAND_CURSOR
                    )
            );

            addMouseListener(
                    new MouseAdapter() {

                        @Override
                        public void mouseEntered(
                                MouseEvent e
                        ) {

                            hovering = true;

                            repaint();
                        }

                        @Override
                        public void mouseExited(
                                MouseEvent e
                        ) {

                            hovering = false;

                            repaint();
                        }
                    }
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

            // BACKGROUND

            g2.setColor(
                    hovering
                            ? hoverColor
                            : normalColor
            );

            g2.fillRoundRect(
                    0,
                    0,
                    getWidth(),
                    getHeight(),
                    18,
                    18
            );

            // BORDER

            g2.setColor(
                    hovering
                            ? Color.WHITE
                            : new Color(
                                    70,
                                    85,
                                    110
                            )
            );

            g2.setStroke(
                    new BasicStroke(
                            1.2f
                    )
            );

            g2.drawRoundRect(
                    1,
                    1,
                    getWidth() - 2,
                    getHeight() - 2,
                    18,
                    18
            );

            // ICON

            g2.setFont(
                    new Font(
                            "Segoe UI Emoji",
                            Font.PLAIN,
                            27
                    )
            );

            FontMetrics iconMetrics =
                    g2.getFontMetrics();

            int iconX =
                    (getWidth()
                            - iconMetrics.stringWidth(
                            icon
                    )) / 2;

            int iconY =
                    40;

            g2.setColor(Color.WHITE);

            g2.drawString(
                    icon,
                    iconX,
                    iconY
            );

            // TEXT

            g2.setFont(
                    new Font(
                            "Segoe UI",
                            Font.BOLD,
                            14
                    )
            );

            FontMetrics textMetrics =
                    g2.getFontMetrics();

            int textX =
                    (getWidth()
                            - textMetrics.stringWidth(
                            title
                    )) / 2;

            int textY =
                    70;

            g2.setColor(
                    Color.WHITE
            );

            g2.drawString(
                    title,
                    textX,
                    textY
            );

            g2.dispose();
        }
    }

    // ============================================================
    // MAIN
    // ============================================================

    public static void main(
            String[] args
    ) {

        SwingUtilities.invokeLater(
                () -> {

                    try {

                        UIManager.setLookAndFeel(
                                UIManager
                                        .getSystemLookAndFeelClassName()
                        );

                    } catch (Exception ignored) {
                    }

                    new ATM().setVisible(true);
                }
        );
    }
}