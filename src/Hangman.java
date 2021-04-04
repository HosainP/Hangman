import java.util.Collections;
import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;
import java.io.Console;


class User {
    String username;
    String password;
    int score = 0;
}
class Hanger {
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_RESET = "\u001B[0m";

    int step ;
    String username ;
    int maxumum_chances ;
    Hanger(){
        step = 0;
        username = "";
        maxumum_chances = 0;
    }
    static void print_hanger(int step, String username, int maximum_chances) {
//        clrscr();
        System.out.println("\nplaying as " + username + "\n\n");
        System.out.print(ANSI_PURPLE);
        System.out.println("----");
        if (maximum_chances == 7) {
            System.out.print("| ");
            if (step > 0)
                System.out.print("|");
            System.out.println();

            System.out.print("| ");
            if (step > 1)
                System.out.print("O");
            System.out.println();

            System.out.print("|");
            if (step > 2)
                System.out.print("/");
            if (step > 3)
                System.out.print("|");
            if (step > 4)
                System.out.print("\\");
            System.out.println();

            System.out.print("|");
            if (step > 5)
                System.out.print("/ ");
            if (step > 6)
                System.out.print("\\");
            System.out.println();

            System.out.println("|");
        } else { // means that maximus chances is 14
            System.out.print("| ");
            if (step > 1)
                System.out.print("|");
            System.out.println();

            System.out.print("| ");
            if (step > 3)
                System.out.print("O");
            System.out.println();

            System.out.print("|");
            if (step > 5)
                System.out.print("/");
            if (step > 7)
                System.out.print("|");
            if (step > 9)
                System.out.print("\\");
            System.out.println();

            System.out.print("|");
            if (step > 11)
                System.out.print("/ ");
            if (step > 13)
                System.out.print("\\");
            System.out.println();

            System.out.println("|");
        }
        System.out.print(ANSI_RESET);
        System.out.println();
    }
}

public class Hangman {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_RESET = "\u001B[0m";

    static void clrscr() {
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {
        }
    }

    static void Signin_Signup(ArrayList usernames, ArrayList passwords, ArrayList scores) {
        Scanner input = new Scanner(System.in);
        clrscr();
        System.out.println();
        System.out.println("1.SignUp / 2.Login");
        int what_to_do = input.nextInt();
        if (what_to_do == 1) {
            Signup(usernames, passwords, scores);
        } else if (what_to_do == 2) {
            Login(usernames, passwords, scores);
        } else
            Signin_Signup(usernames, passwords, scores);
    }

    static boolean is_password_acceptable(String password) {
        String capitals = ".*[A-Z].*";
        String smalls = ".*[a-z].*";
        String nums = ".*[1-9].*";
        String length = ".......*";
        String special_characters = ".*[!@#$%^*&+?].*";
        if (password.matches(capitals) && password.matches(smalls) && password.matches(nums) && password.matches(length) && password.matches(special_characters)) {
            return true;
        } else
            return false;
    }

    static void Signup(ArrayList usernames, ArrayList passwords, ArrayList scores) {
        clrscr();
        System.out.println("Choose a username : ");
        Scanner input = new Scanner(System.in);
        String username = input.nextLine();
        while (usernames.contains(username)) {
            System.out.print(ANSI_RED);
            System.out.println("username is already taken !");
            System.out.println("please choose another one :");
            System.out.print(ANSI_RESET);
            username = input.nextLine();
        }
        usernames.add(username);
        System.out.println("Choose a password that contains small and capital alphabets and numbers and special characters and longer that 6 digits :");
        String password = input.nextLine();
        while (!is_password_acceptable(password)) {
            System.out.print(ANSI_RED);
            System.out.println("Your password is not acceptable !");
            System.out.println("please choose another one :");
            System.out.print(ANSI_RESET);
            password = input.nextLine();
        }
        passwords.add(password);
        scores.add(0);
        Login(usernames, passwords, scores);
    }

    static void Login(ArrayList usernames, ArrayList passwords, ArrayList scores) {
        clrscr();
        Scanner input = new Scanner(System.in);
        System.out.println("1.Start game / 2.Show leaderboard");
        int what_to_do = input.nextInt();
        while (what_to_do != 1 && what_to_do != 2) {
            System.out.println(ANSI_RED + "invalid order !\n please try again !" + ANSI_RESET);
            what_to_do = input.nextInt();
        }

        if (what_to_do == 1) {
            System.out.println("Enter your username :");
            String username = input.nextLine();
            username = input.nextLine();
            while (!usernames.contains(username)) {
                System.out.println(ANSI_RED + "this username is not signed up !\n please try again !" + ANSI_RESET);
                username = input.nextLine();
            }
            int index = usernames.indexOf(username);
            System.out.println("Enter your password :");


            Console console = System.console();
            char[] passwordChars = console.readPassword();
            String password = new String(passwordChars);

            ///////////////////////////// the masked password

            while (!password.equals(passwords.get(index))) {
                System.out.println(ANSI_RED + "password is incorrect !\n please try again !" + ANSI_RESET);
                passwordChars = console.readPassword();
                password = new String(passwordChars);
            }
            play(username, scores, index);
        } else {                            // means that we should show leader board
            for (int i = 0; i < usernames.size(); i++) { //sorts the leaderboard
                for (int j = i + 1; j < usernames.size(); j++) {
                    if ((int) scores.get(j) > (int) scores.get(i)) {
                        Collections.swap(usernames, i, j);
                        Collections.swap(passwords, i, j);
                        Collections.swap(scores, i, j);
                    }
                }
            }
            for (int i = 0; i < usernames.size(); i++) {
                System.out.print(usernames.get(i));
                System.out.print(" : ");
                System.out.println(scores.get(i));
            }
            System.out.println("press 1 to continue : ");
            int Continue = input.nextInt();
            while (Continue != 1) {
                Continue = input.nextInt();
            }
            Signin_Signup(usernames, passwords, scores);
        }
    }

    static void play(String username, ArrayList scores, int index) {
        String[] words = {"tehran", "pizza", "banana", "new york", "advanced programming", "michael jordan",
                "lionel messi", "apple", "macaroni", "university", "intel", "kitten", "python", "java",
                "data structures", "algorithm", "assembly", "basketball", "hockey", "leader", "javascript",
                "toronto", "united states of america", "psychology", "chemistry", "breaking bad", "physics",
                "abstract classes", "linux kernel", "january", "march", "time travel", "twitter", "instagram",
                "dog breeds", "strawberry", "snow", "game of thrones", "batman", "ronaldo", "soccer",
                "hamburger", "italy", "greece", "albert einstein", "hangman", "clubhouse", "call of duty",
                "science", "theory of languages and automata"};
        Scanner input = new Scanner(System.in);
        int step = 0;
        int random_word = (int) (Math.random() * 50);
        String the_word = words[random_word];
        int length = the_word.length();

        int unknown_alphabets_int = length;
        for (int i = 0; i < the_word.length(); i++) {
            if (the_word.charAt(i) == ' ') {
                unknown_alphabets_int--;
            }
        }

        ArrayList<Character> guessed_alphabets = new ArrayList<Character>(0);
        ArrayList<Character> not_guessed_alphabets = new ArrayList<Character>(length);
        for (int i = 0; i < the_word.length(); i++) {
            if (the_word.charAt(i) != ' ') {
                not_guessed_alphabets.add(the_word.charAt(i));
            }
        }
        ArrayList<Character> wrong_guessed_alphabets = new ArrayList<Character>(0);
        ArrayList<Character> right_guessed_alphabets = new ArrayList<Character>(0);
        int maximum_chances = 7;
        if (the_word.length() > 9) {
            maximum_chances = 14;
        }
        int guide = 0;
        while (step < maximum_chances && unknown_alphabets_int > 0) {
            clrscr();
            Hanger x = new Hanger();
            x.step = step;
            x.username = username;
            x.maxumum_chances = maximum_chances;
            Hanger.print_hanger(x.step, x.username, x.maxumum_chances);
            print_word(the_word, guessed_alphabets, wrong_guessed_alphabets.size());
            System.out.print("wrong guessed alphabets : ");
            System.out.println(wrong_guessed_alphabets);
            System.out.print("right guessed alphabets : ");
            System.out.println(right_guessed_alphabets);

            if ((int) scores.get(index) > 9) {
                System.out.println(ANSI_YELLOW + "you can give 10 points and have one alphabet discovered! right now you have " + scores.get(index) + " points.");
                System.out.println("if you want to do that, enter '$' (you can only do it once in a round.)" + ANSI_RESET);
            }
            System.out.println("Enter an alphabet : ");
            Character next_alphabet = input.next().charAt(0);
            while (guessed_alphabets.contains(next_alphabet)) {
                System.out.println(ANSI_RED + "character is already guessed !\n please try another one !" + ANSI_RESET);
                next_alphabet = input.next().charAt(0);
            }
            while (next_alphabet == '$' && (int) scores.get(index) < 10) {
                System.out.println(ANSI_RED + "you dont have enough points to do that! \n enter an alphabet." + ANSI_RESET);
                next_alphabet = input.next().charAt(0);
            }
            while (next_alphabet == '$' && guide == 1) {
                System.out.println(ANSI_RED + "you have done this once. you can't do it again! \n enter an alphabet." + ANSI_RESET);
                next_alphabet = input.next().charAt(0);
            }
            if (next_alphabet == '$') {
                int random_alphabet = (int) (Math.random() * not_guessed_alphabets.size());
                next_alphabet = not_guessed_alphabets.get(random_alphabet);
                scores.set(index, (int) scores.get(index) - 10);
                guide++;
            }
            int repeats = 0;
            for (int i = 0; i < the_word.length(); i++) {
                if (the_word.charAt(i) == next_alphabet) {
                    repeats++;
                }
            }
            guessed_alphabets.add(next_alphabet);
            String myStr = Character.toString(next_alphabet);
            if (!the_word.contains(myStr)) {
                wrong_guessed_alphabets.add(next_alphabet);
                step++;
            } else {
                for (int i = not_guessed_alphabets.size() - 1; i >= 0; i--) {
                    if (next_alphabet == not_guessed_alphabets.get(i)) {
                        not_guessed_alphabets.remove(i);
                    }
                }
                right_guessed_alphabets.add(next_alphabet);
                unknown_alphabets_int = unknown_alphabets_int - repeats;
            }
        }
        clrscr();
        Hanger x = new Hanger();
        x.step = step;
        x.username = username;
        x.maxumum_chances = maximum_chances;
        Hanger.print_hanger(x.step, x.username, x.maxumum_chances);
        if (not_guessed_alphabets.size() == 0) {
            System.out.println("YOU WON !");
            scores.set(index, (int) scores.get(index) + 5);

        } else {
            System.out.println("YOU LOST !");

        }
        System.out.println("the word was " + "\"" + the_word + "\"");
        System.out.print("wrong guessed alphabets : ");
        System.out.println(wrong_guessed_alphabets);
        System.out.print("right guessed alphabets : ");
        System.out.println(right_guessed_alphabets);
        System.out.println("Enter 1 to continue ...");
        int Continue = input.nextInt();
        while (Continue != 1) {
            Continue = input.nextInt();
        }
    }

    static void print_word(String word, ArrayList guessed_alphabets, int wrong_guessed_alphabets) {
        System.out.println();
        // Vs ans Xs
        int chances = 7;
        if (word.length() > 9)
            chances = 14;
        if (wrong_guessed_alphabets > 0)
            System.out.print(ANSI_RED + "|" + ANSI_RESET);
        else
            System.out.print(ANSI_GREEN + "|" + ANSI_RESET);
        for (int i = 0; i < wrong_guessed_alphabets; i++) {
            System.out.print(ANSI_RED + " X |" + ANSI_RESET);
        }
        for (int i = wrong_guessed_alphabets; i < chances; i++) {
            System.out.print(ANSI_GREEN + " V |" + ANSI_RESET);
        }
        System.out.println();
        ////////////////
        System.out.println("\n");
        for (int i = 0; i < word.length(); i++) {
            if (guessed_alphabets.contains(word.charAt(i)))
                System.out.print(word.charAt(i));
            else if (word.charAt(i) == ' ') {
                System.out.print(" ");
            } else
                System.out.print("-");
        }
        System.out.println();
    }


    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);


        ArrayList<String> usernames = new ArrayList<String>(0); // all the usernames
        ArrayList<String> passwords = new ArrayList<String>(0); // all the passwords
        ArrayList<Integer> scores = new ArrayList<Integer>(0);  // all the scores

        while (true) {
            Signin_Signup(usernames, passwords, scores);
        }
    }
}

// extra parts :
// {
// masked password
// Xs & Vs
// color printings
// }

// todo make leaderboard more nice