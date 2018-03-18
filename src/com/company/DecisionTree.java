package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class DecisionTree {

    private class BinaryTree {
        private int nodeID;
        private String questionOrAnswer = null;
        private BinaryTree yesBranch = null;
        private BinaryTree noBranch = null;

        public BinaryTree(int newNodeID, String newQuestionOrAnswer) {
            this.nodeID = newNodeID;
            this.questionOrAnswer = newQuestionOrAnswer;
        }
    }

    static BufferedReader keyboardInput = new BufferedReader(new InputStreamReader(System.in));
    BinaryTree rootNode = null;

    public DecisionTree() {
    }

    public void createRootNode(int newNodeId, String newQuestionAnswer) {
        rootNode = new BinaryTree(newNodeId, newQuestionAnswer);
        System.out.println("Created a new root node: " + newNodeId);
    }

    /* ADD YES NODE */
    public void addYesNode(int existingNodeID, int newNodeID, String newQuestionAnswer) {
        if (rootNode == null) {
            System.out.println("ERROR: There is no root node!");
            return;
        }

        // Search tree
        if (searchTreeAndAddYesNode(rootNode, existingNodeID, newNodeID, newQuestionAnswer)) {
            System.out.println("Added node " + newNodeID + " onto \"yes\" branch of node " + existingNodeID);
        } else {
            System.out.println("Node " + existingNodeID + " not found");
        }
    }

    /* SEARCH TREE AND ADD YES NODE */
    public boolean searchTreeAndAddYesNode(BinaryTree currentNode, int existingNodeID, int newNodeID, String newQuestionAnswer) {
        if (currentNode.nodeID == existingNodeID) {
            // Found the node
            if (currentNode.yesBranch == null) {
                currentNode.yesBranch = new BinaryTree(newNodeID, newQuestionAnswer);
            } else {
                System.out.println("WARNING! Overwritting previous node " + "(id = " + currentNode.yesBranch.nodeID + ") linked to yes branch of node " + existingNodeID);
                currentNode.yesBranch = new BinaryTree(newNodeID, newQuestionAnswer);
            }
            return (true);
        } else {
            // Try yes branch if it exist
            if (currentNode.yesBranch != null) {
                if (searchTreeAndAddYesNode(currentNode.yesBranch, existingNodeID, newNodeID, newQuestionAnswer)) {
                    return (true);
                } else {
                    // Try no branch if it exists
                    if (currentNode.noBranch != null) {
                        return (searchTreeAndAddYesNode(currentNode.noBranch, existingNodeID, newNodeID, newQuestionAnswer));
                    } else {
                        return (false); // Not found here
                    }
                }
            }
            return (false); // Not found here
        }
    }

    /* ADD NO NODE*/
    public void addNoNode(int existingNodeID, int newNodeID, String newQustionAnswer) {
        // if no root node do nothing
        if (rootNode == null) {
            System.out.println("ERROR: There is no root node!");
            return;
        }

        // Search tree
        if (searchTreeAndAddNoNode(rootNode, existingNodeID, newNodeID, newQustionAnswer)) {
            System.out.println("Added node " + newNodeID + " onto \"no\" branch of node " + existingNodeID);
        }
        else {
            System.out.println("Node " + existingNodeID + " not found");
        }
    }

    /* SEARCH TREE AND ADD NO NODE */
    private boolean searchTreeAndAddNoNode(BinaryTree currentNode, int existingNodeID, int newNodeID, String newQuestionAnswer) {
        if (currentNode.nodeID == existingNodeID) {
            // Found node
            if (currentNode.noBranch == null) {
                currentNode.noBranch = new BinaryTree(newNodeID, newQuestionAnswer);
            } else {
                System.out.println("WARNING: Overwriting previous node " + "(id = " + currentNode.noBranch.nodeID + ") linked to yes branch of node " + existingNodeID);
                currentNode.noBranch = new BinaryTree(newNodeID, newQuestionAnswer);
            }
            return (true);
        } else {
            // Try yes branch if it exist
            if (currentNode.yesBranch != null) {
                if (searchTreeAndAddNoNode(currentNode.yesBranch, existingNodeID, newNodeID, newQuestionAnswer)) {
                    return (true);
                }
                else {
                    // Try no branch if it exist
                    if (currentNode.noBranch != null) {
                        return (searchTreeAndAddNoNode(currentNode.noBranch, existingNodeID, newNodeID, newQuestionAnswer));
                    } else {
                        return (false); // Not found here
                    }
                }
            } else {
                return (false); // Not found here
            }
        }
    }

    /* --------------------------------------------- */
    /*                                               */
    /*               TREE QUERY METHODS              */
    /*                                               */
    /* --------------------------------------------- */

    public void queryBinaryTree() throws IOException {
        queryBinaryTree(rootNode);
    }

    private void queryBinaryTree(BinaryTree currentNode) throws IOException {
        // Test for lead node (answer) and missing branches

        if (currentNode.yesBranch == null) {
            if (currentNode.noBranch == null) {
                System.out.println(currentNode.questionOrAnswer);
            } else {
                System.out.println();
                System.out.println("ERROR: Missing \"Yes\" branch at\"" + currentNode.questionOrAnswer + "\" question");
                return;
            }
        }
        if (currentNode.noBranch == null) {
            System.out.println();
            System.out.println("ERROR: Missing \"No\" branch at \"" + currentNode.questionOrAnswer + "\" question");
            return;
        }
        // Question
        askQuestion(currentNode);
    }

    private void askQuestion(BinaryTree currentNode) throws IOException {
        System.out.println(currentNode.questionOrAnswer + " (enter \"Yes\" or \"No\")");
        String aswer = keyboardInput.readLine();
        if (aswer.equals("Yes")) {
            queryBinaryTree(currentNode.yesBranch);
        } else {
            if (aswer.equals("No")) {
                queryBinaryTree(currentNode.noBranch);
            } else {
                System.out.println("ERROR: Must answer \"Yes\" or \"No\"");
                askQuestion(currentNode);
            }
        }
    }

    /* OUTPUT BIN TREE */
    public void outputBinaryTree()   {
        outputBinaryTree("1", rootNode);
    }

    private void outputBinaryTree(String tag, BinaryTree currentNode) {
        // Check for empty node
        if (currentNode == null) {
            return;
        }

        // Output
        System.out.println("[" + tag + "] nodeID = " + currentNode.nodeID + ", question/answer = " + currentNode.questionOrAnswer);

        // Go down yes branch
        outputBinaryTree(tag + ".1",currentNode.yesBranch);

        // Go down no branch
        outputBinaryTree(tag + ".2",currentNode.noBranch);
    }
}