//
//  DMLocationManager.m
//  iosmuncher
//
//  Created by DM on 11/6/10.
//  see /LICENSE
//

#import "DMLocationManager.h"
#import "DMHeroAnnotation.h"

@implementation DMLocationManager

NSString *apiURL = @"http://urban.pyxc.org/api/v0/";
NSString *debugURL = @"http://urban.pyxc.org/api/v0/debug.json";

@synthesize locationManager, mapView, heroAnnotation, redGhostAnnotation, orangeGhostAnnotation, pinkGhostAnnotation, greenGhostAnnotation, heroAnnotationView, lastReceivedEvent;

- (DMLocationManager *)init {
	jsonWriter = [SBJsonWriter new];
	jsonParser = [SBJsonParser new];
	heroAnnotation = [[[DMHeroAnnotation alloc] init] retain];
	redGhostAnnotation = [[[DMHeroAnnotation alloc] init] retain];
	pinkGhostAnnotation = [[[DMHeroAnnotation alloc] init] retain];
	orangeGhostAnnotation = [[[DMHeroAnnotation alloc] init] retain];
	greenGhostAnnotation = [[[DMHeroAnnotation alloc] init] retain];
	heroAnnotationView = nil;
	lastReceivedEvent = 0;
	[self beginGame];
	
	[self startTrackingCoordinates];
	return self;
}

- (void)startTrackingCoordinates {
    if (nil == locationManager)
        locationManager = [[CLLocationManager alloc] init];
	
    locationManager.delegate = self;
    locationManager.desiredAccuracy = kCLLocationAccuracyBestForNavigation; // will kill the battery :(
	
    [locationManager startUpdatingLocation];	
	[locationManager startUpdatingHeading];
		
}

// Delegate method from the CLLocationManagerDelegate protocol.
- (void)locationManager:(CLLocationManager *)manager
    didUpdateToLocation:(CLLocation *)newLocation
		   fromLocation:(CLLocation *)oldLocation
{
//	NSLog(@"latitude %+.6f, longitude %+.6f\n",
//			  newLocation.coordinate.latitude,
//			  newLocation.coordinate.longitude);
	[currentPlayerLocation release];
	currentPlayerLocation = [newLocation retain];
	/*
	if (phoneID == 0) {
		[heroAnnotation setCoordinate:newLocation.coordinate]; // update pacman
		[heroAnnotation setType:HERO_DOT_MUNCHER];
		[mapView addAnnotation:heroAnnotation];
	}
	if (phoneID == 1) {
		[redGhostAnnotation setCoordinate:newLocation.coordinate]; // update pacman
		[redGhostAnnotation setType:HERO_GHOST_RED];
		[mapView addAnnotation:redGhostAnnotation];
	}
	if (phoneID == 2) {
		[pinkGhostAnnotation setCoordinate:newLocation.coordinate]; // update pacman
		[pinkGhostAnnotation setType:HERO_GHOST_PINK];
		[mapView addAnnotation:pinkGhostAnnotation];
	}
	if (phoneID == 3) {
		[greenGhostAnnotation setCoordinate:newLocation.coordinate]; // update pacman
		[greenGhostAnnotation setType:HERO_GHOST_GREEN];
		[mapView addAnnotation:greenGhostAnnotation];
	}
	
	*/
	NSLog(@"hero %i location: latitude %+.6f, longitude %+.6f\n",
		  phoneID,
		  newLocation.coordinate,
		  newLocation.coordinate);	

	[mapView setCenterCoordinate:newLocation.coordinate animated:TRUE];
	
}

- (void)locationManager:(CLLocationManager *)manager didUpdateHeading:(CLHeading *)newHeading {
	[heroAnnotation setHeading:[newHeading trueHeading]];

	float heading = ([newHeading trueHeading] - 90) * M_PI / 180.0; // -90 for hero to look correct
	CGAffineTransform transform = CGAffineTransformMakeRotation(heading);
	if (heroAnnotationView != nil)
		heroAnnotationView.transform = transform;	
}

- (void)beginGame {
	NSString *jsonString = [[jsonWriter stringWithObject:[NSArray array]] stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding];

	NSString *requestURLString = [NSString stringWithFormat:@"%@find_games.json?json=%@", apiURL, [[NSString stringWithFormat:@"{\"lat\": 0, \"lng\": 0, \"phoneToken\": \"%@\"}", [[UIDevice currentDevice] uniqueIdentifier]] stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding]];
	NSURL *requestURL = [NSURL URLWithString:requestURLString];
	NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:requestURL];
	NSURLConnection *returnData = [NSURLConnection sendSynchronousRequest:request returningResponse:nil error:nil];	
	NSString *responseString = [[NSString alloc] initWithData:returnData encoding:NSUTF8StringEncoding];
	NSLog(@"%@", responseString);
	NSDictionary *returnDict = [jsonParser objectWithString:responseString];
	NSLog(@"%@", [returnDict description]);
	NSArray *gamesToJoin = [returnDict objectForKey:@"items"]; // put a ui for this eventually, for now just join most recent game
	NSDictionary *gameDict = [gamesToJoin objectAtIndex:0]; // for now

	NSNumber *newGameID = [gameDict objectForKey:@"id"];
//	gameID = [newGameID intValue]; // NOPE NOPE NOPE
	gameID = 21;
	NSNumber *newPhoneID = [returnDict objectForKey:@"phone"];
	phoneID = [newPhoneID intValue];
	
	
	/*
	
	requestURLString = [NSString stringWithFormat:@"%@new_game.json?json=%@", apiURL, [[NSString stringWithFormat:@"{\"map\": 1, \"phone\": %i}", phoneID] stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding]];
	requestURL = [NSURL URLWithString:requestURLString];
	request = [NSMutableURLRequest requestWithURL:requestURL];
	returnData = [NSURLConnection sendSynchronousRequest:request returningResponse:nil error:nil];
	responseString = [[NSString alloc] initWithData:returnData encoding:NSUTF8StringEncoding];
	NSLog(@"%@", responseString);
	returnDict = [jsonParser objectWithString:responseString];
	NSLog(@"%@", [returnDict description]);	
	
	*/
	
	requestURLString = [NSString stringWithFormat:@"%@join_game.json?json=%@", apiURL, [[NSString stringWithFormat:@"{\"game\": %i, \"phone\": %i}", gameID, phoneID] stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding]];
	requestURL = [NSURL URLWithString:requestURLString];
	request = [NSMutableURLRequest requestWithURL:requestURL];
	returnData = [NSURLConnection sendSynchronousRequest:request returningResponse:nil error:nil];
	responseString = [[NSString alloc] initWithData:returnData encoding:NSUTF8StringEncoding];
	NSLog(@"%@", responseString);
	returnDict = [jsonParser objectWithString:responseString];
	NSLog(@"%@", [returnDict description]);
	mapInfoDict = [[returnDict objectForKey:@"mapInfo"] retain];
	
	// start our network queue
	dispatch_async(dispatch_get_global_queue(0, 0), ^{
		BOOL networkThreadActive = YES;
		//TODO: this should sometimes not be yes
		
		while (networkThreadActive) {
			__block NSNumber *game;
			__block NSNumber *phone;
			__block NSNumber *lastEvent;
			__block CLLocation *currentLocation;
			dispatch_sync(dispatch_get_main_queue(), ^{
				game = [[NSNumber numberWithInt:gameID] retain];
				phone = [[NSNumber numberWithInt:phoneID] retain];
				lastEvent = [[NSNumber numberWithInt:lastReceivedEvent] retain];
				currentLocation = [currentPlayerLocation copy];
			});
			NSMutableDictionary *returnDict = [self serverUpdateWithLocation:currentLocation gameID:game phoneID:phone lastReceivedEvent:lastEvent];
			NSLog(@"got updated server locations");
			dispatch_sync(dispatch_get_main_queue(), ^{
				[self updateHeroPositions:returnDict];
			});
			[currentLocation autorelease];
			[NSThread sleepForTimeInterval:0.5];
		}
	});
}

- (NSMutableDictionary *)serverUpdateWithLocation:(CLLocation *)newLocation gameID:(NSNumber *)game phoneID:(NSNumber *)phone lastReceivedEvent:(NSNumber *)lastEvent {
	NSMutableDictionary *locationRequestDict = [NSMutableDictionary dictionaryWithCapacity:1];
	
	NSMutableArray *locationRequestArray = [NSMutableArray array];
	
	NSDictionary *locationDictionary = [NSDictionary dictionaryWithObjectsAndKeys:
										lastEvent,  @"id__gte",
										game,  @"game",
										phone,  @"phone",
										[NSString stringWithFormat:@"%+.6f", newLocation.coordinate.latitude],  @"lat",
										[NSString stringWithFormat:@"%+.6f", newLocation.coordinate.longitude], @"lng",
										[NSString stringWithFormat:@"%+.6f", newLocation.horizontalAccuracy],  @"hacc",
										[NSString stringWithFormat:@"%+.6f", newLocation.verticalAccuracy], @"vacc", nil ];
		
	NSString *requestURLString = [NSString stringWithFormat:@"%@update.json?json=%@", apiURL, [[jsonWriter stringWithObject:locationDictionary] stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding]];
	NSURL *requestURL = [NSURL URLWithString:requestURLString];
	NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:requestURL];

	/*
	NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:debugURL];

	NSData *locationData = [[NSString stringWithFormat:@"\"json\"=%@", [jsonWriter stringWithObject:locationRequestDict]] dataUsingEncoding:NSUTF8StringEncoding];
	NSLog(@"REQUEST: %@", [NSString stringWithFormat:@"json=%@", [jsonWriter stringWithObject:locationRequestDict]]);
	[request setHTTPMethod: @"POST"];
	[request setHTTPBody:locationData];
	 */
	// server wants GET requests for some reason, eh.

	
	NSData *returnData = [NSURLConnection sendSynchronousRequest:request returningResponse:nil error:nil];	
	NSString *returnString = [[NSString alloc] initWithData:returnData encoding:NSUTF8StringEncoding];
	NSLog(@"%@", returnString);
	NSMutableDictionary *returnDict = [jsonParser objectWithString:returnString];
	
	[returnString release];
	
	return returnDict;
}

- (void)updateHeroPositions:(NSMutableDictionary *)serverResponse {
	NSUInteger heroID = 0;
	for (NSDictionary *dict in [serverResponse objectForKey:@"phoneStates"]) {
		CLLocationCoordinate2D latlong = CLLocationCoordinate2DMake([[dict objectForKey:@"lat"] floatValue], [[dict objectForKey:@"lng"] floatValue]);
		if (heroID == 0) {
			NSLog(@"moved hero: latitude %+.6f, longitude %+.6f\n",
				  latlong.latitude,
				  latlong.longitude);	
			
			[heroAnnotation setCoordinate:latlong]; // update pacman
			[heroAnnotation setType:HERO_DOT_MUNCHER];
			[mapView addAnnotation:heroAnnotation];
		}
		if (heroID == 1) {
			NSLog(@"moved red: latitude %+.6f, longitude %+.6f\n",
				  latlong.latitude,
				  latlong.longitude);	
			
			[redGhostAnnotation setCoordinate:latlong]; // update pacman
			[redGhostAnnotation setType:HERO_GHOST_RED];
			[mapView addAnnotation:redGhostAnnotation];
		}
		if (heroID == 2) {
			NSLog(@"moved pink: latitude %+.6f, longitude %+.6f\n",
				  latlong.latitude,
				  latlong.longitude);	
			
			[pinkGhostAnnotation setCoordinate:latlong]; // update pacman
			[pinkGhostAnnotation setType:HERO_GHOST_PINK];
			[mapView addAnnotation:pinkGhostAnnotation];
		}
		if (heroID == 3) {
			NSLog(@"moved green: latitude %+.6f, longitude %+.6f\n",
				  latlong.latitude,
				  latlong.longitude);	
			
			[greenGhostAnnotation setCoordinate:latlong]; // update pacman
			[greenGhostAnnotation setType:HERO_GHOST_GREEN];
			[mapView addAnnotation:greenGhostAnnotation];
		}
		heroID++;
	}
//	lastReceivedEvent = [[serverResponse objectForKey:@"max_i"] intValue];
}

#pragma mark Map View Delegate methods

- (void)mapView:(MKMapView *)map regionDidChangeAnimated:(BOOL)animated
{
	//do stuff with annotations
}

- (MKAnnotationView *)mapView:(MKMapView *)map viewForAnnotation:(id <MKAnnotation>)annotation
{
	NSString *AnnotationViewID = [NSString stringWithFormat:@"heroID%i", [annotation type]];
	
    DMHeroAnnotationView *annotationView =
	(DMHeroAnnotationView *)[mapView dequeueReusableAnnotationViewWithIdentifier:AnnotationViewID];
    if (annotationView == nil)
    {
        annotationView = [[[DMHeroAnnotationView alloc] initWithAnnotation:annotation reuseIdentifier:AnnotationViewID] autorelease];
    }
    
    annotationView.annotation = annotation;
	
	if ([annotation type] == HERO_DOT_MUNCHER)
		heroAnnotationView = annotationView;
	
    return annotationView;
}


@end
