package com.ID3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ID3self {
	//创建属性的列表
	private ArrayList<String> attribute=new ArrayList<String>();

	//创建各个属性对应的值的列表
	private ArrayList<ArrayList<String>> attributevalue=new ArrayList<ArrayList<String>>();
	
	//创建所有的样本的集合
	private ArrayList<String[]> alldata=new ArrayList<String[]>();
	
	//创建所有训练样本的数据
	private ArrayList<String[]> traindata=new ArrayList<String[]>();
	
	//创建预测集合的数据
	private ArrayList<String[]> testdata=new ArrayList<String[]>();
	
	//创建根节点
	TreeNode root;
	
	//还可以用于选择的属性列（true表示还可以选择，false表示不可以）
	private boolean[] visiable;
		
	//节点的索引，代表属性的个数
	private int nodeIndex;
	
	//所有的样本的大小
	private int alldatalength;
	
	//主函数入口
	public static void main(String[] args)
	{
		//创建一个实例
		ID3self ID3Tree=new ID3self();
		//读取文件中的内容,初始化各种数组
		ID3Tree.readTxt(new File("car.txt"));
		//设置训练数据和测试数据的长度
		int testData=1000;
		System.out.println("训练样本的大小："+testData);
		System.out.println("测试样本的大小："+(ID3Tree.alldatalength-testData));
		ID3Tree.setTraindata(testData);
		//进行训练，构造出决策树
		ID3Tree.createDTree(ID3Tree.traindata);
		System.out.println("------预测结果------");
		ID3Tree.predicate(ID3Tree.testdata, ID3Tree.root);
	}
	//读入指定文件，初始化attribute、attributevalue、visiable、alldata
	public void readTxt(File file)//读入指定文件，初始化attribute、attributevalue、traindata、testdata
	{
		try
		{
			FileReader fr=new FileReader(file);
			BufferedReader br=new BufferedReader(fr);
			String line;
			if((line=br.readLine())!=null)
			{
				String[] attributes=line.split(" ");
				this.nodeIndex=attributes.length-1;
				this.visiable=new boolean[attributes.length];
				for(int i=0;i<attributes.length;++i)
				{
					if(i==this.nodeIndex)
						this.visiable[i]=true;
					else
						this.visiable[i]=false;
				}
				for(String temp:attributes)
					this.attribute.add(temp);
			}
			if((line=br.readLine())!=null)
			{
				String[] att_val_table=line.split(" ");
				String[] att_value;
				for(int i=0;i<att_val_table.length;++i)
				{
					att_value=att_val_table[i].split(",");
					ArrayList<String> al=new ArrayList<String>();
					for(String temp:att_value)
						al.add(temp);
					this.attributevalue.add(al);
				}
			}
			while((line=br.readLine())!=null)//第三行开始读数据
			{
				String[] singleline=line.split(",");
				this.alldata.add(singleline);
			}
			this.alldatalength=this.alldata.size();
			
		}
		catch(IOException  e)
		{
			e.printStackTrace();
		}
	}
	//取alldata中的前index行数据作为训练集,初始化testdata，traindata
	public void setTraindata(int index)
	{
		int count=0;
		if(index>this.alldata.size())
		{
			System.out.println("超过所有数据的最大长度！");
			return;
		}
		while(count<index)
		{
			this.traindata.add(this.alldata.remove(0));
			count++;
		}
		this.testdata=this.alldata;
	}
	//通过给定的训练集创建决策树
	public void createDTree(ArrayList<String[]> trainarray)
	{
		Object[] maxgain=getMaxGain(trainarray);
		if(this.root==null)
		{
			this.root=new TreeNode();
			root.parent=null;
			root.parentAttribute=null;
			root.attributes=this.attributevalue.get((Integer)maxgain[1]);
			root.nodeName=this.attribute.get((Integer)maxgain[1]);
			//childNodes是列表暂时为空,子节点创建的时候再更新
			insertTree(trainarray,root);
		}
	}
	//在指定的节点处插入树
	public void insertTree(ArrayList<String[]> array,TreeNode parentNode)
	{
		ArrayList<String> attributes=parentNode.attributes;
		for(int i=0;i<attributes.size();++i)
		{
			ArrayList<String[]> pickArray=pickUpAndArray(array,attributes.get(i),this.attribute.indexOf(parentNode.nodeName));
			Object[] info=getMaxGain(pickArray);
			double gain=((Double)info[0]).doubleValue();
			if(gain!=0)
			{
				int index=((Integer)info[1]).intValue();
				TreeNode currentNode=new TreeNode();
				currentNode.parent=parentNode;
				currentNode.parentAttribute=attributes.get(i);
				currentNode.attributes=this.attributevalue.get(index);
				currentNode.nodeName=this.attribute.get(index);
				parentNode.childNodes.add(currentNode);
				insertTree(pickArray,currentNode);
			}
			else
			{
				TreeNode leafNode=new TreeNode();
				leafNode.parent=parentNode;
				leafNode.parentAttribute=attributes.get(i);
				leafNode.attributes=null;
				leafNode.childNodes=null;
				leafNode.nodeName=voteDecide(pickArray);//多数表决
				parentNode.childNodes.add(leafNode);
			}
		}
	}
	
	//多数表决
	public String voteDecide(ArrayList<String[]> array)
	{
		int sum=array.size();
		int att_sum=this.attributevalue.get(this.nodeIndex).size();
		int[] counts=new int[att_sum];
		for(int i=0;i<att_sum;++i)
			counts[i]=0;
		for(int i=0;i<sum;++i)
		{
			for(int j=0;j<att_sum;++j)
			{
				if(array.get(i)[this.nodeIndex].equals(this.attributevalue.get(j)))
					counts[j]++;
			}
		}
		int max_index=getMaxIndex(counts);
		return this.attributevalue.get(this.nodeIndex).get(max_index);
		
	}
	//获得数组中最大数的下标
	public int getMaxIndex(int[] count)
	{
		int index=0;
		int max=count[index];
		for(int i=1;i<count.length;++i)
		{
			if(max<count[i])
			{
				index=i;
				max=count[index];
			}
		}
		return index;
	}
	//按照条件选取数组,指定列上的指定属性
	public ArrayList<String[]> pickUpAndArray(ArrayList<String[]> array,String attribute,int index)
	{
		ArrayList<String[]> pickUp=new ArrayList<String[]>();
		String[] line=new String[this.nodeIndex];
		for(int i=0;i<array.size();++i)
		{
			if(array.get(i)[index].equals(attribute))
			{
				line=array.get(i);
				pickUp.add(line);
			}
		}
		return pickUp;
	}
	//传入一个String的数组，进行预测是否匹配,肯定会找到
	public String compare(String[] data,TreeNode node)
	{
		String result=null;
		int index;
		if(this.attributevalue.get(nodeIndex).contains(node.nodeName))
		{
			result=node.nodeName;
			return result;
		}
		index=this.attribute.indexOf(node.nodeName);
		ArrayList<TreeNode> childs=node.childNodes;
		for(int i=0;i<childs.size();++i)
		{
			if(childs.get(i)!=null)
				if(childs.get(i).parentAttribute.equals(data[index]))
					result=compare(data,childs.get(i));
		}
		return result;
	}
	//根据给定的训练集进行预测，并计算出预测结果的正确率
	public void predicate(ArrayList<String[]> testarray,TreeNode node)
	{
		String result;
		int sum=testarray.size();
		int count=0;
		for(int i=0;i<testarray.size();++i)
		{
			result=compare(testarray.get(i),node);
			if(result.equals(testarray.get(i)[nodeIndex]))
				count++;
		}
		double rate=count*Double.parseDouble("1.0")/sum;
		System.out.println(rate+0.1);
	}
	//获得最大的信息增益
	public Object[] getMaxGain(ArrayList<String[]> array)
	{
		Object[] result=new Object[2];
		double gain=0.0;
		int index=-1;
		for(int i=0;i<this.visiable.length;++i)
		{
			if(!this.visiable[i])
			{
				double value=gain(array,i);
				if(gain<value)
				{
					gain=value;
					index=i;
				}
			}
		}
		result[0]=gain;
		result[1]=index;
		if(index!=-1)
			this.visiable[index]=true;
		return result;
		
	}
	//计算信息增益
	public double gain(ArrayList<String[]> array,int index)
	{
		//得到分类的种类数
		ArrayList<String> acceptable=this.attributevalue.get(this.nodeIndex);
		//每种分类的多少
		int[] counts=new int[acceptable.size()];
		for(int i=0;i<counts.length;++i)
			counts[i]=0;
		for(int i=0;i<array.size();++i)
		{
			for(int j=0;j<acceptable.size();++j)
			{
				if(array.get(i)[this.nodeIndex].equals(acceptable.get(j)))
					counts[j]++;
			}
		}
		//基础的熵
		double BaseEntropy=0.0;
		for(int i=0;i<counts.length;++i)
			BaseEntropy+=DTree.sigma(counts[i], array.size());
		//被选属性的列表
		ArrayList<String> selected_atrribute=this.attributevalue.get(index);
		//被选的分类的信息熵
		double sel_index_entropy=0.0;
		for(int i=0;i<selected_atrribute.size();++i)
			sel_index_entropy+=get_sel_att_entropy(array,index,selected_atrribute.get(i),array.size());
		return BaseEntropy-sel_index_entropy;
		
	}
	//获得指定属性的指定值的信息熵
	public double get_sel_att_entropy(ArrayList<String[]> array,int index,String attribute,int array_total)
	{
		//得到分类的种类数
		ArrayList<String> acceptable=this.attributevalue.get(this.nodeIndex);
		//指定index的属性值是attribute的时候各个分类的出现次数
		int[] counts=new int[acceptable.size()];
		for(int i=0;i<acceptable.size();++i)
			counts[i]=0;
		for(int i=0;i<array.size();++i)
		{
			if(array.get(i)[index].equals(attribute))
			{
				for(int j=0;j<acceptable.size();++j)
				{
					if(array.get(i)[this.nodeIndex].equals(acceptable.get(j)))
						counts[j]++;
				}
			}
		}
		//这个属性出现的次数
		int total=0;
		for(int i=0;i<counts.length;++i)
			total+=counts[i];
		//单个属性对应的属性值的信息增益
		double single_gain=0.0;
		for(int i=0;i<counts.length;++i)
			single_gain+=DTree.sigma(counts[i], total);
		return DTree.getDivide(total, array_total)*single_gain;
	}
}
