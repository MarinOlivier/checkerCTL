package main;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by olivier on 27/05/2016.
 */
public class Model {

    public Node[] _nodes;
    private double _nbNode;
    private Node _initial;

    public Model() {
        _nbNode = 5;

        Node n1 = new Node("p");
        n1.setDeg(1);
        n1.setName("1");

        Node n2 = new Node("!p");
        n2.setDeg(2);
        n2.setName("2");

        Node n3 = new Node("p");
        n3.setDeg(3);
        n3.setName("3");

        Node n4 = new Node("!p");
        n4.setDeg(1);
        n4.setName("4");

        Node n5 = new Node("p");
        n5.setDeg(1);
        n5.setName("5");

        n1.setSuiv(new Node[]{n2});
        n2.setSuiv(new Node[]{n1, n3});
        n3.setSuiv(new Node[]{n1, n4, n2});
        n4.setSuiv(new Node[]{n5});
        n5.setSuiv(new Node[]{n1});

        _nodes = new Node[(int)_nbNode];
        _nodes[0] = n1;
        _nodes[1] = n2;
        _nodes[2] = n3;
        _nodes[3] = n4;
        _nodes[4] = n5;

        _initial = n1;

    }

    public double getNbNode() {
        return _nbNode;
    }

    public void setNodeLabel(int i, boolean val) {
        _nodes[i].setPhi(val);
    }

    public boolean getNodeLabel(int i) {
        return _nodes[i].getPhi();
    }

    public Boolean getPhi(String NodeName) {
        for (Node n : _nodes) {
            if(n.getName().equals(NodeName))
                return n.getPhi();
        }
        return false;
    }

    public void printLabel() {
        for(int i=0; i < _nbNode ; i++) {
            System.out.println("Node : " + i + " label : " + _nodes[i].getPhi());
        }
    }

    public ArrayList<Neighbor> findNeighbor() {
        ArrayList<Neighbor> neighbors = new ArrayList<Neighbor>(0);
        for(int i=0 ; i < _nbNode ; i++) {
            Node curr = _nodes[i];
            for(int j = 0 ; j < curr.getSuiv().length ; j++){
               neighbors.add(new Neighbor(curr, curr.getSuiv(j)));
            }
        }

        return neighbors;
    }

    @Override
    public String toString() {
        return "Model{\n" +
                "_nodes=\n" + Arrays.toString(_nodes) +
                ", _nbNode=" + _nbNode +
                ", _initial=" + _initial +
                '}';
    }
}

class Node {
    private double _deg;
    private String _name;
    private Node[] _suiv;
    private String _val;
    private Boolean _phi;

    public Node(String val) {
        _val = val;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public double getDeg() {
        return _deg;
    }

    public void setDeg(double deg) {
        this._deg = deg;
    }

    public Node[] getSuiv() {
        return _suiv;
    }

    public Node getSuiv(int i) {
        return _suiv[i];
    }

    public void setSuiv(Node[] suiv) {
        this._suiv = suiv;
    }

    public String getVal() {
        return _val;
    }

    public void setVal(String _val) {
        this._val = _val;
    }

    public Boolean getPhi() {
        return _phi;
    }

    public void setPhi(Boolean _phi) {
        this._phi = _phi;
    }

    @Override
    public String toString() {
        return "\tNode: (" +
                "nbVoisins = " + _deg +
                ", suivants =" + _suiv +
                ", _val='" + _val + '\'' +
                ", name = " + _name +
                "}\n";
    }
}

class Neighbor {
    private Node s;
    private Node d;

    public Neighbor(Node s, Node d) {
        this.s = s;
        this.d = d;
    }

    public Node getS() {
        return s;
    }

    public void setS(Node s) {
        this.s = s;
    }

    public Node getD() {
        return d;
    }

    public void setD(Node d) {
        this.d = d;
    }

    @Override
    public String toString() {
        return "Neighbor{" +
                "s=" + s.getName() +
                ", d=" + d.getName() +
                '}';
    }
}
