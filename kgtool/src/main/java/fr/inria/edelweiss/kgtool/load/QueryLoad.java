package fr.inria.edelweiss.kgtool.load;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import fr.inria.edelweiss.kgraph.query.QueryEngine;

public class QueryLoad {
	static final String HTTP = "http://";
	static final String FILE = "file://";
	static final String FTP  = "ftp://";

	static final String[] PROTOCOLS = {HTTP, FILE, FTP};


	
	QueryEngine engine;
	
	QueryLoad(){
		
	}
			
	QueryLoad(QueryEngine e){
		engine = e;
	}
	
	public static QueryLoad create(){
		return new QueryLoad();
	}
	
	public static QueryLoad create(QueryEngine e){
		return new QueryLoad(e);
	}
	
	public void load(String name){
		String q = read(name);
		if (q != null){
			engine.addQuery(q);
		}
	}
	
	boolean isURL(String name){
		for (String s : PROTOCOLS){
			if (name.startsWith(s)){
				return true;
			}
		}
		return false;
	}
	
	public String read(String name){
		String query = "", str = "";
		try {
			Reader fr;
			if (isURL(name)){
				URL url = new URL(name);
				fr = new InputStreamReader(url.openStream());
			}
			else {
				fr = new FileReader(name);
			}
			BufferedReader fq = new BufferedReader(fr);
			while (true){
				str = fq.readLine();
				if (str == null){
					fq.close();
					break;
				}
				query += str + "\n";
			}
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
		}
		if (query == "") return null;
		return query;
	}
	
	

}
