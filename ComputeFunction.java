/**
 * Common computation function interface for use with ScoccerLeageTableProcessor
 * to encapsulate the computation part of the data processing during data processing. 
 * 
 * It provides a mechanism to create arbitrary number of functions during data parsing.
 * 
 * @author Henry
 *
 */
public interface ComputeFunction {
	
	/**
	 * Compute with the given data string.
	 *  
	 * @param data String of data.
	 */
	public void compute(String data);
	
	/**
	 * Retrieve result after the computation.
	 * 
	 * @return
	 */
	public String getResult();

}
