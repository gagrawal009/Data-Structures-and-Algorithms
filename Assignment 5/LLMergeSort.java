package col106.assignment5;

import java.util.Comparator;

/*
Implementation of MergeSort Algorithm :
    1. you get linked list of size <=1 you just return the list as it is already sorted
    2. Find mid node using findSplit method(Don't forget to add mid element to globalList before returning it)
    3. Create two LinkedList lt (with head = head and tail = mid) and rt (with head = mid.next and tail = tail)
    4. Now recursively MergSort lt and rt Linked lists(Maintain this order)
    5. Now merge these two lists that we got from recursive calls using given crieteria for ordering
    6. Return merged Linked list
*/
@SuppressWarnings("unchecked")
public class LLMergeSort <T>  {

  LinkedList<T>  globalList = new LinkedList<T>();

  //CALL THIS METHOD AFTER EVERY CALL OF findSplit and DO NOT MODIFY THIS METHOD
  public void adjustGlobalPointer(T node){
      globalList.add(node);
  }

  // Utility function to get the middle of the linked list
  public Node<T> findSplit(LinkedList<T>  lst) {
    Node<T> middle = lst.getHead();

    int n = lst.getSize();

    for (int i=0; i<n/2 - 1; i++) {
      middle = middle.next;
    }
    if (n%2!=0) middle = middle.next;

    adjustGlobalPointer(middle.getData());
    return middle;
  }

  public LinkedList<T>  merge(LinkedList<T>  lt, LinkedList<T>  rt, Comparator c) {

     int l = lt.getSize();
     int r = rt.getSize();

     LinkedList<T> res = new LinkedList<T>();
     int i=0;
     int j=0;

     Node<T> left = lt.getHead();
     Node<T> right = rt.getHead();
     Node<T> temp = null;

     while (i<l && j<r) {

      if (c.compare(left, right) < 0) {
        if (res.getHead()==null) {
          Node<T> head = new Node<T> (left.getData());
          res.add(head.getData());
          temp = head;
        }
        else {
          Node<T> created = new Node<T> (left.getData());
          res.add(created.getData());
          temp.next = created;
          temp = temp.next;
        }
      left = left.next;
      i++;
      }

      else {

        if (res.getHead()==null) {
          Node<T> head = new Node<T> (right.getData());
          res.add(head.getData());
          temp = head;
        }
        else {
          Node<T> created = new Node<T> (right.getData());
          res.add(created.getData());
          temp.next = created;
          temp = temp.next;

        }
      right = right.next;
      j++;
      }

    }

    while (i<l) {
      Node<T> created = new Node<T> (left.getData());
      res.add(created.getData());
      temp.next = created;
      temp = temp.next;
      left = left.next;
      i++;
    }

    while (j<r) {
      Node<T> created = new Node<T> (right.getData());
      res.add(created.getData());
      temp.next = created;
      temp = temp.next;
      right = right.next;
      j++;
    }

    return res;
  }


  public LinkedList<T>  MergeSort(LinkedList<T>  lst, Comparator c) {
    //Recursively Apply MergeSort, by calling function findSplit(..) to find middle node to split
    //Enter your code here
    if (lst.getHead()==null || lst.getHead().next==null) return lst;

    Node middle = findSplit(lst);
    Node<T> temp = lst.getHead();

    LinkedList<T> lt = new LinkedList<T>();
    LinkedList<T> rt = new LinkedList<T>();

    while (temp!=middle.next) {
      lt.add(temp.getData());
      temp = temp.next;
    }

    while (temp!=null) {
      rt.add(temp.getData());
      temp=temp.next;
    }

    LinkedList<T> left = MergeSort(lt, c);
    LinkedList<T> right= MergeSort(rt, c);

    lst = merge(left, right, c);

    return lst;
  }

  //DO NOT CALL OR MODIFY THESE METHODS IN YOUR CALL THIS IS FOR USE IN DRIVER CODE
  public LinkedList<T> getGlobalList() {
    return this.globalList;
  }

  public void clearGlobalList(){
    globalList  = new LinkedList<>();
  }

}
