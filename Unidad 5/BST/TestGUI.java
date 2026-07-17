import core.BSTree;

// bajar el paquete nativo  
// https://gluonhq.com/products/javafx/ 

// en el VM poner el lib del paquete nativo
// --module-path C:\Users\lgomez\Downloads\javafx-sdk-11.0.2\lib --add-modules javafx.fxml,javafx.controls


import controller.GraphicsTree;
import core.BSTree;
import core.Person;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;

public class TestGUI extends Application {

	public static void main(String[] args) {
		// GUI
		launch(args);
	}

    @Override
	public void start(Stage stage) {
		stage.setTitle("Drawing the BSTree");
		StackPane root = new StackPane();
		Scene scene = new Scene(root, 1300, 700);

		BSTree<Integer> myTree = createModel();
		GraphicsTree<Integer> c = new GraphicsTree<>(myTree);
//		BSTree<Person> myTree = createModel2();
//		GraphicsTree<Person> c = new GraphicsTree<>(myTree);

		c.widthProperty().bind(scene.widthProperty());
		c.heightProperty().bind(scene.heightProperty());
	
		root.getChildren().add(c);
		stage.setScene(scene);
		stage.show();
		

	}


	private BSTree<Integer> createModel() {
		BSTree<Integer> myTree = new BSTree<>();
		myTree = new BSTree<>();
		myTree.insert(50);
		myTree.insert(60);
		myTree.insert(80);
		myTree.insert(20);
		myTree.insert(70);
		myTree.insert(40);
		myTree.insert(44);
		myTree.insert(10);
		myTree.insert(40);
        myTree.delete(50);

        for (Integer data : myTree) {
            System.out.print(data +  " ");
        }

        System.out.println();

        myTree.forEach( t-> System.out.print(t + " ") );

        myTree.inOrder();

		return myTree;
	}

	private BSTree<Person> createModel2() {
		BSTree<Person> myTree = new BSTree<>();
		myTree = new BSTree<>();
		myTree.insert(new Person( 50, "Ana" ));
		myTree.insert(new Person( 60, "Juan") );
		myTree.insert(new Person( 80, "Sergio") );
		myTree.insert(new Person( 20, "Lila ") );
		myTree.insert(new Person( 77, "Ana") );
		myTree.inOrder();

		return myTree;
	}


}