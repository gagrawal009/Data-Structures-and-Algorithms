package col106.assignment5;

import java.util.Comparator;

@SuppressWarnings("unchecked")
public class StockMgmt implements StockMgmtInterface {
	//!!!!!!!*********DON'T CREATE YOUR OWN OBJECTS OF LLMergeSort*********!!!!!!!
	//use these only this object to sort
	LLMergeSort mergeSortobj;

	LinkedList<CategoryNode> categories;

	//DO NOT MNODIFY THIS CONSTRUCTOR
	public StockMgmt() {

		mergeSortobj = new LLMergeSort();
		categories = new LinkedList<CategoryNode>();

		categories.add(new CategoryNode(1, "mobile"));
		categories.add(new CategoryNode(2, "utensils"));
		categories.add(new CategoryNode(3, "sanitary"));
		categories.add(new CategoryNode(4, "medicalEquipment"));
		categories.add(new CategoryNode(5, "clothes"));

	}

	public void addItem(int categoryId, int itemId, String itemName, int stock) {
		//Your code goes here
		int i=0;
		Node<CategoryNode> main_head = categories.getHead();

		for (i=1; i<categoryId; i++) {
			main_head = main_head.next;
		}

		//Node<ItemNode> sub_head = main_head.data.itemList.getHead();

		ItemNode created_item = new ItemNode(itemId, itemName, stock);

		Node<ItemNode> created = new Node<ItemNode> (created_item);
		main_head.data.itemList.add(created.getData());

	}

	public void addItemTransaction(int categoryId, int itemId, String itemName, int numItemPurchased, int day, int month, int year) {
		//Your code goes here
        int i=0;
		Node<CategoryNode> main_head = categories.getHead();

		for (i=1; i<categoryId; i++) {
			main_head = main_head.next;
		}

		Node<ItemNode> sub_head = main_head.getData().itemList.getHead();

		while (sub_head.getData().itemId != itemId) {
			sub_head = sub_head.next;
		}

		PurchaseNode created_purch = new PurchaseNode(numItemPurchased, day, month, year);
		sub_head.getData().addTransaction(created_purch);

		Node<PurchaseNode> purch = new Node<PurchaseNode>(created_purch);

		sub_head.getData().purchaseList.add(purch.getData());

	}

	//Query 1
	public LinkedList<ItemNode> sortByLastDate() {
		//Your code goes here
		LinkedList<ItemNode> lst = new LinkedList<ItemNode>();

		Node<CategoryNode> main_head = categories.getHead();

		while(main_head!=null) {
			Node<ItemNode> sub_head = main_head.getData().itemList.getHead();

			while(sub_head!=null) {
				if (sub_head.getData().purchaseList.getHead() == null) {
					PurchaseNode dummy_purch = new PurchaseNode(0, 1, 8, 1970);
					Node<PurchaseNode> dummy = new Node<PurchaseNode>(dummy_purch);
					sub_head.getData().purchaseList.add(dummy.getData());
				}
				sub_head.getData().purchaseList = mergeSortobj.MergeSort(sub_head.getData().purchaseList, new purch_date());
                mergeSortobj.clearGlobalList();
				lst.add(sub_head.getData());
				sub_head = sub_head.next;
			}
			main_head = main_head.next;
		}

		LinkedList<ItemNode> res = mergeSortobj.MergeSort(lst, new Query_1());
		return res;
	}

	public int compare_date(int one_d, int one_m, int one_y, DateNode second) {
		int two_d = second.getDay();
		int two_m = second.getMonth();
		int two_y = second.getYear();

		if (one_y< two_y) return -1;
		if (one_y> two_y) return 1;

		if (one_y==two_y) {

			if (one_m< two_m) return -1;
			if (one_m> two_m) return 1;

			if (one_m==two_m) {
				if (one_d< two_d) return -1;
				if (one_d> two_d) return 1;

				if (one_d==two_d) return 0;
			}

		}
		return 0;
	}

	public void get_nums(int day1, int month1, int year1, int day2, int month2, int year2,Node<ItemNode> sub_head) {

		Node<PurchaseNode> temp = sub_head.getData().purchaseList.getHead();
		int first_purch=0;
		int last_purch =0;
		float total=0;
		boolean flag = true;

		while(temp!=null) {
			DateNode second = temp.getData().getDate();

			if(compare_date(day1, month1, year1, second)!=1 && compare_date(day2, month2, year2, second)!=-1) {

				if (flag) {
					first_purch = temp.getData().getDate().getYear();
					flag=false;
				}
				last_purch = temp.getData().getDate().getYear();
				total = total + temp.getData().numItemsPurchased();
			}
			temp=temp.next;

			// if(compare_date(day2, month2, year2, second)!=1) {
			// 	last_purch = temp.getData().getDate().getYear();
			// 	flag=False;
			// 	break;
			// }

			// if(flag){
			// 	total = total + temp.getData().numItemsPurchased();
			// }
		}
		sub_head.getData().numPeriod = total/(last_purch-first_purch+1);
	}

	//Query 2
	public LinkedList<ItemNode> sortByPurchasePeriod(int day1, int month1, int year1, int day2, int month2, int year2) {
		//Your code goes here

		LinkedList<ItemNode> lst = new LinkedList<ItemNode>();

		Node<CategoryNode> main_head = categories.getHead();

		while(main_head!=null) {
			Node<ItemNode> sub_head = main_head.data.itemList.getHead();

			while(sub_head!=null) {
				if (sub_head.getData().purchaseList.getHead() == null) {
					PurchaseNode dummy_purch = new PurchaseNode(0, 1, 8, 1970);
					Node<PurchaseNode> dummy = new Node<PurchaseNode>(dummy_purch);
					sub_head.getData().purchaseList.add(dummy.getData());
				}
				sub_head.getData().purchaseList = mergeSortobj.MergeSort(sub_head.getData().purchaseList, new purch_date());
                mergeSortobj.clearGlobalList();
				get_nums(day1, month1, year1, day2, month2, year2, sub_head);
				lst.add(sub_head.getData());
				sub_head = sub_head.next;
			}
			main_head = main_head.next;
		}

		LinkedList<ItemNode> res = mergeSortobj.MergeSort(lst, new Query_2());
		return res;

	}

	//Query 3
	public LinkedList<ItemNode> sortByStockForCategory(int category) {
		//Your code goes here

		int i=0;
		Node<CategoryNode> main_head = categories.getHead();

		for (i=1; i<category; i++) {
			main_head = main_head.next;
		}

		LinkedList<ItemNode> res = mergeSortobj.MergeSort(main_head.getData().itemList, new Query_3());
		return res;
	}

	//Query 4
	public LinkedList<ItemNode> filterByCategorySortByDate(int category) {
		//Your code goes here

		LinkedList<ItemNode> lst = new LinkedList<ItemNode>();

		int i=0;
		Node<CategoryNode> main_head = categories.getHead();

		for (i=1; i<category; i++) {
			main_head = main_head.next;
		}

		Node<ItemNode> sub_head = main_head.getData().itemList.getHead();

		while(sub_head!=null) {
			if (sub_head.getData().purchaseList.getHead() == null) {
				PurchaseNode dummy_purch = new PurchaseNode(0, 1, 8, 1970);
				Node<PurchaseNode> dummy = new Node<PurchaseNode>(dummy_purch);
				sub_head.getData().purchaseList.add(dummy.getData());
			}
			sub_head.getData().purchaseList = mergeSortobj.MergeSort(sub_head.getData().purchaseList, new purch_date());
            mergeSortobj.clearGlobalList();
			lst.add(sub_head.getData());
			sub_head = sub_head.next;
		}


		LinkedList<ItemNode> res = mergeSortobj.MergeSort(lst, new Query_4());
		return res;

	}


	//!!!!!*****DO NOT MODIFY THIS METHOD*****!!!!!//
	public LinkedList<ItemNode> checkMergeSort() {
		LinkedList<ItemNode> retLst = mergeSortobj.getGlobalList();
		mergeSortobj.clearGlobalList();
		return retLst;
	}



    // public static void main(String[] args) {

    //     StockMgmt obj = new StockMgmt();
    //     obj.addItem(2, 2,"item22",100);
    //     obj.addItem(1, 1, "item11", 100);
    //     obj.addItem(3, 3, "item33", 500);
    //     obj.addItem(4, 4, "item44", 100);
    //     obj.addItem(5, 5, "item55", 300);

    //     obj.addItemTransaction(1, 1, "item11", 10, 10, 3, 2000);
    //     obj.addItemTransaction(2, 2, "item22", 10, 10, 3, 2000);
    //     obj.addItemTransaction(3, 3, "item33", 10, 10, 3, 2000);
    //     obj.addItemTransaction(4, 4, "item44", 10, 10, 3, 2000);
    //     obj.addItemTransaction(5, 5, "item55", 10, 10, 3, 2000);

    //     LinkedList<ItemNode> res = obj.sortByLastDate();
    //     Node<ItemNode> head = res.getHead();

    //     while(head!=null) {
    //         System.out.println(head.getData().itemName);
    //         head=head.next;
    //     }

    //     LinkedList<ItemNode> glo = obj.checkMergeSort();

    //     Node<ItemNode> head2 = glo.getHead();

    //     while(head2!=null) {
    //         System.out.println(head2.getData().itemName);
    //         head2=head2.next;
    //     }

    // }
}

class purch_date implements Comparator<Node<PurchaseNode>> {

	public int compare_date(DateNode first, DateNode second) {

    int one_d = first.getDay();
    int one_m = first.getMonth();
    int one_y = first.getYear();

    int two_d = second.getDay();
    int two_m = second.getMonth();
    int two_y = second.getYear();

    if (one_y< two_y) return -1;
    if (one_y> two_y) return 1;

    if (one_y==two_y) {

      if (one_m< two_m) return -1;
      if (one_m> two_m) return 1;

      if (one_m==two_m) {
        if (one_d< two_d) return -1;
        if (one_d> two_d) return 1;

        if (one_d==two_d) return 0;
      }
    }
    return 0;
  }

	public int compare (Node<PurchaseNode> t1, Node<PurchaseNode> t2) {

		DateNode first = t1.getData().getDate();
     	DateNode second = t2.getData().getDate();

     	return compare_date(first, second);

	}
}

class Query_1 implements Comparator<Node<ItemNode>> {

	public int compare_date(DateNode first, DateNode second) {

    int one_d = first.getDay();
    int one_m = first.getMonth();
    int one_y = first.getYear();

    int two_d = second.getDay();
    int two_m = second.getMonth();
    int two_y = second.getYear();

    if (one_y< two_y) return -1;
    if (one_y> two_y) return 1;

    if (one_y==two_y) {

      if (one_m< two_m) return -1;
      if (one_m> two_m) return 1;

      if (one_m==two_m) {
        if (one_d< two_d) return -1;
        if (one_d> two_d) return 1;

        if (one_d==two_d) return 0;
      }
    }
    return 0;
  }

	public int compare (Node<ItemNode> t1, Node<ItemNode> t2) {
		DateNode first = t1.getData().purchaseList.getTail().getData().getDate();
     	DateNode second = t2.getData().purchaseList.getTail().getData().getDate();

     	int date_compared = compare_date(first, second);

     	if (date_compared!=0) return date_compared;

     	else {
       		if (t1.getData().itemName.compareTo(t2.getData().itemName)>0) return -1;
     		else return 1;
    	}
    	//return 0;
	}

}

class Query_2 implements Comparator<Node<ItemNode>> {

	public int compare (Node<ItemNode> t1, Node<ItemNode> t2) {
		float p1 = t1.getData().numPeriod;
      	float p2 = t2.getData().numPeriod;

      	if (p1<p2) return -1;
      	else if(p1>p2) return 1;
      	else {
        	if (t1.getData().itemName.compareTo(t2.getData().itemName)>0) return -1;
        	else return 1;
      	}
      	//return 0;
	}

}

class Query_3 implements Comparator<Node<ItemNode>> {

	public int compare (Node<ItemNode> t1, Node<ItemNode> t2) {
		int s1 = t1.getData().stock;
      	int s2 = t2.getData().stock;

	    if (s1<s2) return 1;
    	else if(s1>s2) return -1;

      	else {
        	if (t1.getData().itemName.compareTo(t2.getData().itemName)>0) return -1;
        	else return 1;
      	}
      	//return 0;
	}

}

class Query_4 implements Comparator<Node<ItemNode>> {

	public int compare_date(DateNode first, DateNode second) {

    int one_d = first.getDay();
    int one_m = first.getMonth();
    int one_y = first.getYear();

    int two_d = second.getDay();
    int two_m = second.getMonth();
    int two_y = second.getYear();

    if (one_y< two_y) return -1;
    if (one_y> two_y) return 1;

    if (one_y==two_y) {

      if (one_m< two_m) return -1;
      if (one_m> two_m) return 1;

      if (one_m==two_m) {
        if (one_d< two_d) return -1;
        if (one_d> two_d) return 1;

        if (one_d==two_d) return 0;
      }
    }
    return 0;
  }

	public int compare (Node<ItemNode> t1, Node<ItemNode> t2) {

		DateNode first = t1.getData().purchaseList.getTail().getData().getDate();
      	DateNode second = t2.getData().purchaseList.getTail().getData().getDate();

     	int date_compared = compare_date(first, second);
      	if (date_compared==1) return -1;
      	if (date_compared==-1) return 1;

      	if (date_compared==0) {
        	if (t1.getData().itemName.compareTo(t2.getData().itemName)>0) return -1;
        	else return 1;
      	}
      	return 0;
	}

}
