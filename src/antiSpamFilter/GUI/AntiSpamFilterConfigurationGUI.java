package antiSpamFilter.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import antiSpamFilter.AntiSpamFilterAutomaticConfiguration;
import antiSpamFilter.GUI.AntiSpamFilterStyles.*;
import antiSpamFilter.rules.Rule;

/**
 * <p>AntiSpamFilterConfigurationGUI - the configuration GUI class</br>
 * </br>
 * The Graphics User Interface for the AntiSpamFilter manual configuration window. 
 * The default window has a 500x500 size and a modern design. 
 * With a six panel system design, it is possible to check and search all the
 * rules applied in the e-mails, change the weight of them manually and test the
 * optimization comparing it with the previous values.</p>
 *
 * @author ES1-2017-LIGE-PL-102
 */

public class AntiSpamFilterConfigurationGUI {
	private JFrame antiSpamFilterFrame = new JFrame("AntiSpamFilter Configuration v1.0");
	private AntiSpamFilterGUI gui;
	private AntiSpamFilterAutomaticConfiguration main;
	private String[] listOfRules;
	
	private final int WINDOW_HSIZE = 500;
	private final int WINDOW_VSIZE = 500;
	private final int COMPONENT_GAP = 10;
	private final int COMPONENT_MAX_WIDTH = WINDOW_HSIZE-(2*COMPONENT_GAP);
	private final int COMPONENT_MAX_HEIGHT = WINDOW_VSIZE-(2*COMPONENT_GAP);
	
	private final double MIN_RULE = -5.0;
	private final double MAX_RULE = 5.0;
	private final double INT_RULE = 0.1;
	private final double INIC_RULE = 0.0;
	
	//Panels initiation
	APanel ruleslistPanel, principalPanel, searchPanel, configurationPanel, inputPanel, applyPanel,
		testsPanel, conclusionPanel;

	public AntiSpamFilterConfigurationGUI(
			AntiSpamFilterAutomaticConfiguration main, AntiSpamFilterGUI gui, boolean visible) {
		//Dimension and position of the window
		antiSpamFilterFrame.setSize(WINDOW_HSIZE, WINDOW_VSIZE);
		antiSpamFilterFrame.setLocationRelativeTo(null);
		antiSpamFilterFrame.setLocation(
				(int)antiSpamFilterFrame.getLocation().getX()+80,
				(int)antiSpamFilterFrame.getLocation().getY()+20);
		
		antiSpamFilterFrame.setVisible(visible);
		this.gui = gui;
		this.main = main;
				
		setRulesListPanel();
		setMainConfigurationPanel();		
		setSearchPanel();
		setRuleConfigurationPanel();		
		setInputPanel();
		setApplyTestButtons();		
		setTestResultsPanel();
		
		configurationPanel.setLayout(new BorderLayout(COMPONENT_GAP,COMPONENT_GAP));
		configurationPanel.setPreferredSize(new Dimension(200,300));
		configurationPanel.setBorder(
				BorderFactory.createEmptyBorder(COMPONENT_GAP, 0, COMPONENT_GAP, 0));
		
		configurationPanel.add(inputPanel, BorderLayout.PAGE_START);
		configurationPanel.add(applyPanel, BorderLayout.CENTER);
		configurationPanel.add(testsPanel, BorderLayout.PAGE_END);
		
		//Implementation of back and save buttons
		conclusionPanel = new AntiSpamFilterStyles().new APanel();
		conclusionPanel.setPreferredSize(new Dimension(COMPONENT_MAX_WIDTH,50));
		setConclusaoPanel();
		
		//Inclusion of the panels in the application window
		principalPanel.add(searchPanel, BorderLayout.NORTH);
		principalPanel.add(configurationPanel, BorderLayout.CENTER);
		principalPanel.add(conclusionPanel, BorderLayout.PAGE_END);

		
		//Implementation of the environment
		antiSpamFilterFrame.setLayout(new BorderLayout(2,2));

		antiSpamFilterFrame.getContentPane().add(ruleslistPanel,BorderLayout.WEST);
		antiSpamFilterFrame.getContentPane().add(principalPanel,BorderLayout.CENTER);

		antiSpamFilterFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("Icon.png"));
		antiSpamFilterFrame.setResizable(false);
		antiSpamFilterFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		antiSpamFilterFrame.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {}
			
			@Override
			public void windowIconified(WindowEvent e) {}
			
			@Override
			public void windowDeiconified(WindowEvent e) {}
			
			@Override
			public void windowDeactivated(WindowEvent e) {}
			
			@Override
			public void windowClosing(WindowEvent e) {
				confirmCloseWindow();
			}
			
			@Override
			public void windowClosed(WindowEvent e) {}
			
			@Override
			public void windowActivated(WindowEvent e) {}
		});
	}

	private void setRulesListPanel() {
		//Implementation of rules list window
		ruleslistPanel = new AntiSpamFilterStyles().new APanel();
		ruleslistPanel.setPreferredSize(new Dimension(200,COMPONENT_MAX_HEIGHT));
		ruleslistPanel.setLayout(new BorderLayout(10,10));
		ruleslistPanel.setBorder(
				BorderFactory.createEmptyBorder(COMPONENT_GAP,COMPONENT_GAP,COMPONENT_GAP,COMPONENT_GAP));

		ALabel spamLabel = new AntiSpamFilterStyles().new ALabel("Rules List");
		ruleslistPanel.add(spamLabel, BorderLayout.PAGE_START);
		
		
	}

	
	private void setMainConfigurationPanel() {
		//Implementation of main configuration window
		principalPanel = new AntiSpamFilterStyles().new APanel();
		principalPanel.setLayout(new BorderLayout(COMPONENT_GAP,COMPONENT_GAP));
		principalPanel.setBorder(
				BorderFactory.createEmptyBorder(COMPONENT_GAP,COMPONENT_GAP,COMPONENT_GAP,COMPONENT_GAP));
	}

	
	private void setSearchPanel() {
		//Implementation of search window
		searchPanel = new AntiSpamFilterStyles().new APanel();
		ATextField searchField = new AntiSpamFilterStyles().new ATextField("");
		searchField.setPlaceholder("Search field");

		AButton clearSearchButton = 
				new AntiSpamFilterStyles().
				new AButton("Clear search", AntiSpamFilterStyles.BTN_DEFAULT);

		searchPanel.setLayout(new BorderLayout(COMPONENT_GAP,5));
		searchPanel.setPreferredSize(new Dimension(200,75));
		searchPanel.add(searchField,BorderLayout.NORTH);
		searchPanel.add(clearSearchButton, BorderLayout.CENTER);
	}

	
	private void setRuleConfigurationPanel() {
		//Implementation of rule configuration window
		configurationPanel = new AntiSpamFilterStyles().new APanel();
		inputPanel = new AntiSpamFilterStyles().new APanel();
		testsPanel = new AntiSpamFilterStyles().new APanel();
	}

	
	private void setInputPanel() {
		//Implementation of input window
		inputPanel.setLayout(new BorderLayout(COMPONENT_GAP,COMPONENT_GAP));
		ALabel configurationLabel = new AntiSpamFilterStyles().new ALabel("Configuration Panel");
		ALabel weightLabel = new AntiSpamFilterStyles().new ALabel("Rule weight:", AntiSpamFilterStyles.TXT_SMALL);

		SpinnerModel spinnerModel = new SpinnerNumberModel(INIC_RULE, MIN_RULE, MAX_RULE, INT_RULE);
		ASpinner spinner = new AntiSpamFilterStyles().new ASpinner(spinnerModel);

		inputPanel.add(configurationLabel, BorderLayout.PAGE_START);
		inputPanel.add(weightLabel, BorderLayout.LINE_START);
		inputPanel.add(spinner, BorderLayout.CENTER);
	}

	
	private void setApplyTestButtons() {
		//Implementation of apply and test buttons
		applyPanel = new AntiSpamFilterStyles().new APanel();
		applyPanel.setPreferredSize(new Dimension(COMPONENT_MAX_WIDTH,40));
		setApplyPanel();
	}

	
	private void setTestResultsPanel() {
		//Implements the test results window
		testsPanel.setLayout(new BorderLayout(COMPONENT_GAP,COMPONENT_GAP));
		ALabel testsLabel = new AntiSpamFilterStyles().new ALabel("Test results");
		String[] testResults = {"FP: 38 in 2000 mails", "FN: 45 in 2000 mails", "Compare results: improved", "Efficiency: 7%", "Tip: save the optimization values"};
		AList testResultsList = new AntiSpamFilterStyles().new AList(testResults);
		testResultsList.setFont(new Font("Arial", Font.PLAIN, 16));
		testsPanel.add(testsLabel, BorderLayout.PAGE_START);
		testsPanel.add(testResultsList, BorderLayout.CENTER);
	}

	
	private void setApplyPanel() {
		APanel buttonPanel = setButtonPanel();

		AButton applyButton = 
				new AntiSpamFilterStyles().
				new AButton("< Apply", AntiSpamFilterStyles.BTN_DEFAULT);
		
		AButton testButton = 
				new AntiSpamFilterStyles().
				new AButton("Test configuration", AntiSpamFilterStyles.BTN_DEFAULT);

		buttonPanel.add(applyButton, BorderLayout.LINE_START);
		buttonPanel.add(testButton, BorderLayout.CENTER);
		applyPanel.add(buttonPanel);
		
		testButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {				
				System.out.println("Test button test");
			}
		});
		
		applyButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {				
				System.out.println("Apply button test");
			}
		});
	}
	
	private void setConclusaoPanel() {
		APanel buttonPanel = setButtonPanel();

		AButton backButton = 
				new AntiSpamFilterStyles().
				new AButton("Back", AntiSpamFilterStyles.BTN_DEFAULT);
		
		
		AButton saveButton = 
				new AntiSpamFilterStyles().
				new AButton("Save configuration", AntiSpamFilterStyles.BTN_DEFAULT);
		
		buttonPanel.add(backButton, BorderLayout.LINE_START);
		buttonPanel.add(saveButton, BorderLayout.CENTER);
		conclusionPanel.add(buttonPanel);
		
		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {				
				confirmCloseWindow();
			}
		});

		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {				
				new AntiSpamFilterStyles().new AOptionPane();
				int result = AOptionPane.showConfirmDialog(null, 
						"Are you sure? The manual changes will be permanent",
						null, AOptionPane.YES_NO_OPTION);
				
				if (result == AOptionPane.OK_OPTION) {
					System.out.println("Save button test");
				}
			}
		});
	}
	
	private APanel setButtonPanel() {
		APanel buttonPanel = new AntiSpamFilterStyles().new APanel();
		buttonPanel.setPreferredSize(new Dimension(270,40));
		BorderLayout layout = new BorderLayout(COMPONENT_GAP,COMPONENT_GAP);
		buttonPanel.setLayout(layout);
		return buttonPanel;
	}

	public void setVisible(boolean visible) {
		antiSpamFilterFrame.setVisible(visible);
	}
	
	private void confirmCloseWindow() {
		new AntiSpamFilterStyles().new AOptionPane();
		int result = AOptionPane.showConfirmDialog(null, 
				"Are you sure? All the changes will be lost",
				null, AOptionPane.YES_NO_OPTION);

		if (result == AOptionPane.OK_OPTION) {
			antiSpamFilterFrame.setVisible(false);
			gui.setEnable(true);
		}
	}
	
	private String[] buildListOfRules() {
		ArrayList<Rule> mainListOfRules = main.getListOfRules();
		String[] listOfRules = new String[mainListOfRules.size()];
		String name;
		
		for (int i = 0; i < mainListOfRules.size(); i++) {
			name = mainListOfRules.get(i).getName();
			listOfRules[i] = name;
		}
		
		return listOfRules;
	}

	public void startConfiguration() {
		listOfRules = buildListOfRules();
		AList rulesList = new AntiSpamFilterStyles().new AList(listOfRules);
		AScrollPane scroll = new AntiSpamFilterStyles().new AScrollPane(rulesList,
				AScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
	            AScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		ruleslistPanel.add(scroll, BorderLayout.CENTER);
		
		this.setVisible(true);
	}

}
