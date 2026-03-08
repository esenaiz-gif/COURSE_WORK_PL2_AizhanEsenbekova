import java.util.*;

public class BudgetSystem {

    // ===== STORAGE =====

    // Stores balance of each account (AccountName -> Balance)
    static HashMap<String, Integer> balanceByAccount = new HashMap<>();

    // Stores total spending by category (Category -> Amount spent)
    static HashMap<String, Integer> categorySpend = new HashMap<>();

    // Stores goal targets (GoalName -> Target amount)
    static HashMap<String, Integer> goalTarget = new HashMap<>();

    // Stores how much money is saved for each goal (GoalName -> Saved amount)
    static HashMap<String, Integer> goalSaved = new HashMap<>();

    // Stores locked accounts that cannot withdraw/transfer
    static HashSet<String> lockedAccounts = new HashSet<>();

    // optional

    // Stores budget limit for categories (Category -> Budget)
    static HashMap<String, Integer> categoryBudget = new HashMap<>();

    // Stores history of all operations
    static ArrayList<String> ledger = new ArrayList<>();

    // Percentage used for automatic saving rule
    static int autoSavePercent = 0;

    // Default goal name used for auto saving
    static String defaultGoal = "AUTO_GOAL";

    public static void main(String[] args) {

        // Scanner to read user input from console
        Scanner sc = new Scanner(System.in);

        // Infinite loop to keep the program running
        while (true) {

            // Print available commands
            printMenu();
            System.out.print("Command: ");

            // Read command and convert to uppercase
            String cmd = sc.nextLine().trim().toUpperCase();

            try {

                // Command processing
                switch (cmd) {

                    case "HELP": help(); break;

                    case "OPEN":
                        System.out.print("Account: ");
                        open(sc.nextLine());
                        break;

                    case "DEPOSIT":
                        System.out.print("Account: ");
                        String acc = sc.nextLine();
                        System.out.print("Amount: ");
                        deposit(acc, Integer.parseInt(sc.nextLine()));
                        break;

                    case "WITHDRAW":
                        System.out.print("Account: ");
                        acc = sc.nextLine();
                        System.out.print("Amount: ");
                        withdraw(acc, Integer.parseInt(sc.nextLine()));
                        break;

                    case "TRANSFER":
                        System.out.print("From: ");
                        String from = sc.nextLine();
                        System.out.print("To: ");
                        String to = sc.nextLine();
                        System.out.print("Amount: ");
                        transfer(from, to, Integer.parseInt(sc.nextLine()));
                        break;

                    case "BALANCE":
                        System.out.print("Account: ");
                        balance(sc.nextLine());
                        break;

                    case "ACCOUNTS":
                        accounts();
                        break;

                    case "SPEND":
                        System.out.print("Account: ");
                        acc = sc.nextLine();
                        System.out.print("Category: ");
                        String cat = sc.nextLine();
                        System.out.print("Amount: ");
                        spend(acc, cat, Integer.parseInt(sc.nextLine()));
                        break;

                    case "CATEGORY":
                        System.out.print("Category: ");
                        category(sc.nextLine());
                        break;

                    case "TOP_CATEGORIES":
                        System.out.print("k: ");
                        topCategories(Integer.parseInt(sc.nextLine()));
                        break;

                    case "SET_GOAL":
                        System.out.print("Goal: ");
                        String goal = sc.nextLine();
                        System.out.print("Target: ");
                        setGoal(goal, Integer.parseInt(sc.nextLine()));
                        break;

                    case "SAVE":
                        System.out.print("Account: ");
                        acc = sc.nextLine();
                        System.out.print("Goal: ");
                        goal = sc.nextLine();
                        System.out.print("Amount: ");
                        save(acc, goal, Integer.parseInt(sc.nextLine()));
                        break;

                    case "GOAL_STATUS":
                        System.out.print("Goal: ");
                        goalStatus(sc.nextLine());
                        break;

                    case "GOALS":
                        goals();
                        break;

                    case "LOCK":
                        System.out.print("Account: ");
                        lock(sc.nextLine());
                        break;

                    case "UNLOCK":
                        System.out.print("Account: ");
                        unlock(sc.nextLine());
                        break;

                    case "LEDGER":
                        ledger();
                        break;

                    case "RESET":
                        reset();
                        break;

                    case "SET_BUDGET":
                        System.out.print("Category: ");
                        cat = sc.nextLine();
                        System.out.print("Amount: ");
                        setBudget(cat, Integer.parseInt(sc.nextLine()));
                        break;

                    case "BUDGET_STATUS":
                        System.out.print("Category: ");
                        budgetStatus(sc.nextLine());
                        break;

                    case "RULE_SAVE":
                        System.out.print("Percent: ");
                        ruleSave(Integer.parseInt(sc.nextLine()));
                        break;

                    case "APPLY_RULES":
                        applyRules();
                        break;

                    case "REPORT":
                        report();
                        break;

                    case "EXIT":
                        // Exit program
                        System.out.println("Bye.");
                        return;

                    default:
                        System.out.println("Unknown command.");
                }

            } catch (Exception e) {
                // Handles invalid input (e.g., wrong number format)
                System.out.println("Invalid input.");
            }
        }
    }

    // ===== MENU =====

    // Prints list of available commands
    static void printMenu() {
        System.out.println("\nCommands:");
        System.out.println("HELP \nOPEN \nDEPOSIT \nWITHDRAW \nTRANSFER \nBALANCE");
        System.out.println("ACCOUNTS \nSPEND \nCATEGORY \nTOP_CATEGORIES");
        System.out.println("SET_GOAL \nSAVE GOAL_STATUS \nGOALS");
        System.out.println("LOCK UNLOCK \nLEDGER \nRESET");
        System.out.println("SET_BUDGET \nBUDGET_STATUS");
        System.out.println("RULE_SAVE \nAPPLY_RULES");
        System.out.println("REPORT \nEXIT");

    }

    // Displays help information
    static void help() {
        System.out.println("Budget system commands list.");
    }

    // ===== ACCOUNT COMMANDS =====

    // Creates a new account
    static void open(String acc) {

        // Check if account already exists
        if (balanceByAccount.putIfAbsent(acc, 0) != null) {
            System.out.println("Account exists👍");
            return;
        }

        // Record operation
        ledger.add("OPEN " + acc);

        System.out.println("Account created:🥳");
    }

    // Adds money to an account
    static void deposit(String acc, int amount) {

        if (!balanceByAccount.containsKey(acc)) {
            System.out.println("Account not found❌");
            return;
        }

        if (amount <= 0) {
            System.out.println("Invalid amount❌");
            return;
        }

        // Increase account balance
        balanceByAccount.put(acc, balanceByAccount.get(acc) + amount);

        ledger.add("DEPOSIT " + acc + " " + amount + " KGS");
        System.out.println("Deposit complete💚");
    }

    // Withdraws money from an account
    static void withdraw(String acc, int amount) {

        if (!balanceByAccount.containsKey(acc)) {
            System.out.println("Account not found❌");
            return;
        }

        // Prevent withdrawal from locked accounts
        if (lockedAccounts.contains(acc)) {
            System.out.println("Account locked🚫");
            return;
        }

        // Check if enough money
        if (balanceByAccount.get(acc) < amount) {
            System.out.println("Insufficient funds❌");
            return;
        }

        // Reduce balance
        balanceByAccount.put(acc, balanceByAccount.get(acc) - amount);

        ledger.add("WITHDRAW " + acc + " " + amount + " KGS");
        System.out.println("Withdraw complete💚");
    }

    // Transfers money between two accounts
    static void transfer(String from, String to, int amount) {

        if (!balanceByAccount.containsKey(from) ||
                !balanceByAccount.containsKey(to)) {
            System.out.println("Account not found❌");
            return;
        }

        if (lockedAccounts.contains(from)) {
            System.out.println("Account locked🚫");
            return;
        }

        if (balanceByAccount.get(from) < amount) {
            System.out.println("Insufficient funds❌");
            return;
        }

        // Deduct from sender
        balanceByAccount.put(from, balanceByAccount.get(from) - amount);

        // Add to receiver
        balanceByAccount.put(to, balanceByAccount.get(to) + amount);

        ledger.add("TRANSFER " + from + " " + to + " " + amount + " KGS");
        System.out.println("Transfer complete👍");
    }

    // Shows balance of an account
    static void balance(String acc) {

        if (!balanceByAccount.containsKey(acc)) {
            System.out.println("Account not found❌");
            return;
        }

        System.out.println("Balance: " + balanceByAccount.get(acc) + " KGS");
    }

    // Prints all accounts and balances
    static void accounts() {

        for (String acc : balanceByAccount.keySet()) {
            System.out.println(acc + " : " + balanceByAccount.get(acc) + " KGS");
        }
    }

    // ===== SPENDING =====

    // Records spending in a category
    static void spend(String acc, String cat, int amount) {

        // Withdraw money from account
        withdraw(acc, amount);

        // Add spending to category total
        int current = categorySpend.getOrDefault(cat, 0);
        categorySpend.put(cat, current + amount);

        // Check if category exceeded budget
        checkAlert(cat);

        ledger.add("SPEND " + acc + " " + cat + " " + amount + " KGS");
    }

    // Shows total spent in a category
    static void category(String cat) {

        int total = categorySpend.getOrDefault(cat, 0);
        System.out.println(cat + " spent: " + total + " KGS");
    }

    // Shows top K spending categories
    static void topCategories(int k) {

        ArrayList<String> keys = new ArrayList<>(categorySpend.keySet());

        // Selection sort by spending amount
        for (int i = 0; i < keys.size(); i++) {

            int max = i;

            for (int j = i + 1; j < keys.size(); j++) {

                if (categorySpend.get(keys.get(j)) >
                        categorySpend.get(keys.get(max))) {

                    max = j;
                }
            }

            String tmp = keys.get(i);
            keys.set(i, keys.get(max));
            keys.set(max, tmp);
        }

        // Print top K categories
        for (int i = 0; i < Math.min(k, keys.size()); i++) {

            String c = keys.get(i);
            System.out.println(c + " : " + categorySpend.get(c) + " KGS");
        }
    }

    // ===== GOALS =====

    // Creates a savings goal
    static void setGoal(String goal, int target) {

        if (target <= 0) {
            System.out.println("Invalid target❌");
            return;
        }

        goalTarget.put(goal, target);
        goalSaved.putIfAbsent(goal, 0);

        ledger.add("SET_GOAL " + goal + " KGS");
    }

    // Saves money to a goal
    static void save(String acc, String goal, int amount) {

        withdraw(acc, amount);

        if (!goalTarget.containsKey(goal)) {
            System.out.println("Goal not found🚫");
            return;
        }

        goalSaved.put(goal, goalSaved.get(goal) + amount);

        ledger.add("SAVE " + acc + " " + goal + " " + amount + " KGS");
    }

    // Shows goal progress
    static void goalStatus(String goal) {

        if (!goalTarget.containsKey(goal)) {
            System.out.println("Goal not found🚫");
            return;
        }

        int saved = goalSaved.get(goal);
        int target = goalTarget.get(goal);

        int percent = Math.min(100, saved * 100 / target);

        System.out.println("Saved: " + saved + " KGS" +
                " Target: " + target +
                " Progress: " + percent + "%");
    }

    // Shows all goals
    static void goals() {

        for (String g : goalTarget.keySet()) {
            System.out.println(g + " " +
                    goalSaved.get(g) + "/" +
                    goalTarget.get(g));
        }
    }

    // ===== LOCK =====

    // Locks an account
    static void lock(String acc) {

        if (!balanceByAccount.containsKey(acc)) {
            System.out.println("Account not found❌");
            return;
        }

        lockedAccounts.add(acc);
    }

    // Unlocks an account
    static void unlock(String acc) {

        if (!balanceByAccount.containsKey(acc)) {
            System.out.println("Account not found❌");
            return;
        }

        lockedAccounts.remove(acc);
    }

    // ===== OPTIONAL FEATURES =====

    // Sets budget for a category
    static void setBudget(String cat, int amount) {

        categoryBudget.put(cat, amount);
    }

    // Shows spending vs budget
    static void budgetStatus(String cat) {

        int spent = categorySpend.getOrDefault(cat, 0);
        int budget = categoryBudget.getOrDefault(cat, 0);

        System.out.println(cat + " : " + spent + " KGS" + "/" + budget + " KGS");
    }

    // Sets auto save percentage
    static void ruleSave(int percent) {

        autoSavePercent = percent;
        System.out.println("Rule set");
    }

    // Applies auto save rule to all accounts
    static void applyRules() {

        for (String acc : balanceByAccount.keySet()) {

            int bal = balanceByAccount.get(acc);

            int save = bal * autoSavePercent / 100;

            if (save > 0) {

                balanceByAccount.put(acc, bal - save);

                goalSaved.put(defaultGoal,
                        goalSaved.getOrDefault(defaultGoal, 0) + save);
            }
        }
    }

    // Generates system report
    static void report() {

        System.out.println("\n=== REPORT ===");

        accounts();

        System.out.println("\nGoals:");
        goals();

        System.out.println("\nTop categories:");
        topCategories(3);
    }

    // Checks if category exceeded budget
    static void checkAlert(String cat) {

        if (!categoryBudget.containsKey(cat)) return;

        int spent = categorySpend.get(cat);
        int budget = categoryBudget.get(cat);

        if (spent > budget) {
            System.out.println("ALERT: budget exceeded for " + cat + " KGS");
        }
    }

    // ===== UTIL =====

    // Prints operation history
    static void ledger() {

        for (String s : ledger) {
            System.out.println(s);
        }
    }

    // Clears all data and resets system
    static void reset() {

        balanceByAccount.clear();
        categorySpend.clear();
        goalTarget.clear();
        goalSaved.clear();
        lockedAccounts.clear();
        ledger.clear();

        System.out.println("System reset💭");
    }
}