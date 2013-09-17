/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.inria.edelweiss.kgtool.util;

import fr.inria.acacia.corese.exceptions.EngineException;
import fr.inria.acacia.corese.triple.parser.ASTQuery;
import fr.inria.acacia.corese.triple.parser.NSManager;
import fr.inria.acacia.corese.triple.printer.SPIN;
import fr.inria.edelweiss.kgram.core.Query;
import fr.inria.edelweiss.kgraph.core.Graph;
import fr.inria.edelweiss.kgraph.query.QueryProcess;
import fr.inria.edelweiss.kgtool.load.Load;
import fr.inria.edelweiss.kgtool.load.LoadException;
import fr.inria.edelweiss.kgtool.print.PPrinter;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Compile SPARQL Query in SPIN format
 *
 * @author Olivier Corby, Wimmics Inria I3S, 2013
 *
 */
public class SPINProcess {

    Graph graph;
    QueryProcess exec;
    private boolean isDebug = false,
            isSPARQLCompliant = false;


    public static SPINProcess create() {
        return new SPINProcess();
    }

    SPINProcess() {
        exec = QueryProcess.create(Graph.create());
    }

   public Graph getGraph(){
       return graph;
   }
   
    /**
     * PPrint SPARQL query into SPIN Turtle using Visitor, parse SPIN Turtle
     * into RDF Graph, PPrint RDF graph using templates back into SPARQL
     */
    public String toSpinSparql(String sparql) throws EngineException {
        if (isDebug) {
            System.out.println("Input: \n" + sparql);
        }
        Query qq = exec.compile(sparql);
        if (isSPARQLCompliant && !qq.isCorrect()) {
            return sparql;
        }
        return toSpinSparql(exec.getAST(qq));
    }

    public String toSpin(String sparql) throws EngineException {
        if (isDebug) {
            System.out.println("Input: \n" + sparql);
        }
        Query qq = exec.compile(sparql);
        ASTQuery ast = exec.getAST(qq);
        if (isDebug) {
            System.out.println("AST: \n" + ast);
        }
        SPIN sp = SPIN.create();
        sp.visit(ast);
        String spin = sp.toString();
        return spin;
    }

    String toSpinSparql(ASTQuery ast) throws EngineException {
        if (isDebug) {
            System.out.println("AST: \n" + ast);
        }
        SPIN sp = SPIN.create();
        sp.visit(ast);
        String spin = sp.toString();
        if (isDebug) {
            System.out.println("SPIN: \n" + spin);
        }
        return toSparql(spin, ast.getNSM());
    }

    public String toSparql(String spin) throws EngineException {
        return toSparql(spin, null);
    }

     public Graph toGraph(String spin) throws EngineException {
         graph = Graph.create();
         return toGraph(spin, graph);
     }
     
     
     public Graph toGraph(String spin, Graph g) throws EngineException {
        Load ld = Load.create(g);
        try {
            ld.load(new ByteArrayInputStream(spin.getBytes("UTF-8")), "spin.ttl");
        } catch (LoadException ex) {
            Logger.getLogger(SPINProcess.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SPINProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return g;
     }
    
    public String toSparql(String spin, NSManager nsm) throws EngineException {
        Graph g = toGraph(spin);
        if (isDebug) {
            //System.out.println("Graph:\n" + g.display());
        }
        return toSparql(g, nsm);
    }
    
    

    String toSparql(Graph g, NSManager nsm) throws EngineException {
        PPrinter p = PPrinter.create(g, PPrinter.SPIN);
        if (nsm != null) {
            p.setNSM(nsm);
        }
        String s = p.toString();

        if (s.length() == 0) {
            throw new EngineException("Uncorrect SPIN Query");
        }
        if (isDebug) {
            System.out.println("PPrint: \n" + s);
        }
        return s;
    }

    public void setDebug(boolean b) {
        isDebug = b;
    }

    /**
     * @return the isSPARQLCompliant
     */
    public boolean isSPARQLCompliant() {
        return isSPARQLCompliant;
    }

    /**
     * @param isSPARQLCompliant the isSPARQLCompliant to set
     */
    public void setSPARQLCompliant(boolean isSPARQLCompliant) {
        this.isSPARQLCompliant = isSPARQLCompliant;
    }
}