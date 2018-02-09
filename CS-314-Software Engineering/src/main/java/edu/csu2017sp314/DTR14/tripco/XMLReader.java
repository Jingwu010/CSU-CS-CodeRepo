/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.csu2017sp314.DTR14.tripco;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class XMLReader {

    public String[] readSelectFile(String filename, StringBuilder csvName) throws FileNotFoundException{
    	if(!new File(filename).exists()) return new String[0];
		Scanner scan = new Scanner(new File(filename));
		ArrayList<String> subs = new ArrayList<String>();
		String temp = null;
		while(scan.hasNextLine()){
			if(!checkValid(scan.nextLine().trim(), "<?xml")) break;
			if(scan.hasNextLine() && !checkValid(scan.nextLine().trim(), "<selection")) break;
			if(scan.hasNextLine() && !checkValid(scan.nextLine().trim(), "<title")) break;
			if(scan.hasNextLine() && !checkValid(scan.nextLine().trim(), "<destinations")) break;
			while(scan.hasNextLine() && checkValid(temp = scan.nextLine().trim(), "<id")){
				temp = temp.substring(temp.indexOf('>')+1);
				temp = temp.substring(0, temp.indexOf('<'));
				subs.add(temp.trim());
			} 
			if(scan.hasNextLine() && !checkValid(temp, "</destinations")) break;
			if(scan.hasNextLine() && checkValid(scan.nextLine().trim(), "</selection")){
				scan.close();
				return subs.toArray(new String[0]);
			}
		}
		return new String[0];
    }

	private boolean checkValid(String checks, String compareTo){
		if (checks == null) return false;
		else if (checks.length() > compareTo.length() &&
				checks.substring(0, compareTo.length()).equalsIgnoreCase(compareTo)){
			return true;
		}
		else return false;
	}
}