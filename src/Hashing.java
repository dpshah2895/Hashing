/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//import static Chaining.getUserTime;
import java.util.*;
import java.util.*;
import java.lang.management.ThreadMXBean;
import java.util.*;
import java.lang.management.ManagementFactory;


/**
 *
 * @author Darshan
 */

class NodeL
    {
        int key;
        int value;
        NodeL(int k, int v)
        {
            key=k;
            value=v;
        }
        NodeL(){
            
        }
    }
class MyHashMap
    {
        //assuming 11 as the N value initially
        Node myTable[];
        int cap;
        int size;
        double loadFactor;
        int collisions;
        MyHashMap()
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
            if(myTable[location]!=null)
            {
                collisions++;
                
            }
            while(myTable[location]!=null && myTable[location].key!=k && myTable[location].key!=-1)
            {
                location++;
                location = location%cap;
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
            int flag=0;
            Node tempDel = new Node(-1,-1);
            int location = hashFunction(k);
            while(myTable[location]!=null)
            {
                if(myTable[location].key == k)
                {
                    System.out.println("Deleting key"+k);
                    myTable[location] = tempDel;
                    size--;
                    flag=1;
                    break;
                }
                
                location++;
                location = location % cap;
            }
            if(flag==0)
           System.out.println("Key to be deleted is not found");
          
        }
        
        int search(int k)
        {
            int count=0;
            int location = hashFunction(k);
            while(myTable[location]!=null && count!=myTable.length)
            {
                if(myTable[location].key == k )
                {
                    return myTable[location].value;
                }
                location++;
                location = location % cap;
                count++;
            }
            //if key is not found return -1
            System.out.println("Key not found");
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
        collisions=0;
        for(int i=0;i<myTempTable.length;i++)
        {
            if(myTempTable[i].key!=-1)
            insert(myTempTable[i].key,myTempTable[i].value);
        }
        size-=(cap/2);
      //  collisions=temp;
        }
        
          
        void resizeDel()
        {
        Node myTempTable[]=new Node[myTable.length];
        for(int i=0;i<myTempTable.length;i++)
        {
            myTempTable[i]=myTable[i];
        }
        cap=cap/2;
        myTable=new Node[cap];
         for(int i=0;i<cap;i++)
            {
                
                myTable[i] = null;
            }
        for(int i=0;i<myTempTable.length;i++)
        {
            if(myTempTable[i].key!=-1)
            insert(myTempTable[i].key,myTempTable[i].value);
        }
        }
               
        
        
    }
    

public class Hashing {
    public static long getUserTime( ) {
           ThreadMXBean bean = ManagementFactory.getThreadMXBean( );
	    return bean.isCurrentThreadCpuTimeSupported( ) ?
	        bean.getCurrentThreadUserTime( ) : 0L;
	}
    
       public static void main(String[] args) {
        // TODO code application logic here
        Scanner sc=new Scanner(System.in);
        Random rn=new Random();
        MyHashMap hMap=new MyHashMap();
        hMap.collisions = 0;
        //double startUserTimeNanos = getUserTime( );
        long startT=System.currentTimeMillis();
//
//        for(int i=0;i<(0.75*hMap.cap);i++)
//        {
//            int inp = rn.nextInt(100)+0;
//          //  System.out.println("key to be inserted is"+inp);
//            hMap.insert(inp, rn.nextInt(1000)+10);
//        }
     //   System.out.println("Collisions:"+hMap.collisions);
//        System.out.println("After inserting 25 values size is"+hMap.size);
//        System.out.println("After inserting 25 values capacity is"+hMap.cap);
//        
     //   System.out.println("No of collisions for load factor 0.75 is"+hMap.collisions);
     //  System.out.println("Inserting more elements . . . ");
        hMap.collisions=0;
        for(int i=0;i<150;i++)
        {
           // System.out.println("Size is:"+hMap.size+" and capacity is:"+hMap.cap);
            if(hMap.size == hMap.cap)
            {
                hMap.resize();
            }
            //System.out.println(i*5);
            hMap.insert(i*5, rn.nextInt(1000)+10);
        }
        long endT=System.currentTimeMillis();
      //  double taskUserTimeNano = getUserTime();
        System.out.println("User time: "+(endT- startT) +"  in mili seconds");
        System.out.println("No of collisions "+hMap.collisions);
        //System.out.println("Randomly deleting keys . . . ");
//        for(int i=0;i<25;i++)
//        {
//          //  System.out.println("Size is:"+hMap.size+" and capacity is:"+hMap.cap);
//            if(hMap.size <= hMap.cap/4)
//            {
//                //means load factor is <0.25 and now resizing should happen
//                System.out.println("Load factor is 1ow now and so resizing the Hash Table...");
//                hMap.resizeDel();
//                System.out.println("New capacity is"+hMap.myTable.length);
//            }
//            hMap.delete(rn.nextInt(100)+0);
//           
//        }
       // System.out.println("Searching"+hMap.search(5));
       // System.out.println("Searching"+hMap.search(10));  
       
       
       long startSearch=System.currentTimeMillis();
       System.out.println("Randomly searching keys . . . ");
       for(int i=0;i<10;i++)
        {
            System.out.println("Value found: "+hMap.search(i*5));
        }
        long endSearch=System.currentTimeMillis();
      //  double taskUserTimeNano = getUserTime();
        System.out.println("Search time: "+(endSearch- startSearch) +"  in mili seconds");
       
       
        System.out.println("Current size"+hMap.size);
        System.out.println("Current capacity"+hMap.cap);
        System.out.println("Current load factor value is:"+hMap.getLoadFactorValue());
        
          
        
        
    }
}
    
   
    
 
    

