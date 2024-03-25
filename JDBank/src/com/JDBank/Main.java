package com.JDBank;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;




public class Main {
	  public static void main(String[] args) {
		  	Client[] clients = generateClients(3);
		  	Account[] accounts;
		  	Hashtable<Integer,Account> accountsHashtable;
		  	Flow[] flows;
		  	
		  	// Comment methods, by default generating Accounts and Flows don't come from the JSON and XML files
		  	// Swap the comments to change that
			try {
				// Accounts
				accounts = generateAccounts(clients);
				//accounts = generateAccountsFromXmlFile(new File("resources/accounts.xml"), clients);

				accountsHashtable = generateHashtable(accounts);			
				
				// Flows
				flows = generateFlows(accounts);
				//flows = generateFlowsFromJsonFile(Path.of("resources", "flows.json"));
				updateAccountBalances(flows, accountsHashtable);
				
			  	// Prints
			  	printClients(clients);  			  	
			  	printAccountsAscendingBalance(accountsHashtable);
			  	checkNegativeBalances(accountsHashtable);
			  	printFlows(flows);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		  	
	  }
	  
	  
	  static Client[] generateClients(int numberOfClients) {
		  Client[] clients =  new Client[numberOfClients];
		  
		  for (int i = 1; i <= numberOfClients; i++) {
			  clients[i-1] = new Client("name" + i, "firstname" + i);
		  }
		  return clients;
		    
	  }
	  
	  static void printClients(Client[] clients) {
		  System.out.println("--------Clients--------");
		  Arrays.stream(clients).forEach(client -> System.out.println(client.toString()));
	  }  
	  
		static Account[] generateAccounts(Client[] clients) {
			int totalNumberOfAccounts = clients.length * 2;
			int accountIndex = 0; // keeps track of what index we are working on on the accounts array
			Account[] accounts = new Account[totalNumberOfAccounts];
			
				// create 1 CurrentAccount and 1 SavingsAccount per Client
			  for (int i = 0; i < clients.length; i++) {
				  accounts[accountIndex] = new CurrentAccount("Current", clients[i]);
				  accountIndex++;
				  accounts[accountIndex] = new SavingsAccount("Savings", clients[i]);
				  accountIndex++;
			  }
			
			
			return accounts;
		}
		
	    public static void printAccounts(Account[] accounts) {
	    	Arrays.stream(accounts).forEach(account -> System.out.println(account.toString()));
	    }
	    
	    public static Hashtable<Integer,Account> generateHashtable(Account[] accounts) {
	        Hashtable<Integer, Account> accountHashtable = new Hashtable<>();
	        for (Account account : accounts) {
	            accountHashtable.put(account.getAccountNumber(), account);
	        }
	        return accountHashtable;    
	    }
	    
	    public static void printAccountsAscendingBalance(Hashtable<Integer, Account> accountHashtable) {
	    	System.out.println("--------Accounts ascending balance--------");
	        accountHashtable.entrySet().stream()
	                .sorted(Map.Entry.comparingByValue((a1, a2) -> Double.compare(a1.getBalance(), a2.getBalance())))
	                .forEach(entry -> System.out.println(entry.getValue().toString()));
	    }
	    
	    public static Flow[] generateFlows(Account[] accounts) {	  
	    	// (debit from #1) + (credit & savings accounts) + (transform from #1 to #2)
	        Flow[] flows = new Flow[1 + accounts.length + 1]; 

	        // Calculate date 2 days from now
	        LocalDate futureDate = LocalDate.now().plusDays(2);
	        Date flowDate = Date.from(futureDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

	        // Debit of 50€ from account #1
	        flows[0] = new Debit("Debit of 50€", 1, 50.0, 1, false, flowDate);

	        // Starting at 1 because we already included a Debit above in the flows array
	        int flowIndex = 1;
	        for (Account account : accounts) {
	        	// Credit of 100.50€ on all current accounts
	            if (account instanceof CurrentAccount) {
	                flows[flowIndex] = new Credit("Credit of 100.50€ on current account", flowIndex + 1, 100.50, account.getAccountNumber(), true, flowDate);
	                flowIndex++;
	                
	             // Credit of 1500€ on all savings accounts
	            } else if (account instanceof SavingsAccount) {
	                flows[flowIndex] = new Credit("Credit of 1500€ on savings account", flowIndex + 1, 1500.0, account.getAccountNumber(), true, flowDate);
	                flowIndex++;
	            }
	        }

	        // Transfer of 50€ from account #1 to account #2
	        flows[flowIndex] = new Transfer("Transfer of 50€ from account #1 to account #2", flowIndex + 1, 50.0, 2, false, flowDate, 1);

	        return flows;
	    }
	    
	    public static void printFlows(Flow[] flows) {
	    	System.out.println("--------Flows--------");
	        for (Flow flow : flows) {
	            System.out.println(flow.toString());
	        }
	    }
	    
	    public static void updateAccountBalances(Flow[] flows, Map<Integer, Account> accountMap) {
	        for (Flow flow : flows) {
	            Account account = accountMap.get(flow.getTargetAccountNumber());
	            if (account != null) {
	                account.setBalance(flow);
	            }
	            if (flow instanceof Transfer) {
	                Transfer transfer = (Transfer) flow;
	                Account sourceAccount = accountMap.get(transfer.getOriginAccountNumber());
	                if (sourceAccount != null) {
	                    sourceAccount.setBalance(flow);
	                }
	            }
	        }
	    }
	    
	    public static void checkNegativeBalances(Map<Integer, Account> accountMap) {
	        Predicate<Account> isNegativeBalance = account -> account.getBalance() < 0;
	        Optional<Account> negativeBalanceAccount = accountMap.values().stream()
	                .filter(isNegativeBalance)
	                .findFirst();

	        negativeBalanceAccount.ifPresent(account ->
	                System.out.println("Account " + account.getAccountNumber() + " has a negative balance."));
	    }
	    
	    // ------------------------ JSON Start -------------------------------

	    public static Flow[] generateFlowsFromJsonFile(Path jsonFilePath) throws IOException {
	        ObjectMapper mapper = new ObjectMapper();
	        
	        // Deserialize JSON array into a list of JsonFlowData objects
	        List<JsonFlowData> jsonFlowDataList = mapper.readValue(Files.newBufferedReader(jsonFilePath), new TypeReference<List<JsonFlowData>>(){});
	        
	        // Convert JsonFlowData objects to Flow objects 
	        return jsonFlowDataList.stream()
                    .map(Main::mapToFlow)
                    .toArray(Flow[]::new);
	    }
	    
	    private static Flow mapToFlow(JsonFlowData jsonFlowData) {
	        switch (jsonFlowData.getType()) {
	            case "Debit":
	                return new Debit(jsonFlowData.getComment(), jsonFlowData.getIdentifier(), jsonFlowData.getAmount(), jsonFlowData.getTargetAccountNumber(), jsonFlowData.isEffect(), jsonFlowData.getDate());
	            case "Credit":
	                return new Credit(jsonFlowData.getComment(), jsonFlowData.getIdentifier(), jsonFlowData.getAmount(), jsonFlowData.getTargetAccountNumber(), jsonFlowData.isEffect(), jsonFlowData.getDate());
	            case "Transfer":
	                return new Transfer(jsonFlowData.getComment(), jsonFlowData.getIdentifier(), jsonFlowData.getAmount(), jsonFlowData.getTargetAccountNumber(), jsonFlowData.isEffect(), jsonFlowData.getDate(), jsonFlowData.getSourceAccountNumber());
	            default:
	                throw new IllegalArgumentException("Invalid flow type: " + jsonFlowData.getType());
	        }
	    }
	    
	    // -------------------------- JSON END --------------------------------
	    
	    // -------------------------- XML START -------------------------------
	    public static Account[] generateAccountsFromXmlFile(File xmlFile, Client[] clients) throws Exception {
	        List<Account> accountList = new ArrayList<>();

	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder builder = factory.newDocumentBuilder();
	        Document document = builder.parse(xmlFile);

	        NodeList accountNodes = document.getElementsByTagName("account");
	        for (int i = 0; i < accountNodes.getLength(); i++) {
	            Element accountElement = (Element) accountNodes.item(i);
	            String label = accountElement.getAttribute("label");
	            NodeList clientNodes = accountElement.getElementsByTagName("client");
	            Element clientElement = (Element) clientNodes.item(0);
	            int clientNumber = Integer.parseInt(clientElement.getAttribute("clientNumber"));
	            

	            // Find the right client to associate
	            for (int j = 0; j < clients.length ; j++) {
	            	if(clients[j].getClientNumber() == clientNumber) {
	    	            String accountType = accountElement.getAttribute("type");
	    	            Account account;
	    	            if ("Savings".equalsIgnoreCase(accountType)) { // Savings
	    	                account = new SavingsAccount(label, clients[j]);
	    	            } else { // Current
	    	                account = new CurrentAccount(label, clients[j]);
	    	            }
	    	            accountList.add(account);
	            	}
	            }
	        }

	        return accountList.toArray(new Account[0]);
	    }
	    
	 // -------------------------- XML END -------------------------------
	    
	    
}


