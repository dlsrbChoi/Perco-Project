package dao;

public class UserObj {
private String id, name, birth, phon;
	
	public UserObj(String id, String name, String birth, String phon) {
		this.id = id;
		this.name = name;
		this.birth = birth;
		this.phon = phon;
	}
	
	public String getId() { return this.id; }
	public String getName() { return this.name; }
	public String getBirth() { return this.birth; }
	public String getPhon() { return this.phon; }
}
