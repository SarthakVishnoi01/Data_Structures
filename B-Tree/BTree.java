//package col106.a3;

import java.util.List;
import java.util.Vector;

public class BTree<Key extends Comparable<Key>,Value> implements DuplicateBTree<Key,Value> {
	
	private int height;                           // height of the tree
	private int size;                             //number of key-value pairs
	private Node root;                            //root of the tree
	private static int B;                   //B of B-tree
	private int t=B/2;                            //normal t// limit of children from t-1 to 2t-1 except root
	private int i=0,j=0,k=0,l=0,m=0,n=0,p=0,s=0;  ///Regular use of integers

	private class foo
	{
		private Comparable key;      //The last element of foo ie children[B-1] will not contain key and value at any case
   		private Object val;    //Each next will store the values less than the key and value combined while the last one will store greater than 
		private Node next;    //Pointer to child whoose key-value is less than or equal to current foo
		private Node previous;//Pointer to parent node /// previous of root points to null

		//Constructor
		private foo (Key key, Value val, Node next,Node previous)
		{
			this.key=key;
			this.val=val;
			this.next=next;
			this.previous=previous;
		}
	}
	
	//ArrayList <abc> ejewijewij=new ArrayList<abc>();
	private class Node
	{
		private int number; //number of element
		foo[] element= new foo[B]; // foo is a type of array in which each element will store a key, a value and a reference to next node
		//Constructor
		private Node (int m)
		{
			number=m;
		}
		
	}
	
	
	private int compare(foo a,foo b)//a is the node to be inserted while b is the node which is already present in b tree
	{
		if(a.key.compareTo(b.key)<0) 			//returns 0 if key-value pair equal in both, 1 if node a> node b and -1 if node a <node b 
			return -1;
		else if(a.key.compareTo(b.key)>0)
			return 1;
		else 
			return 0;
		
	}
	
	private int compare_key(foo a, Key b)
	{
		if(a.key.compareTo(b)==0)
			return 0;
		else
			return 1;
	}
    public BTree(int b) throws bNotEvenException 
	{  /* Initializes an empty b-tree. Assume b is even. */
        //throw new RuntimeException("Not Implemented");
		if(b%2!=0)
			throw new bNotEvenException();
		B=b;
		root=new Node(0);
		height=-1;
		size=0;
		t=B/2;
		for(i=0;i<B;i++)
		{
			root.element[i].next=null;
			root.element[i].previous=null;
		}
    }

    @Override
    public boolean isEmpty() {
        //throw new RuntimeException("Not Implemented");
		if(size==0)
			return true;
		else
			return false;
    }

    @Override
    public int size() {
        //throw new RuntimeException("Not Implemented");
		return size;
    }

    @Override
    public int height() {
        //throw new RuntimeException("Not Implemented");
		return height;
    }

    @Override
    public List<Value> search(Key key) throws IllegalKeyException {
        //throw new RuntimeException("Not Implemented");
		if(key==null)
			throw new IllegalKeyException();
		else{
			List v=new Vector<Value>();
			search_key(root,key,v);
			return v;
		}
    }
	/*
	the next of leaf nodes will always be null (hence method to check for leaf nodes)
	the last foo in each node will be empty and only full in case of overload, in that case bring up the the middle element to above node
	*/
	public void search_key(Node node,Key key,List vector)
	{
		
		for(i=0;i<node.number;i++)
		{
			if(compare_key(node.element[i],key)==0)
			{
				vector.add(node.element[i].val);
				if(node.element[i].next!=null)
				{	search_key(node.element[i].next,key,vector);
					search_key(node.element[i+1].next,key,vector);
				}
				else 
					continue;
			}
		}
	}

    @Override
    public void insert(Key key, Value val) {
		size++;
		foo entry=new foo(key,val,null,null);		///See if this new node goes up then will have to do a bit of change in pointers
		foo swap;
		Node temp=null;
		foo temp1=null;
			
		//Addition when root is empty
		if(root.number==0)//empty b tree
		{
			root.element[0]=entry;
			root.number++;
			height=0;
		}
		
		//Addition to root when it is a leaf node and can have more elements in it without overflowing
		else if (height==0 && root.number<B-1) 
		{
			for(i=0;i<root.number;i++)
			{
				p=compare(entry,root.element[i]);
				if(p==0 || p==-1)
				{
					break;
				}
			}
			for(j=root.number-1;j>=i;j--)
			{
				root.element[j+1]=root.element[j];				
			}
			root.element[i]=entry;
			root.number++;
		}
		
		//Addition to root when it is leaf node but addition will cause an overflow
		else if(height==0 && root.number==B-1)  
		{
			for(i=0;i<root.number;i++)
			{
				p=compare(entry,root.element[i]);
				if(p==0 || p==-1)                          ///For tak code copiped so make changes in both
				{
					break;
				}
			}
			for(j=root.number;j>=i;j--)
			{
				root.element[j+1]=root.element[j];				
			}
			root.element[i]=entry;
			root.number++;      //Root is overflowed with 0,1,2.......B-1 entries
			
			//Splitting the root
			Node left_child=new Node(t);
			Node right_child=new Node(t-1);
			Node temp_root=new Node(1);
			temp_root.element[0]=root.element[t];
			temp_root.element[0].next=new Node(t);  //Directly cannot equate to left_child cause then null pointer exception would come 
			temp_root.element[1].next=new Node(t-1);
			for(i=0;i<t;i++)
			{
				left_child.element[i]=root.element[i];
				left_child.element[i].previous=temp_root;
			}
			for(i=0;i<t-1;i++)
			{
				right_child.element[i]=root.element[i+t+1];
				right_child.element[i].previous=temp_root;
			}
			temp_root.element[0].next=left_child;
			temp_root.element[1].next=right_child;
			root=temp_root;
			height++;			
		}
		
		else if(height>0)
		{
			temp=new Node(root.number);
			temp=root;
			for(i=0;i<height;i++,k=0)    //for height 
			{
				for(j=0;j<temp.number;j++)    ///for which foo to enter at a specific height
				{
					p=compare(entry,temp.element[j]);
					if(p==-1 || p==0)
					{
						k=1;
						temp=temp.element[j].next;
						break;
					}
				}
				if(k==0 && j==temp.number)
					temp=temp.element[j].next;
			}
			//We have now entered the leaf node in which we have to enter this foo(entry). ///with temp pointing at this particular node
			
			//Case when there will be no splitting of leaf node
			if(temp.number<B-1)
			{
				for(i=0;i<temp.number;i++)
				{
					p=compare(entry,temp.element[i]);
					if(p==0 || p==-1)
					{
						break;
					}
				}
				Node temp_temp=temp.element[i].previous; //temp_temp contains the parent of this foo
				for(j=temp.number;j>=i;j--)
				{
					temp.element[j+1]=temp.element[j];				
				}
				temp.element[i].key=entry.key;
				temp.element[i].val=entry.val;
				temp.element[i].next=entry.next;
				temp.element[i].previous=temp_temp;
				temp.number++;
			}
			
			//Case when we will have to split the leaf node
			else if(temp.number==B-1)
			{
				for(i=0;i<temp.number;i++)
				{
					p=compare(entry,temp.element[i]);
					if(p==0 || p==-1)
					{
						break;
					}
				}
				Node temp_temp=temp.element[i].previous; //temp_temp contains the parent of this foo
				for(j=temp.number;j>=i;j--)
				{
					temp.element[j+1]=temp.element[j];				
				}
				temp.element[i].key=entry.key;
				temp.element[i].val=entry.val;
				temp.element[i].next=entry.next;
				temp.element[i].previous=temp_temp;
				temp.number++;
				
				//case of splitting the nodes begins
				
				for(i=height,k=0;i>0;i++)
				{
					if(temp.number<B-1)
					{
						k=1;
						break;
						
					}
					else
					{	
						Node temp_previous=temp.element[0].previous;
						Node left_child=new Node(t);
						Node right_child=new Node(t-1);
						foo dummy=temp.element[t]; ///The foo which needs to go up
						for(i=0;i<t;i++)
						{
							left_child.element[i]=temp.element[i];
						}	
						for(i=0;i<t-1;i++)
						{
							right_child.element[i]=temp.element[i+t+1];
						}
					
						//updating temp_previous
						for(i=0;i<temp_previous.number;i++)
						{
							p=compare(dummy,temp_previous.element[i]);
							if(p==0 || p==-1)                          ///For tak code copiped so make changes in both
							{
								break;
							}
						}
						Node temp_previous_temp=temp_previous.element[i].previous;
						for(j=temp_previous.number;j>i;j--)
						{
							temp_previous.element[j+1]=temp_previous.element[j];				
						}
					
						//Updating parent node due to splitting
						temp_previous.element[i+1].key=temp_previous.element[i].key;
						temp_previous.element[i+1].val=temp_previous.element[i].val;
						temp_previous.element[i+1].next=right_child;
						temp_previous.element[i+1].previous=temp_previous_temp;
						
						
						temp_previous.element[i].key=dummy.key;
						temp_previous.element[i].val=dummy.val;
						temp_previous.element[i].next=left_child;
						temp_previous.element[i].previous=temp_previous_temp;
						
						temp_previous.number++;
						
						//Updating the pointers
						temp=temp.element[0].previous;
					}	
					
				}
				//Now we just need to check the root.
				if(k==0 && root.number==B)//means an element has been added to the root
				{
					foo dummy= root.element[t];
					Node left_child=new Node(t);
					Node right_child=new Node(t-1);
					Node temp_root=new Node(1);
					temp_root.element[0]=root.element[t];
					temp_root.element[0].next=new Node(t);  //Directly cannot equate to left_child cause then null pointer exception would come 
					temp_root.element[1].next=new Node(t-1);
					for(i=0;i<t;i++)
					{
						left_child.element[i]=root.element[i];
						left_child.element[i].previous=temp_root;
					}
					for(i=0;i<t-1;i++)
					{
						right_child.element[i]=root.element[i+t+1];
						right_child.element[i].previous=temp_root;
					}
					temp_root.element[0].next=left_child;
					temp_root.element[1].next=right_child;
					root=temp_root;
					height++;			
				}
			}
		}
	}	
	
    @Override
    public void delete(Key key) throws IllegalKeyException {
		
        throw new RuntimeException("Not Implemented");
    }
	
	@Override
	public String toString()
	{
		String s="";
		s=inorder(root,s);
		return s;
	}
	//Make a recursive method for inorder traversal
	private String inorder(Node node, String str)
	{
		
		if(node.element[0].next!=null)
		{
			str=str+"[";
			for(int i=0;i<node.number;i++)
			{
				str=str+inorder(node.element[i].next, str);
				str=str+", "+node.element[i].key + "=" + node.element[i].val + ", ";
			}
			str=str+inorder(node.element[i].next,str);
			str=str+ "]";
		}
		else
		{
			str=str+ "[";
			for(j=0;j<node.number-1;j++)
			{
				str=str+node.element[j].key + "=" + node.element[j].val + ", ";
			}
			str=str+node.element[j].key + "=" + node.element[j].val + "]";
		}
		return str;
	}
}
