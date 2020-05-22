package liyu.test.yml.bata;

import java.util.ArrayList;

public class Yml {
	private String name;
	public String getName() {
		return this.name;
	}
	
	private Object value;
	public Object getValue() {
		return this.value;
	}
	
	private int level;
	public int getLevel() {
		return this.level;
	}
	
	private Yml parent;
	private ArrayList<Yml> children;
	private void setLevel(int level) {
		this.level = level;
	}
	private void setParent(Yml yml) {
		this.parent = yml;
	}
	private Yml(String name,Object value) {
		this.name = name;
		this.value = value;
	}
	public Yml() {
		this.level = -1;
	}
	public Yml(String name) {
		this.name = name;
	}
	public Yml setString(String name,Object value) {
		Yml yml = new Yml(name,value);
		this.add(yml);
		return this;
	}
	
	public Yml add(Yml yml) {
		if(this.children == null) {
			this.children = new ArrayList<Yml>();
		}
		
		yml.setLevel(this.level+1);
		yml.setParent(this);
		this.children.add(yml);
		return this;
	}
	
	public Yml addAndReturn(Yml yml) {
		this.add(yml);
		return yml;
	}
	
	public Yml end() {
		return this.parent;
	}
	
	public ArrayList<Yml> getChildren(){
		return this.children;
	}
	
	public static void main(String[] args) {
		Yml root = new Yml();
		root.setString("a", "b")
			.setString("c", "d")
			.add(new Yml("next")
					.setString("aa", "a")
					.addAndReturn(new Yml("e"))
						.setString("ee", 1));
		
		YmlUtil.print(root);
	}
}
class YmlUtil{
	public static void print(Yml root) {
		if(root.getChildren()==null) {
			for(int i=0;i<root.getLevel();i++) {
				System.out.print("  ");
			}
			System.out.println(root.getName()+": "+root.getValue());
		}else {
			for(int i=0;i<root.getLevel();i++) {
				System.out.print("  ");
			}
			if(root.getLevel()>-1)
				System.out.println(root.getName()+": ");
			
			for(Yml el:root.getChildren()) {
				print(el);
			}
		}
	}
}