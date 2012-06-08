package com.flipkart.clusters;
import java.util.ArrayList;


public class Clusters {
	
	public static double OFFSET = 268435456;
	public static double RADIUS = 85445659.4471;

  public ArrayList getClusters(double[][] latlngPoints, int numClusters) {
    double[][] data = convertToXY(latlngPoints);
    ArrayList ret = new ArrayList();
    int i = 0;
    
    Kmeans kmeans = new Kmeans(data, numClusters);
    //kmeans.setEpsilon(0.000001);
    kmeans.calculateClusters();

    ArrayList[] clusters = kmeans.getClusters();
    for(ArrayList cluster: clusters)
    {
        ArrayList outer = new ArrayList();
    	for(Object dat : cluster)
        {
        	double[] arr = new double[2];
    		int x = (int)((double[])dat)[2];
        	arr[0] = latlngPoints[x][0];
        	arr[1] = latlngPoints[x][1];
        	outer.add(arr);
        }
    	ret.add(outer);
    }
    //System.out.println(ret);
    return ret;
}
  
  public double lonToX(double lon) {
	    return Math.round(OFFSET + RADIUS * lon * Math.PI / 180);        
	}

  public double latToY(double lat) {
	    return Math.round(OFFSET - RADIUS * 
	                Math.log((1 + Math.sin(lat * Math.PI / 180)) / 
	                (1 - Math.sin(lat * Math.PI / 180))) / 2);
	}  
  
  public double[][] convertToXY(double[][] latLng){
	  double data[][] = new double[latLng.length][3];
	  for(int i = 0; i<latLng.length; i++){
		  data[i][0] = latToY(latLng[i][0]);
		  data[i][1] = lonToX(latLng[i][1]);
		  data[i][2] = i;
	  }
	  
	  return data;
  }

}
