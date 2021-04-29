package gui;




import Server.CEMSserver;
import Server.ServerUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
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
	}

	


	public void getExitBtn(ActionEvent event) throws Exception {
		System.out.println("exit Academic Tool");
		System.exit(0);
	}

}