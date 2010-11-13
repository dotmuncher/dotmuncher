//
//  DMHeroAnnotation.h
//  iosmuncher
//
//  Created by DM on 11/6/10.
//  see /LICENSE
//

#import <UIKit/UIKit.h>
#import <MapKit/MapKit.h>

#define HERO_DOT_MUNCHER 0
#define HERO_GHOST_RED 1
#define HERO_GHOST_ORANGE 2
#define HERO_GHOST_PINK 3
#define HERO_GHOST_GREEN 4

@interface DMHeroAnnotation : NSObject <MKAnnotation> {
    CLLocationCoordinate2D coordinate;
	CLLocationDirection heading;
	NSUInteger type;
}

@property (nonatomic, readwrite) CLLocationCoordinate2D coordinate;
@property (nonatomic, readwrite) CLLocationDirection heading;
@property (nonatomic, readwrite) NSUInteger type;

@end
