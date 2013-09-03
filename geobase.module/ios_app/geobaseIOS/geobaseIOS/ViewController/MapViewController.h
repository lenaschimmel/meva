//
//  MapViewController.h
//  geobaseIOS
//
//  Created by Tillmann Heigel on 19.06.13.
//  Copyright (c) 2013 Greenmobile Innovations. All rights reserved.
//

#import <MapKit/MapKit.h>
#import <UIKit/UIKit.h>
#import "MapsObject.h"

@interface MapViewController : UIViewController <MKMapViewDelegate>

@property (weak, nonatomic) IBOutlet MKMapView *mapView;
@property (weak, nonatomic) IBOutlet UIButton *markerButton;
@property (weak, nonatomic) IBOutlet UIImageView *crosshairs;

- (id)initWithMapsObject:(MapsObject *)mapsObject;
- (id)init;

#pragma mark MKMapViewDelegate overwrite methods

- (void)mapView:(MKMapView *)mapView didAddAnnotationViews:(NSArray *)views;


@end
