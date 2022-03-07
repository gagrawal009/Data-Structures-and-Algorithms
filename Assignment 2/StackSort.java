import java.util.Arrays;
import java.lang.reflect.Array;

public class StackSort extends MyStack {
	public StackSort(){

	}
	public String[] sort(int[] nums)throws EmptyStackException{
		//System.out.println("YO");
		//for(int m=0;m<nums.length;m++)
		//	System.out.println(nums[m]);

		MyStack<Integer> m = new MyStack<Integer>();
		String[] sp = new String[2*nums.length];
		//String s1 = new String();
		if(nums.length==1){
			String[] st1 = new String[2];
			st1[0]="PUSH";
			st1[1]="POP";
			return st1;
		}
		int[] nums2 = new int[nums.length];
		m.push(nums[0]);
		int c=0;
		int i=1;
		int j=0;
		sp[c] = "PUSH";
		c++;
		while(i<nums.length){
			if(nums[i]>m.top()){
				nums2[j] = m.top();
				m.pop();
				j++;
				sp[c]="POP";
				c++;
			}
			if(m.isEmpty()==true||nums[i]<=m.top()){
				m.push(nums[i]);
				sp[c] = "PUSH";
				i++;
				c++;
			}
			
		}

		if(j<nums.length){
			while(j<nums.length){
				nums2[j]=m.top();
				m.pop();
				j++;
				sp[c] ="POP";
				c++;
			}
		}
		int flag=0;
		for(int a=0;a<nums.length-1;a++){
			if(nums2[a]>nums2[a+1]){
				flag=1;
				break;
			}
		}
		//String[] sp = s1.split(" ");
		String[] sp1 = new String[1];
		sp1[0]="NOTPOSSIBLE";
		/*for(int z=0;z<nums.length*2;z++){
			System.out.println(sp[z]);
		
		}*/
		//if(flag==1)
		//	System.out.println(sp[0]);
		if(flag==1)
			return sp1;
		return sp;


	}
	public String[][] kSort(int[] nums)throws EmptyStackException{
		String[][] sp = new String[nums.length][2*nums.length];
		int flag=0;
		int i=0;
		int c=0;
		int j=0;
		int k=0;
		while(true){
			flag=0;
			MyStack<Integer> m = new MyStack<Integer>();
			m.push(nums[0]);
			c=0;
			i=1;
			j=0;
			sp[k][c] = "PUSH";
			c++;
			while(i<nums.length){
				if(nums[i]>m.top()){
					nums[j] = m.top();
					m.pop();
					j++;
					sp[k][c]="POP";
					c++;
				}
				if(m.isEmpty()==true||nums[i]<=m.top()){
					m.push(nums[i]);
					sp[k][c] = "PUSH";
					i++;
					c++;
				}
				
			}
			if(j<nums.length){
				while(j<nums.length){
					nums[j]=m.top();
					m.pop();
					j++;
					sp[k][c] ="POP";
					c++;
				}
			}
			for(int a=0;a<nums.length-1;a++){
				if(nums[a]>nums[a+1]){
					flag=1;
					break;
				}
			}
			//for(int b=0;b<nums.length;b++)
			//	System.out.println(nums[b]);
			if(flag==0)
				break;
			if(flag==1){
				//System.out.println("k");
				k++;
			}

		}
		String[][] sp1 = new String[k+1][2*nums.length];
		for(int q=0;q<k+1;q++){
			for(int p=0;p<2*nums.length;p++){
				sp1[q][p]=sp[q][p];
			}
		}
		return sp1;
	}
	/*public static void main(String[] args)throws EmptyStackException{
		StackSort k = new StackSort();
		int[] nums = {1023,5029,158};
		String[][] s1 = k.kSort(nums);
		for(int i=0;i<s1.length;i++){
			for(int j=0;j<s1[0].length;j++){
				System.out.println(s1[i][j]);
			}
		}

	} */
}