import java.util.LinkedList;

public class ABinBusPrioridad {
    private class Node {
        int prioridad;
        LinkedList<Paciente> cola;
        Node left, right;
        Node(int prioridad, Paciente p) {
            this.prioridad = prioridad;
            this.cola = new LinkedList<>();
            this.cola.add(p);
        }
    }

    private Node raiz;

    public ABinBusPrioridad() { raiz = null; }

    public boolean isEmpty() { return raiz == null; }

    // Insertar paciente: si ya existe node para esa prioridad, añadir al final (FIFO)
    public void insert(Paciente p) {
        raiz = insertRec(raiz, p);
    }

    private Node insertRec(Node node, Paciente p) {
        if (node == null) {
            return new Node(p.getPrioridad(), p);
        }
        if (p.getPrioridad() < node.prioridad) {
            node.left = insertRec(node.left, p);
        } else if (p.getPrioridad() > node.prioridad) {
            node.right = insertRec(node.right, p);
        } else {
            node.cola.addLast(p);
        }
        return node;
    }

    // Buscar paciente por ID (recorrido completo hasta encontrar)
    public Paciente searchById(String id) {
        return searchByIdRec(raiz, id);
    }

    private Paciente searchByIdRec(Node node, String id) {
        if (node == null) return null;
        // search in this node's queue
        for (Paciente p : node.cola) {
            if (p.getIdentificacion().equalsIgnoreCase(id)) return p;
        }
        Paciente left = searchByIdRec(node.left, id);
        if (left != null) return left;
        return searchByIdRec(node.right, id);
    }

    // Delete paciente por id: eliminar de la cola correspondiente; si cola queda vacía, borrar nodo del BST
    public boolean deleteById(String id) {
        ResultDelete res = deleteByIdRec(raiz, null, id);
        if (res != null) {
            raiz = res.root; // actualiza raíz si fue modificado
            return res.deleted;
        }
        return false;
    }

    // helper to allow updating root
    private class ResultDelete { Node root; boolean deleted; ResultDelete(Node root, boolean deleted) { this.root = root; this.deleted = deleted; } }

    private ResultDelete deleteByIdRec(Node node, Node parent, String id) {
        if (node == null) return new ResultDelete(raiz, false);
        // check this node
        for (Paciente p : node.cola) {
            if (p.getIdentificacion().equalsIgnoreCase(id)) {
                node.cola.remove(p);
                // if cola empty, remove this node from tree
                if (node.cola.isEmpty()) {
                    Node newRoot = deleteNode(node);
                    // need to reattach newRoot in parent
                    if (parent == null) {
                        // deleting root: find replacement
                        if (node == raiz) raiz = newRoot;
                    } else {
                        if (parent.left == node) parent.left = newRoot;
                        else parent.right = newRoot;
                    }
                    return new ResultDelete(raiz, true);
                } else {
                    return new ResultDelete(raiz, true);
                }
            }
        }
        // traverse
        if ((resLeftPossible(node.left, id))) {
            return deleteByIdRec(node.left, node, id);
        } else {
            return deleteByIdRec(node.right, node, id);
        }
    }

    // small helper: check quickly if id could be in left subtree by scanning lists (we'll just call both to be safe)
    private boolean resLeftPossible(Node left, String id){
        // We don't have indexes by id; return true to search.
        return true;
    }

    // Delete a node from BST and return the node that should replace it in parent's pointer.
    private Node deleteNode(Node node) {
        if (node.left == null && node.right == null) return null;
        if (node.left == null) return node.right;
        if (node.right == null) return node.left;
        // two children: find inorder successor (min in right)
        Node succParent = node;
        Node succ = node.right;
        while (succ.left != null) {
            succParent = succ;
            succ = succ.left;
        }
        // copy succ data into node
        node.prioridad = succ.prioridad;
        node.cola = succ.cola;
        // delete successor node
        if (succParent.left == succ) succParent.left = succ.right;
        else succParent.right = succ.right;
        return node;
    }

    // Obtener siguiente paciente (más urgente): ir al mínimo (leftmost)
    public Paciente pollNext() {
        if (raiz == null) return null;
        Node parent = null;
        Node cur = raiz;
        while (cur.left != null) {
            parent = cur;
            cur = cur.left;
        }
        // cur es el nodo de prioridad mínima
        Paciente next = cur.cola.pollFirst();
        if (next == null) return null;
        if (cur.cola.isEmpty()) {
            // eliminar el nodo del árbol
            Node replacement = deleteNode(cur);
            if (parent == null) {
                raiz = replacement;
            } else {
                if (parent.left == cur) parent.left = replacement;
                else parent.right = replacement;
            }
        }
        return next;
    }

    // Inorden: listado por prioridad ascendente (1..10)
    public String inOrden() {
        StringBuilder sb = new StringBuilder();
        inOrdenRec(raiz, sb);
        return sb.toString();
    }

    private void inOrdenRec(Node node, StringBuilder sb) {
        if (node == null) return;
        inOrdenRec(node.left, sb);
        sb.append("Prioridad ").append(node.prioridad).append(": ");
        for (Paciente p : node.cola) {
            sb.append(p.getIdentificacion()).append("(").append(p.getLlegada()).append(") ");
        }
        sb.append("\n");
        inOrdenRec(node.right, sb);
    }
}
