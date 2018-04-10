import java.util.*;
import java.lang.*;
import java.io.*;

//max number of word in vocab=40000
//max number of queries in input=100
//max length of word in input=12
//max length of word in vocab=12
public class Anagram
{
	int[] primes=new int[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157};
	char[] alphabet = "abcdefghijklmnopqrstuvwxyz0123456789'".toCharArray();
	int tablesize=40013;
	ArrayList[] HashTable;
	int count=0;
	public Anagram()
	{
		HashTable=new ArrayList[tablesize];
		for(int i=0;i<tablesize;i++)
		{
			HashTable[i]=new ArrayList<String>();
		}
	}
		
	//int tableSize=40009;
	public void hash(String str)
	{
		int temp=1,j=0;
		for(int i=0;i<str.length();i++)
		{
			for(j=0;j<37;j++)
			{
				if(str.charAt(i)==alphabet[j])
				{
					temp=temp*primes[j];
					temp=temp%tablesize;
					break;
				}
			}
		}
		//temp contains where I have to add this string;
		if(HashTable[temp].size()!=0)
			count++;
		HashTable[temp].add(str);
		
		//System.out.println(temp);
	}
	
	public int get_hash (String str) ///returns the value of integer where we have to save it in HashTable
	{
		int temp=1,j=0;
		for(int i=0;i<str.length();i++)
		{
			for(j=0;j<37;j++)
			{
				if(str.charAt(i)==alphabet[j])
				{
					temp=temp*primes[j];
					temp=temp%tablesize;
					break;
				}
			}
		}
		return temp;
	}
	
	public void search(String str, ArrayList<String> out)
	{
		//ArrayList<String> out= new ArrayList<String>();
		int temp=get_hash(str);
		
		//System.out.println(temp);
		//temp contains where I have to add this string;
		String g;
		g=lex(str);
		str=g;
		String pp; 
		//System.out.println(HashTable[temp].size());
		if(HashTable[temp].size()!=0)
		{
			for(int i=0;i<HashTable[temp].size();i++)  // check if any stored in Hashtable is anagram of the input then print it.
			{
				String p=HashTable[temp].get(i).toString();
				pp=lex(p);
				//System.out.println(ans);
				if(pp.equals(str))
				{
					out.add(HashTable[temp].get(i).toString());
				}
			}
		}
		//System.out.println(out);
	}
		
	public void search2(ArrayList<String> firsthalf, ArrayList<String> secondhalf, ArrayList<String> out)///firsthalf => 3 waale ///secondhalf=>4 waale
	{
		for(int i=0;i<firsthalf.size();i++) //size of firsthalf and second half will be same
		{
			int p=firsthalf.size()-1-i;
			int hash1=get_hash(firsthalf.get(i));
			int hash2=get_hash(secondhalf.get(p));
			if(HashTable[hash1].size()!=0 && HashTable[hash2].size()!=0)
			{
				String a=lex(firsthalf.get(i));
				String b=lex(secondhalf.get(p));
				for(int j=0;j< HashTable[hash1].size();j++)
				{
					String temp1=lex(HashTable[hash1].get(j).toString());
					
					for(int k=0;k<HashTable[hash2].size();k++)
					{
						String temp2=lex(HashTable[hash2].get(k).toString());
						if(a.equals(temp1) && b.equals(temp2))
						{
							if(firsthalf.get(0).length()==secondhalf.get(0).length())
							{
								String h=HashTable[hash2].get(k).toString()+" "+HashTable[hash1].get(j).toString();
								out.add(h);
							}
							else
							{
								String g=HashTable[hash1].get(j).toString() +" " + HashTable[hash2].get(k).toString();
								String h=HashTable[hash2].get(k).toString()+" "+HashTable[hash1].get(j).toString();
								out.add(g);
								out.add(h);
							}
						}
					}
				}
			}
		}
	}
	
	public void search3 (ArrayList<String> first, ArrayList<String> second, ArrayList<String> out,int b, int c)
	{
		int a=first.get(0).length();
		for(int i=0;i<first.size();i++)//3,4,5 first=>3 waala, second=> 9 wala
		{
			int hash1=get_hash(first.get(i));
			if(HashTable[hash1].size()!=0)
			{	int p=first.size()-1-i;
				String aa=lex(first.get(i));
				ArrayList<String> temp1=new ArrayList<String> ();
				ArrayList<String> temp2=new ArrayList<String> ();
				String ss=lex(second.get(p));
				cool(ss,temp1,b);
				cool(ss,temp2,c);
				for(int j=0;j<temp1.size();j++)
				{
					int q=temp1.size()-1-j;
					int hash2=get_hash(temp1.get(j));
					int hash3=get_hash(temp2.get(q));
					if(HashTable[hash2].size()!=0 && HashTable[hash3].size()!=0)
					{
						String bb=lex(temp1.get(j));
						String cc=lex(temp2.get(q));
						for(int m=0;m<HashTable[hash1].size();m++)
						{
							String temp_temp1=lex(HashTable[hash1].get(m).toString());
							for(int x=0;x<HashTable[hash2].size();x++)
							{
								String temp_temp2=lex(HashTable[hash2].get(x).toString());
								for(int k=0;k<HashTable[hash3].size();k++)
								{
									String temp_temp3=lex(HashTable[hash3].get(k).toString());
									if(bb.equals(temp_temp2) && cc.equals(temp_temp3) && aa.equals(temp_temp1))
									{	
										if(a==b && b==c)
										{
											String gg=HashTable[hash1].get(m).toString()+" "+HashTable[hash2].get(x).toString()+" "+HashTable[hash3].get(k).toString();
											out.add(gg);
										}
										else if(a==b)
										{
											String gg=HashTable[hash1].get(m).toString()+" "+HashTable[hash2].get(x).toString()+" "+HashTable[hash3].get(k).toString();
											String hh=HashTable[hash2].get(x).toString()+" "+HashTable[hash3].get(k).toString()+" "+HashTable[hash1].get(m).toString();
											String ff=HashTable[hash3].get(k).toString()+" "+HashTable[hash1].get(m).toString()+" "+HashTable[hash2].get(x).toString();
											out.add(gg);
											out.add(hh);
											out.add(ff);
										}
										else if(b==c)
										{
											String gg=HashTable[hash1].get(m).toString()+" "+HashTable[hash2].get(x).toString()+" "+HashTable[hash3].get(k).toString();
											String hh=HashTable[hash2].get(x).toString()+" "+HashTable[hash3].get(k).toString()+" "+HashTable[hash1].get(m).toString();
											String ff=HashTable[hash3].get(k).toString()+" "+HashTable[hash1].get(m).toString()+" "+HashTable[hash2].get(x).toString();
											out.add(gg);
											out.add(hh);
											out.add(ff);
										}
										else if(a==c)
										{
											String gg=HashTable[hash1].get(m).toString()+" "+HashTable[hash2].get(x).toString()+" "+HashTable[hash3].get(k).toString();
											String hh=HashTable[hash2].get(x).toString()+" "+HashTable[hash3].get(k).toString()+" "+HashTable[hash1].get(m).toString();
											String ff=HashTable[hash3].get(k).toString()+" "+HashTable[hash1].get(m).toString()+" "+HashTable[hash2].get(x).toString();
											out.add(gg);
											out.add(hh);
											out.add(ff);
										}
									
										else
										{
											String gg=HashTable[hash1].get(m).toString()+" "+HashTable[hash2].get(x).toString()+" "+HashTable[hash3].get(k).toString();
											String hh=HashTable[hash2].get(x).toString()+" "+HashTable[hash3].get(k).toString()+" "+HashTable[hash1].get(m).toString();
											String ff=HashTable[hash3].get(k).toString()+" "+HashTable[hash1].get(m).toString()+" "+HashTable[hash2].get(x).toString();
											String kk=HashTable[hash1].get(m).toString()+" "+HashTable[hash3].get(k).toString()+" "+HashTable[hash2].get(x).toString();
											String jj=HashTable[hash2].get(x).toString()+" "+HashTable[hash1].get(m).toString()+" "+HashTable[hash3].get(k).toString();
											String ll=HashTable[hash3].get(k).toString()+" "+HashTable[hash2].get(x).toString()+" "+HashTable[hash1].get(m).toString();
											out.add(gg);
											out.add(hh);
											out.add(ff);
											out.add(kk);
											out.add(jj);
											out.add(ll);
										}
									}
								}
							}
						}
					}
				}				
			}
		}
	}
	
	public String lex(String str)  //returns a string which is lexicographic order of str
	{
		String hi;
		char[] yoyo=str.toCharArray();
		Arrays.sort(yoyo);
		hi=new String(yoyo);
		return hi;
	}
	
	public void chullz(String original, char[] temp, ArrayList<String> storage, int first, int last, int pos, int r) //index=currently how many in temp string
	{
		if(pos==r)
		{
			String bro="";
			for(int m=0;m<pos;m++)
			{
				bro=bro+temp[m];
			}
			storage.add(bro);
			return;
		}
		else
		{
			for (int i=first; i<=last && last-i+1 >= r-pos; i++)
			{
				temp[pos] = original.charAt(i);
				chullz(original, temp, storage, i+1, last, pos+1, r);
				if(i<last)
				{
					while (original.charAt(i) == original.charAt(i+1))
					{
						i++;
						if(i>=last)
							break;
					}
				}
			}
		}
	}
	
	public void cool(String original, ArrayList<String> storage, int r)
	{
		char[] temp=new char[r];
		chullz(original, temp, storage, 0, original.length()-1, 0, r);
	}
			
	public static void main(String[] args)
	{
		long startTime=System.currentTimeMillis();
		long time;
		File vocabulary=  new File(args[0]);
		File input= new File(args[1]);
		try{
			FileInputStream vocabulary_stream = new FileInputStream(vocabulary);
			FileInputStream input_stream = new FileInputStream(input);
			Scanner in1=new Scanner(vocabulary_stream);
			Scanner in2=new Scanner(input_stream);			
			Anagram obj=new Anagram();
			//int count=0;
			String k=new String();
			int f=in1.nextInt();
			int q;
			for(int h=0;h<f;h++)
			{
				k=in1.next().toString();
				
				if(k.length()<=12)
				{
					obj.hash(k);
					//q=obj.get_hash(k);
					//System.out.println(q);
				}
			}
			//////////////////////TIME FOR HASHING
			//time=System.currentTimeMillis()-startTime;
			//System.out.println("time: "+time+" millis");
		
			/////////////////////CHECKED HASHING 6900 collisions
			//System.out.println(obj.count);
			
			f=in2.nextInt();
			for(int h=0;h<f;h++) // ans contains the final arraylist
			{
				ArrayList<String> ans= new ArrayList<String>();
				k=in2.next().toString();
				if(k.length()<=5 && k.length()>=3)
				{
					obj.search(k,ans);
				}
				
				////////////////3,4,5 is working fine///////////////////////////
				
				else if(k.length()==6)   //get all the distinct permutations of all the 3 lettered words
				{
					obj.search(k,ans);
					String lex=obj.lex(k);
					ArrayList<String> temp1=new ArrayList<String> ();
					obj.cool(lex,temp1,3);
					obj.search2(temp1,temp1,ans);
				}
				
				else if(k.length()==7)
				{
					obj.search(k,ans);
					String lex=obj.lex(k);
					ArrayList<String> temp1= new ArrayList<String> ();
					ArrayList<String> temp2= new ArrayList<String>();
					obj.cool(lex,temp1,3);
					obj.cool(lex,temp2,4);
					obj.search2(temp1,temp2,ans);
				}
				
				else if(k.length()==8)
				{
					obj.search(k,ans);
					String lex=obj.lex(k);
					ArrayList<String> temp1=new ArrayList<String> ();
					obj.cool(lex,temp1,4);
					ArrayList<String> temp3=new ArrayList<String> ();
					ArrayList<String> temp4=new ArrayList<String> ();
					obj.cool(lex,temp3,3);
					obj.cool(lex,temp4,5);
					obj.search2(temp1,temp1,ans);
					obj.search2(temp3,temp4,ans);
				}
				
				///////////6,7,8 ALSO WORKING FINE
				
				else if(k.length()==9)
				{
					ArrayList<String> temp1=new ArrayList<String> ();
					ArrayList<String> temp2=new ArrayList<String> ();
					ArrayList<String> temp3=new ArrayList<String> ();
					ArrayList<String> temp4=new ArrayList<String> ();
					//ArrayList<String> temp5=new ArrayList<String> ();
					String lex=obj.lex(k);
					obj.cool(lex,temp1,3);
					obj.cool(lex,temp2,6);
					obj.search3(temp1,temp2,ans,3,3);
					obj.search2(temp1,temp2,ans);
					obj.cool(lex,temp3,4);
					obj.cool(lex,temp4,5);
					obj.search2(temp3,temp4,ans);
					obj.search(k,ans);
				}
				else if(k.length()==10)
				{
					ArrayList<String> temp1=new ArrayList<String> ();
					ArrayList<String> temp2=new ArrayList<String> ();
					ArrayList<String> temp3=new ArrayList<String> ();
					ArrayList<String> temp4=new ArrayList<String> ();
					ArrayList<String> temp5=new ArrayList<String> ();
					String lex=obj.lex(k);
					obj.cool(lex,temp1,3);
					obj.cool(lex,temp2,7);
					obj.search3(temp1,temp2,ans,3,4);
					obj.search2(temp1,temp2,ans);
					obj.cool(lex,temp3,4);
					obj.cool(lex,temp4,6);
					obj.search2(temp3,temp4,ans);
					obj.cool(lex,temp5,5);
					obj.search2(temp5,temp5,ans);
					obj.search(k,ans);
				}
				else if(k.length()==11)
				{
					ArrayList<String> temp1=new ArrayList<String> ();
					ArrayList<String> temp2=new ArrayList<String> ();
					ArrayList<String> temp3=new ArrayList<String> ();
					ArrayList<String> temp4=new ArrayList<String> ();
					ArrayList<String> temp5=new ArrayList<String> ();
					ArrayList<String> temp6=new ArrayList<String> ();
					ArrayList<String> temp7=new ArrayList<String> ();
					//ArrayList<String> temp7=new ArrayList<String> ();
					String lex=obj.lex(k);
					obj.cool(lex,temp1,3);
					obj.cool(lex,temp2,8);
					//obj.search3(temp1,temp2,ans,3,5);
					obj.search3(temp1,temp2,ans,4,4);
					obj.search2(temp1,temp2,ans);
					obj.cool(lex,temp3,4);
					obj.cool(lex,temp4,7);
					obj.search2(temp3,temp4,ans);
					obj.cool(lex,temp5,5);
					obj.cool(lex,temp6,6);
					obj.search3(temp5,temp6,ans,3,3);
					obj.search2(temp5,temp6,ans);
					obj.search(k,ans);
				}
				else if(k.length()==12)
				{
					ArrayList<String> temp1=new ArrayList<String> ();
					ArrayList<String> temp2=new ArrayList<String> ();
					ArrayList<String> temp3=new ArrayList<String> ();
					ArrayList<String> temp4=new ArrayList<String> ();
					ArrayList<String> temp5=new ArrayList<String> ();
					ArrayList<String> temp6=new ArrayList<String> ();
					ArrayList<String> temp7=new ArrayList<String> ();
					String lex=obj.lex(k);
					obj.cool(lex,temp1,3);
					obj.cool(lex,temp2,9);
					obj.search3(temp1,temp2,ans,4,5);
					obj.search2(temp1,temp2,ans);
					obj.cool(lex,temp3,4);
					obj.cool(lex,temp4,8);
					obj.search3(temp3,temp4,ans,4,4);
					obj.search2(temp3,temp4,ans);
					obj.cool(lex,temp5,5);
					obj.cool(lex,temp6,7);
					obj.search2(temp5,temp6,ans);
					obj.cool(lex,temp7,6);
					obj.search2(temp7,temp7,ans);
					obj.search(k,ans);
					obj.search3(temp7,temp7,ans,3,3);					
				}
				
				Collections.sort(ans);
				ArrayList<String> poo=new ArrayList<String>();
				if(ans.size()!=0)
					poo.add(ans.get(0));
				for(int i=0;i<ans.size();i++)
				{
					if(!(poo.get(poo.size()-1).equals(ans.get(i))))
					{
						poo.add(ans.get(i));
					}
				}
				for(int i=0;i<poo.size();i++)
					System.out.println(poo.get(i));   ///can print even in above for loop
				System.out.println(-1);
			}
			time=System.currentTimeMillis()-startTime;
			System.out.println("time: "+time+" millis");
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Incorrect File");
		}		
	}
}
