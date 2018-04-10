class ListNode 
	{
		private int element;
		private ListNode next;
		
		public ListNode (int item, ListNode n)
		{
			element=item;
			next=n;
		}
		public ListNode(int item)
		{
			this(item,null);
		}
		public ListNode getNext(){
		return next;
		}
		
		public int getElement(){
		return element;
		}
		
		public void setNext(ListNode n){
		next=n;
		}
	}