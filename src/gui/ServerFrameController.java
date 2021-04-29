package gui;




import Server.CEMSserver;
import Server.ServerUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
<<<<<<< HEAD
=======
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
>>>>>>> branch 'main' of https://github.com/yuval96/CEMSprototype.git
import javafx.scene.control.Button;
<<<<<<< HEAD
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
=======
>>>>>>> branch 'main' of https://github.com/yuval96/CEMSprototype.git
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class ServerFrameController {

	@FXML
    private VBox rootOfMain;

   

    @FXML
    private SplitPane paneSplitter;

    @FXML
    private Pane serevrUserInteraction;

    @FXML
    private TextField portxt;

    @FXML
    private Button btnStartServer;

    @FXML
    private Button btnStop;

    @FXML
    private Font x1;

    @FXML
    private TextArea txtArea;

    @FXML
    private Font x3;

	private String getPort() {
		return portxt.getText();
	}
	
	
	
    @FXML
	void initialize() {
		//miscellaneousVBox.setVisible(false);
    
		
		txtArea.setEditable(false);
			}
    
	
	
	//connect between messages to present in txtLogArea
	public String messagesConnector(String oldStr,String addStr) {
		return oldStr + "\n" + addStr;
	}

	public void pressStartServerBtn(ActionEvent event) throws Exception {
		String p;
				p = getPort();
		if (p.trim().isEmpty()) {
			runServer("5555");// default port
			txtArea.appendText("Server listening for connections on port 5555\n");
			 btnStartServer.setDisable(true);
			 btnStop.setDisable(false);
			
		} else {
			runServer(p);
			txtArea.appendText("Server listening for connections on port " + p +"\n");
			
		}

	}

	public  void runServer(String p) 
	{
		 int port = 0; //Port to listen on

<<<<<<< HEAD
	        try
	        {
	        	port = Integer.parseInt(p); //Set port to 5555
	          
	        }
	        catch(Throwable t)
	        {
	        	System.out.println("ERROR - Could not connect!\n");
	        }
	    	
	        CEMSserver sv = new CEMSserver(port);
	        
	        try 
	        {
	          sv.listen(); //Start listening for connections
	        } 
	        catch (Exception ex) 
	        {
	        txtArea.appendText("ERROR - Could not listen for clients!\n");
	   	 btnStartServer.setDisable(true);
	       
	        }
=======
	public void start(Stage primaryStage) throws Exception {
		try {
		Pane root = FXMLLoader.load(getClass().getResource("ServerGUI.fxml"));
		Scene scene = new Scene(root);
		//scene.getStylesheets().add(getClass().getResource("/gui/ServerPort.css").toExternalForm());
		primaryStage.setTitle("CEMS Server");
		primaryStage.setScene(scene);

		primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
>>>>>>> branch 'main' of https://github.com/yuval96/CEMSprototype.git
	}

	


	public void getExitBtn(ActionEvent event) throws Exception {
		System.out.println("exit Academic Tool");
		System.exit(0);
	}


	public void printToTextArea(String string) {
		// TODO Auto-generated method stub
		
	}

}