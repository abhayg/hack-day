// Kmeans.java
 
package com.flipkart.clusters;

import java.util.Random;
import java.util.ArrayList;
 
/**
 *
 * @author Selcuk Orhan DEMIREL
 *
 */
 
public class  Kmeans{
 
    private double[][] data;         // data to cluster
    private int numClusters;    // number of clusters
    private double[][] clusterCenters;   // cluster centers
    private int dataSize;               // size of the data
    private int dataDim;                // dimention of the data
    private ArrayList[] clusters;     // calculated clusters
    private double[] clusterVars;        // cluster variances
 
    private double epsilon;
 
    public Kmeans(double[][] data, int numClusters, double[][] clusterCenters)
    {
        dataSize = data.length;
        dataDim = data[0].length;
 
        this.data = data;
 
        this.numClusters = numClusters;
 
        this.clusterCenters = clusterCenters;
 
        clusters = new ArrayList[numClusters];
         for(int i=0;i<numClusters;i++)
        {
            clusters[i] = new ArrayList();
        }
        clusterVars = new double[numClusters];
 
        epsilon = 0.01;
    }
 
    public Kmeans(double[][] data, int numClusters)
    {
        this(data, numClusters, true);
    }
 
    public Kmeans(double[][] data, int numClusters, boolean randomizeCenters)
    {
        dataSize = data.length;
        dataDim = 2;
 
        this.data = data;
 
        this.numClusters = numClusters;
 
        this.clusterCenters =  new double[numClusters][dataDim];
 
        clusters = new ArrayList[numClusters];
        for(int i=0;i<numClusters;i++)
        {
            clusters[i] = new ArrayList();
        }
        clusterVars = new double[numClusters];
 
        epsilon = 0.01;
 
        if(randomizeCenters)
        {
            randomizeCenters(numClusters, data);
        }
    }
 
    private void randomizeCenters(int numClusters, double[][] data) {
        Random r = new Random();
        int[] check = new int[numClusters];
        for (int i = 0; i < numClusters; i++) {
            int rand = r.nextInt(dataSize);
            if (check[i] == 0) {
                this.clusterCenters[i] = data[rand].clone();
                check[i] = 1;
            } else {
                i--;
            }
        }
    }
 
    private void calculateClusterCenters()
    {
        for(int i=0;i<numClusters;i++)
        {
            int clustSize = clusters[i].size();
 
            for(int k= 0; k < dataDim; k++)
            {
 
                double sum = 0d;
                for(int j =0; j < clustSize; j ++)
                {
                    double[] elem = (double[]) clusters[i].get(j);
                    sum += elem[k];
                }
 
                clusterCenters[i][k] = sum / clustSize;
            }
        }
    }
 
     private void calculateClusterVars()
    {
        for(int i=0;i<numClusters;i++)
        {
            int clustSize = clusters[i].size();
            Double sum = 0d;
 
                for(int j =0; j < clustSize; j ++)
                {
 
                    double[] elem = (double[])clusters[i].get(j);
 
                    for(int k= 0; k < dataDim; k++)
                    {
                        sum += Math.pow( (Double)elem[k] - getClusterCenters()[i][k], 2);
                    }
                }
 
            clusterVars[i] = sum / clustSize;
        }
    }
 
     public double getTotalVar()
    {
         double total = 0d;
         for(int i=0;i< numClusters;i++)
         {
             total += clusterVars[i];
         }
 
         return total;
     }
 
     public double[] getClusterVars()
    {
        return  clusterVars;
    }
 
     public ArrayList[] getClusters()
    {
         return clusters;
    }
 
     private void assignData()
    {
        for(int k=0;k<numClusters;k++)
        {
            clusters[k].clear();
        }
        //System.out.println(dataSize);
        for(int i=0; i<dataSize; i++)
        {
 
        	//System.out.println("Adding data val idx="+i);
            int clust = 0;
            double dist = Double.MAX_VALUE;
            double newdist = 0;
 
            for(int j=0; j<numClusters; j++)
            {
                newdist = distToCenter( data[i], j );
                if( newdist <= dist )
                {
                    clust = j;
                    dist = newdist;
                }
            }
            //System.out.println("Adding data val idx="+i+" to cluster idx="+clust);
            clusters[clust].add(data[i]);
        }
 
    }
 
     private double distToCenter( double[] datum, int j )
    {
         double sum = 0d;
         for(int i=0;i < dataDim; i++)
         {
             sum += Math.pow(( datum[i] - getClusterCenters()[j][i] ), 2);
         }
 
         return Math.sqrt(sum);
    }
 
      public void calculateClusters()
    {
 
         double var1 = Double.MAX_VALUE;
         double var2;
         double delta;
 
        do
        {
              calculateClusterCenters();
              assignData();
              calculateClusterVars();
              var2 = getTotalVar();
              if (Double.isNaN(var2))    // if this happens, there must be some empty clusters
             {
                 delta = Double.MAX_VALUE;
                 randomizeCenters(numClusters, data);
                 assignData();
                 calculateClusterCenters();
                 calculateClusterVars();
             }
             else
             {
                 delta = Math.abs(var1 - var2);
                 var1 = var2;
             }
 
        }while(delta > epsilon);
    }
 
    public void setEpsilon(double epsilon)
    {
        if(epsilon > 0)
        {
            this.epsilon = epsilon;
        }
    }
 
    /**
     * @return the clusterCenters
     */
    public double[][] getClusterCenters() {
        return clusterCenters;
    }
}
    