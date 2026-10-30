package com.scc.ui;

import com.scc.dao.UserDao;
import com.scc.model.User;

import java.sql.SQLException;
import java.util.Scanner;

public class Authentication {

    private final UserDao userDao = new UserDao();
    private final Scanner scanner;

    public Authentication(Scanner scanner) {
        this.scanner = scanner;
    }

    public void register() {
        System.out.println("\n--- Register Account ---");
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.println("Role: 1) TEACHER  2) STUDENT");
        System.out.print("Choose: ");
        String role = switch (scanner.nextLine().trim()) {
            case "1" -> "TEACHER";
            case "2" -> "STUDENT";
            default -> null;
        };

        if (username.isEmpty() || password.isEmpty() || role == null) {
            System.out.println("Invalid input. Registration cancelled.");
            return;
        }

        try {
            if (userDao.usernameExists(username)) {
                System.out.println("Username already taken.");
            } else if (userDao.register(username, password, role)) {
                System.out.println("Registered successfully! Status: PENDING");
            }
        } catch (SQLException e) {
            System.out.println("Registration failed: " + e.getMessage());
        }
    }

    public void login() {
        System.out.println("\n--- Login ---");
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        try {
            User user = userDao.login(username, password);
            if (user == null) {
                System.out.println("Invalid username or password.");
                return;
            }
            new SessionMenu(scanner, user).run();
        } catch (SQLException e) {
            System.out.println("Login failed: " + e.getMessage());
        }
    }
}