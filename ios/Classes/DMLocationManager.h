//
//  DMLocationManager.h
//  iosmuncher
//
//  Created by DM on 11/6/10.
//  see /LICENSE
//

#import <Foundation/Foundation.h>
#import <CoreLocation/CoreLocation.h>
#import <MapKit/MapKit.h>
#import "JSON.h"
#import "DMDots.h"
#import "DMHeroAnnotation.h"
#import "DMHeroAnnotationView.h"
#define POSITION_EVENT 1
#define OHHAI_EVENT 2
#define POWER_PELLET_EVENT 3
#define COLLISION_EVENT 4
#define DOT_EATEN_EVENT 5

@interface DMLocationManager : NSObject <CLLocationManagerDelegate, MKMapViewDelegate> {
	CLLocationManager *locationManager;
	DMDots *dots;
	MKMapView *mapView;
	SBJsonWriter *jsonWriter;
	SBJsonParser *jsonParser;
	DMHeroAnnotation *heroAnnotation;
	DMHeroAnnotation *redGhostAnnotation;
	DMHeroAnnotation *pinkGhostAnnotation;
	DMHeroAnnotation *orangeGhostAnnotation;
	DMHeroAnnotation *greenGhostAnnotation;
	DMHeroAnnotationView *heroAnnotationView;
	NSUInteger lastReceivedEvent;
	NSUInteger gameID;
	NSUInteger phoneID;
	NSMutableDictionary *mapInfoDict;
	CLLocation *currentPlayerLocation;
}

@property (nonatomic, retain) CLLocationManager *locationManager;
@property (nonatomic, retain) MKMapView *mapView;
@property (nonatomic, retain) DMHeroAnnotation *heroAnnotation;
@property (nonatomic, retain) DMHeroAnnotation *redGhostAnnotation;
@property (nonatomic, retain) DMHeroAnnotation *pinkGhostAnnotation;
@property (nonatomic, retain) DMHeroAnnotation *orangeGhostAnnotation;
@property (nonatomic, retain) DMHeroAnnotation *greenGhostAnnotation;
@property (nonatomic, retain) DMHeroAnnotationView *heroAnnotationView;
@property (nonatomic, readwrite) NSUInteger lastReceivedEvent;

- (void)startTrackingCoordinates;
- (NSMutableDictionary *)serverUpdateWithLocation:(CLLocation *)newLocation gameID:(NSNumber *)game phoneID:(NSNumber *)phone lastReceivedEvent:(NSNumber *)lastEvent;

@end
