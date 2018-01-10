package sample.repoD;

import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.repodriller.RepoDriller;
import org.repodriller.RepositoryMining;
import org.repodriller.Study;
import org.repodriller.filter.range.Commits;
import org.repodriller.persistence.csv.CSVFile;
import org.repodriller.scm.GitRepository;

/**
 * Hello world!
 *
 */
public class App implements Study
{
    public static void main( String[] args )
    {
    	BasicConfigurator.configure();
    	new RepoDriller().start(new App());
    }
    
    public void execute() {
    	DevelopersVisitor dv = new DevelopersVisitor();
    	new RepositoryMining()
		.in(GitRepository.singleProject("C:\\Users\\oehsan\\Documents\\git repositories\\jadx"))
		.through(Commits.all())
		.process(dv, new CSVFile("C:\\Users\\oehsan\\Documents\\devs.csv"))
		.mine();
	}
}
