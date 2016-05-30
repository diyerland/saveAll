package com.ID3;

public class DTree {
	public static double sigma(int x,int total)
	{
		if(x==0)
			return 0.0;
		double x_Divide=getDivide(x,total);
		return -x_Divide*logBase2(x_Divide);
	}
	public static double logBase2(double x)
	{
		return Math.log(x)/Math.log(2);
	}
	public static double getDivide(int x,int total)
	{
		return x*Double.parseDouble("1.0")/total;
	}

}
