import java.util.*;
import java.lang.*;
import java.io.*;

public class Puzzle
{
	int[] cloud=new int[362881];//rank starts from 1
	int[] in_list=new int [362881];//rank starts from 1
	vertex[] globe=new vertex[362881];
	HashMap<String,Integer> hm= new HashMap<String,Integer>();
	Integer map_integer=new Integer(0);
	Integer end;
	vertex final_end;
	BinHeap bh=new BinHeap();
	public void reset()
	{
		for(int i=0;i<362880;i++)
		{
			cloud[i]=in_list[i]=0;
			globe[i].cost=Integer.MAX_VALUE;
			globe[i].length=Integer.MAX_VALUE;
			bh.heap[i]=null;
		}
		bh.heapSize=0;
	}
	
	public int compare_vertex (vertex a, vertex b) //first cost, then length
	{
		/**returns 1 when a>b, -1 when a<b**/
		if((a.cost>b.cost) || (a.cost==b.cost && a.length>b.length))
			return 1;
		else
			return -1;
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public class vertex
	{
		public String str;  //string
		public vertex previous=null; //previous pointer in path
		public int cost=Integer.MAX_VALUE;//cost from source to this vertex
		public int length=Integer.MAX_VALUE;//length of path from source to vertex
		public String transition;
		public vertex(String str)//constructor
		{
			this.str=str;
		}
	}
	public ArrayList<String> backtrack(String start)
	{
		ArrayList<String> ans=new ArrayList<String>();
		vertex temp=final_end;
		while((temp.str).compareTo(start)!=0)
		{
			ans.add(temp.transition);
			temp=temp.previous;
		}
		return ans;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void find1(String start, String end, int[] cost)
	{
		int a=hm.get(start).intValue();
		int b=hm.get(end).intValue();
		int p,count=0,k=0,j,check,temp_min=0;
		int min=0;
		in_list[a]=1; 
		ArrayList<String> temp=new ArrayList<String>();
		ArrayList<Integer> temp_cost=new ArrayList<Integer>(4);
		temp_cost.add(0);
		temp_cost.add(0);
		temp_cost.add(0);
		temp_cost.add(0);
		vertex parent=globe[a];
		parent.cost=0;
		parent.length=0;
		bh.insert(parent);
		while(cloud[b]==0)
		{
			parent=bh.deleteMin();
			k=hm.get(parent.str).intValue();
			cloud[k]=1;
			temp=neighbour(parent.str,cost,temp_cost);
			//System.out.println(temp);
			for(int i=0;i<temp.size();i++)
			{
				j=0;
				p=hm.get(temp.get(i).substring(0,9)).intValue();      //temp ke transitions stored in cool
				if(in_list[p]==0)//given string isn't in the list hence not in cloud too
				{
					in_list[p]=1;
					vertex child=globe[p];
					child.previous=parent;
					child.cost=parent.cost+temp_cost.get(i);
					child.length=parent.length+1;
					child.transition=temp.get(i).substring(9,11);
					bh.insert(child);
				}
				else if(in_list[p]==1 && cloud[p]==0)
				{
					String str=temp.get(i).substring(0,9);
					/**minimize this time by using another hash function which gives the location in the array**/
						//location j has the str.
					// 
					if((globe[p].cost>parent.cost+temp_cost.get(i)) || ((globe[p].cost==parent.cost+temp_cost.get(i)) && globe[p].length > parent.length+1))
					{
						vertex child=globe[p];
						child.previous=parent;
						child.cost=parent.cost+temp_cost.get(i);
						child.length=parent.length+1;
						child.transition=temp.get(i).substring(9,11);
						//now this child is not at correct place
						for(j=0;j<bh.heapSize;j++)
						{
							if(child.str.equals(bh.heap[j].str))
								break;
						}
						//this vertex is at position j
						globe[p]=bh.percolateUp(j);
					}					
				}
			}
		}
		final_end=parent;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/*CREATE A BINARY HEAP FOR STORING VERTICES IN THE SAME WAY AS IN LIST */
	public class BinHeap    
	{
		//starting to fill from 0
		public int heapSize;
		public vertex[] heap;
		
		//constructor
		public BinHeap()
		{
			heapSize = 0;
			heap = new vertex[362881];
		}
		/**I only want insert, delete min**/
		
		public int parent(int i) 
		{
			return (i - 1)/2;
		}
 
		//k=1=> lest child, k=2=> right child
		public int Child(int i, int k) 
		{
			return 2 * i + k;
		}
 
		
		//Inserting vertex in heap
		public void insert(vertex node)
		{
			heap[heapSize] = node;
			heapSize++;
			percolateUp(heapSize - 1);
		}

		public vertex deleteMin()
		{
			vertex temp = heap[0];
			heap[0] = heap[heapSize - 1];
			heapSize--;
			percolateDown(0); 
			return temp;
		}

		public vertex percolateUp(int index)
		{
			vertex temp = heap[index];    
			while (index > 0 && compare_vertex(temp,heap[parent(index)])==-1)
			{
				heap[index] = heap[ parent(index) ];
				index = parent(index);
			}                   
			heap[index] = temp;
			return heap[index];
		}
 
		private void percolateDown(int index)
		{
			int rasmalai;
			vertex temp = heap[index];
			while (Child(index,1) < heapSize)
			{
				rasmalai = minChild(index);
				if (compare_vertex(heap[rasmalai],temp)==-1)
					heap[index] = heap[rasmalai];
				else
					break;
				index=rasmalai;
			}
			heap[index] = temp;
		}
 
		//which child is small
		private int minChild(int ind) 
		{
			int child_left = Child(ind, 1);
			int child_right= Child(ind, 2);
			if(child_left<heapSize)
				if ((compare_vertex(heap[child_right],heap[child_left])==-1)) 
					child_left = child_right;
			return child_left;
		}
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public int check(String str)
	{
		int check=0;
		for(int i=0;i<str.length();i++)
		{
			for(int j=i+1;j<str.length();j++)
			{
				if(str.charAt(i)!='G' && str.charAt(j)!='G')
				{
					if(str.charAt(i)>str.charAt(j))
						check++;
				}
			}
		}
	return check;
	}


	public ArrayList<String> neighbour(String str, int[] cost, ArrayList<Integer> cool) 
	{	//returns neighbours of a string(0-8) 9-10 gives 1D type information and 11 gives the cost of travelling
		ArrayList<String> temp=new ArrayList<String>();
		String temp1=new String();
		String temp2=new String();
		String temp3=new String();
		String temp4=new String();
		
		String move1=new String();
		String move2=new String();
		String move3=new String();
		String move4=new String();
		
		int k=str.indexOf("G");
		if(k==0)
		{
			int p1 = Integer.parseInt(""+str.charAt(1));
			int p2 = Integer.parseInt(""+str.charAt(3));
			
			temp1=str.substring(1,2)+str.substring(0,1)+str.substring(2,9)+str.charAt(1)+"L";  //0,1
			temp2=str.substring(3,4)+str.substring(1,3)+str.substring(0,1)+str.substring(4,9)+str.charAt(3)+"U"; //0,3
			temp.add(temp1);
			temp.add(temp2);
			cool.set(0,cost[p1-1]);
			cool.set(1,cost[p2-1]);
		}
		else if(k==1)
		{
			int p1=Integer.parseInt(""+str.charAt(0));
			int p2=Integer.parseInt(""+str.charAt(2));
			int p3=Integer.parseInt(""+str.charAt(4));
			temp1=str.substring(1,2)+str.substring(0,1)+str.substring(2,9)+str.charAt(0)+"R";
			temp2=str.substring(0,1)+str.substring(2,3)+str.substring(1,2)+str.substring(3,9)+str.charAt(2)+"L"; // 1,2
			temp3=str.substring(0,1)+str.substring(4,5)+str.substring(2,4)+str.substring(1,2)+str.substring(5,9)+str.charAt(4)+"U"; //1,4
			temp.add(temp1);
			temp.add(temp2);
			temp.add(temp3);
			cool.set(0,cost[p1-1]);
			cool.set(1,cost[p2-1]);
			cool.set(2,cost[p3-1]);
		}
		else if(k==2)
		{
			int p1=Integer.parseInt(""+str.charAt(1));
			int p2=Integer.parseInt(""+str.charAt(5));
			temp1=str.substring(0,1)+str.substring(2,3)+str.substring(1,2)+str.substring(3,9)+str.charAt(1)+"R";//1,2
			temp2=str.substring(0,2)+str.substring(5,6)+str.substring(3,5)+str.substring(2,3)+str.substring(6,9)+str.charAt(5)+"U";//2,5
			temp.add(temp1);
			temp.add(temp2);
			cool.set(0,cost[p1-1]);
			cool.set(1,cost[p2-1]);
		}
		else if(k==3)
		{
			int p1=Integer.parseInt(""+str.charAt(0));
			int p2=Integer.parseInt(""+str.charAt(6));
			int p3=Integer.parseInt(""+str.charAt(4));
			temp1=str.substring(3,4)+str.substring(1,3)+str.substring(0,1)+str.substring(4,9)+str.charAt(0)+"D"; //0,3
			temp2=str.substring(0,3)+str.substring(6,7)+str.substring(4,6)+str.substring(3,4)+str.substring(7,9)+str.charAt(6)+"U";//3,6
			temp3=str.substring(0,3)+str.substring(4,5)+str.substring(3,4)+str.substring(5,9)+str.charAt(4)+"L";//3,4
			
			temp.add(temp1);
			temp.add(temp2);
			temp.add(temp3);
			
			cool.set(0,cost[p1-1]);
			cool.set(1,cost[p2-1]);
			cool.set(2,cost[p3-1]);
		}
		else if(k==4)
		{
			int p1=Integer.parseInt(""+str.charAt(1));
			int p2=Integer.parseInt(""+str.charAt(3));
			int p3=Integer.parseInt(""+str.charAt(5));
			int p4=Integer.parseInt(""+str.charAt(7));
			move1=str.charAt(1)+"D";
			move2=str.charAt(3)+"R";
			move3=str.charAt(5)+"L";
			move4=str.charAt(7)+"U";
			
			temp1=str.substring(0,1)+str.substring(4,5)+str.substring(2,4)+str.substring(1,2)+str.substring(5,9)+move1;//4,1
			temp2=str.substring(0,3)+str.substring(4,5)+str.substring(3,4)+str.substring(5,9)+move2;//4,3
			temp3=str.substring(0,4)+str.substring(5,6)+str.substring(4,5)+str.substring(6,9)+move3;//4,5
			temp4=str.substring(0,4)+str.substring(7,8)+str.substring(5,7)+str.substring(4,5)+str.substring(8,9)+move4;//4,7
			temp.add(temp1);
			temp.add(temp2);
			temp.add(temp3);
			temp.add(temp4);
			
			cool.set(0,cost[p1-1]);
			cool.set(1,cost[p2-1]);
			cool.set(2,cost[p3-1]);
			cool.set(3,cost[p4-1]);
		}
		else if(k==5)
		{
			move1=str.charAt(2)+"D";
			move2=str.charAt(4)+"R";
			move3=str.charAt(8)+"U";
			
			int p1=Integer.parseInt(""+str.charAt(2));
			int p2=Integer.parseInt(""+str.charAt(4));
			int p3=Integer.parseInt(""+str.charAt(8));
			temp1=str.substring(0,2)+str.substring(5,6)+str.substring(3,5)+str.substring(2,3)+str.substring(6,9)+move1;//5,2
			temp2=str.substring(0,4)+str.substring(5,6)+str.substring(4,5)+str.substring(6,9)+move2;//5,4
			temp3=str.substring(0,5)+str.substring(8,9)+str.substring(6,8)+str.substring(5,6)+move3;//5,8
			
			temp.add(temp1);
			temp.add(temp2);
			temp.add(temp3);
			
			cool.set(0,cost[p1-1]);
			cool.set(1,cost[p2-1]);
			cool.set(2,cost[p3-1]);
		}
		else if(k==6)
		{
			move1=str.charAt(3)+"D";
			move2=str.charAt(7)+"L";
			
			int p1=Integer.parseInt(""+str.charAt(3));
			int p2=Integer.parseInt(""+str.charAt(7));
			
			temp1=str.substring(0,3)+str.substring(6,7)+str.substring(4,6)+str.substring(3,4)+str.substring(7,9)+move1;//6,3
			temp2=str.substring(0,6)+str.substring(7,8)+str.substring(6,7)+str.substring(8,9)+move2;//6,7
			temp.add(temp1);
			temp.add(temp2);
			
			cool.set(0,cost[p1-1]);
			cool.set(1,cost[p2-1]);
		}
		else if(k==7)
		{
			move1=str.charAt(6)+"R";
			move2=str.charAt(4)+"D";
			move3=str.charAt(8)+"L";
			
			int p1=Integer.parseInt(""+str.charAt(6));
			int p2=Integer.parseInt(""+str.charAt(4));
			int p3=Integer.parseInt(""+str.charAt(8));
			temp1=str.substring(0,6)+str.substring(7,8)+str.substring(6,7)+str.substring(8,9)+move1;//6,7
			temp2=str.substring(0,4)+str.substring(7,8)+str.substring(5,7)+str.substring(4,5)+str.substring(8,9)+move2;//7,4
			temp3=str.substring(0,7)+str.substring(8,9)+str.substring(7,8)+move3;//7,8
			temp.add(temp1);
			temp.add(temp2);
			temp.add(temp3);
			
			cool.set(0,cost[p1-1]);
			cool.set(1,cost[p2-1]);
			cool.set(2,cost[p3-1]);
		}
		else if(k==8)
		{
			move1=str.charAt(7)+"R";
			move2=str.charAt(5)+"D";
			
			int p1=Integer.parseInt(""+str.charAt(7));
			int p2=Integer.parseInt(""+str.charAt(5));
			
			temp1=str.substring(0,7)+str.substring(8,9)+str.substring(7,8)+move1;//7,8
			temp2=str.substring(0,5)+str.substring(8,9)+str.substring(6,8)+str.substring(5,6)+move2;//5,8				
			temp.add(temp1);
			temp.add(temp2);
			cool.set(0,cost[p1-1]);
			cool.set(1,cost[p2-1]);
		}
		return temp;
	}	
	//This function is only for hashing
	private void permutations(String prefix, String str) 
	{
		//prefix ki length=9
		int n = str.length();
		if (n == 0)
		{
			hm.put(prefix,map_integer);
			globe[map_integer]=new vertex(prefix);
			end=map_integer+1;
			map_integer=end;
		}
		else 
		{
			for (int i = 0; i < n; i++)
				permutations(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n));
		}
	}
	//all permutations of 12345678G stored in hash
	
	public static void main(String[] args) 
	{
		String start=new String();
		String end=new String();
		int check1;
		int check2;
		int[] cost=new int[8];
		Puzzle obj= new Puzzle();	
		long startTime=System.currentTimeMillis();
		long time;
		File input= new File(args[0]);
		File output=  new File(args[1]);
		try{
			FileInputStream input_stream = new FileInputStream(input);
			FileOutputStream output_stream=new FileOutputStream(output);
			Scanner in=new Scanner(input_stream);
			PrintWriter out=new PrintWriter(output_stream);
			obj.permutations("","12345678G");
			ArrayList<String> temp=new ArrayList<String>();
			int k=in.nextInt();
			for(int i=0;i<k;i++)
			{
				obj.reset();
				start=in.next();
				end=in.next();
				for(int j=0;j<8;j++)
				{
					cost[j]=in.nextInt();
				}
				if(start.equals(end))
				{
					out.println(0+" "+0);
					out.println("");
				}
				else 
				{
					check1=obj.check(start);
					check2=obj.check(end);
					if(check1%2!=check2%2)
					{
						out.println(-1+" "+-1);
						out.println("");
					}
					else
					{
						obj.find1(start,end,cost);
						temp=obj.backtrack(start);
						Collections.reverse(temp);
						out.println(obj.final_end.length+" "+obj.final_end.cost);
						for(int j=0;j<temp.size();j++)
						{
							out.print(temp.get(j)+" ");
						}
						out.println("");
					}
				}
				
			}
			time=System.currentTimeMillis()-startTime;
			System.out.println("time: "+time+" millis");
			out.close();
		}
		catch ( FileNotFoundException e) {
			System .out . println ("File not found ");
		}
	
	}
}