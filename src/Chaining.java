/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Darshan
 */
import java.util.*;
import java.lang.management.ThreadMXBean;
import java.util.*;
import java.lang.management.ManagementFactory;



class NodeC
{
    int key;
    int value;
    NodeC(int k, int v)
    {
        key=k;
        value=v;
    }
    NodeC(){

    }
}
class chainMap
{
    int bucketSize;
    int n;
    int collisions;
    LinkedList<NodeC> bucketList[];
    chainMap(int x)
    {
        bucketSize=x;
        collisions=0;
        n=0;
        bucketList = new LinkedList[bucketSize];
        for(int i=0;i<bucketList.length;i++)
        {
            bucketList[i]=new LinkedList<NodeC>();
        }
        
    }
    
    int hashFunction(int inpKey)
    {
        return (inpKey % bucketSize);
    }    

    void insertIntoBucket(int k, int v)
    {
        n++;
        int location = hashFunction(k);
        NodeC tempNode=new NodeC(k,v);
        if(!bucketList[location].isEmpty())
        {
            collisions++;
        }
        bucketList[location].addLast(tempNode);
        //inserting
        //System.out.println("Inserted key"+k);
    }
    
    void deleteKey(int inpKey)
    {
        int i=0;
        int location = hashFunction(inpKey);
        for(i=0;i<bucketList[location].size();i++)
        {
            if(bucketList[location].get(i).key == inpKey)
            {
                break;
            }
        }
        if(i != bucketList[location].size())
        {
            System.out.println("Deleting key "+inpKey);
            bucketList[location].remove(i);
            n--;
        }
        else
        {
            System.out.println("Element to be deleted is not found. . .");
        }
    }
    
    int search(int k)
    {
        int location = hashFunction(k);
        for(int i=0;i<bucketList[location].size();i++)
        {
            if(bucketList[location].get(i).key == k)
            {
                return bucketList[location].get(i).value;
            }
        }
        
        System.out.println("Element to be searched is not found. . .");
        return -1;
        
    }
    
    void printHashMap()
    {
        for(int i=0;i<bucketList.length;i++)
        {
            System.out.println("Keys present at bucket "+(i));
            for(int j=0;j<bucketList[i].size();j++)
            {
                System.out.print(bucketList[i].get(j).key+"\t");
            }
            System.out.println();
        }
    }
    
    void reSize()
    {
       // System.out.println("Bucket size before resizing"+bucketSize);
        LinkedList<NodeC> tempArray[]=new LinkedList[bucketSize];
        for(int i=0;i<tempArray.length;i++)
        {
            tempArray[i]=bucketList[i];
        }
        int y=collisions;
        int x=n;
        bucketSize*=2;
        bucketList=new LinkedList[bucketSize];
        for(int i=0;i<bucketList.length;i++)
        {
            bucketList[i]=new LinkedList<NodeC>();
        }
        collisions=0;
        for(int i=0;i<tempArray.length;i++)
        {
               for(int j=0;j<tempArray[i].size();j++)
                {
                //    System.out.println("Inserting key"+tempArray[i].get(j).key);
                    insertIntoBucket(tempArray[i].get(j).key, tempArray[i].get(j).value);
                    
                }
            
        }
        n=x;
    }
    void reSizeDel()
    {
        LinkedList<NodeC> tempArray[]=new LinkedList[bucketSize];
        System.arraycopy(bucketList, 0, tempArray, 0, tempArray.length);
        bucketSize/=2;
        bucketList=new LinkedList[bucketSize];
         for(int i=0;i<bucketList.length;i++)
        {
            bucketList[i]=new LinkedList<NodeC>();
        }
        for(int i=0;i<tempArray.length;i++)
        {
            
                for(int j=0;j<tempArray[i].size();j++)
                {
                    
                        insertIntoBucket(tempArray[i].get(j).key, tempArray[i].get(j).value);
                    
                }
            
        }
    }
    
    double loadFactor()
    {
        return n/(double)bucketSize;
    }
   
}
public class Chaining {
     
    public static long getUserTime( ) {
           ThreadMXBean bean = ManagementFactory.getThreadMXBean( );
	    return bean.isCurrentThreadCpuTimeSupported( ) ?
	        bean.getCurrentThreadUserTime( ) : 0L;
	}
   
    public static void main(String args[])
    {
        Scanner sc=new Scanner(System.in);
        chainMap cMap=new chainMap(97);
        Random rn=new Random();
        long stI = System.currentTimeMillis();
        for(int i=0;i<(100000);i++)
        {
            if(cMap.bucketSize==cMap.n)
            {
                cMap.reSize();
            }
            cMap.insertIntoBucket(rn.nextInt(100)+0, rn.nextInt(100)+0);
        }
        System.out.println("Total number of collisions  "+cMap.collisions);
          long etI = System.currentTimeMillis();
           System.out.println("User time for Insertion: "+(etI- stI) +"  in mili seconds");
        
        for(int i=0;i<50;i++)
        {
            
            cMap.deleteKey(rn.nextInt(30)+0);
            
        }
        
        
        System.out.println("Load factor after deleting 50 keys "+cMap.loadFactor());
        
        
        long startSearch=System.currentTimeMillis();
       System.out.println("Randomly searching keys . . . ");
       for(int i=0;i<10;i++)
        {
            System.out.println("Value found: "+cMap.search(rn.nextInt(100)+0));
        }
        long endSearch=System.currentTimeMillis();
      //  double taskUserTimeNano = getUserTime();
        System.out.println("Search time: "+(endSearch- startSearch) +"  in mili seconds");
        //cMap.printHashMap();
        if(cMap.loadFactor() < 0.25)
        {
            System.out.println("Resizing for delete. . .");
            cMap.reSizeDel();
            System.out.println("No of elements"+cMap.n);
            System.out.println("No of Buckets"+cMap.bucketSize);
        }
        
        
       
    }
    
}
