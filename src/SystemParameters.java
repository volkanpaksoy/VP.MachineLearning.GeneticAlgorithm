
public class SystemParameters {

	private String m_strLogPath = "";
	
	private int m_nGenerationCount = 0;
	
	private int m_nGenerationSize = 0;
	
	private double m_dCrossOverRatio = 0.0;
	
	private double m_dMutationRatio = 0.0;
	
	private int m_nCrossoverLocation = 0;
	
	private int m_nGenomeLength = 0;

	
	public void setGenerationCount(int m_nGenerationCount) {
		this.m_nGenerationCount = m_nGenerationCount;
	}

	public int getGenerationCount() {
		return m_nGenerationCount;
	}

	public void setGenerationSize(int m_nGenerationSize) {
		this.m_nGenerationSize = m_nGenerationSize;
	}

	public int getGenerationSize() {
		return m_nGenerationSize;
	}

	public void setCrossOverRatio(double m_dCrossOverRatio) {
		this.m_dCrossOverRatio = m_dCrossOverRatio;
	}

	public double getCrossOverRatio() {
		return m_dCrossOverRatio;
	}

	public void setMutationRatio(double m_dMutationRatio) {
		this.m_dMutationRatio = m_dMutationRatio;
	}

	public double getMutationRatio() {
		return m_dMutationRatio;
	}

	public void setCrossoverLocation(int m_nCrossoverLocation) {
		this.m_nCrossoverLocation = m_nCrossoverLocation;
	}

	public int getCrossoverLocation() {
		return m_nCrossoverLocation;
	}

	public void setGenomeLength(int m_nGenomeLength) {
		this.m_nGenomeLength = m_nGenomeLength;
	}

	public int getGenomeLength() {
		return m_nGenomeLength;
	}

	public void setLogPath(String m_strLogPath) {
		this.m_strLogPath = m_strLogPath;
	}

	public String getLogPath() {
		return m_strLogPath;
	}

	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append(" ------ System Parameters ------ "  + "\n");
		sb.append("Log Path ..............:" + this.getLogPath() + "\n");
		sb.append("Generation Count ......:" + this.getGenerationCount() + "\n");
		sb.append("Generation Size .......:" + this.getGenerationSize() + "\n");
		sb.append("Crossover Ratio .......:" + this.getCrossOverRatio() + "\n");
		sb.append("Mutation Ratio ........:" + this.getMutationRatio() + "\n");
		sb.append("Crossover Location ....:" + this.getCrossoverLocation() + "\n");
		sb.append("Genome Length .........:" + this.getGenomeLength() + "\n");
		sb.append(" ------------------------------- "  + "\n");
		return sb.toString();
		
	}
	
}
