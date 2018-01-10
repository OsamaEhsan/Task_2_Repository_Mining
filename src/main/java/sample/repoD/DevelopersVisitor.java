package sample.repoD;

import java.util.ArrayList;
import java.util.List;

import org.repodriller.domain.Commit;
import org.repodriller.domain.Modification;
import org.repodriller.persistence.PersistenceMechanism;
import org.repodriller.scm.CommitVisitor;
import org.repodriller.scm.SCMRepository;

public class DevelopersVisitor implements CommitVisitor {

	public void process(SCMRepository repo, Commit commit, PersistenceMechanism writer) {
 
		List<Modification> listModifications =  commit.getModifications();

		List<String> insertions = new ArrayList<String>();
		List<String> deletions = new ArrayList<String>();
		
		for (Modification modification : listModifications) {
			String difference = modification.getDiff();
			String[] lineTokens = difference.split("\n");
			//only picking up lines from commit which include insertions and deletions of type method
			for (String token : lineTokens) {
				if(token.startsWith("+") && !token.startsWith("+++") && !token.contains("//") && !token.contains("=") && !token.contains(";") && (token.contains("public") || token.contains("private"))){
					insertions.add(token);
				}
				if(token.startsWith("-") && !token.startsWith("---") && !token.contains("//") && !token.contains("=") && !token.contains(";") && (token.contains("public") || token.contains("private"))){
					deletions.add(token);
				}
			}	
		}
		CommitDifference commitDifference = new CommitDifference(insertions,deletions,commit.getHash());
		
		this.CheckForFunctions(commitDifference, writer);
	}
	
	public void CheckForFunctions(CommitDifference commitDifference, PersistenceMechanism writer) {		
		//looping over all the deletions and insertions to make sure whether function change exists or not	
		for (int i = 0; i < commitDifference.deletions.size(); i++) {
				String[] iTokens = commitDifference.getDeletions().get(i).split("\\(");
				
				for (int j = 0; j < commitDifference.insertions.size(); j++) {	
					String[] jTokens = commitDifference.getInsertions().get(j).split("\\(");
					
					if(iTokens[0].length() > 1 && jTokens[0].length() > 1)
					{
						iTokens[0] = iTokens[0].substring(1, iTokens[0].length());
						jTokens[0] = jTokens[0].substring(1, jTokens[0].length());
						
						if(iTokens[0].equals(jTokens[0])) {
							int iCount = iTokens[1].length() - iTokens[1].replace(",", "").length();
							int jCount = jTokens[1].length() - jTokens[1].replace(",", "").length();
							
							if(iCount != jCount) {
								writer.write(
										commitDifference.getHash(),
										commitDifference.getDeletions().get(i).replace(",", ":"),
										commitDifference.getInsertions().get(j).replace(",", ":")
									);
							}
						}
					}
				}
			}
	}
	
	public String name() {
		return "developers";
	}

}
