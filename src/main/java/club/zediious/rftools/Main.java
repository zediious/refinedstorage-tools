package club.zediious.rftools;

import static club.zediious.rftools.Tool.*;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        idle();

    }

    static void idle() throws IOException {

        Scanner userInput = new Scanner(System.in);
        System.out.println("""
                Enter a number corresponding to the tool you would like to use;
                1) FindCovers - Find all nodes of a given type that have covers.
                2) PullCovers - Remove all covers from a given node type.
                3) Exit""");
        String userChoice = userInput.nextLine();

        switch (userChoice) {

            case "1": findCovers(); idle();
            case "2": pullCovers(); idle();
            case "3": System.exit(0);

        }

    }

}
