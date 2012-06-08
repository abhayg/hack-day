package com.flipkart.clusters;
import java.util.ArrayList;
 
/**
 *
 * @author Selcuk Orhan DEMIREL
 */
public class Main {
	
		public static double OFFSET = 268435456;
		public static double RADIUS = 85445659.4471;
 
      public static void main(String[] args) {
    	double[][] latlngPoints = {{12.969933,77.585077}, {12.976289,77.61117}, {12.927105,77.669706}};  
       // double[][] data = {{1d,2d},{2d,4d},{1d,1d},{0d,0d},{2d,5d}, {2d,15d}, {4d,20d}};
        double[][] data = convertToXY(latlngPoints);
        int numClusters = 2;
 
        Kmeans kmeans = new Kmeans(data, numClusters);
        //kmeans.setEpsilon(0.000001);
        kmeans.calculateClusters();
        
        ArrayList ret = new ArrayList();
        int i = 0;
 
        ArrayList[] clusters = kmeans.getClusters();
        for(ArrayList cluster: clusters)
        {
        	ArrayList outer = new ArrayList();
        	//System.out.println("CLUSTER");
            for(Object dat : cluster)
            {
            	double[] arr = new double[2];
            	int x = (int)((double[])dat)[2];
                //System.out.print(latlngPoints[x][0] + " "+ latlngPoints[x][1]);
                //System.out.println();
                arr[0] = latlngPoints[x][0];
            	arr[1] = latlngPoints[x][1];
                outer.add(arr);
                //System.out.println(arr[0] + "     " + arr[1]);
            }
            ret.add(outer);
        }
        
        //System.out.println(ret);
    }
      
      public static double lonToX(double lon) {
    	    return Math.round(OFFSET + RADIUS * lon * Math.PI / 180);        
    	}

      public static double latToY(double lat) {
    	    return Math.round(OFFSET - RADIUS * 
    	                Math.log((1 + Math.sin(lat * Math.PI / 180)) / 
    	                (1 - Math.sin(lat * Math.PI / 180))) / 2);
    	}  
      
      public static double[][] convertToXY(double[][] latLng){
    	  double data[][] = new double[latLng.length][3];
    	  for(int i = 0; i<latLng.length; i++){
    		  data[i][0] = latToY(latLng[i][0]);
    		  data[i][1] = lonToX(latLng[i][1]);
    		  data[i][2] = i;
    	  }
    	  
    	  return data;
      }
 
}