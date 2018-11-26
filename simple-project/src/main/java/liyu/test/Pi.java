package liyu.test;

public class Pi {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double xf = 0.0d;
		double yf = 0.0d;
		int total = 0;
		for (int i = 0; i < 1000000; i++) {
			xf = Math.random();
			yf = Math.random();
			if (Math.sqrt(xf * xf + yf * yf) < 1)
				total++;
		}
		System.out.println("脚本之家测试结果：");
		System.out.println(4 * (total / 1000000.0));
	}
}
