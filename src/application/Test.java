package application;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Test extends Application{


        public abstract class AbstractTreeItem extends TreeItem {
            public abstract ContextMenu getMenu();
        }

        public class ProviderTreeItem extends AbstractTreeItem{
            // make class vars here like psswd
            public ProviderTreeItem(String name) {
                this.setValue(name);
            }

            @Override
            public ContextMenu getMenu(){
                MenuItem addInbox = new MenuItem("add inbox");
                addInbox.setOnAction((EventHandler) t -> {
                    BoxTreeItem newBox = new BoxTreeItem("inbox");
                    getChildren().add(newBox);
                });
                return new ContextMenu(addInbox);
            }
        }

        public class BoxTreeItem extends AbstractTreeItem{
            //private List<String> emails = new LinkedList<>();
            public BoxTreeItem(String name) {
                this.setValue(name);
            }

            @Override
            public ContextMenu getMenu() {
                return new ContextMenu(new MenuItem("test"));
            }
        }

        private final class TreeCellImpl extends TreeCell<String> {

            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(getItem() == null ? "" : getItem().toString());
                    setGraphic(getTreeItem().getGraphic());
                    setContextMenu(((AbstractTreeItem) getTreeItem()).getMenu());
                }
            }
        }

        @Override
        public void start(Stage primaryStage) {
            StackPane sceneRoot = new StackPane();
            TreeItem  treeRoot  = new TreeItem();
            treeRoot.setExpanded(true);
            ProviderTreeItem gm = new ProviderTreeItem("gmail");
            ProviderTreeItem yh = new ProviderTreeItem("yahoo");
            treeRoot.getChildren().addAll(gm,yh);
            TreeView<String> treeView = new TreeView<String>(treeRoot);
            treeView.setShowRoot(false);
            treeView.setCellFactory(p -> new TreeCellImpl());
            sceneRoot.getChildren().add(treeView);
            Scene scene = new Scene(sceneRoot, 300, 500);


            primaryStage.setScene(scene);
            primaryStage.show();
        }
}
