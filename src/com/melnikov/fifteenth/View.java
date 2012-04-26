package com.melnikov.fifteenth;



import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

public class View {

	private Model model;
	
	
	public View(Model model) {
		
		this.model = model;
		
		
	}
	
	public void show(){
		
		final Display display = new Display();
		final Shell shell = new Shell(SWT.SHELL_TRIM & (~SWT.RESIZE));
		
		shell.setText("Game 15");
		
		Menu menu = new Menu(shell, SWT.BAR);
		MenuItem gameInfoHeader = new MenuItem(menu, SWT.CASCADE);
		gameInfoHeader.setText("&Game");
		
		Menu game = new Menu(shell, SWT.DROP_DOWN);
		gameInfoHeader.setMenu(game);
		
		MenuItem newGame = new MenuItem(game, SWT.PUSH);
		newGame.setText("&New game...");
		
		MenuItem about = new MenuItem(game, SWT.PUSH);
		about.setText("&About");
		
		MenuItem exit = new MenuItem(game, SWT.PUSH);
		exit.setText("&Exit");
		
		newGame.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				initBoard(shell);
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		about.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageBox messageBox = new MessageBox(shell);
				messageBox.setText("About");
				messageBox.setMessage("Author : Melnikov Aleksandr, DonNTU.\nE-mail : makovkastar@gmail.com");
				messageBox.open();
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		exit.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
				display.dispose();
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		shell.setMenuBar(menu);
		
		initBoard(shell);

		
		 shell.setSize(shell.computeSize(400, 400));
		 
		 //shell.setBackgroundImage(new Image(Display.getCurrent(), "images/wood.jpg"));
		 shell.setBackground(new Color(display,84,84,84));
			
		 Monitor primary = display.getPrimaryMonitor();
		 Rectangle bounds = primary.getBounds();
		 Rectangle rect = shell.getBounds();
		    
		 int x = bounds.x + (bounds.width - rect.width) / 2;
		 int y = bounds.y + (bounds.height - rect.height) / 2;
		    
		 shell.setLocation(x, y);
		
		 shell.setImage(new Image(display, "images/icon.png"));
		 
		 shell.open ();


		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep ();
			}
		}
		display.dispose ();
	}
	
	private void initBoard(final Shell shell){
		
		for(Control control : shell.getChildren()){
			
			control.dispose();
			
		}
		
		model.initMatrix();
		
		GridLayout gridLayout = new GridLayout(4, true);
		gridLayout.horizontalSpacing = 2;
		gridLayout.verticalSpacing = 2;
		
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		
				
		shell.setLayout(gridLayout);
		
		final MouseListener clickListener = new MouseAdapter() {

			@Override
			public void mouseUp(MouseEvent e) {

				SquareButton button = (SquareButton)e.widget;
				Rectangle rect = button.getBounds();
				
				int buttonY = rect.x / rect.width;
				int buttonX = rect.y / rect.height;
				
				
				Move move = model.cellMove(buttonX, buttonY);
				
				switch (move) {
				case UP:
					
					rect.y = rect.y - rect.height - 2;
					break;

				case DOWN:
					
					rect.y = rect.y + rect.height + 2;
					break;
					
				case RIGHT:
					
					rect.x = rect.x + rect.width + 2;
					break;
					
				case LEFT:
					
					rect.x = rect.x - rect.width - 2;
					break;
					
				default:
					break;
				}
				
				
				button.setBounds(rect);
				
				if(model.checkWin()){
					
					MessageBox messageBox = new MessageBox(shell, SWT.ICON_INFORMATION);
				    messageBox.setMessage("Congratulations. You win!");
				    int rc = messageBox.open();
				    
				    switch (rc) {
				    	case SWT.OK:
				    		initBoard(shell);
				    		break;
				    }
				    
				}
			}
		}; 
		
		
		int[][] matrix = model.getMatrix();
		
		for(int i=0; i<Constants.BOARD_X; i++){
			
			for(int j = 0; j<Constants.BOARD_Y; j++){

				SquareButton cell = new SquareButton(shell, SWT.PUSH);
				cell.setRoundedCorners(true);
				cell.setBackgroundImage(new Image(Display.getCurrent(), "images/wood.jpg"));
				cell.setBackgroundImageStyle(SquareButton.BG_IMAGE_STRETCH);
				cell.setText(String.valueOf(matrix[i][j]));
				cell.setBorderWidth(0);
				cell.setFont(new Font(Display.getCurrent(),"Georgia", 16, SWT.BOLD));
				cell.addMouseListener(clickListener);
				cell.setLayoutData(gridData);
				
				if(matrix[i][j] == Constants.EMPTY_CELL){
					cell.setVisible(false);	
				}
			}
			
		}
		
		shell.layout(true, true);

		
	}
	
	
}
