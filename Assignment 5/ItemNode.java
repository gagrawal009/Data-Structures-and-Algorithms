package col106.assignment5;

@SuppressWarnings("unchecked")
public class ItemNode implements ItemInterface{

	int itemId;
	String itemName;
	int stock;
	LinkedList<PurchaseNode> purchaseList;
	float numPeriod;

	public ItemNode(int itemId, String itemName, int stock){
		this.itemId = itemId;
		this.itemName = itemName;
		this.stock = stock;
		this.purchaseList = new LinkedList<PurchaseNode>();
		this.numPeriod=0;
	}

	public int getItemId(){
		//Enter your code here
		return this.itemId;
	}

	public  String getItemName(){
		//Enter your code here
		return this.itemName;
	}

	public int getStockLeft(){
		//Enter your code here
		return this.stock;
	}

	public void addTransaction(PurchaseNode purchaseT){
		//Enter your code here
		this.stock = this.stock - purchaseT.numItemPurchased;
	}

	public Node<PurchaseNode> getPurchaseHead(){
		//Enter your code here

		Node<PurchaseNode> res = purchaseList.getHead();
		return res;
	}

}
