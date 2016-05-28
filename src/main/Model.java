package main;

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

        Node n2 = new Node("!p");
        n2.setDeg(2);

        Node n3 = new Node("p");
        n3.setDeg(2);

        Node n4 = new Node("!p");
        n4.setDeg(1);

        Node n5 = new Node("p");
        n5.setDeg(1);

        n1.setSuiv(new Node[]{n2});
        n2.setSuiv(new Node[]{n1, n3});
        n3.setSuiv(new Node[]{n1, n4});
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
    private Node[] _suiv;
    private String _val;
    private Boolean _phi;

    public Node(String val) {
        _val = val;
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

    public void setSuiv(Node[] suiv) {
        this._suiv = suiv;
    }

    public String getVal() {
        return _val;
    }

    public void setVal(String _val) {
        this._val = _val;
    }

    private String printNodeList(Node[] list) {
        String res = "[ ";
        for (Node n: _suiv) {
            res = res + " | " + n.getVal();
        }
        res += " ]";
        return res;
    }

    public Boolean getPhi() {
        return _phi;
    }

    public void setPhi(Boolean _phi) {
        this._phi = _phi;
    }

    @Override
    public String toString() {
        return "\tNode{" +
                "_deg=" + _deg +
                ", _suiv=" + printNodeList(_suiv) +
                ", _val='" + _val + '\'' +
                "}\n";
    }
}
