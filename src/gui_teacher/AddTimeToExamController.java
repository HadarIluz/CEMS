package gui_teacher;

import java.io.IOException;

import client.CEMSClient;
import client.ClientUI;
import entity.ActiveExam;
import entity.Exam;
import entity.ExtensionRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.RequestToServer;

public class AddTimeToExamController {

	@FXML
	private Button btnSubmitTimeExtentionRequest;

	@FXML
	private Label lblNoteInMinutes;

	@FXML
	private TextField textAdditionalTime;

	@FXML
	private TextField textReqReason;

	@FXML
	private TextField textExamCode;

	@FXML
	private TextField textExamID;

	public static ActiveExam activeExam;
	public static Exam exam;

	/**
	 * @param event that occurs when clicking on 'Submit' button
	 * @throws IOException if failed.
	 */
	@FXML
	void btnSubmitTimeExtentionRequest(ActionEvent event) throws IOException {
		String examID = textExamID.getText();
		String examCode = textExamCode.getText();
		String additionalTime = textAdditionalTime.getText();
		String reqReason = textReqReason.getText();
		ActiveExam activeExamInSystem;

		// Check that all fields that must be filled are filled.
		if (textExamID.getText().trim().isEmpty()) {
			popUp("Please fill the ExamID Field");
		} else if (textExamCode.getText().trim().isEmpty()) {
			popUp("Please fill the Exam Code Field");
		} else if (textAdditionalTime.getText().trim().isEmpty()) {
			popUp("Please fill the Additional time Field");
		} else if (textReqReason.getText().trim().isEmpty()) {
			popUp("Please fill the Request reason Field");
		}
		// in case fields not empty checks if exist in DB
		else {
			if (examID.length() == 6 && isOnlyDigits(examID) && examCode.length() == 4
					&& isOnlyDigits(additionalTime)) {
				exam = new Exam(examID);
				activeExam = new ActiveExam(exam);
				// set in 'Serializable' class my request from server.
				RequestToServer req = new RequestToServer("addTimeToExam");
				req.setRequestData(activeExam);
				ClientUI.cems.accept(req); // sent server pk(exam) to DB in order to checks if activeExam exist or not.
				// activeExam does not exist in cems system.
				if (CEMSClient.responseFromServer.getStatusMsg().getStatus().equals("ACTIVE EXAM NOT FOUND")) {
					System.out.println("press on submit button and server returns: -->ACTIVE EXAM NOT FOUND");
					popUp("This activeExam doesn`t exist in CEMS system.");
				}
				// handle case that activeExam found and checks examCode.
				else {
					activeExamInSystem = (ActiveExam) CEMSClient.responseFromServer.getResponseData();
					// the exam code entered does not match the set exam code
					if (examCode.equals(activeExamInSystem.getExamCode()) == false)
						popUp("The examCode insert is incorrect. Please try again.");
					// the exam code entered correctly.
					else if (Integer.parseInt(additionalTime) <= 0)
						// When additional time is not a positive number.
						popUp("The additional time must be positive.");
					else {
						ExtensionRequest newExtensionReq = new ExtensionRequest(activeExam, additionalTime, reqReason);
						RequestToServer extReq = new RequestToServer("createNewExtensionRequest");
						extReq.setRequestData(newExtensionReq);
						ClientUI.cems.accept(extReq);
						// the extension didn't sent to the principal.
						if (CEMSClient.responseFromServer.getStatusMsg().getStatus().equals("EXTENSION REQUEST DIDN'T CREATED")) {
							System.out.println("press on submit button and server returns: -->EXTENSION REQUEST DIDN'T CREATED");
							popUp("The request to add time for the exam was not sent to the principal.");
						}
						else {	// the extension sent to the principal.
							popUp("The request to add time for the exam was sent to the principal.\nPlease wait for approval.");	
						}
					}
				}
			} else 
				popUp("At least one of the fields was not entered as required.\nExam ID number: 6 digits\nExam code number: 4 digits or letters\nExtra time for the exam: A positive number");
		}
	}

	/**
	 * this method create a popup with a message.
	 * 
	 * @param str
	 */
	public void popUp(String str) {
		final Stage dialog = new Stage();
		VBox dialogVbox = new VBox(20);
		Label lbl = new Label(str);
		lbl.setPadding(new Insets(5));
		lbl.setAlignment(Pos.CENTER);
		lbl.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		dialogVbox.getChildren().add(lbl);
		Scene dialogScene = new Scene(dialogVbox, lbl.getMinWidth(), lbl.getMinHeight());
		dialog.setScene(dialogScene);
		dialog.show();
	}

	/**
	 * this method checks if the given string includes letters.
	 * 
	 * @param str
	 * @return true if the String contains only digits.
	 */
	private boolean isOnlyDigits(String str) {
		boolean onlyDigits = true;
		for (char ch : str.toCharArray()) {
			if (!Character.isDigit(ch)) {
				onlyDigits = false;
				System.out.println("The string contains a character that he does not digit");
				break;
			}
		}
		System.out.println("isOnlyDigits returns:" + onlyDigits); // message to console
		return onlyDigits;
	}

}
