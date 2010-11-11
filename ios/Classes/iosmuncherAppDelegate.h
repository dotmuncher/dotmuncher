//
//  iosmuncherAppDelegate.h
//  iosmuncher
//
//  Created by DM on 11/6/10.
//  see /iosmuncher/LICENSE
//

#import <UIKit/UIKit.h>

@class iosmuncherViewController;

@interface iosmuncherAppDelegate : NSObject <UIApplicationDelegate> {
    UIWindow *window;
    iosmuncherViewController *viewController;
}

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic, retain) IBOutlet iosmuncherViewController *viewController;

@end

