package com.cucumber.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class CommonUtils {

	/*
	 * Method to get the property value
	 * @param String Property Name
	 * @param String Property Location
	 * @return String property value
	 */
	public static String getConfigProperty(String propertyName, String configFileLocation) {
		String propertyValue;
		try {
			FileInputStream fileInputStream = new FileInputStream(configFileLocation);
			Properties prop = new Properties();
			try {
				prop.load(fileInputStream);
				propertyValue = prop.getProperty(propertyName);
			} catch (IOException exp) {
				throw new RuntimeException(exp);
			}
		} catch (FileNotFoundException exp) {
			throw new RuntimeException(exp);
		}
		return propertyValue;
	}
	
	public static String getConfigProperty(String propertyName) {
		String propertyValue;
		try {
			FileInputStream fileInputStream = new FileInputStream(RunConstants.RUN_CONFIG_LOCATION);
			Properties prop = new Properties();
			try {
				prop.load(fileInputStream);
				propertyValue = prop.getProperty(propertyName);
			} catch (IOException exp) {
				throw new RuntimeException(exp);
			}
		} catch (FileNotFoundException exp) {
			throw new RuntimeException(exp);
		}
		return propertyValue;
	}


	/*
	 * Method to pass data in text box
	 * @param int minimum number
	 * @param int maximum number
	 */
	public static int generateRandomNumber(int minNum, int maxNum) {
		return new Random().nextInt((maxNum - minNum) + 1) + minNum;
	}

	public static int getTextAnalysisScore(String first_text, String second_text) {

		String[] tokens1 = first_text.toLowerCase().split("\\s+");
		String[] tokens2 = second_text.toLowerCase().split("\\s+");
		int commonWordCount = 0;

		for (String token1 : tokens1) {
			for (String token2 : tokens2) {
				if (token1.equals(token2)) {
					commonWordCount++;
					break; // Break to the next token in tokens1
				}
			}
		}
		// Calculate the percentage of shared words in the first text
		if (tokens1.length == 0) {
			return 0; // Handle the case where the first text is empty
		} else {
			return (int) ((double) commonWordCount / tokens1.length * 100.0);
		}

	}
	
	public static String removeDuplicateWords(String input) {
        String[] words = input.split(" "); 
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            if (!result.toString().contains(word)) {
                result.append(word).append(" "); 
            }
        }
        return result.toString().trim();
    }
	
	public static String getTextFromPageSource(String pageSource) {
        String text= "";

		try {
		Document doc = Jsoup.parse(pageSource);
        Elements element =  doc.select("p");
        System.out.println( "");
        for (Element paragraph : element) {
            String paragraphText = paragraph.text();
            
            text = text+paragraphText;
            text = removeDuplicateWords(text);
        	}
		 }
		 catch (Exception e) {
	            e.printStackTrace();
		}
        return text;
        
	}
	
	public static String generateDummyEmail() {
        String[] domains = {"gmail.com", "outlook.com"};
        Random rand = new Random();
        int domainIndex = rand.nextInt(domains.length);
        String randomUsername = generateRandomString(8); // Generate a random username
        String domain = domains[domainIndex];
        return randomUsername + "@" + domain;
    }

    public static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(length);

        Random rand = new Random();
        for (int i = 0; i < length; i++) {
            int index = rand.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        return sb.toString();
    }

}
