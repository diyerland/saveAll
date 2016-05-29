/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package auxiliary;

import java.util.ArrayList;

/**
 *
 * @author Michael Kong
 * bayes分类的算法大致如下：

（1）对于属性值是离散的，并且目标label值也是离散的情况下。分别计算label不同取值的概率，以及样本在label情况下的概率值，然后将这些概率值相乘最后得到一个概率的乘积，选择概率乘积最大的那个值对应的label值就为预测的结果。

例如以下：是预测苹果在给定属性的情况是甜还是不甜的情况：

color={0,1,2,3} weight={2,3,4};是属性序列，为离散型。sweet={yes,no}是目标值，也为离散型；
训练样本一共5个。｛3:color,2:weight,yes:sweet｝ ｛2:color,3:weight,yes:sweet｝
｛3:color,1:weight,no:sweet｝｛0:color,3:weight,no:sweet｝｛2:color,2:weight,no:sweet｝

这时我们要预测在color=3,weight=3的情况下的目标值，计算过程如下：

P{y=yes}=2/5=0.4; P{color=3|yes}=1/2=0.5;P{weight=3|yes}=1/2=0.5;  故F{color=3,weight=3}取yesd的概率为 0.4*0.5*0.5=0.1;

P{y=no}=3/5=0.6; P{color=3|no}=1/3 P{weight=3|no}=1/3;  故P{color=3,weight=3}取no为 0.6*1/3*1/3=1/15;

0.1>1/15 所以认为 F{color=3,weight=3}=yes;

(2)对于属性值是连续的情况，思想和离散是相同的，只是这时候我们计算属性的概率用的是高斯密度：

这里的Xk就是样本的取值，u是样本所在列的均值，kesi是标准差；
 */
public class NaiveBayes extends Classifier {

	boolean isClassfication[];
   ArrayList <Double>lblClass=new ArrayList<Double>();  //存储目标值的种类
   ArrayList<Integer>lblCount=new ArrayList<Integer>();//存储目标值的个数
   ArrayList<Float>lblProba=new ArrayList<Float>();//存储对应的label的概率
   CountProbility countlblPro;
   /*@ClassListBasedLabel是将训练数组按照 label的顺序来分类存储*/
   ArrayList<ArrayList<ArrayList<Double>>> ClassListBasedLabel=new  ArrayList<ArrayList<ArrayList<Double>>> ();
    public NaiveBayes() {
    }
    @Override
    /**
     * @train主要完成求一些概率
     * 1.labels中的不同取值的概率f(Yi);  对应28,29行两段代码
     * 2.将训练数组按目标值分类存储   第37行代码

     * */
    public void train(boolean[] isCategory, double[][] features, double[] labels){
    	isClassfication=isCategory;
    	countlblPro=new CountProbility(isCategory,features,labels);
    	countlblPro.getlblClass(lblClass, lblCount, lblProba);
    	ArrayList<ArrayList<Double>> trainingList=countlblPro.UnionFeaLbl(features, labels); //union the features[][] and labels[]
    	ClassListBasedLabel=countlblPro.getClassListBasedLabel(lblClass, trainingList);
    }
    @Override
    /**3.在Y的条件下，计算Xi的概率 f(Xi/Y)；
     * 4.返回使得Yi*Xi*...概率最大的那个label的取值
     * */
    public double predict(double[] features) {

    	int max_index; //用于记录使概率取得最大的那个索引
    	int index=0; //这个索引是 标识不同的labels 所对应的概率
    	ArrayList<Double> pro_=new ArrayList<Double>(); //这个概率数组是存储features[] 在不同labels下对应的概率
		for(ArrayList<ArrayList<Double>> elements: ClassListBasedLabel)  //依次取不同的label值对应的元祖集合
		{
		 	ArrayList<Double> pro=new ArrayList<Double>();//存同一个label对应的所有概率，之后其中的元素自乘
			double probility=1.0; //计算概率的乘积

			for(int i=0;i<features.length;i++)
			{
				if(isClassfication[i]) //用于对属性的离散还是连续做判断
				{

					int count=0;
					for(ArrayList<Double> element:elements) //依次取labels中的所有元祖
					{
						if(element.get(i).equals(features[i])) //如果这个元祖的第index数据和b相等，那么就count就加1
							count++;
					}
					if(count==0)
					{
						pro.add(1/(double)(elements.size()+1));
					}
					else
						pro.add(count/(double)elements.size()); //统计完所有之后  计算概率值 并加入
				}
				else
				{

		  			double Sdev;
		    		double Mean;
	    			double probi=1.0;
    				Mean=countlblPro.getMean(elements, i);
    				Sdev=countlblPro.getSdev(elements, i);
    				if(Sdev!=0)
    				{
    					probi*=((1/(Math.sqrt(2*Math.PI)*Sdev))*(Math.exp(-(features[i]-Mean)*(features[i]-Mean)/(2*Sdev*Sdev))));
    	    			pro.add(probi);
    				}
    				else
    					pro.add(1.5);

	        	}
			}
			for(double pi:pro)
				probility*=pi; //将所有概率相乘
			probility*=lblProba.get(index);//最后再乘以一个 Yi
			pro_.add(probility);// 放入pro_ 至此 一个循环结束，
			index++;
		}
		double max_pro=pro_.get(0);
		max_index=0;


		for(int i=1;i<pro_.size();i++)
		{
			if(pro_.get(i)>=max_pro)
			{
				max_pro=pro_.get(i);
				max_index=i;
			}
		}
		return  lblClass.get(max_index);
    }




    public class CountProbility
    {
    	boolean []isCatory;
    	double[][]features;
    	private double[]labels;
    	public CountProbility(boolean[] isCategory, double[][] features, double[] labels)
    	{
    		this.isCatory=isCategory;
    		this.features=features;
    		this.labels=labels;
    	}
    	//获取label中取值情况
    	public void getlblClass(  ArrayList <Double>lblClass,ArrayList<Integer>lblCount,ArrayList<Float>lblProba)
    	{
    		int j=0;
            for(double i:labels)
            {
            	//如果当前的label不存在于lblClass则加入
            	if(!lblClass.contains(i))
            	{
            		lblClass.add(j,i);
            		lblCount.add(j++,1);
            	}
            	else //如果label中已经存在，就将其计数加1
            	{
            		int index=lblClass.indexOf(i);
            		int count=lblCount.get(index);
            		lblCount.set(index,++count);
            	}

            }
            for(int i=0;i<lblClass.size();i++)
            {
//            	System.out.println("值为"+lblClass.get(i)+"的个数有"+lblCount.get(i)+"概率是"+lblCount.get(i)/(float)labels.length);
            	lblProba.add(i,lblCount.get(i)/(float)labels.length);
            }

    	}
    	//将label[]和features[][]合并
    	public ArrayList<ArrayList<Double>>  UnionFeaLbl(double[][] features, double[] labels)
    	{
    		ArrayList<ArrayList<Double>>traingList=new  ArrayList<ArrayList<Double>>();
    		for(int i=0;i<features.length;i++)
    		{
    			ArrayList<Double>elements=new ArrayList<Double>();
    			for(int j=0;j<features[i].length;j++)
    			{
    				elements.add(j,features[i][j]);
    			}
    			elements.add(features[i].length,labels[i]);
    			traingList.add(i,elements);

    		}
    		return traingList;
    	}
    	/*将测试数组按label的值分类存储*/
    	public ArrayList<ArrayList<ArrayList<Double>>> getClassListBasedLabel (ArrayList <Double>lblClass,ArrayList<ArrayList<Double>>trainingList)
    	{
    		ArrayList<ArrayList<ArrayList<Double>>> ClassListBasedLabel=new ArrayList<ArrayList<ArrayList<Double>>> () ;
        		for(double num:lblClass)
        		{
    				ArrayList<ArrayList<Double>> elements=new ArrayList<ArrayList<Double>>();
	    			for(ArrayList<Double>element:trainingList)
	    			{
	    				if(element.get(element.size()-1).equals(num))
	    					elements.add(element);
	    			}
    			ClassListBasedLabel.add(elements);
        		}
    			return ClassListBasedLabel;
    	}
    	public double getMean(ArrayList<ArrayList<Double>> elements,int index)
    	{
    		double sum=0.0;
    		double Mean;

    		for(ArrayList<Double> element:elements)
    		{
    			sum+=element.get(index);

    		}
    		Mean=sum/(double)elements.size();
    		return  Mean;
    	}
    	public double getSdev(ArrayList<ArrayList<Double>> elements,int index)
    	{
    		double dev=0.0;
    		double Mean;
    		Mean=getMean(elements,index);
    		for(ArrayList<Double> element:elements)
    		{
    			dev+=Math.pow((element.get(index)-Mean),2);
    		}
    		dev=Math.sqrt(dev/elements.size());
    		return  dev;
    	}


    }
}

