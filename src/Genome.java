import java.util.BitSet;

public class Genome extends BitSet{

	private int m_nLength = 0;
	
	private double m_dFitness = 0.0;
	private double m_dSelectionRangeMin = 0.0;
	private double m_dSelectionRangeMax = 0.0;
	
	public void setFitness(double mDFitness) {
		m_dFitness = mDFitness;
	}

	public double getFitness() {
		return m_dFitness;
	}
	
	public double getSelectionRangeMin() {
		return m_dSelectionRangeMin;
	}

	public void setSelectionRangeMin(double mDSelectionRangeMin) {
		m_dSelectionRangeMin = mDSelectionRangeMin;
	}

	public double getSelectionRangeMax() {
		return m_dSelectionRangeMax;
	}

	public void setSelectionRangeMax(double mDSelectionRangeMax) {
		m_dSelectionRangeMax = mDSelectionRangeMax;
	}


	

	public Genome(int length) {
		
		super(length);
		
		m_nLength = length;		
	}
	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < m_nLength; i++) {
			if (this.get(i) == true)
				sb.append("1");
			else
				sb.append("0");
		}
		
		return sb.toString();
	}
	
	
	public boolean isEligibleForSelection(double dSelectedValue) {
		
		if (dSelectedValue > this.getSelectionRangeMin() &&
			dSelectedValue <= this.getSelectionRangeMax()) {
			return true;
		}
		
		return false;
	}
	
}
