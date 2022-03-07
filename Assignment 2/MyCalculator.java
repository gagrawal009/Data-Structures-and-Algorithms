import java.util.Arrays;
import java.lang.reflect.Array;

public class MyCalculator extends MyStack{
	public MyCalculator(){
	}
	public int calc(MyStack<String> m3, MyStack<String> m2)throws EmptyStackException{
		int s3=0;
		try{
		m3.push(m2.top());
		m2.pop();
		while(m2.isEmpty()==false||!(m2.top().equals(")"))){
			if(m2.top().equals("*")){
				m2.pop();
				int p = Integer.parseInt(m3.top());
				m3.pop();
				int q = Integer.parseInt(m2.top());
				m2.pop();
				m3.push(Integer.toString(p*q));
			}
			if(m2.isEmpty()==true)
				break;
			if(m2.top().equals(")")){
				m2.pop();
				break;
			}
			if(m2.top().equals("+")){
				m3.push(m2.top());
				m2.pop();
				m3.push(m2.top());
				m2.pop();
			}
			if(m2.isEmpty()==true)
				break;
			if(m2.top().equals(")")){
				m2.pop();
				break;
			}
			if(m2.top().equals("-")){
				m3.push("+");
				String sa = m2.top();
				m2.pop();
				m3.push(m2.top());
				String sb = m2.top();
				m2.pop();
				m3.push(sa+sb);
			}
			if(m2.isEmpty()==true)
				break;
			if(m2.top().equals(")")){
				m2.pop();
				break;
			}
			
		}
		String s1 = m3.top();
		m3.pop();
		
		while(m3.isEmpty()==false){
			if(m3.top().equals("+")){
				m3.pop();
				int a = Integer.parseInt(m3.top());
				m3.pop();
				s3 = s3 + a;
				m3.push(Integer.toString(s3));
			}
			m3.pop();
		}
		int s2 = Integer.parseInt(s1);
		s3 = s3+s2;
		}catch(Exception e){

		}
		return s3;
	}
	public int calculate(String expression)throws EmptyStackException{
		int sum1=0;
		try{String exp = expression.replace(" ","");
		//System.out.println(exp);
		int n = exp.length();
		MyStack<String> m1 = new MyStack<String>();
		int i=0;
		while(i<n){
			if(exp.charAt(i)=='+'||exp.charAt(i)=='-'||exp.charAt(i)=='*'||exp.charAt(i)=='('||exp.charAt(i)==')'){
				m1.push(exp.substring(i,i+1));
				//System.out.println(m1.top());
				i++;
			}else{
				int cnt = 0;

				while((i<n)&&!(exp.charAt(i)=='+'||exp.charAt(i)=='-'||exp.charAt(i)=='*'||exp.charAt(i)=='('||exp.charAt(i)==')')){
					cnt++;
					i++;
				}
				m1.push(exp.substring(i-cnt,i));

				//System.out.println(m1.top());
			}
		}
		if(m1.size==1)
			return Integer.parseInt(m1.top());
		MyStack<String> m2 = new MyStack<String>();
		MyStack<String> m3 = new MyStack<String>();
		
		while(m1.isEmpty()==false||m2.size>1){
			if(m1.isEmpty()==false){
			while(!m1.top().equals("(")){
				m2.push(m1.top());
				//System.out.println(m2.top());
				m1.pop();
				if(m1.isEmpty()==true)
					break;
			}}
			if(m1.isEmpty()==false&&m1.top().equals("("))
				m1.pop();
			sum1 = calc(m3,m2);
			//System.out.println(sum1);
			m2.push(Integer.toString(sum1));
			//System.out.println(m2.size);
			//if(m2.size>1)
				//m1.push("(");
		}
	}
		
		catch(Exception e){
			
		}
		return sum1;
	}
	/*public static void main(String args[])throws Exception{
		MyCalculator m3 = new MyCalculator();
		System.out.println(m3.calculate("((4+(1))*(3+(2))*9-(3))"));
	}*/
}