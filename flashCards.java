import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.BoxLayout;
import java.util.Random;
import static javax.swing.JOptionPane.*;

public class flashCards {
	int limit;
	Integer choice;
	Integer correct;
	Integer num1;
	Integer num2;
	Integer score = 0;
	Integer count = 0;
	String operator;
	String gameType = "addition";
	Integer numberOfDigits = 1;
	JFrame win;
	JTextField problem;
	JTextField title;
	JTextField scoreBoard;
	JTextField answer;
	JTextField results;
	
	String additionString = "+ Addition";
	String subtractionString = "- Subtraction";
	String multiplicationString = "x Multiplication";
	String divisionString = "\u00F7 Division";
	String singdigitString = "1 Digit";
	String doubdigitString = "2 Digits";
	
	Font font = new Font("Times New Roman", Font.PLAIN, 24);
	Font fontBig = new Font("Times New Roman", Font.PLAIN, 36);
	
	JPanel menuPanel;
	JPanel menuButtons;
	JPanel gamePanel;
	JPanel problemPanel;
	JPanel scoreBoardPanel;
	JPanel resultsPanel;
	JPanel answerButtonPanel;
	
	MyListener listener;
	boolean initialized = false;
	String tempGameType;
	Integer tempNumberOfDigits;
	String resultsString;
	String problemString;
	
	JRadioButton addButton;
	JRadioButton subButton;
	JRadioButton multButton;
	JRadioButton diviButton;
	
	JRadioButton singDigit;
	JRadioButton doubDigit;
	
	JButton ok;
	JButton cancel;
	
	JButton quit;
	JButton newGame;
	JButton reset;
	JButton answerButton;
	
	Random rng;
	private class MyListener implements ActionListener
	{
		public void actionPerformed( ActionEvent e )
      {
			if (e.getSource( ) == addButton)
			{
				gameType = "addition";
			}
			if (e.getSource( ) == subButton)
			{
				gameType = "subtraction";	
			}
			if (e.getSource( ) == multButton)
			{
				gameType = "multiplication";
			}
			if (e.getSource( ) == diviButton)
			{
				gameType = "division";
			}
			if (e.getSource( ) == singDigit)
			{
				numberOfDigits = 1;
			}
			if (e.getSource( ) == doubDigit)
			{
				numberOfDigits = 2;
			}
			if (e.getSource( ) == ok)
			{
				if (initialized)
				{
					goToGame( );
					playGame( );
				} else {
					setUpGame( );
				}
				
			}
			if (e.getSource( ) == cancel)
			{
				if (initialized)
				{
					gameType = tempGameType;
					numberOfDigits = tempNumberOfDigits;
					if (gameType == "addition")
					{
						addButton.setSelected(true);
					} else if (gameType == "subtraction")
					{
						subButton.setSelected(true);
					} else if (gameType == "multiplication")
					{
						multButton.setSelected(true);
					} else {
						diviButton.setSelected(true);
					}
					if (numberOfDigits == 1)
					{
						singDigit.setSelected(true);
					} else {
						doubDigit.setSelected(true);
					}
					goToGame( );
					playGame( );
				} else {
					System.exit(0);
				}
				
			}
			if (e.getSource( ) == newGame)
			{
				tempGameType = gameType;
				tempNumberOfDigits = numberOfDigits;
				goToMenu( );
				win.repaint( );				
			}
			if (e.getSource( ) == reset)
			{
				score = 0;
				count = 0;
				updateScoreBoard( );
				answer.requestFocus();
			}
			if (e.getSource( ) == quit)
			{
				System.exit(0);
			}
			if (e.getSource( ) == answerButton || e.getSource( ) == answer)
			{
				checkAnswer( );
			}
      }

	}
	
	public static void main(String[] args) {
		new flashCards( );
		
	}
	
	public flashCards( )
	{
		Color bgColor1 = new Color(128, 128, 128);
		Color textColor = new Color (0, 0, 0);
		
		win = new JFrame("Flash Cards");
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		title = new JTextField (30);
		title.setText("Welcome to Flash Cards");
		title.setHorizontalAlignment(JTextField.CENTER);
		title.setFont( new Font("Times New Roman", Font.BOLD, 36));
		title.setForeground(textColor);
		title.setEnabled(false);
		listener = new MyListener( );
		
		win.setForeground(textColor);
		win.setBackground(bgColor1);
		win.setLayout( new BorderLayout( ));
		win.add( title, BorderLayout.NORTH );
		flashCardMenu( );
		
		
	}
		
	public void flashCardMenu( )
	{
		JPanel gameTypePanel = new JPanel( );
		gameTypePanel.setLayout( new BoxLayout(gameTypePanel, BoxLayout.Y_AXIS ));
		
		JPanel numDigitsPanel = new JPanel( );
		numDigitsPanel.setLayout( new BoxLayout(numDigitsPanel, BoxLayout.Y_AXIS));
		
		JPanel optionsPanel = new JPanel( );
		optionsPanel.setLayout( new FlowLayout( ));
		
		JPanel buttonsPanel = new JPanel( );
		buttonsPanel.setLayout( new FlowLayout( ));
		
		menuPanel = new JPanel( );
		menuPanel.setLayout( new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
		
		
		
		ButtonGroup gameType = new ButtonGroup( );
		ButtonGroup numDigits = new ButtonGroup( );
		
		addButton = addARadioButton(additionString, gameTypePanel, gameType, listener, true);
		subButton = addARadioButton(subtractionString, gameTypePanel, gameType, listener);
		multButton = addARadioButton(multiplicationString, gameTypePanel, gameType, listener);
		diviButton = addARadioButton(divisionString, gameTypePanel, gameType, listener);
		
		singDigit = addARadioButton(singdigitString, numDigitsPanel, numDigits, listener, true);
		doubDigit = addARadioButton(doubdigitString, numDigitsPanel, numDigits, listener);
		
		ok = addAButton("Let's Go!", buttonsPanel, listener);
		cancel = addAButton("Nevermind", buttonsPanel, listener);
		
		optionsPanel.add(gameTypePanel);
		optionsPanel.add(numDigitsPanel);
		
		menuPanel.add(optionsPanel);
		menuPanel.add(buttonsPanel);
				
		win.add(menuPanel, BorderLayout.CENTER);
		win.setSize(1000, 750);
		win.setVisible( true );
	}
	
	public JRadioButton addARadioButton(String text, Container container, ButtonGroup buttonGroup, MyListener list)
	{
		JRadioButton radioButton = new JRadioButton(text);
		radioButton.setActionCommand(text);
		buttonGroup.add(radioButton);
		container.add(radioButton);
		radioButton.addActionListener(list);
		radioButton.setFont(font);
		return radioButton;
	}
	
	public JRadioButton addARadioButton(String text, Container container, ButtonGroup buttonGroup, MyListener list, Boolean selected)
	{
		JRadioButton radioButton = new JRadioButton(text);
		radioButton.setActionCommand(text);
		buttonGroup.add(radioButton);
		container.add(radioButton);
		radioButton.setFont(font);
		radioButton.addActionListener(list);
		radioButton.setSelected(selected);
		return radioButton;
	}
	
	public JButton addAButton(String text, Container container, MyListener list)
	{
		JButton button = new JButton(text);
		button.setActionCommand(text);
		container.add(button);
		button.addActionListener(list);
		button.setFont(font);
		button.setPreferredSize(new Dimension(200, 40));
		return button;
	}
	
	public void setUpGame( )
	{
		
		gamePanel = new JPanel( );
		gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));
		
		resultsPanel = new JPanel( );
		resultsPanel.setLayout(new FlowLayout( ));
		
		results = new JTextField(25);
		results.setEnabled(false);
		results.setFont(fontBig);
		results.setText("Let's get Started!");
		
		resultsPanel.add(results);
		
		problemPanel = new JPanel( );
		problemPanel.setLayout(new FlowLayout());
		
		problem = new JTextField(10 );
		problem.setEnabled(false);
		problem.setFont(fontBig);
		
		answer = new JTextField(3 );
		answer.setFont(fontBig);
		answer.addActionListener(listener);
		
		answerButtonPanel = new JPanel( );
		answerButtonPanel.setLayout(new FlowLayout( ));
		
		answerButton = addAButton("Answer", answerButtonPanel, listener);
		
		initialized = true;

			
		setUpGameButtons( );
		setUpScoreBoard( );
		problemPanel.add(problem);
		problemPanel.add(answer);
		
		rng = new Random( );
		
		gamePanel.add(resultsPanel);
		gamePanel.add(problemPanel);
		gamePanel.add(answerButtonPanel);
		win.remove(menuPanel);
		win.add(gamePanel, BorderLayout.CENTER);
		win.setVisible(true);
		answer.requestFocus( );
		
		score = 0;
		count = 0;
		updateScoreBoard( );
		
		playGame( );
	}
	
	public void playGame( )
	{
		limit = getPower(10, numberOfDigits);
		switch (gameType)
		{
		case "addition":
			operator = " + ";
			additionGame( );
			break;
		case "subtraction":
			operator = " - ";
			subtractionGame( );
			break;
		case "multiplication":
			operator = " x ";
			multiplicationGame( );
			break;
		case "division":
			operator = " \u00F7 ";
			divisionGame( );
			break;
		default:
			System.out.println("error in game types");
		}

		win.repaint();
	}
	
	public void setUpGameButtons( )
	{
		menuButtons = new JPanel( );
		menuButtons.setLayout(new GridLayout(3, 1, 2, 40));
		reset = addAButton("Reset Scores", menuButtons, listener);
		newGame = addAButton("New Game", menuButtons, listener);
		quit = addAButton("Quit", menuButtons, listener);
		win.add(menuButtons, BorderLayout.WEST);
		
		win.setVisible(true);
		win.repaint();
	}
	
	public void goToMenu( )
	{
		
		win.remove(gamePanel);
		win.remove(menuButtons);
		win.remove(scoreBoardPanel);
		win.add(menuPanel, BorderLayout.CENTER);
		win.setVisible(true);
		win.repaint();
	}
	
	public void goToGame( )
	{
		win.remove(menuPanel);
		win.add(gamePanel, BorderLayout.CENTER);
		win.add(menuButtons, BorderLayout.WEST);
		win.add(scoreBoardPanel, BorderLayout.SOUTH);
		win.repaint();
		answer.requestFocus();
	}
		
	public void additionGame( )
	{
		getNewProblem( );
		correct = num1 + num2;
		showProblem(num1, num2, operator);
	}
		
	public void subtractionGame( )
	{
		getNewProblem( );
		correct = num1 - num2;
		showProblem(num1, num2, operator);		
	}
	
	public void multiplicationGame( )
	{
		getNewProblem( );
		correct = num1 * num2;
		showProblem(num1, num2, operator);		
	}
	
	public void divisionGame( )
	{
		num2 = rng.nextInt(limit-1) + 1;
		correct = rng.nextInt(limit-1) + 1;
		num1 = num2 * correct;
		showProblem(num1, num2, operator);			
	}
	
	public void setUpScoreBoard( )
	{
		scoreBoardPanel = new JPanel( );
		scoreBoard = new JTextField(15 );
		scoreBoard.setEnabled(false);
		scoreBoard.setFont(fontBig);
		scoreBoard.setHorizontalAlignment(JTextField.CENTER);
		scoreBoardPanel.add(scoreBoard);
		win.add(scoreBoardPanel, BorderLayout.SOUTH);
		updateScoreBoard( );
		win.setVisible(true);
		win.repaint( );
		
	}
	
	public void getNewProblem( )
	{
		num1 = rng.nextInt(limit);
		num2 = rng.nextInt(limit);
		if (num1 < num2)
		{
			Integer temp = num1;
			num1 = num2;
			num2 = temp;
		}
	}
	
	public void updateScoreBoard( )
	{
		String scoreString = "Score: " + score.toString( ) + " out of " + count.toString( );
		scoreBoard.setText(scoreString);
	}
	
	public void showProblem(Integer n1, Integer n2, String operator)
	{
		problemString = n1.toString() + operator + n2.toString() + " = ";
		problem.setText(problemString);
		win.repaint();
	}
	
	public void checkAnswer( )
	{
		try
		{
			choice = Integer.parseInt(answer.getText( ));
			count++;
			if (choice.equals(correct))
			{
				score++;
				resultsString = "That is right! " + problemString + correct.toString();				
			} else {
				resultsString = "Oops, that is not right. " + problemString + correct.toString();				
			}
			results.setText(resultsString);
			updateScoreBoard( );
			playGame( );
		}
		catch (NumberFormatException ex)
		{
			showMessageDialog(win, ex.getMessage() + " is not a number.  Try again.");
			answer.setText(" ");
			answer.requestFocus(true);
		}
		answer.setText("");
		answer.requestFocus(true);		
	}
	
	public int getPower(int base, int power)
	{
		int j = 1;
		for (int i=1; i <= power; i++)
		{
			j*= base;
		}
		return j;
	}

}
