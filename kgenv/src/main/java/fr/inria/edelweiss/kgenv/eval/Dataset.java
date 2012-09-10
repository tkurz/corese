package fr.inria.edelweiss.kgenv.eval;

import java.util.ArrayList;
import java.util.List;

import fr.inria.acacia.corese.triple.parser.ASTQuery;
import fr.inria.acacia.corese.triple.parser.Constant;
import fr.inria.edelweiss.kgram.api.core.ExpType;

/**
 * 
 * SPARQL Dataset
 * from or named may be null
 * 
 * @author Olivier Corby, Wimmics, INRIA 2012
 *
 */
public class Dataset {
	protected static final String KG = ExpType.KGRAM;
	static final String EMPTY = KG + "empty";
	static final Constant CEMPTY = Constant.create(EMPTY);

	List<String> from, named;
	
	// true when used by update (delete in default graph specified by from)
	// W3C test case is true
	// Protocol is false
	boolean isUpdate = false;
	
	Dataset(){
	}
	
	Dataset(List<String> f, List<String> n){
		from = f;
		named = n;
	}
	
	public static Dataset create(){
		return new Dataset();
	}
	
	public static Dataset create(List<String> f, List<String> n){
		if (f==null && n==null) return null;
		return new Dataset(f, n);
	}
	
	public String toString(){
		String str = "";
		str += "from:  " + from + "\n";
		str += "named: " + named ;
		return str;
	}
	
	public void defFrom(){
		from = new ArrayList<String>();
	}
	public void defNamed(){
		named = new ArrayList<String>();
	}
	public boolean isUpdate(){
		return isUpdate;
	}
	
	public boolean isEmpty(){
		return ! hasFrom() && ! hasNamed();
	}
	
	public boolean hasFrom(){
		return from != null && from.size() >0;
	}
	
	public boolean hasNamed(){
		return named != null && named.size() >0;
	}
	
	
	public void setUpdate(boolean b){
		isUpdate = b;
	}
	
	public List<String> getFrom(){
		return from;
	}
	
	public List<String> getNamed(){
		return named;
	}
	
	public void clean(){
		from.remove(EMPTY);
	}

	
	public void addFrom(String s){
		if (from == null) defFrom();
		if (! from.contains(s)){
			from.add(s);
		}
	}
	
	public void addNamed(String s){
		if (named == null) defNamed();
		if (! named.contains(s)){
			named.add(s);
		}
	}
	
	/**
	 * Std SPARQL Dataset requires that if from (resp named) is empty in a Dataset
	 * simple query triple (resp graph query triple) fail
	 * In order to make kgram fail accordingly, we add a fake from (resp named) 
	 */
	public void complete(){
		if (getFrom() != null && getNamed() == null){
			addNamed(EMPTY);
		}
		else if (getFrom() == null && getNamed() != null){
			addFrom(EMPTY);
		}
	}
	
	public void complete(ASTQuery ast){
		if (ast.getFrom().size() > 0 && ast.getNamed().size() == 0){
			ast.setNamed(CEMPTY);
		}
		if (ast.getFrom().size() == 0 && ast.getNamed().size() > 0){
			ast.setFrom(CEMPTY);
		}
	}

}
