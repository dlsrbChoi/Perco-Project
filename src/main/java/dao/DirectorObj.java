package dao;

public class DirectorObj {
private String id, name, gymname, cnt, position, number, gongzi;
	
	public DirectorObj(String id, String name, String gymname, String cnt, String position, String number, String gongzi) {
		this.id = id;
		this.name = name;
		this.gymname = gymname;
		this.cnt = cnt;
		this.position = position;
		this.number = number;
		this.gongzi = gongzi;
	}
	
	public String getId() { return this.id; }
	public String getName() { return this.name; }
	public String getGymName() { return this.gymname; }
	public String getCnt() { return this.cnt; }
	public String getPosition() { return this.position; }
	public String getNumber() { return this.number; }
	public String getGongzi() { return this.gongzi; }
}
