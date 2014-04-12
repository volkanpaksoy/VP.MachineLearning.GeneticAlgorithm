import java.io.File;
import org.w3c.dom.Document;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XmlConfigReader{

    public static SystemParameters ReadConfig (String fileName){

    	SystemParameters params = new SystemParameters();
    	
    	try {
    		
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse (new File(fileName));

            doc.getDocumentElement().normalize ();
            
            // Log path
            NodeList logFilePath = doc.getElementsByTagName("logFilePath");
            if (logFilePath.getLength() == 0)
            	throw new ApplicationException("Config error: logFilePath value not set");
            
            params.setLogPath(logFilePath.item(0).getFirstChild().getNodeValue());
            Logger.logMessage("LogPath: " + params.getLogPath());
            
            // Generation count
            NodeList generationCount = doc.getElementsByTagName("generationCount");
            if (generationCount.getLength() == 0)
            	throw new ApplicationException("Config error: generationCount value not set");
            
            params.setGenerationCount(Integer.parseInt(generationCount.item(0).getFirstChild().getNodeValue()));
            Logger.logMessage("GenerationCount: " + params.getGenerationCount());
            
            // Generation size
            NodeList generationSize = doc.getElementsByTagName("generationSize");
            if (generationSize.getLength() == 0)
            	throw new ApplicationException("Config error: generationSize value not set");
            
            params.setGenerationSize(Integer.parseInt(generationSize.item(0).getFirstChild().getNodeValue()));
            Logger.logMessage("GenerationSize: " + params.getGenerationSize());
            
            // Crossover Ratio
            NodeList crossOverRatio = doc.getElementsByTagName("crossOverRatio");
            if (crossOverRatio.getLength() == 0)
            	throw new ApplicationException("Config error: crossOverRatio value not set");
            
            params.setCrossOverRatio(Double.parseDouble(crossOverRatio.item(0).getFirstChild().getNodeValue()));
            Logger.logMessage("CrossOverRatio: " + params.getCrossOverRatio());
            
            // Mutation Ratio
            NodeList mutationRatio = doc.getElementsByTagName("mutationRatio");
            if (mutationRatio.getLength() == 0)
            	throw new ApplicationException("Config error: mutationRatio value not set");
            
            params.setMutationRatio(Double.parseDouble(mutationRatio.item(0).getFirstChild().getNodeValue()));
            Logger.logMessage("MutationRatio: " + params.getMutationRatio());
            
            // Crossover Location
            NodeList crossOverLocation = doc.getElementsByTagName("crossOverLocation");
            if (crossOverLocation.getLength() == 0)
            	throw new ApplicationException("Config error: crossOverLocation value not set");
            
            params.setCrossoverLocation(Integer.parseInt(crossOverLocation.item(0).getFirstChild().getNodeValue()));
            Logger.logMessage("CrossOverLocation: " + params.getCrossoverLocation());
            
            // Crossover Location
            NodeList genomeLength = doc.getElementsByTagName("genomeLength");
            if (genomeLength.getLength() == 0)
            	throw new ApplicationException("Config error: genomeLength value not set");
            
            params.setGenomeLength(Integer.parseInt(genomeLength.item(0).getFirstChild().getNodeValue()));
            Logger.logMessage("GenomeLength: " + params.getGenomeLength());
            
            
            return params;
            
        }catch (SAXParseException err) {
        	System.out.println ("** Parsing error" + ", line " + err.getLineNumber () + ", uri " + err.getSystemId ());
        	System.out.println(" " + err.getMessage ());
        	return null;
        }catch (SAXException e) {
        	Exception x = e.getException ();
        	((x == null) ? e : x).printStackTrace ();
        	return null;
        }catch (Throwable t) {
        	t.printStackTrace ();
        	return null;
        }

        
    }
}

	
