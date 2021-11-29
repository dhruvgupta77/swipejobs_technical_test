package com.swipejobs.util;

public class DistanceCalculator {

	public static double calculateDistance(double lat1, double lat2, double lon1, double lon2, String unit) {

		// The math module contains a function named toRadians which converts from
		// degrees to radians.
		lon1 = Math.toRadians(lon1);
		lon2 = Math.toRadians(lon2);
		lat1 = Math.toRadians(lat1);
		lat2 = Math.toRadians(lat2);

		// Haversine formula
		double dlon = lon2 - lon1;
		double dlat = lat2 - lat1;
		double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);

		double c = 2 * Math.asin(Math.sqrt(a));
		double r = 6371; // default distance is assumed in km

		// Radius of earth in kilometers. Use 3956 for miles
		if ("km".equalsIgnoreCase(unit) || "kilometers".equalsIgnoreCase(unit))
			r = 6371;
		else if ("m".equalsIgnoreCase(unit) || "miles".equalsIgnoreCase(unit))
			r = 3956;

		// calculate the result
		return (c * r);
	}

	public static double calculateDistance(String lat1, String lat2, String lon1, String lon2, String unit) {
		double latitude1 = Double.parseDouble(lat1);
		double latitude2 = Double.parseDouble(lat2);
		double longitude1 = Double.parseDouble(lon1);
		double longitude2 = Double.parseDouble(lon2);
		return calculateDistance(latitude1, latitude2, longitude1, longitude2, unit);
	}

	public static void main(String[] args) {
		double lat1 = 53.32055555555556;
		double lat2 = 53.31861111111111;
		double lon1 = -1.7297222222222221;
		double lon2 = -1.6997222222222223;
		System.out.println(calculateDistance(lat1, lat2, lon1, lon2, "km") + " K.M");
	}

}
