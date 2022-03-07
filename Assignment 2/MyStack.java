import java.util.Arrays;
import java.lang.reflect.Array;

class EmptyStackException extends Exception{
	public EmptyStackException(String s){
		super(s);
	}
}
public class MyStack<T>{
	//public int[] arr;
	public T[] arr;
	public int size;
	//public int size;
	public MyStack(){
		this.arr =(T[]) new Object[10];
		//this.arr = new int[100];
		this.size = 0;
	}
	public void push(T n){
		if(this.size<this.arr.length){
			this.arr[size]=n;
			this.size++;
		}else{
			T[] newarr = (T[]) new Object[2*size];
			for(int i=0;i<this.arr.length;i++){
				newarr[i]=this.arr[i];
			}
			newarr[size] = n;
			size++;
			this.arr = newarr;
		}
		
	}
	public T pop()throws EmptyStackException{
		
		if(this.size!=0){
			T a = this.top();
			this.size--;
			return a;
			
		}else{
			throw new EmptyStackException("EmptyStack");
		}
	}
	public T top()throws EmptyStackException{
		if(this.size!=0){
			//@SuppressWarnings("unchecked")
			T a = (T)arr[this.size-1];
			return a;
		}else{
			throw new EmptyStackException("EmptyStack");
		}
	}
	public boolean isEmpty(){
		if(this.size==0)
			return true;
		return false;
	}
	/*public static void main(String args[]) throws Exception{
		MyStack<String> m = new MyStack<String>();
		try{
			m.push("Kushu");
			System.out.println(m.pop());
			System.out.println(m.isEmpty());
			System.out.println(m.top());
		}catch( EmptyStackException e){
			System.out.println(e);
		}
	}*/
}