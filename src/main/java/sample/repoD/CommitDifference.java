package sample.repoD;

import java.util.List;

public class CommitDifference {

	List<String> insertions;
	List<String> deletions;
	String hash;
	
	public CommitDifference(List<String> insertions, List<String> deletions, String hash) {
		 
		this.insertions = insertions;
		this.deletions = deletions;
		this.hash = hash;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public List<String> getInsertions() {
		return insertions;
	}
	public void setInsertions(List<String> insertions) {
		this.insertions = insertions;
	}
	public List<String> getDeletions() {
		return deletions;
	}
	public void setDeletions(List<String> deletions) {
		this.deletions = deletions;
	}
	
	
}
