package com.glensoft.trinote;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;

public class MainController implements Initializable{
	
	@FXML
	private ListView<NoteBook> lstNotebooks;
	
	@FXML
	private ListView<NoteHeader> lstNoteheaders;
	
	@FXML
	private ListView<NoteDetail> lstNotedetails;
	
	@FXML
	private MenuBar mnuMain;
	
	@FXML
	private Button btnNewNoteDetail;
	
	@FXML
	private Button btnNewNoteHeader;
	
	@FXML
	private Button btnNewNotebook;
	
	@FXML
	private void handleFileMenuQuit()
	{
		Stage stage = (Stage)mnuMain.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	private void handleNewNoteDetail()
	{
		NoteDetail newNoteDetail = new NoteDetail(0l, lstNoteheaders.getSelectionModel().getSelectedItem().getId(), "", "", "", "");
		NoteDetailEditDialogFactory mng = new NoteDetailEditDialogFactory();
		Scene parentScene = btnNewNoteDetail.getScene();
		Window parentWin = parentScene.getWindow();
		mng.ShowDialog(parentWin, newNoteDetail);
		this.refreshDetail(newNoteDetail.getNoteHeaderId());
	}
	
	@FXML 
	private void handleNewNotebook()
	{
		NoteBook newNoteBook= new NoteBook(0l,"", "");
		NotebookEditDialogFactory mng = new NotebookEditDialogFactory();
		Scene parentScene = btnNewNotebook.getScene();
		Window parentWin = parentScene.getWindow();
		mng.ShowDialog(parentWin, newNoteBook);
		this.refreshNotebooks();
	}
	
	@FXML
	private void handleNewNoteheader()
	{
		NoteBook selectedNotebook = lstNotebooks.getSelectionModel().getSelectedItem();
		NoteHeader newNoteHeader = new NoteHeader(0l, selectedNotebook.getId(), "", "");
		NoteHeaderEditDialogFactory mng = new NoteHeaderEditDialogFactory();
		Scene parentScene = btnNewNoteHeader.getScene();
		Window parentWin = parentScene.getWindow();
		mng.ShowDialog(parentWin, newNoteHeader);
		this.refreshNoteheaders(selectedNotebook.getId());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
			this.refreshNotebooks();
	 
	       lstNotebooks.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
	    	    if (newSelection != null) {
	    	    	this.refreshNoteheaders(newSelection.getId());
	    	        this.btnNewNoteHeader.setDisable(false);
	    	    }
	    	});
	       
	       lstNoteheaders.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
	    	    if (newSelection != null) {
	    	    	this.refreshDetail(newSelection.getId());
	    	    	this.btnNewNoteDetail.setDisable(false);
	    	    }
	    	});
	       
	       
	       lstNotebooks.setCellFactory(new Callback<ListView<NoteBook>, ListCell<NoteBook>>(){
	            @Override
	            public ListCell<NoteBook> call(ListView<NoteBook> p) {
				ListCell<NoteBook> cell = new ListCell<NoteBook>() {
					@Override
					protected void updateItem(NoteBook t, boolean bln) {
						super.updateItem(t, bln);
						if (t != null) {
							setText(t.getName());
							setOnMouseClicked(null);

						} else {
							setText("");
							setOnMouseClicked(null);
						}
					}

				};
	                return cell;
	            }
	            
	        });   
	       
	       lstNoteheaders.setCellFactory(new Callback<ListView<NoteHeader>, ListCell<NoteHeader>>(){
	            @Override
	            public ListCell<NoteHeader> call(ListView<NoteHeader> p) {
	                ListCell<NoteHeader> cell = new ListCell<NoteHeader>(){
	                    @Override
	                    protected void updateItem(NoteHeader t, boolean bln) {
	                        super.updateItem(t, bln);
	                        if (t != null) {
	                            setText(t.getName());
	                            setOnMouseClicked((MouseEvent event) -> 
	                            {
	                            	if(event.getClickCount() > 1)
	                            	{
	                            		NoteHeaderEditDialogFactory mng = new NoteHeaderEditDialogFactory();
	                            		Node eventSource = (Node)event.getSource();
	                            		Scene parentScene = eventSource.getScene();
	                            		Window parentWin = parentScene.getWindow();
	                            		mng.ShowDialog(parentWin, lstNoteheaders.getSelectionModel().getSelectedItem());
	                            	}
	                            });
	                        }
	                        else
	                        {
	                        	setText("");
	                        	setOnMouseClicked(null);
	                        }
	                    }	 
	                };
	                return cell;
	            }
	        });  
	       
	       lstNotedetails.setCellFactory(new Callback<ListView<NoteDetail>, ListCell<NoteDetail>>(){
	            @Override
	            public ListCell<NoteDetail> call(ListView<NoteDetail> p) {
	                ListCell<NoteDetail> cell = new ListCell<NoteDetail>(){
	                    @Override
	                    protected void updateItem(NoteDetail t, boolean bln) {
	                        super.updateItem(t, bln);
	                        if (t != null) {
	                            setText(t.getName());
	                            setOnMouseClicked((MouseEvent event) -> 
	                            {
	                            	if(event.getClickCount() > 1)
	                            	{
	                            		NoteDetailEditDialogFactory mng = new NoteDetailEditDialogFactory();
	                            		Node eventSource = (Node)event.getSource();
	                            		Scene parentScene = eventSource.getScene();
	                            		Window parentWin = parentScene.getWindow();
	                            		mng.ShowDialog(parentWin, lstNotedetails.getSelectionModel().getSelectedItem());
	                            	}
	                            });
	                        }
	                        else
	                        {
	                        	setText("");
	                        	setOnMouseClicked(null);
	                        }
	                    }	 
	                };
	                return cell;
	            }
	        });  
	}
	
	private void refreshNotebooks()
	{
	     List<NoteBook> notebooks = Context.getInstance().getProvider().getNotebooks();
	     ObservableList<NoteBook> observableList = FXCollections.observableList(notebooks);
	     lstNotebooks.setItems(observableList);
	     /*
	       observableList.addListener(new ListChangeListener<NoteBook>() {
	           @Override
	           public void onChanged(ListChangeListener.Change change) {
	               System.out.println("Detected a change! ");
	           }
	       });
	       */   
	}
	
	private void refreshNoteheaders(Long noteBookId)
	{
    	List<NoteHeader> noteheaders = Context.getInstance().getProvider().getNoteheaders(noteBookId);
        ObservableList<NoteHeader> lstUiNoteHeaders = FXCollections.observableList(noteheaders);
        lstNoteheaders.setItems(lstUiNoteHeaders);
		
	}


    private void refreshDetail(Long noteHeaderId)
    {
    	List<NoteDetail> notedetails = Context.getInstance().getProvider().getNotedetails(noteHeaderId);
        ObservableList<NoteDetail> lstUiNoteDetails = FXCollections.observableList(notedetails);
        lstNotedetails.setItems(lstUiNoteDetails);
    }
	
}
