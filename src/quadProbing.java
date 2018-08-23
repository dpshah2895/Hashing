/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.*;
import java.util.*;
import java.lang.management.ThreadMXBean;
import java.util.*;
import java.lang.management.ManagementFactory;


/**
 *
 * @author Darshan
 */

class Node
    {
        int key;
        int value;
        Node(int k, int v)
        {
            key=k;
            value=v;
        }
        Node(){
            
        }
    }
class MyHashMapQ
    {
        //assuming 11 as the N value initially
        Node myTable[];
        int cap;
        int size;
        double loadFactor;
        int collisions;
        MyHashMapQ()
        {
            cap=100;
            size=0;
            loadFactor=0;
            myTable=new Node[cap];
            
          for(int i=0;i<cap;i++)
            {
                
                myTable[i] = null;
                //System.out.println("node is:"+myTable[i].toString());
            }
        }
        
        int hashFunction(int inpKey)
        {
            return (inpKey % cap);
        }
        
        double getLoadFactorValue()
        {
            return (double)size/(double)cap;
        }
        
        void insert(int k, int v)
        {
            Node temp=new Node(k,v);
            int location = hashFunction(k);
            int tempVal=location;
            int count=1;
            if(myTable[location]!=null)
            {
                //System.out.println("Collision found for key: "+k);
                collisions++;
                
            }
            while(myTable[location]!=null && myTable[location].key!=k && myTable[location].key!=-1)
            {
                
                location = tempVal + (count * count) ;
                location = location%cap;
                count++;
            }
            
            if(myTable[location]==null || myTable[location].key==-1)
            {
               // System.out.println("Incrementing size"+countSize++);
                size++;
                myTable[location]=temp;
            }
            else
            {
               // System.out.println("Missed incrementing size for key"+k);
                myTable[location]=temp;
                        
            }
    
           
        }
        
        void printMap()
        {
            for(int i=0;i<cap;i++)
            {
                if(myTable[i]!=null)
                {
                    System.out.println("Key = "+myTable[i].key+" At location"+i);
                }
            }
        }

        
        void delete(int k)
        {
            int count=1;
            int flag=0;
            Node tempDel = new Node(-1,-1);
            int location = hashFunction(k);
            int temp=location;
            while(myTable[location]!=null)
            {
                if(myTable[location].key == k)
                {
                    myTable[location] = tempDel;
                    size--;
                    flag=1;
                    break;
                }
                
                location = temp + (count*count);
                location = location % cap;
                count++;
            }
            if(flag==0)
           System.out.println("Key to be deleted is not found");
          
        }
        
        int search(int k)
        {
            int count=1;
            int location = hashFunction(k);
            int temp=location;
            while(myTable[location]!=null)
            {
                if(myTable[location].key == k)
                {
                    return myTable[location].value;
                }
                
                location = temp + (count*count);
                location = location % cap;
            }
            //if key is not found return -1
            return -1; 
        }
        
        void resize()
        {
        
        Node myTempTable[]=new Node[myTable.length];
        for(int i=0;i<myTempTable.length;i++)
        {
            myTempTable[i]=myTable[i];
        }
        cap*=2;
        myTable=new Node[cap];
        for(int i=0;i<cap;i++)
            {
                
                myTable[i] = null;
                //System.out.println("node is:"+myTable[i].toString());
            }
        for(int i=0;i<myTempTable.length;i++)
        {
            insert(myTempTable[i].key, myTempTable[i].value);
        }
        
        }
        
        void resizeDel()
        {
        int x=0;
        Node myTempTable[]=new Node[myTable.length/2];
        for(int i=0;i<myTempTable.length&&x<myTable.length;x++)
        {
            if(myTable[x]!=null)
            myTempTable[i++]=myTable[x];
        }
        
        myTable=new Node[cap/2];
        for(int i=0;i<myTempTable.length;i++)
        {
            myTable[i]=myTempTable[i];
        }
        cap = cap/2;
        }
    }
    

public class quadProbing {
    public static long getUserTime( ) {
           ThreadMXBean bean = ManagementFactory.getThreadMXBean( );
	    return bean.isCurrentThreadCpuTimeSupported( ) ?
	        bean.getCurrentThreadUserTime( ) : 0L;
	}
    
       public static void main(String[] args) {
        // TODO code application logic here
        Scanner sc=new Scanner(System.in);
        Random rn=new Random();
        MyHashMapQ hMap=new MyHashMapQ(); 
       // hMap.printMap();
         for(int i=0;i<(0.75*hMap.cap);i++)
        {
            int inp = rn.nextInt(100)+0;
          //  System.out.println("key to be inserted is"+inp);
            hMap.insert(inp, rn.nextInt(1000)+10);
        }
        System.out.println("No of collisions for load factor 0.75 is"+hMap.collisions);
      //  System.out.println("After inserting 75 values size is"+hMap.size);
     //   System.out.println("After inserting 75 values capacity is"+hMap.cap);
        
     //   System.out.println("No of collisions for load factor 0.75 is"+hMap.collisions);
        
        for(int i=0;i<75;i++)
        {
          //  System.out.println("Size is:"+hMap.size+" and capacity is:"+hMap.cap);
            if(hMap.size == hMap.cap)
            {
                //means load factor is 1 and now resizing should happen
                System.out.println("Load factor is 1 now and so resizing the Hash Table...");
                hMap.resize();
                System.out.println("New capacity is"+hMap.myTable.length);
            }
            hMap.insert(rn.nextInt(100)+0, rn.nextInt(1000)+10);
           
        }
        
        for(int i=0;i<5;i++)
        {
          //  System.out.println("Size is:"+hMap.size+" and capacity is:"+hMap.cap);
            if(hMap.size <= hMap.cap/4)
            {
                //means load factor is <0.25 and now resizing should happen
                System.out.println("Load factor is 1ow now and so resizing the Hash Table...");
                hMap.resizeDel();
                System.out.println("New capacity is"+hMap.myTable.length);
            }
            hMap.delete(rn.nextInt(100)+0);
           
        }
        
        System.out.println("Current size"+hMap.size);
        System.out.println("Current capacity"+hMap.cap);
        System.out.println("Current load factor value is:"+hMap.getLoadFactorValue());
        
    }
}
    
   
    
 
    

