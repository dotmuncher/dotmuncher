//
//  DMHeroAnnotationView.m
//  iosmuncher
//
//  Created by DM on 11/6/10.
//  see /LICENSE
//

#import "DMHeroAnnotationView.h"
#import "DMHeroAnnotation.h"

@implementation DMHeroAnnotationView


- (id)initWithAnnotation:(id <MKAnnotation>)annotation reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithAnnotation:annotation reuseIdentifier:reuseIdentifier];
    if (self != nil)
    {
        CGRect frame = self.frame;
        frame.size = CGSizeMake(50.0, 50.0);
        self.frame = frame;
        self.backgroundColor = [UIColor clearColor];
		UIImageView *animatedHero = [[UIImageView alloc] initWithFrame:self.frame];
		animatedHero.animationDuration = 0.2;
		switch ((NSUInteger)[annotation type]) {
			case HERO_DOT_MUNCHER:
				animatedHero.animationImages = [NSArray arrayWithObjects:[UIImage imageNamed:@"pacman_chomp1.png"], [UIImage imageNamed:@"pacman_chomp3.png"], nil];
				break;
			case HERO_GHOST_RED:
				animatedHero.animationImages = [NSArray arrayWithObjects:[UIImage imageNamed:@"ghost_red1_eyes.png"], [UIImage imageNamed:@"ghost_red2_eyes.png"], nil];
				break;
			case HERO_GHOST_ORANGE:
				animatedHero.animationImages = [NSArray arrayWithObjects:[UIImage imageNamed:@"ghost_orange1_eyes.png"], [UIImage imageNamed:@"ghost_orange2_eyes.png"], nil];
				break;
			case HERO_GHOST_PINK:
				animatedHero.animationImages = [NSArray arrayWithObjects:[UIImage imageNamed:@"ghost_pink1_eyes.png"], [UIImage imageNamed:@"ghost_pink2_eyes.png"], nil];
				break;
			case HERO_GHOST_GREEN:
				animatedHero.animationImages = [NSArray arrayWithObjects:[UIImage imageNamed:@"ghost_green1_eyes.png"], [UIImage imageNamed:@"ghost_green2_eyes.png"], nil];
				break;
		}
		[animatedHero startAnimating];
		[self addSubview:animatedHero];
			
    }
    return self;
}

@end
