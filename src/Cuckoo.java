/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Darshan
 */
//class cNode
//    {
//        int key;
//        int value;
//        cNode(int k, int v)
//        {
//            key=k;
//            value=v;
//        }
//       cNode(){
//            
//        }
//    }
import java.util.*;
import java.util.*;
import java.lang.management.ThreadMXBean;
import java.util.*;
import java.lang.management.ManagementFactory;

class cuckooHashing
{
    int n;
    int bSize;
    int cTable[][];
    int possLoc[];
    int collisions;
    cuckooHashing(int bucketSize)
    {
        bSize=bucketSize;
        collisions=0;
        cTable=new int[2][bSize];
        n=0;
        possLoc=new int[2];
    }
    
    void initVal()
    {
        for(int i=0;i<2;i++)
        {
            for(int j=0;j<bSize;j++)
            {
                cTable[i][j] = -999;
            }
        }
        
    }
    
    int h1(int inpKey)
    {
        return inpKey % bSize;
    }
    
    int h2(int inpKey)
    {
        return (inpKey / bSize) % bSize;
    }
    
    void reHashCuckoo(int num)
    {
        int tempCopy[][]=new int[2][bSize];
        for(int i=0;i<2;i++)
        {
            for(int j=0;j<bSize;j++)
            {
                tempCopy[i][j]=cTable[i][j];
            }
        }
        
        bSize*=2;
        cTable=new int[2][bSize];
        initVal();
        collisions=0;
        for(int i=0;i<2;i++)
        {
            for(int j=0;j<tempCopy[i].length;j++)
            {
                if(tempCopy[i][j]!=-999)
                {
                    insertCuckoo(tempCopy[i][j],0, 0, num);
                }
            }
        }
      //  System.out.println("Collisions:"+collisions);
       // bSize*=2;
    }
    
    void insertCuckoo(int k, int tNo, int count, int ns)
    {
        
        if(count==ns)
        {
            System.out.println("Cycle is present. Hence, Rehashing.");
            //System.out.println("Resizing the table . . .");
            reHashCuckoo(ns);
            return;
        }
        
        possLoc[0]=h1(k);
        possLoc[1]=h2(k);
        
        for(int i=0;i<cTable.length;i++)
        {
            if(cTable[i][possLoc[i]]==k)
            {
                return;
            }
        }
        /// -999 is to check if it is empty or not
        if(cTable[tNo][possLoc[tNo]]!=-999)
        {
            if(tNo==0)
            collisions++;
            int displacedKey=cTable[tNo][possLoc[tNo]]; 
            cTable[tNo][possLoc[tNo]]=k;
            insertCuckoo(displacedKey,((tNo+1)%2),(count+1),ns);
        }
        else
        {
            cTable[tNo][possLoc[tNo]]=k;
        }
        
     //   System.out.println("Key"+k);
      
    }
    
    int getSize()
    {
        int count=0;
         for(int i=0;i<2;i++)
        {
            for(int j=0;j<bSize;j++)
            {
               if(cTable[i][j]!=-999)
               {
                   count++;
               }
            }

        }
        return count;
    }
    void printCuckoo()
    {
        for(int i=0;i<2;i++)
        {
            for(int j=0;j<bSize;j++)
            {
                System.out.print(cTable[i][j]+" ");
            }
            System.out.println();
        }
    }
    
    void cDelete(int x)
    {
        possLoc[0]=h1(x);
        possLoc[1]=h2(x);
        
        if(cTable[0][possLoc[0]]==x)
        {
            cTable[0][possLoc[0]]=-999;
            n--;
        }
        else if(cTable[1][possLoc[1]]==x)
        {
            cTable[1][possLoc[1]]=-999;
            n--;
        }
        else
        {
            System.out.println("Deletion failed as key not present. . .");
        }
        
    }  
    
}
public class Cuckoo {
    
    public static void main(String args[])
    {
        Random rn=new Random();
        cuckooHashing cuMap=new cuckooHashing(97);
        cuMap.initVal();
        System.out.println("Inserting elements . . .");
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
        cuMap.collisions=0;
        for(int i=0;i<1000;i++)
        {
           // System.out.println("Size is:"+hMap.size+" and capacity is:"+hMap.cap);
            if(cuMap.bSize == cuMap.getSize())
            {
                cuMap.reHashCuckoo(1000);
            }
            cuMap.insertCuckoo(rn.nextInt(1000)+0, 0, 0, 1000);
        }
        long endT=System.currentTimeMillis();
      //  double taskUserTimeNano = getUserTime();
        System.out.println("User time: "+(endT- startT) +"  in mili seconds");
     //   System.out.println("No of collisions "+cuMap.collisions);


//                
       // System.out.println("Cuckoo Size is:"+cuMap.getSize());
       // cuMap.printCuckoo();
        cuMap.cDelete(3);
        cuMap.cDelete(6);
      //  System.out.println("Size is:"+cuMap.getSize());
        System.out.println("Bucket Size is:"+cuMap.bSize);
//        
        
        
        
        
    }
    
}
