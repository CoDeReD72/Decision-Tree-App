package com.example.paplsoftware;

import java.io.FileNotFoundException;
import java.io.InputStream;


public class NodeMap {

    private Node head;
    private static Node currentNode;

/****************************************************/
/**************      NAVIGATE       *****************/
/****************************************************/
/****************************************************/
    public static Node currentNode() { return currentNode;}



    public static void decision(int decision) {
        switch (decision) {
            case 1:
                currentNode = currentNode.getYesNode();
                break;
            case 2:
                currentNode = currentNode.getNoNode();
                break;
        }

    }

    public static boolean isEndNode(){
        if(currentNode.getYesID() == 0 && currentNode.getNoID() == 0){
            return true;
        }
        return false;
    }

/****************************************************/
/**************         BUILD      ******************/
/****************************************************/
/****************************************************/

    public NodeMap(InputStream prc)  {
        NodeCollection nodeCollection;   //scope: constructor only, part of process, no requirement to keep;
        try {
            nodeCollection = new NodeCollection(prc);
            head = nodeCollection.get(0);
        } catch (FileNotFoundException e) {
            //message
            return;
        }
        buildMap(nodeCollection);
        currentNode = head;
    }


    private void buildMap(NodeCollection nodeCollection)   {
        if (nodeCollection == null) {return;}
        for(Node source : nodeCollection.arrayList()){
            int yesID = source.getYesID();
            int noID = source.getNoID();

            Node yesNode = nodeCollection.locateNodeBy(yesID);
            Node noNode = nodeCollection.locateNodeBy(noID);

            source.setYesNode(yesNode);
            source.setNoNode(noNode);
        }
    }

    public String toString(){
        String string = "";
        string += yesPath() + "\n";
        string += noPath() + "\n";
        return string;
    }

    public String yesPath(){
        Node node = head;
        String string = "YES PATH\n";
        while(node != null) {
            string += node.toString() + "\n";
            node = node.getYesNode();
            if (node.getID() == 0) { node = null;}
        }
        return string;
    }

    public String noPath(){
        Node node = head;
        String string = "NO PATH\n";
        while(node != null) {
            string += node.toString() + "\n";
            node = node.getNoNode();
            if (node.getID() == 0) { node = null;}
        }
        return string;
    }



}