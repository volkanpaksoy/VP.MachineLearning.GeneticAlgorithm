import java.util.ArrayList;
import java.util.Random;
import java.text.DecimalFormat;


public class GeneticAlgorithm {
	
	private SystemParameters parameters = null;
	
	private ArrayList<Genome> parentGenomePool = null;
	
	
	private double m_dAverageFitness = 0.0;
	
	public GeneticAlgorithm(SystemParameters params) {
		this.parameters = params;
		
		parentGenomePool = new ArrayList<Genome>();
	}
	
	private DecimalFormat df = new DecimalFormat("#.####");

	
	public void run() {
		
		createFirstGeneration();
		
		// Select, crossover and mutate to create new generations
		// Run for genCount - 1 times because 1st generation is already generated above 
		for (int i = 0; i < parameters.getGenerationCount() - 1; i++) {

			// Select next generation  
			determineSelectionRanges();
			select();
			calculateGenerationFitnessValues();
			Logger.logMessage("After selection:");
			logGenerationInfo();

			crossOver();
			calculateGenerationFitnessValues();
			Logger.logMessage("After crossover:");
			logGenerationInfo();
			
			mutate();
			calculateGenerationFitnessValues();
			Logger.logMessage("After mutation:");
			logGenerationInfo();
		}
		
	}
	
	private void createFirstGeneration() {
		Random rand = new Random(System.currentTimeMillis());
		
		// Create first generation
		Logger.logMessage("Creating first generation");
		for (int i = 0; i < parameters.getGenerationSize(); i++) {
			
			Genome g = new Genome(parameters.getGenomeLength());
			
			for (int j = 0; j < parameters.getGenomeLength(); j++) {
				double d = rand.nextDouble();
				if (d >= 0.5) {
					g.set(j);
				}
			}
			
			double dFitness = Fitness.fitnessFunction(g);
			g.setFitness(dFitness);
			m_dAverageFitness += dFitness;
			parentGenomePool.add(g);
			
			Logger.logMessage("Genome created:" + g.toString() + " Fitness: " + g.getFitness());
		}
		
		m_dAverageFitness = m_dAverageFitness / parentGenomePool.size(); 
		Logger.logMessage("Avearage fitness:" + m_dAverageFitness);
	}
	
	private void calculateGenerationFitnessValues() {
		
		for (int i = 0; i < parentGenomePool.size(); i++) {
			Genome genome = parentGenomePool.get(i);
			double dFitness = Fitness.fitnessFunction(genome);
			genome.setFitness(dFitness);
		}
	}
	
	// Must be called before selection process so that each genome will be set their 
	// selection probability values.
	private void determineSelectionRanges() {
		
		double dTemp = 0.0;
		
		for (int i = 0; i < parentGenomePool.size(); i++) {
			
			Genome genome = parentGenomePool.get(i);
			genome.setSelectionRangeMin(dTemp);
			
			dTemp += genome.getFitness() / (m_dAverageFitness * parentGenomePool.size());
			genome.setSelectionRangeMax(dTemp);
			
			Logger.logMessage("Genome  " + genome.toString() + " will be selected in range: {" + df.format(genome.getSelectionRangeMin()) + "," + df.format(genome.getSelectionRangeMax()) + "}");
		}
	}
	
	
	private void select() {
		
		Logger.logMessage("-------------------------------------");
		Logger.logMessage("Selection started");
		
		Random rand = new Random(System.currentTimeMillis());
		ArrayList<Genome> nextGeneration = new ArrayList<Genome>();
		
		while (nextGeneration.size() < parameters.getGenerationSize()) {
			double dRandValue = rand.nextDouble();
			Logger.logMessage("Random value: " + df.format(dRandValue));
			
			for (Genome genome : parentGenomePool) {
				
				if (genome.isEligibleForSelection(dRandValue)) {
					nextGeneration.add(genome);
					Logger.logMessage("Selected genome: " + genome.toString());
					break;
				}
			}
		}
		
		parentGenomePool = nextGeneration;
		
		Logger.logMessage("Selection completed");
		Logger.logMessage("-------------------------------------");
	}
	
	
	private void crossOver() {
		
		Logger.logMessage("-------------------------------------");
		Logger.logMessage("Crossover started");
		
		double dCrossoverRatio = parameters.getCrossOverRatio();
		int nCrossoverLocation = parameters.getCrossoverLocation();
		
		Random rand = new Random(System.currentTimeMillis());
		ArrayList<Genome> childGenomePool = new ArrayList<Genome>();
		
		while (childGenomePool.size() < parameters.getGenerationSize()) {
			
			// Select a random number. If its greater than crossover ratio, continue and pick new one.
			// e.g: If crossover ratio is provided 0.1 and we generate 0.5 we continue. Because we only have %10 probability
			// to crossover. But since the system's state doesn't change, it doesn't have any effect on anything.
			// So this process can skipped. 
			double dCrossoverProbability = rand.nextDouble();
			if (dCrossoverProbability > dCrossoverRatio)
				continue;
			
			// Pick random parents.
			Genome parent1 = parentGenomePool.get(rand.nextInt(parentGenomePool.size()));	
			Genome parent2 = parentGenomePool.get(rand.nextInt(parentGenomePool.size()));
			Logger.logMessage("Parent1 :" + parent1);
			Logger.logMessage("Parent2 :" + parent2);
			
			// Create children. First child gets bits until crossover location from parent1 and the rest from parent 2
			// Vice versa for child 2.
			Genome child1 = new Genome(parameters.getGenomeLength());
			for (int i = 0; i < nCrossoverLocation; i++) {
				child1.set(i, parent1.get(i));
			}
			for (int i = nCrossoverLocation; i < parameters.getGenomeLength(); i++) {
				child1.set(i, parent2.get(i));
			}
			
			Genome child2 = new Genome(parameters.getGenomeLength());
			for (int i = 0; i < nCrossoverLocation; i++) {
				child2.set(i, parent2.get(i));
			}
			for (int i = nCrossoverLocation; i < parameters.getGenomeLength(); i++) {
				child2.set(i, parent1.get(i));
			}

			Logger.logMessage("Child1 :" + child1);
			Logger.logMessage("Child2 :" + child2);

			childGenomePool.add(child1);
			childGenomePool.add(child2);
		}
		
		parentGenomePool = childGenomePool;
			
		Logger.logMessage("Crossover completed");
		Logger.logMessage("-------------------------------------");
	}
	
	
	private void mutate() {
		
		Logger.logMessage("-------------------------------------");
		Logger.logMessage("Mutation started");
		
		double dMutationRatio = parameters.getMutationRatio();
		int nGenomeLength = parameters.getGenomeLength();
		int nPoolGeneTotalSize = parameters.getGenerationSize() * nGenomeLength; 
		int nNumberOfAverageMutations = (int) Math.floor(dMutationRatio * nPoolGeneTotalSize); 
		Logger.logMessage("Number Of Average Mutations:" + nNumberOfAverageMutations);
		
		Random rand = new Random(System.currentTimeMillis());
		for (int i = 0; i < nNumberOfAverageMutations; i++) {
			// Choose gene
			int nBitToMutate = rand.nextInt(nPoolGeneTotalSize);
			Logger.logMessage("Bit index to mutate:" + nBitToMutate);
			
			// Determine gene's owner genome and locate the gene in that genome.
			Genome ownerGenome = parentGenomePool.get(nBitToMutate / nGenomeLength); 
			Logger.logMessage("Owner Genome: " + ownerGenome.toString());
			
			Logger.logMessage("Mutating bit at:" + (nBitToMutate % nGenomeLength));
			ownerGenome.flip(nBitToMutate % nGenomeLength);
			Logger.logMessage("Genonme after mutation: " + ownerGenome.toString());
		}
		
		Logger.logMessage("Mutation completed");
		Logger.logMessage("-------------------------------------");
	}
	
	
	private void logGenerationInfo() {
		
		Logger.logMessage("Current generation:");
		for (Genome genome : parentGenomePool) {
			Logger.logMessage(genome.toString() + " Fitness: " + df.format(genome.getFitness()));
		}
	}
	
}
