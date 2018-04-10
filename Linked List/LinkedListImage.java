import java.util.*;
import java.lang.*;
import java.io.*;
public class LinkedListImage implements CompressedImageInterface {
	
	ListNode [] head;//will store the head value for each linked list
	ListNode [] tail;//will store the tail value for each linked list
	int [] count;//will keep the number of elements in each list
	int Width,Height;
	int size=0;

	public int max(int a, int b)
	{
		if(a>b)
			return a;
		else
			return b;
	}
	public int min(int a, int b)
	{
		if(a<b)
			return a;
		else
			return b;
	}
	
	public void addLast(int p,int element)//p=location(where to add)......element=what to add
	{
		if(head[p]!=null)
		{
			tail[p].setNext(new ListNode (element));
			tail[p] = tail[p].getNext();
		}
		else
		{
			tail[p] = new ListNode (element);
			head[p] = tail[p];
		}
	}
	
	public LinkedListImage(String filename)
	{
		int k=3,l;
		int n,m,i,j;
		
		try{
		FileInputStream fstream= new FileInputStream(filename);
		Scanner p=new Scanner(fstream);
		n=p.nextInt();//width
		m=p.nextInt();//height
		count=new int[m];
		Height=m;
		Width=n;
		head= new ListNode[m];
		tail= new ListNode[m];
		for(i=0;i<m;i++)
		{
			head[i]=null;
			tail[i]=null;
			count[i]=0;
		}
		for(i=0;i<m;i++,k=3)
		{
			for(j=0;j<n;j++)
			{
				l=k;
				k=p.nextInt();
				if(j==0)
				{
					if(k==0){
					addLast(i,j);
					count[i]++;
					}										
				}
				else if(j==n-1)
				{
					if(k==0 && k==l)
					{
						addLast(i,j);
						count[i]++;
					}
					else if(k-l==-1)
					{
						addLast(i,j);
						addLast(i,j);
						count[i]++;
						count[i]++;
					}
				    else if(k-l==1)
					{
						addLast(i,j-1);	
						count[i]++;
					}
				}
				
				else
				{
					if(k-l==-1){
					addLast(i,j);
					count[i]++;
					}
				    else if(k-l==1){
					addLast(i,j-1);	
					count[i]++;
					}
				}
			}
			addLast(i,-1);
			count[i]++;
		}
	}	
		catch(FileNotFoundException e)
		{
			System.out.println("Such file does not exist");
		}
	}

    public LinkedListImage(boolean[][] grid, int width, int height)// correct
    {
		
		int k=2,l;
		int n,m,i,j;
		boolean g;
		n=width;//width
		m=height;//height
		Width=width;
		Height=height;
		
		head= new ListNode[m];
		tail= new ListNode[m];
		count=new int[m];
		for(i=0;i<m;i++)
		{
			head[i]=null;
			tail[i]=null;
			count[i]=0;
		}
		
		for(i=0;i<m;i++,k=2)
		{
			for(j=0;j<n;j++)
			{
				l=k;
				g=grid[i][j];
				if(g==true)
					k=1;
				else
					k=0;
				if(j==0)
				{
					if(k==0){
					addLast(i,j);
					count[i]++;
					}
				}
				
				else if(j==n-1)
				{
					if(k==0 && k==l)
					{
						addLast(i,j);
						count[i]++;
					}
					else if(k-l==-1)
					{
						addLast(i,j);
						count[i]++;
						addLast(i,j);
						count[i]++;
					}
				    else if(k-l==1)
					{
						addLast(i,j-1);	
						count[i]++;
					}
				}
				else
				{
					if(k-l==-1){
					addLast(i,j);
					count[i]++;
					}
				    else if(k-l==1){
					addLast(i,j-1);	
					count[i]++;
					}
				}
			}
			addLast(i,-1);
			count[i]++;
		}
    }

    public boolean getPixelValue(int x, int y) throws PixelOutOfBoundException
    {
		if(x>=Height || y>=Width || x<0 || y<0){
			throw new PixelOutOfBoundException("");
		}
		int k,f;
		ListNode temp = head[x];
		for(k=1;k<=count[x]/2;k++)
		{
			for(f=temp.getElement();f<=(temp.getNext()).getElement();f++)
			{
				if(y==f)
				return false;
			}
			temp=(temp.getNext()).getNext();
		}
		return true;
		
    }

    public void setPixelValue(int x, int y, boolean val) throws PixelOutOfBoundException
    {
		if(x>=Height || y>=Width || x<0 || y<0){
			throw new PixelOutOfBoundException("");
		}
		
		int i=0,k,p=0,f,l,j;
		int n=Width;
		ListNode temp = head[x];
		int[] array=new int[Width];
		for(p=0; p<temp.getElement();p++)
		{
			array[p]=1;
		}
		for(k=1;k<count[x];k++)
			{
				if(k%2==1 && (temp.getNext()).getElement()!=-1)
				{
					for(f=temp.getElement();f<=(temp.getNext()).getElement();f++)
					{
						array[f]=0;
					}
					temp=temp.getNext();
				}
				else if(k%2==0 && (temp.getNext()).getElement()!=-1)
				{
					for(f=temp.getElement()+1;f<(temp.getNext()).getElement();f++)
					{
						array[f]=1;
					}
					temp=temp.getNext();
				}
			}
			for(f=temp.getElement()+1;f<Width;f++)
			{
				array[f]=1;
			}			
			if(array[y]==1 && val==false)
			{
				array[y]=0;
			}
			else if(array[y]==0 && val==true)
			{
				array[y]=1;
			}
			k=2;
			count[x]=0;
			head[x]=null;
			tail[x]=null;
			
			for(j=0;j<Width;j++)
			{
				l=k;
				k=array[j];
				if(j==0)
				{
					if(k==0){
					addLast(x,j);
					count[x]++;
					}										
				}
				else if(j==n-1)
				{
					if(k==0 && k==l)
					{
						addLast(x,j);
						count[x]++;
					}
					else if(k-l==-1)
					{
						addLast(x,j);
						count[x]++;
						count[x]++;
						addLast(x,j);
					}
				    else if(k-l==1)
					{
						addLast(x,j-1);	
						count[x]++;
					}
				}
				
				else
				{
					if(k-l==-1){
					addLast(x,j);
					count[x]++;
					}
				    else if(k-l==1){
					addLast(x,j-1);	
					count[x]++;
					}
				}
			}
			addLast(x,-1);
			count[x]++;	
	}			
	public int[] numberOfBlackPixels()
    {
		int n=Width;
		int m=Height;
		int i,k,f;
		int[] black=new int[m];
		ListNode temp;
		for(i=0;i<m;i++)
		{	temp=head[i];
			for(k=1;k<=count[i]/2;k++)
			{
				for(f=temp.getElement();f<=(temp.getNext()).getElement();f++)
				{
					black[i]++;
				}
				temp=(temp.getNext()).getNext();
			}
		}
		return black;
		
    }
    
    public void invert()
    {
		ListNode temp=null;
		ListNode temp_temp=null;
		ListNode temp2=null;
		ListNode temp3=null;
		int m=Height;
		int n=Width;
		int[] size=new int[m];
		int i,j,q=-1,f,g,k;
		for(i=0;i<m;i++){
			temp2=head[i];
			temp3=head[i];
			size[i]=0;
			for(j=1;j<count[i];j++)
			{
				q=temp3.getElement();
				temp3=temp3.getNext();
			}
			if(temp2.getElement()!=0 && q!=n-1)//1......1
			{
				temp = new ListNode(0,null);
				temp_temp=temp;
				size[i]++;
				for(k=1;k<=count[i]/2;k++)
				{
					f=temp2.getElement();
					g=(temp2.getNext()).getElement();
					if(temp==null){
						temp=new ListNode(f-1,null);
						temp_temp=temp;
					}	
					else
						temp.setNext(new ListNode(f-1,null));
					size[i]++;
					temp=temp.getNext();
					temp.setNext(new ListNode(g+1,null));
					size[i]++;
					temp=temp.getNext();
					temp2=(temp2.getNext()).getNext();
				}
				temp.setNext(new ListNode(n-1,null));
				size[i]++;
				temp=temp.getNext();
				temp.setNext(new ListNode(-1,null));
				size[i]++;
				tail[i]=temp.getNext();
				head[i]=temp_temp;
			}
			else if(temp2.getElement()!=0 && q==n-1)// 1........0
			{
				temp=new ListNode(0,null);
				temp_temp=temp;
				size[i]++;
				for(k=1;k<count[i]/2;k++)
				{
					f=temp2.getElement();
					g=(temp2.getNext()).getElement();
					if(temp==null)
					{
						temp=new ListNode(f-1,null);
						temp_temp=temp;
					}
					else
						temp.setNext(new ListNode(f-1,null));
					size[i]++;
					temp=temp.getNext();
					temp.setNext(new ListNode(g+1,null));
					size[i]++;
					temp=temp.getNext();
					temp2=(temp2.getNext()).getNext();
				}
				temp.setNext(new ListNode(temp2.getElement()-1,null));
				size[i]++;
				temp=temp.getNext();
				temp.setNext(new ListNode(-1,null));
				size[i]++;
				tail[i]=temp.getNext();
				head[i]=temp_temp;
			}
			else if(temp2.getElement()==0 && q!=n-1)//0.......1
			{
				temp=new ListNode((temp2.getNext()).getElement()+1,null);
				size[i]++;
				temp_temp=temp;
				temp2=(temp2.getNext()).getNext();
				for(k=1;k<(count[i]/2);k++)
				{
					f=temp2.getElement();
					g=(temp2.getNext()).getElement();
					temp.setNext(new ListNode(f-1,null));
					size[i]++;
					temp=temp.getNext();
					temp.setNext(new ListNode(g+1,null));
					size[i]++;
					temp=temp.getNext();
					temp2=(temp2.getNext()).getNext();
				}
				temp.setNext(new ListNode(n-1,null));
				size[i]++;
				temp=temp.getNext();
				temp.setNext(new ListNode(-1,null));
				size[i]++;
				tail[i]=temp.getNext();
				head[i]=temp_temp;
			}
			else if(temp2.getElement()==0 && q==n-1)//0........0
			{
				if((temp2.getNext()).getElement()!=n-1)
				{
					temp=new ListNode((temp2.getNext()).getElement()+1,null);
					size[i]++;
					temp_temp=temp;
					temp2=(temp2.getNext()).getNext();
					for(k=1;k<(count[i]/2)-1;k++)
					{
						f=temp2.getElement();
						g=(temp2.getNext()).getElement();
						temp.setNext(new ListNode(f-1,null));
						size[i]++;
						temp=temp.getNext();
						temp.setNext(new ListNode(g+1,null));
						size[i]++;
						temp=temp.getNext();
						temp2=(temp2.getNext()).getNext();
					}
					temp.setNext(new ListNode(temp2.getElement()-1,null));
					size[i]++;
					temp=temp.getNext();
					temp.setNext(new ListNode(-1,null));
					size[i]++;
					tail[i]=temp.getNext();
					head[i]=temp_temp;
				}
				else
				{
					temp=new ListNode(-1,null);
					size[i]++;
					temp_temp=temp;
					head[i]=tail[i]=temp_temp;
				}
			}
			count[i]=size[i];
		}
    }
    
    public void performAnd(CompressedImageInterface img) throws BoundsMismatchException
    {
		LinkedListImage temp_img=(LinkedListImage) img;
		if(Height!=temp_img.Height || Width!=temp_img.Width)
		{
			throw new BoundsMismatchException("");
		}
		ListNode temp,temp1,temp2,temp3,temp4;
		int[] array=new int[Width];
		int[] array1=new int[Width];
		int[] array2=new int[Width];
		int i,a,b,d,x,k,m,p=0,f,l,j,n=Width;
		for(i=0;i<Height;i++)
		{
			for(d=0;d<Width;d++)
				array[d]=0;
			temp3=null;
			m=2;
			temp = null;
			temp=head[i];
			for(p=0; p<temp.getElement();p++)
			{
				array[p]=1;
			}
			for(k=1;k<count[i];k++)
			{
				if(k%2==1 && (temp.getNext()).getElement()!=-1)
				{
					for(f=temp.getElement();f<=(temp.getNext()).getElement();f++)
					{
						array[f]=0;
					}
					temp=temp.getNext();
				}
				else if(k%2==0 && (temp.getNext()).getElement()!=-1)
				{
					for(f=temp.getElement()+1;f<(temp.getNext()).getElement();f++)
					{
						array[f]=1;
					}
					temp=temp.getNext();
				}
			}
			for(f=temp.getElement()+1;f<Width;f++)
			{
				array[f]=1;
			}
			temp1 = null;
			temp1=temp_img.head[i];
			
			for(p=0; p<temp1.getElement();p++)
			{
				array1[p]=1;
			}
			for(k=1;k<temp_img.count[i];k++)
			{
				if(k%2==1 && (temp1.getNext()).getElement()!=-1)
				{
					for(f=temp1.getElement();f<=(temp1.getNext()).getElement();f++)
					{
						array1[f]=0;
					}
					temp1=temp1.getNext();
				}
				else if(k%2==0 && (temp1.getNext()).getElement()!=-1)
				{
					for(f=temp1.getElement()+1;f<(temp1.getNext()).getElement();f++)
					{
						array1[f]=1;
					}
					temp1=temp1.getNext();
				}
			}
			for(f=temp1.getElement()+1;f<Width;f++)
			{
				array1[f]=1;
			}
			head[i]=null;
			tail[i]=null;
			count[i]=0;
			
			for(j=0;j<Width;j++)//constructing new AND array
			{
				if(array[j]==1 && array1[j]==1)
					array2[j]=1;
				else
					array2[j]=0;
			}
			
			for(j=0;j<Width;j++)
			{
				l=m;
				m=array2[j];
				if(j==0)
				{
					if(m==0){
					addLast(i,j);
					count[i]++;
					}										
				}
				else if(j==n-1)
				{
					if(m==0 && m==l)
					{
						addLast(i,j);
						count[i]++;
					}
					else if(m-l==-1)
					{
						addLast(i,j);
						count[i]++;
						addLast(i,j);
						count[i]++;
					}
				    else if(m-l==1)
					{
						addLast(i,j-1);	
						count[i]++;
					}
				}
				
				else
				{
					if(m-l==-1){
					addLast(i,j);
					count[i]++;
					}
				    else if(m-l==1){
					addLast(i,j-1);	
					count[i]++;
					}
				}
			}
			addLast(i,-1);
			count[i]++;
			
		}			
    }
    
    public void performOr(CompressedImageInterface img) throws BoundsMismatchException
    {
		LinkedListImage temp_img=(LinkedListImage) img;
		if(Height!=temp_img.Height || Width!=temp_img.Width)
		{
			throw new BoundsMismatchException("");
		}
		ListNode temp,temp1,temp2,temp3,temp4;
		int[] array=new int[Width];
		int[] array1=new int[Width];
		int[] array2=new int[Width];
		int i,a,b,d,x,k,m,p=0,f,l,j,n=Width;
		for(i=0;i<Height;i++)
		{
			for(d=0;d<Width;d++)
				array[d]=0;
			temp3=null;
			m=2;
			temp = null;
			temp=head[i];
			for(p=0; p<temp.getElement();p++)
			{
				array[p]=1;
			}
			for(k=1;k<count[i];k++)
			{
				if(k%2==1 && (temp.getNext()).getElement()!=-1)
				{
					for(f=temp.getElement();f<=(temp.getNext()).getElement();f++)
					{
						array[f]=0;
					}
					temp=temp.getNext();
				}
				else if(k%2==0 && (temp.getNext()).getElement()!=-1)
				{
					for(f=temp.getElement()+1;f<(temp.getNext()).getElement();f++)
					{
						array[f]=1;
					}
					temp=temp.getNext();
				}
			}
			for(f=temp.getElement()+1;f<Width;f++)
			{
				array[f]=1;
			}
			temp1 = null;
			temp1=temp_img.head[i];
			
			for(p=0; p<temp1.getElement();p++)
			{
				array1[p]=1;
			}
			for(k=1;k<temp_img.count[i];k++)
			{
				if(k%2==1 && (temp1.getNext()).getElement()!=-1)
				{
					for(f=temp1.getElement();f<=(temp1.getNext()).getElement();f++)
					{
						array1[f]=0;
					}
					temp1=temp1.getNext();
				}
				else if(k%2==0 && (temp1.getNext()).getElement()!=-1)
				{
					for(f=temp1.getElement()+1;f<(temp1.getNext()).getElement();f++)
					{
						array1[f]=1;
					}
					temp1=temp1.getNext();
				}
			}
			for(f=temp1.getElement()+1;f<Width;f++)
			{
				array1[f]=1;
			}
			head[i]=null;
			tail[i]=null;
			count[i]=0;
			
			for(j=0;j<Width;j++)
			{
				if(array[j]==0 && array1[j]==0)
					array2[j]=0;
				else
					array2[j]=1;
			}
			
			
			for(j=0;j<Width;j++)
			{
				l=m;
				m=array2[j];
				if(j==0)
				{
					if(m==0){
					addLast(i,j);
					count[i]++;
					}										
				}
				else if(j==n-1)
				{
					if(m==0 && m==l)
					{
						addLast(i,j);
						count[i]++;
					}
					else if(m-l==-1)
					{
						addLast(i,j);
						count[i]++;
						addLast(i,j);
						count[i]++;
					}
				    else if(m-l==1)
					{
						addLast(i,j-1);	
						count[i]++;
					}
				}
				
				else
				{
					if(m-l==-1){
					addLast(i,j);
					count[i]++;
					}
				    else if(m-l==1){
					addLast(i,j-1);	
					count[i]++;
					}
				}
			}
			addLast(i,-1);
			count[i]++;
			
		}						
    }
    
    public void performXor(CompressedImageInterface img)throws BoundsMismatchException
    {
		LinkedListImage temp_img=(LinkedListImage) img;
		if(Height!=temp_img.Height || Width!=temp_img.Width)
		{
			throw new BoundsMismatchException("");
		}
		ListNode temp,temp1,temp2,temp3,temp4;
		int[] array=new int[Width];
		int[] array1=new int[Width];
		int[] array2=new int[Width];
		int i,a,b,d,x,k,m,p=0,f,l,j,n=Width;
		for(i=0;i<Height;i++)
		{
			for(d=0;d<Width;d++)
				array[d]=0;
			temp3=null;
			m=2;
			temp = null;
			temp=head[i];
			for(p=0; p<temp.getElement();p++)
			{
				array[p]=1;
			}
			for(k=1;k<count[i];k++)
			{
				if(k%2==1 && (temp.getNext()).getElement()!=-1)
				{
					for(f=temp.getElement();f<=(temp.getNext()).getElement();f++)
					{
						array[f]=0;
					}
					temp=temp.getNext();
				}
				else if(k%2==0 && (temp.getNext()).getElement()!=-1)
				{
					for(f=temp.getElement()+1;f<(temp.getNext()).getElement();f++)
					{
						array[f]=1;
					}
					temp=temp.getNext();
				}
			}
			for(f=temp.getElement()+1;f<Width;f++)
			{
				array[f]=1;
			}
			
			temp1 = null;
			temp1=temp_img.head[i];
			
			for(p=0; p<temp1.getElement();p++)
			{
				array1[p]=1;
			}
			for(k=1;k<temp_img.count[i];k++)
			{
				if(k%2==1 && (temp1.getNext()).getElement()!=-1)
				{
					for(f=temp1.getElement();f<=(temp1.getNext()).getElement();f++)
					{
						array1[f]=0;
					}
					temp1=temp1.getNext();
				}
				else if(k%2==0 && (temp1.getNext()).getElement()!=-1)
				{
					for(f=temp1.getElement()+1;f<(temp1.getNext()).getElement();f++)
					{
						array1[f]=1;
					}
					temp1=temp1.getNext();
				}
			}
			for(f=temp1.getElement()+1;f<Width;f++)
			{
				array1[f]=1;
			}
			
			head[i]=null;
			tail[i]=null;
			count[i]=0;
			
			for(j=0;j<Width;j++)
			{
				if(array[j]==array1[j])
					array2[j]=0;
				else
					array2[j]=1;
			}
			
			
			for(j=0;j<Width;j++)
			{
				l=m;
				m=array2[j];
				if(j==0)
				{
					if(m==0){
					addLast(i,j);
					count[i]++;
					}										
				}
				else if(j==n-1)
				{
					if(m==0 && m==l)
					{
						addLast(i,j);
						count[i]++;
					}
					else if(m-l==-1)
					{
						addLast(i,j);
						count[i]++;
						addLast(i,j);
						count[i]++;
					}
				    else if(m-l==1)
					{
						addLast(i,j-1);	
						count[i]++;
					}
				}
				
				else
				{
					if(m-l==-1){
					addLast(i,j);
					count[i]++;
					}
				    else if(m-l==1){
					addLast(i,j-1);	
					count[i]++;
					}
				}
			}
			addLast(i,-1);
			count[i]++;
			
		}			
    }
    
    public String toStringUnCompressed()
    {
		
		String s="";
		s=s+Width+" "+Height;

		ListNode temp;
		int p=0;
		int f,i,k,m=Height;
		for(i=0;i<m;i++)
		{	temp=head[i];
			s=s+",";
			for(p=0;p<temp.getElement();p++)
			{
				s=s+" 1";
			}
			for(k=1;k<count[i];k++)
			{
				if(k%2==1 && (temp.getNext()).getElement()!=-1)
				{
					for(f=temp.getElement();f<=(temp.getNext()).getElement();f++)
					{
						s=s+" 0";
					}
					temp=temp.getNext();
				}
				else if(k%2==0 && (temp.getNext()).getElement()!=-1)
				{
					for(f=temp.getElement()+1;f<(temp.getNext()).getElement();f++)
					{
						s=s+" 1";
					}
					temp=temp.getNext();
				}
			}
			for(f=temp.getElement()+1;f<Width;f++)
				{
					s=s+" 1";
				}
		}
		
		return s;
	}
    
    public String toStringCompressed()
    {
		String s="";
		s=s+Width+" "+Height;
		ListNode temp;
		for(int i=0;i<Height;i++)
		{	temp=head[i];
			s=s+","	;
			while(temp.getNext()!=null)
			{
				s=s+" "+temp.getElement();
				temp=temp.getNext();
			}
			s=s+" -1";
			
		}
		return s;
    }

    public static void main(String[] args) {
	// testing all methods here :
    	boolean success = true;

    	// check constructor from file
    	CompressedImageInterface img1 = new LinkedListImage("sampleInputFile.txt");

    	// check toStringCompressed
    	String img1_compressed = img1.toStringCompressed();
    	String img_ans = "16 16, -1, 5 7 -1, 3 7 -1, 2 7 -1, 2 2 6 7 -1, 6 7 -1, 6 7 -1, 4 6 -1, 2 4 -1, 2 3 14 15 -1, 2 2 13 15 -1, 11 13 -1, 11 12 -1, 10 11 -1, 9 10 -1, 7 9 -1";
    	success = success && (img_ans.equals(img1_compressed));

    	if (!success)
    	{
    		System.out.println("Constructor (file) or toStringCompressed ERROR");
    		return;
    	}

    	// check getPixelValue
    	boolean[][] grid = new boolean[16][16];
    	for (int i = 0; i < 16; i++)
    		for (int j = 0; j < 16; j++)
    		{
                try
                {
        			grid[i][j] = img1.getPixelValue(i, j);                
                }
                catch (PixelOutOfBoundException e)
                {
                    System.out.println("Errorrrrrrrr");
                }
    		}

    	// check constructor from grid
    	CompressedImageInterface img2 = new LinkedListImage(grid, 16, 16);
    	String img2_compressed = img2.toStringCompressed();
    	success = success && (img2_compressed.equals(img_ans));

    	if (!success)
    	{
    		System.out.println("Constructor (array) or toStringCompressed ERROR");
    		return;
    	}

    	// check Xor
        try
        {
        	img1.performXor(img2);       
        }
        catch (BoundsMismatchException e)
        {
            System.out.println("Errorrrrrrrr");
        }
    	for (int i = 0; i < 16; i++)
    		for (int j = 0; j < 16; j++)
    		{
                try
                {
        			success = success && (!img1.getPixelValue(i,j));                
                }
                catch (PixelOutOfBoundException e)
                {
                    System.out.println("Errorrrrrrrr");
                }
    		}

    	if (!success)
    	{
    		System.out.println("performXor or getPixelValue ERROR");
    		return;
    	}

    	// check setPixelValue
    	for (int i = 0; i < 16; i++)
        {
            try
            {
    	    	img1.setPixelValue(i, 0, true);            
            }
            catch (PixelOutOfBoundException e)
            {
                System.out.println("Errorrrrrrrr");
            }
        }

    	// check numberOfBlackPixels
    	int[] img1_black = img1.numberOfBlackPixels();
    	success = success && (img1_black.length == 16);
    	for (int i = 0; i < 16 && success; i++)
    		success = success && (img1_black[i] == 15);
    	if (!success)
    	{
    		System.out.println("setPixelValue or numberOfBlackPixels ERROR");
    		return;
    	}

    	// check invert
        img1.invert();
        for (int i = 0; i < 16; i++)
        {
            try
            {
                success = success && !(img1.getPixelValue(i, 0));            
            }
            catch (PixelOutOfBoundException e)
            {
                System.out.println("Errorrrrrrrr");
            }
        }
        if (!success)
        {
            System.out.println("invert or getPixelValue ERROR");
            return;
        }

    	// check Or
        try
        {
            img1.performOr(img2);        
        }
        catch (BoundsMismatchException e)
        {
            System.out.println("Errorrrrrrrr");
        }
        for (int i = 0; i < 16; i++)
            for (int j = 0; j < 16; j++)
            {
                try
                {
                    success = success && img1.getPixelValue(i,j);
                }
                catch (PixelOutOfBoundException e)
                {
                    System.out.println("Errorrrrrrrr");
                }
            }
        if (!success)
        {
            System.out.println("performOr or getPixelValue ERROR");
            return;
        }

        // check And
        try
        {
            img1.performAnd(img2);    
        }
        catch (BoundsMismatchException e)
        {
            System.out.println("Errorrrrrrrr");
        }
        for (int i = 0; i < 16; i++)
            for (int j = 0; j < 16; j++)
            {
                try
                {
                    success = success && (img1.getPixelValue(i,j) == img2.getPixelValue(i,j));             
                }
                catch (PixelOutOfBoundException e)
                {
                    System.out.println("Errorrrrrrrr");
                }
            }
        if (!success)
        {
            System.out.println("performAnd or getPixelValue ERROR");
            return;
        }

    	// check toStringUnCompressed
        String img_ans_uncomp = "16 16, 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1, 1 1 1 1 1 0 0 0 1 1 1 1 1 1 1 1, 1 1 1 0 0 0 0 0 1 1 1 1 1 1 1 1, 1 1 0 0 0 0 0 0 1 1 1 1 1 1 1 1, 1 1 0 1 1 1 0 0 1 1 1 1 1 1 1 1, 1 1 1 1 1 1 0 0 1 1 1 1 1 1 1 1, 1 1 1 1 1 1 0 0 1 1 1 1 1 1 1 1, 1 1 1 1 0 0 0 1 1 1 1 1 1 1 1 1, 1 1 0 0 0 1 1 1 1 1 1 1 1 1 1 1, 1 1 0 0 1 1 1 1 1 1 1 1 1 1 0 0, 1 1 0 1 1 1 1 1 1 1 1 1 1 0 0 0, 1 1 1 1 1 1 1 1 1 1 1 0 0 0 1 1, 1 1 1 1 1 1 1 1 1 1 1 0 0 1 1 1, 1 1 1 1 1 1 1 1 1 1 0 0 1 1 1 1, 1 1 1 1 1 1 1 1 1 0 0 1 1 1 1 1, 1 1 1 1 1 1 1 0 0 0 1 1 1 1 1 1";
        success = success && (img1.toStringUnCompressed().equals(img_ans_uncomp)) && (img2.toStringUnCompressed().equals(img_ans_uncomp));

        if (!success)
        {
            System.out.println("toStringUnCompressed ERROR");
            return;
        }
        else
            System.out.println("ALL TESTS SUCCESSFUL! YAYY!");
    }
}