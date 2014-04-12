import java.util.BitSet;

public class Fitness {
	
	public static double fitnessFunction(BitSet genome) {
		
		int nDecimalValue = Integer.parseInt(genome.toString(), 2);
		int nNumberOfSetBits = genome.cardinality();
		
	//	return 31 * nDecimalValue - (nDecimalValue * nDecimalValue); // Class sample 
		
		return 13 * nDecimalValue * nNumberOfSetBits - (nDecimalValue * nNumberOfSetBits); 
	}
	
	
	
	
}
