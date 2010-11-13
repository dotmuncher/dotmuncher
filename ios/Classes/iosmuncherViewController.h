//
//  iosmuncherViewController.h
//  iosmuncher
//
//  Created by DM on 11/6/10.
//  see /LICENSE
//

#import <UIKit/UIKit.h>
#import <MapKit/MapKit.h>
#import "DMLocationManager.h"

@interface iosmuncherViewController : UIViewController {
	IBOutlet MKMapView *mapView;
}

@property (nonatomic, retain) MKMapView	*mapView;



@end

