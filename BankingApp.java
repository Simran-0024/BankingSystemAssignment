import java.util.*;

class Account {
    String acc_num, acc_holder, acc_type, branch;
    double balance;

    public Account(String acc_num, String acc_holder, double balance, String acc_type, String branch) {
        this.acc_num = acc_num;
        this.acc_holder = acc_holder;
        this.balance = balance;
        this.acc_type = acc_type;
        this.branch = branch;
    }
}

class AccountRepository {
    private Map<String, Account> account = new HashMap<>();

    public void addAccount(Account acc) { account.put(acc.acc_num, acc); }

    // Manual account creation, user enters the account number
    public void open_account(Scanner sc) {
        System.out.print("Enter account number: ");
        String acc_num = sc.nextLine();
        if (account.containsKey(acc_num)) {
            System.out.println("Account number already exists.");
            return;
        }
        System.out.print("Enter account holder name: ");
        String acc_holder = sc.nextLine();

        System.out.print("Enter initial balance: ");
        double balance = sc.nextDouble(); sc.nextLine();
        System.out.print("Enter account type: ");
        String acc_type = sc.nextLine();
        
        System.out.print("Enter branch: ");
        String branch = sc.nextLine();

        addAccount(new Account(acc_num, acc_holder, balance, acc_type, branch));
        System.out.println("Account created! Number: " + acc_num);
    }

    public void getAllAccountsInfo() {
        System.out.println("Account No. | Account Holder | Type | Branch | Balance");
        for (Account a : account.values()){
            System.out.println(a.acc_num + " | " + a.acc_holder + " | " + a.acc_type + " | " + a.branch + " | " + a.balance);
        }
    }

    public void deposit(String acc_num, double amount) {
        Account acc = account.get(acc_num);
        if (acc == null) { 
            System.out.println("Account not found."); 
            return; 
        }
        if (amount > 0) { 
            acc.balance += amount; 
            System.out.println("Rs" + amount + " deposited."); 
        }
        else System.out.println("invalid deposit amount");
    }

    public void withdraw(String acc_num, double amount) {
        Account acc = account.get(acc_num);
        if (acc == null) { 
            System.out.println("Account not found."); 
            return; 
        }
        if (amount > 0 && amount <= acc.balance) {
            acc.balance -= amount; 
            System.out.println("Rs" + amount + " withdrawn");
        } else System.out.println("Insufficient balance or invalid amount");
    }

    public void transfer_amount(String senderAcc, String recieverAcc, double amount) {
        Account s = account.get(senderAcc), r = account.get(recieverAcc);

        if (s == null || r == null) { 
            System.out.println("Invalid account numbers."); 
            return;
         }
        if (senderAcc.equals(recieverAcc)) {
            System.out.println("Sender and receiver cannot be same."); 
            return; 
        }
        if (s.balance < amount) {
            System.out.println("Transaction failed"); 
            return; 
        }
        s.balance -= amount; 
        r.balance += amount; 
        System.out.println("Transaction successful.");
    }
}

public class BankingApp {
    static AccountRepository repo = new AccountRepository();

    public static void main(String[] args) {
        repo.addAccount(new Account("1234", "John Doe", 5000.0, "Savings", "Main Branch"));
        repo.addAccount(new Account("0987", "Jane Smith", 3000.0, "Checking", "City Branch"));
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to the Banking Application!");
        while (true) {
            System.out.println("\n1. Open Account 2. Deposit 3. Withdraw 4. Transfer 5. Show All Accounts 6. Exit");
            System.out.print("Choose option: ");
            int ch = sc.nextInt(); 
            sc.nextLine();
            if (ch == 6) break;
            if (ch == 1) repo.open_account(sc);
            else if (ch == 2) {
                System.out.print("Account number: "); 
                String a = sc.nextLine();
                System.out.print("Amount: "); 
                double am = sc.nextDouble(); 
                sc.nextLine();
                repo.deposit(a, am);
            }
            else if (ch == 3) {
                System.out.print("Account number: "); String a = sc.nextLine();
                System.out.print("Amount: "); 
                double am = sc.nextDouble(); 
                sc.nextLine();
                repo.withdraw(a, am);
            }
            else if (ch == 4) {
                System.out.print("Sender acc: "); 
                String s = sc.nextLine();
                System.out.print("Receiver acc: "); 
                String r = sc.nextLine();
                System.out.print("Amount: "); 
                double am = sc.nextDouble(); 
                sc.nextLine();
                repo.transfer_amount(s, r, am);
            }
            else if (ch == 5) repo.getAllAccountsInfo();
        }
        System.out.println("Thank you for using the Banking Application!");
        sc.close();
    }
}
