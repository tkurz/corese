package fr.inria.acacia.corese.cg.datatype;

import fr.inria.acacia.corese.api.IDatatype;

/**
 * <p>Title: Corese</p>
 * <p>Description: A Semantic Search Engine</p>
 * <p>Copyright: Copyright INRIA (c) 2007</p>
 * <p>Company: INRIA</p>
 * <p>Project: Acacia</p>
 * <br>
 * An abstract class, super-class of all CoreseNumber datatype; it defines static methods
 * <br>
 * @author Olivier Savoie
 */

public abstract class CoreseNumber extends CoreseDatatype {
	static final int code=NUMBER;

	/**
	 * Cast a number to integer means take the integer part,
	 * not just parsing the string label<br />
	 * Cast a number to a boolean is always allowed and should always give a result
	 */
	public IDatatype cast(IDatatype target, IDatatype javaType) {
		if (target.getLabel().equals(RDF.xsdinteger)){
			return DatatypeMap.newInstance(intValue());
		}
		else if (target.getLabel().equals(RDF.xsdboolean)){
			if (longValue() == 0)      return CoreseBoolean.FALSE;
			else if (longValue() == 1) return CoreseBoolean.TRUE;
			else return null;
		}
		else return super.cast(target, javaType);
	}
	
	public boolean isNumber(){
		return true;
	}
	
	public boolean isTrueAble() {
		return true;
	}
	
	
	public  int getCode(){
		return code;
	}
	
	public IDatatype getDatatype() {
		return datatype;
	}
	
	
	public IDatatype plus(IDatatype dt) {
		switch (getCode()){
		case DOUBLE:
			return new CoreseDouble(doubleValue() + dt.doubleValue());
			
		case FLOAT:
			switch (dt.getCode()){
			case DOUBLE: return new CoreseDouble(doubleValue() + dt.doubleValue());
			default:     return new CoreseFloat(floatValue() + dt.floatValue());
			}
			
		case DECIMAL:
			switch (dt.getCode()){
			case DOUBLE: return new CoreseDouble(doubleValue() + dt.doubleValue());
			case FLOAT:  return new CoreseFloat(floatValue() + dt.floatValue());
			default:     return new CoreseDecimal(doubleValue() + dt.doubleValue());
			}
			
		case LONG:
			switch (dt.getCode()){
			case DOUBLE: return new CoreseDouble(doubleValue() + dt.doubleValue());
			case FLOAT:  return new CoreseFloat(floatValue() + dt.floatValue());
			case DECIMAL:return new CoreseDecimal(doubleValue() + dt.doubleValue());
			case LONG:   return new CoreseLong(longValue() + dt.longValue());
			case INTEGER:return new CoreseLong(longValue() + dt.intValue());
			}
			
		case INTEGER:
			switch (dt.getCode()){
			case DOUBLE: return new CoreseDouble(doubleValue() + dt.doubleValue());
			case FLOAT:  return new CoreseFloat(floatValue() + dt.floatValue());
			case DECIMAL:return new CoreseDecimal(doubleValue() + dt.doubleValue());
			case INTEGER:
                            return sum(this, dt); 
                            //return DatatypeMap.newInstance(intValue() + dt.intValue());
			case LONG:   return new CoreseLong(intValue() + dt.longValue());
			}
		}
		return null;
	}
        
        
        IDatatype sum(IDatatype dt1, IDatatype dt2){
            long res = dt1.longValue() + dt2.longValue();
            if (res <= Integer.MAX_VALUE){
                return DatatypeMap.newInstance((int)res);
            }
            return new CoreseLong(res);
        }
        
        IDatatype prod(IDatatype dt1, IDatatype dt2){
            long res = dt1.longValue() * dt2.longValue();
            if (res <= Integer.MAX_VALUE){
                return DatatypeMap.newInstance((int)res);
            }
            return new CoreseLong(res);
        }
	
	public IDatatype minus(IDatatype dt) {
		switch (getCode()){
		case DOUBLE:
			return new CoreseDouble(doubleValue() - dt.doubleValue());
			
		case FLOAT:
			switch (dt.getCode()){
			case DOUBLE: return new CoreseDouble(doubleValue() - dt.doubleValue());
			default:     return new CoreseFloat(floatValue() - dt.floatValue());
			}
			
		case DECIMAL:
			switch (dt.getCode()){
			case DOUBLE: return new CoreseDouble(doubleValue() - dt.doubleValue());
			case FLOAT:  return new CoreseFloat(floatValue() - dt.floatValue());
			default:     return new CoreseDecimal(doubleValue() - dt.doubleValue());
			}
			
		case LONG:
			switch (dt.getCode()){
			case DOUBLE: return new CoreseDouble(doubleValue() - dt.doubleValue());
			case FLOAT:  return new CoreseFloat(floatValue() - dt.floatValue());
			case DECIMAL:return new CoreseDecimal(doubleValue() - dt.doubleValue());
			case INTEGER:return new CoreseLong(longValue() - dt.intValue());
			case LONG:   return new CoreseLong(longValue() - dt.longValue());
			}
			
		case INTEGER:
			switch (dt.getCode()){
			case DOUBLE: return new CoreseDouble(doubleValue() - dt.doubleValue());
			case FLOAT:  return new CoreseFloat(floatValue() - dt.floatValue());
			case DECIMAL:return new CoreseDecimal(doubleValue() - dt.doubleValue());
			case INTEGER:return DatatypeMap.newInstance(intValue() - dt.intValue());
			case LONG:   return new CoreseLong(intValue() - dt.longValue());
			}
		}
		return null;
	}
	
	public IDatatype mult(IDatatype dt) {
		switch (getCode()){
		case DOUBLE:
			return new CoreseDouble(doubleValue() * dt.doubleValue());
			
		case FLOAT:
			switch (dt.getCode()){
			case DOUBLE: return new CoreseDouble(doubleValue() * dt.doubleValue());
			default:     return new CoreseFloat(floatValue() * dt.floatValue());
			}
			
		case DECIMAL:
			switch (dt.getCode()){
			case DOUBLE: return new CoreseDouble(doubleValue() * dt.doubleValue());
			case FLOAT:  return new CoreseFloat(floatValue() * dt.floatValue());
			default:     return new CoreseDecimal(doubleValue() * dt.doubleValue());
			}
			
		case LONG:
			switch (dt.getCode()){
			case DOUBLE: return new CoreseDouble(doubleValue() * dt.doubleValue());
			case FLOAT:  return new CoreseFloat(floatValue() * dt.floatValue());
			case DECIMAL:return new CoreseDecimal(doubleValue() * dt.doubleValue());
			case INTEGER:return new CoreseLong(longValue() * dt.intValue());
			case LONG:   return new CoreseLong(longValue() * dt.longValue());
			}
			
		case INTEGER:
			switch (dt.getCode()){
			case DOUBLE: return new CoreseDouble(doubleValue() * dt.doubleValue());
			case FLOAT:  return new CoreseFloat(floatValue() * dt.floatValue());
			case DECIMAL:return new CoreseDecimal(doubleValue() * dt.doubleValue());
			case INTEGER: return prod(this, dt);
                            //return DatatypeMap.newInstance(intValue() * dt.intValue());
			case LONG:   return new CoreseLong(intValue() * dt.longValue());
			}
		}
		return null;
	}
	
	public IDatatype div(IDatatype dt) {
		
		if (dt.doubleValue() == 0.0) {
			return null;
		} 	
			
		try {
		
		switch (getCode()){
		case DOUBLE:
			return new CoreseDouble(doubleValue() / dt.doubleValue());
			
		case FLOAT:
			switch (dt.getCode()){
			case DOUBLE: return new CoreseDouble(doubleValue() / dt.doubleValue());
			default:     return new CoreseFloat(floatValue() / dt.floatValue());
			}
			
		case DECIMAL:
			switch (dt.getCode()){
			case DOUBLE: return new CoreseDouble(doubleValue() / dt.doubleValue());
			case FLOAT:  return new CoreseFloat(floatValue() / dt.floatValue());
			default:     return new CoreseDecimal(doubleValue() / dt.doubleValue());
			}
			
		case LONG:
			switch (dt.getCode()){
			case DOUBLE: return new CoreseDouble(doubleValue() / dt.doubleValue());
			case FLOAT:  return new CoreseFloat(floatValue() / dt.floatValue());
			case DECIMAL:return new CoreseDecimal(doubleValue() / dt.doubleValue());
			case INTEGER:return new CoreseDecimal(longValue() / dt.doubleValue());
			case LONG:   return new CoreseDecimal(longValue() / dt.doubleValue());
			}
			
		case INTEGER:
			switch (dt.getCode()){
			case DOUBLE: return new CoreseDouble(doubleValue() / dt.doubleValue());
			case FLOAT:  return new CoreseFloat(floatValue() / dt.floatValue());
			case DECIMAL:return new CoreseDecimal(doubleValue() / dt.doubleValue());
			case INTEGER:return new CoreseDecimal(intValue() / dt.doubleValue());
			case LONG:   return new CoreseDecimal(intValue() / dt.doubleValue());
			}
		}
		}
		catch (java.lang.ArithmeticException a){
			
		}
		return null;
	}
	
	
}