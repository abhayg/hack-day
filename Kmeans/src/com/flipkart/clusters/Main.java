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
    	double[][] latlngPoints = {{12.969933,77.585077}, {12.976289,77.61117}, {12.927105,77.669706}, {12.953539,77.700434}, 
    			{12.918405,77.609282}, {12.972777,77.649794}};  
       // double[][] data = {{1d,2d},{2d,4d},{1d,1d},{0d,0d},{2d,5d}, {2d,15d}, {4d,20d}};
        double[][] data = convertToXY(latlngPoints);
        int numClusters = 5;
 
        Kmeans kmeans = new Kmeans(data, numClusters);
        //kmeans.setEpsilon(0.000001);
        kmeans.calculateClusters();
 
        ArrayList[] clusters = kmeans.getClusters();
        for(ArrayList cluster: clusters)
        {
            System.out.println("CLUSTER");
            for(Object dat : cluster)
            {
            	int x = (int)((double[])dat)[2];
                System.out.print(latlngPoints[x][0] + " "+ latlngPoints[x][1]);
                System.out.println();
            }
        }
 
        System.out.println(kmeans.getClusterVars()[0]);
        System.out.println(kmeans.getClusterVars()[1]);
        System.out.println(kmeans.getTotalVar());
        System.out.println(kmeans.getClusterCenters()[0][0] + " " + kmeans.getClusterCenters()[0][1]);
        System.out.println(kmeans.getClusterCenters()[1][0] + " " + kmeans.getClusterCenters()[1][1]);
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
    	  double data[][] = new double[6][3];
    	  for(int i = 0; i<latLng.length; i++){
    		  data[i][0] = latToY(latLng[i][0]);
    		  data[i][1] = lonToX(latLng[i][1]);
    		  data[i][2] = i;
    	  }
    	  
    	  return data;
      }
 
}