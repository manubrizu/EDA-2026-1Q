package mabrizuela;

import java.util.Scanner;


public class ExpTree implements ExpressionService {

	private Node root;

	public ExpTree() {
	    System.out.print("Introduzca la expresi�n en notaci�n infija con todos los par�ntesis y blancos: ");

		// token analyzer
	    Scanner inputScanner = new Scanner(System.in).useDelimiter("\\n");
	    String line= inputScanner.nextLine();
	    inputScanner.close();

	    buildTree(line);
	}
	
	private void buildTree(String line) {	
		  // space separator among tokens
		  Scanner lineScanner = new Scanner(line).useDelimiter("\\s+");
		  root= new Node(lineScanner);
		  lineScanner.close();
	}
	


	
	static final class Node {
		private String data;
		private Node left, right;
		
		private Scanner lineScanner;

		public Node(Scanner theLineScanner) {
			lineScanner= theLineScanner;
			
			Node auxi = buildExpression();
			data= auxi.data;
			left= auxi.left;
			right= auxi.right;
			
			if (lineScanner.hasNext() ) 
				throw new RuntimeException("Bad expression");
		}
		
		private Node() 	{
		}


        private Node buildExpression() {
            Node n = new Node();

            if (lineScanner.hasNext("\\(")) {
                lineScanner.next(); // lo consumo

                n.left = buildExpression(); // subexpression

                // operator
                if (!lineScanner.hasNext()) {
                    throw new RuntimeException("missing or invalid operator");
                }

                n.data = lineScanner.next();

                if (!Utils.isOperator(n.data)) {
                    throw new RuntimeException("missing or invalid operator");
                }

                // subexpression
                n.right = buildExpression();

                // ")" expected
                if (lineScanner.hasNext("\\)")) {
                    lineScanner.next(); // lo consumo
                } else {
                    throw new RuntimeException("missing )");
                }

                return n;
            }

            // constant
            if (!lineScanner.hasNext()) {
                throw new RuntimeException("missing expression");
            }

            n.data = lineScanner.next();

            if (!Utils.isConstant(n.data)) {
                throw new RuntimeException(
                        String.format("illegal termin %s", lineScanner)
                );
            }

            return n;
        }

        private String preOrder(StringBuilder s){
            s.append(data).append(" ");
            if(left != null)
                left.preOrder(s);
            if(right!= null)
                right.preOrder(s);
            return s.toString();
        }

        private String inOrder(StringBuilder s){
            if(left != null) {
                s.append("( ");
                left.inOrder(s);
            }
            s.append(data).append(" ");
            if(right != null){
                right.inOrder(s);
                s.append(")");
            }
            return s.toString();
        }

        private String postOrder(StringBuilder s){
            if(left != null)
                left.postOrder(s);
            if(right!= null)
                right.postOrder(s);
            s.append(data).append(" ");
            return s.toString();
        }

        @Override
        public String toString(){
            StringBuilder buffer = new StringBuilder();
            print(buffer, "" , "");B
            return buffer.toString();
        }

        private void print(StringBuilder buffer, String prefix, String childrenPrefix){
            buffer.append(prefix);
            buffer.append(data);
            buffer.append('\n');
            if(left!=null)
                left.print(buffer, childrenPrefix + "├── ", childrenPrefix + "│   ");
            if(right!=null)
                right.print(buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
        }


	}  // end Node class

    @Override
    public void preorder() {
        if(root==null)
            throw new IllegalStateException();
        System.out.println(root.preOrder(new StringBuilder()));
    }

    @Override
    public void inorder() {
        if(root==null)
            throw new IllegalStateException();
        System.out.println(root.inOrder(new StringBuilder()));
    }

    @Override
    public void postorder() {
        if(root==null)
            throw new IllegalStateException();
        System.out.println(root.postOrder(new StringBuilder()));
    }




	
	
	// hasta que armen los testeos
	public static void main(String[] args) {
		ExpressionService myExp = new ExpTree();
	
	}

}  // end ExpTree class
