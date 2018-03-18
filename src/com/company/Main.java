package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    /* FIELDS */
    static BufferedReader keyBoardInput = new BufferedReader(new InputStreamReader(System.in));
    static DecisionTree newTree;

    /* METHODS */
    public static void main(String[] args) throws IOException {
        // Create a new instance of DecisionTree:
        newTree = new DecisionTree();

        // Generate tree:
        generateTree();

        System.out.println("\nOUTPUT DECISION TREE");
        System.out.println("=====================");

        // Query tree:
        queryTree();
    }

    /* GENERATE TREE */
    static void generateTree() {
        System.out.println("\nGENERATE DECISION TREE");
        System.out.println("====================");
        newTree.createRootNode(1,"Read textbook?");

        newTree.addYesNode(1,2,"Hands-in made in time?");
        newTree.addNoNode(1,3,"Hands-in made in time?");

        newTree.addYesNode(2, 4, "Attend lectures?");
        newTree.addNoNode(2, 5, "You could easily fail the exam.");

        newTree.addYesNode(3, 6, "Attend lectures?");
        newTree.addNoNode(3,7,"You could easily fail the exam.");

        newTree.addYesNode(4,8,"Make exercises?");
        newTree.addNoNode(4,9,"Make exercises?");

        newTree.addYesNode(8,10,"You should be able to pass the exsam.");
        newTree.addNoNode(8,11,"You should be able to pass the exsam.");

        newTree.addYesNode(9,12,"You should be able to pass the exam.");
        newTree.addNoNode(9,13,"You could easily fail the exam.");

        newTree.addYesNode(6, 14,"Make exercises?");
        newTree.addNoNode(6, 15, "You could easily fail the exam.");

        newTree.addYesNode(14, 16,"You should be able to pass the exsam.");
        newTree.addNoNode(14,17,"You should be able to pass the exsam.");
    }

    /* QUERY TREE */
    static void queryTree() throws IOException {
        System.out.println("\nQUERY DECISION TREE");
        System.out.println("===============");
        newTree.queryBinaryTree();

        // Option to exit

        optionToExit();
    }

    /* OPTION TO EXIT THE PROGRAM */
    static void optionToExit() throws IOException {
        System.out.println("Exit? (enter \"Yes\" or \"No\")");
        String answer = keyBoardInput.readLine();
        if (answer.equals("Yes")) return;
        else {
            if (answer.equals("No")) queryTree();
            else {
                System.out.println("ERROR: Must answer \"Yes\" or \"No\"");
                optionToExit();
            }
        }
    }
}
